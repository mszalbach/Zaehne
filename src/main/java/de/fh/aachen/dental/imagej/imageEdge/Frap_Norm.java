package de.fh.aachen.dental.imagej.imageEdge;

import ij.*;
import ij.gui.GUI;
import ij.gui.GenericDialog;
import ij.gui.ImageWindow;
import ij.gui.Roi;
import ij.measure.Measurements;
import ij.measure.ResultsTable;
import ij.plugin.PlugIn;
import ij.plugin.frame.PlugInFrame;
import ij.process.ImageProcessor;
import ij.process.ImageStatistics;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.ImageProducer;
import java.net.URL;

/** This is a plugin to automatize measurements and calculations
 * for FRAP analysis. It is written originally for analysis of chromatin dynamics in 
 * Arabidopsis thaliana, and uses the normalization methods outlined in : 
 * Phair et al (2004), 
 * Measurement of dynamic protein binding to chromatin in vivo using photobleaching microscopy, 
 * Methods Enzymol 375, 393-414.
 * Both single and double normalization can be carried out.
 * 
 * Areas are pointed out by the user, and automatically outlined in three steps. First is a median filter
 * applied. Next, the edges are detected using the Canny-deriche filter followed by hysteresis
 * tresholding (both filters by Thomas Boudier).
 * 
 * The plugin returns a delimited text file with the raw measurements, the calculated
 * pre and post intensities, and the normalized values. These can be used to determine
 * the FRAP parameters using the appropriate software.
 * !!!THIS PLUGIN REQUIRES de.fh.aachen.dental.imagej.imageEdge.ImageEdge TO BE INSTALLED IN THE SAME DIRECTORY OR JAR-FILE!!!
 * @author Joris FA Meys
 * @Created 6 feb 2009
 * @version 1.1 - resultstable added
 * */

public class Frap_Norm implements PlugIn{
	private ImagePlus img, prebleach, postbleach; // holds the prebleach-postbleach slice
	private ImageStack stack;
	private ImageWindow current;
	private ImageProcessor ippre, ippost; // image processors for stack and slices
	private int preref = 1; // holds the slice number for the prebleach slice
	private int postref = 2; // holds the slice number for the postbleach slice
	private Region frap, ref, base, whole;
	private float alphaD = (float) 0.5;
	private float upperTreshold = (float) 50;
	private float lowerTreshold = (float) 100;
	private double radius = 5;
	private int nSlices = 1;
	private ResultsTable rtable = new ResultsTable(), normtable = new ResultsTable();
	private boolean doublenorm = true, autocalc = false, image = false;
	private int[] time;
	private int interval=1;
	float wholepre, frappre;
	

	/** main method
	 * */
	public void run(String arg){
		if(IJ.versionLessThan("1.36"))IJ.error("Version 1.36 or higher required.\n" +
				"Please update your ImageJ."); // make sure right version is used
		
		if(arg.equals("about")){ 
			showAbout();
			return;
		} // end if
		
		if(arg.equals("Pic")){
			showPic();
			return;
		}
		else{
			image = getNewImage();
			
			// Define the arrays and the display
			frap = new Region("FRAP region",nSlices);
			ref = new Region("Reference",nSlices);
			base = new Region("Background",nSlices);
			whole = new Region("Whole cell",nSlices);
			Display display = new Display(); // creating new frame
			GUI.center(display);
			display.setVisible(true);
			
			if (image)displayGuidance(img);	
		}
		
	} // END run FRAP_Analysis

	/**Searches for the current open image. Returns true if found
	 * false if otherwise
	 * */
	private boolean getNewImage() {
		img= WindowManager.getCurrentImage();
		current = WindowManager.getCurrentWindow();
		if (img==null){
			IJ.showMessage("No images open.");
			return false;
		}
		nSlices =img.getStackSize(); 
		if(nSlices<2){
			IJ.error("Stack required");
			return false;
		} // end if
		time = new int[nSlices];
		return true;
		
	}

	/**Displays the guidance pictures for fast identification of the 
	 * different regions*/
	private void displayGuidance(ImagePlus img){
		stack = img.getStack();
		ippre = ImageEdge.areaEdge(stack.getProcessor(preref),
									radius,alphaD,upperTreshold,lowerTreshold);
		prebleach = new ImagePlus("Pre Bleach",ippre);
		prebleach.show();
		
		ippost = ImageEdge.areaEdge(stack.getProcessor(postref),
				radius,alphaD,upperTreshold,lowerTreshold);
		postbleach=new ImagePlus("Post Bleach", ippost);
		postbleach.show();			
	} // END displayGuidance

@SuppressWarnings("serial")
	/**Creates the interface
	 * */
	private class Display extends PlugInFrame implements ActionListener, ItemListener {
	
		Frame window;
		Panel normpanel, autopanel, resetpanel; // for the normalization menu
		Choice norm, auto;
		
		public Display(){
	
			super("FRAP Analysis"); // call construction for the PlugInFrame
			if (window != null){	// prevents construction twice
				window.toFront(); 
				return;
			} // end if
					
			window = this;
			setLayout(new GridLayout(10,1,5,5));
						
			// choice menus
			
			normpanel = new Panel();
			Choice norm = new Choice();
			normpanel.add(new Label("Normalization :"));
			norm.add("Double");
			norm.add("Single");
			norm.addItemListener(this);
			normpanel.add(norm);
			
			autopanel = new Panel();
			Choice auto = new Choice();
			autopanel.add(new Label("Measurement :"));
			auto.add("Manual");
			auto.add("Automatic");
			auto.addItemListener(this);
			autopanel.add(auto);
			
			// reset panel
			resetpanel=new Panel();
			resetpanel.setLayout(new GridLayout(1,2));
			Button resetbutton = new Button("Reset");
			resetbutton.addActionListener(this);
			Button imagebutton = new Button("New image");
			imagebutton.addActionListener(this);
			resetpanel.add(resetbutton);
			resetpanel.add(imagebutton);
			
			// window construction
			window.add(frap.panel);
			window.add(base.panel);
			window.add(whole.panel);
			window.add(ref.panel);
			add(autopanel);
			add(normpanel);
			addButton("Normalize");
			add(resetpanel);
			addButton("Settings");
			addButton("Help");
			pack();
			
		} // END constructor Display
				
		public void windowClosing(WindowEvent e) {
			super.windowClosing(e);
			window = null; // resets the window
		} // END windowClosing Display
	
		private void addButton(String label) {
			Button b = new Button(label);
			b.addActionListener(this);
			window.add(b);
		} // END addButton Display
	
		public void actionPerformed(ActionEvent e){
			String action = e.getActionCommand();
			if (action.equals("Normalize")){
				if(image) Normalize();
			}
			else if (action.equals("Reset")) Reset();
			else if (action.equals("Settings")) Settings();
			else if (action.equals("Help")) showAbout();
			else if (action.equals("New image")){
				if(image){
					prebleach.close();
					postbleach.close();
				}
				image = getNewImage();
				if(image){
					Reset();
					displayGuidance(img);
				}
				
			}
		} // END actionPerformed Display

		public void itemStateChanged(ItemEvent e) {
			String item = (String) e.getItem();
			if (item.equals("Double")) doublenorm=true;
			if (item.equals("Single")) doublenorm=false;
			if (item.equals("Manual")) autocalc=false;
			if (item.equals("Automatic")) autocalc=true;	
		}
		
	} // END Display

	/**Sets up the arrays and the methods for the different regions, and links
	 * the buttons on the panel to those regions. 
	 * */
	private class Region implements ActionListener{
		Panel panel;
		Button measurebutton, applybutton, setbutton;
		Roi roi;
		float [] intensity, area;
		int [] slice;
		String label;
		
		public Region(String label, int length){
			this.label = label;
			this.intensity = new float[length];
			this.area = new float[length];
			this.slice = new int[length];
			panel = new Panel();
			panel.setLayout(new GridLayout(1,4));
			panel.add(new Label(label+" :"));
			
			setbutton = new Button ("Set ROI");
			setbutton.addActionListener(this);
			applybutton = new Button ("Apply to Image");
			applybutton.addActionListener(this);
			measurebutton = new Button("Measure");
			measurebutton.addActionListener(this);
			panel.add(setbutton);
			panel.add(applybutton);
			panel.add(measurebutton);
			roi = null;	
		} // END constructor Region
	
		public void actionPerformed(ActionEvent e) {
			String action = e.getActionCommand();
			if(image){
				if (action.equals("Set ROI")) this.roi = this.roiSet();
				else if(action.equals("Apply to Image")) this.Apply(roi);
				else if(action.equals("Measure"))this.imgMeasure();
			}
			else IJ.showMessage("No images open. \nOpen an image and click \"New image\".");	
		} // END actionPerformed Region
		
		private Roi roiSet(){
			ImagePlus img = getImage();
			if (img==null)return null;
			Roi roi = img.getRoi();
			if (roi==null) {
				IJ.showMessage("No ROI selected");
				return null;}
			return roi;
		}
		private void Apply(Roi roi){
			WindowManager.setCurrentWindow(current);
			ImagePlus img = getImage();
			if (img==null)return; 
			else if(roi==null){
				IJ.showMessage("No ROI available. Set ROI first");
				return;
			}
			img.setRoi(roi);
		}
		
		private void imgMeasure(){
			ImagePlus img = getImage();
			if (img==null) return;
			Roi roi = img.getRoi();
			if(autocalc){
				for(int i=1; i <= nSlices; i++){
					img.setSlice(i);
					Measure(img,roi);
				}
				img.setSlice(1);
				return;
			}
			else Measure(img, roi);
		}
		
		private void Measure(ImagePlus slice, Roi roi){
			ImageProcessor ip = img.getProcessor();
			ip.setRoi(roi);
			ImageStatistics stats = ImageStatistics.getStatistics(ip,Measurements.MEAN+Measurements.AREA,null );
			int index = img.getCurrentSlice()-1;
			this.slice[index]=index+1;
			this.area[index]=(float) stats.area;
			this.intensity[index]=(float) stats.mean;
			
			displayResults(label,index+1,stats.area,stats.mean);
		}
		
		private ImagePlus getImage() {
			ImagePlus imp = WindowManager.getCurrentImage();
			if (imp==null) {
				IJ.showMessage("There are no images open.");
				return null;
			} else
				return imp;
		}

	} // END private abstract class Region
		
	/**Allows to change the settings of the plugin*/
	private void Settings() {
		GenericDialog gd = new GenericDialog("Parameters");
		gd.addNumericField("median filter radius",radius,2);
		gd.addNumericField("Deriche alpha value",alphaD,2);
		gd.addNumericField("Hysteresis High threshold", upperTreshold, 2);
		gd.addNumericField("Hysteresis Low threshold", lowerTreshold, 2);
		gd.addNumericField("Pre Bleaching slice", preref, 0);
		gd.addNumericField("Post Bleaching slice", postref, 0);
		gd.addNumericField("Time units between slides",interval,0);
		gd.showDialog();
		radius = gd.getNextNumber();
		alphaD = (float) gd.getNextNumber();
		upperTreshold = (float) gd.getNextNumber();
		lowerTreshold = (float) gd.getNextNumber();
		preref = (int)gd.getNextNumber();
		postref = (int)gd.getNextNumber();
		interval=(int)gd.getNextNumber();
		Reset();
	}

	public void displayResults(String label, int slice, double area, double mean) {
		rtable.incrementCounter();
		rtable.addLabel("Region", label);
		rtable.addValue("Slice", slice);
		rtable.addValue("Area", area);
		rtable.addValue("Intensity",mean);
		rtable.show("Measurements");
	}

	public void Normalize(){
		
		float[] frapnorm = new float[nSlices];
		frappre = 0;
		wholepre = 0;
		normtable.reset();
		// set time intervals
		for(int i=0;i<nSlices;i++){
			time[i]=(i+1-postref)*interval;
		}
		
		// Calculate average intensities prebleach
		for(int i=0;i<postref-1;i++){
			wholepre += (whole.intensity[i]-base.intensity[i]);
			frappre += (frap.intensity[i]-base.intensity[i]);
		}
		wholepre = wholepre/preref;
		frappre = frappre/preref;
		
		// Calculate single normalization
		for(int i=0;i<nSlices;i++){
			frapnorm[i]=(frap.intensity[i] - base.intensity[i])/frappre;
		}
		if(doublenorm){
			for(int i=0;i<nSlices;i++){
				frapnorm[i]=frapnorm[i]*(wholepre/(whole.intensity[i]-base.intensity[i]));
			}
		}
		
		// display results
		for(int i=0;i<nSlices;i++){
			normtable.incrementCounter();
			normtable.addLabel(" ","Intensities for slice "+(i+1)+" :");
			normtable.addValue("Normalized",frapnorm[i]);
			normtable.addValue("Frap",frap.intensity[i]);
			normtable.addValue("Whole",whole.intensity[i]);
			normtable.addValue("Base",base.intensity[i]);
			normtable.addValue("time", time[i]);
			if(ref.intensity != null) normtable.addValue("Ref",ref.intensity[i]);
			
		}	
		normtable.show("Normalization Results");
	}
	
	public void Reset(){
		setNull(frap);
		setNull(base);
		setNull(ref);
		setNull(whole);
		rtable.reset();
	}
	
	private void setNull(Region reg) {
		reg.area = new float[nSlices];
		reg.intensity=new float[nSlices];
		reg.slice=new int[nSlices];
		
		
	}

	private void showAbout(){ // About message
		IJ.showMessage("About FRAP_Norm",
				"This plugin is specifically constructed to extract data from stacks of images\n"+
				"for FRAP analysis. It implements a median filter, deriche filter and subsequent\n"+
				"hysteresis to find the outline of the different regions in the cell. Parameters\n"+
				"for this filters can be put in the settings, together with the time interval and\n" +
				"the slice numbers for the prebleach and postbleach guidance images.\n \n" +
				"It is written originally for analysis of chromatin dynamics in  Arabidopsis thaliana,\n"+
				"and uses the normalization methods outlined in Phair et al (2004):\n"+ 
				"Measurement of dynamic protein binding to chromatin in vivo using photobleaching \n" +
				"microscopy, Methods Enzymol 375, 393-414.\n"+
				"Both single and double normalization can be carried out.\n \n"+
				"Procedure :\n" +
				"Use the ROI-tools, e.g. the wand tool or the rectangular tool to select\n"+
				"a region of interest. Use the \"Set ROI\" button next to the corresponding region\n"+
				"to save the ROI for that region. After defining the different ROIs, select the slice\n" +
				"you want to measure and press \"Apply to Image\" for a region. This will show up\n" +
				"the ROI for that region. Adjust the ROI by dragging it if necessary, and press\n"+
				"the \"Measure\" button. This will show up the measurement in the results window.\n \n"+
				"When all measurements are done, you can press the \"Normalize\" button to normalize\n"+
				"the measurements. Normalization is done as outlined in Phair et al(2004), and can be\n"+
				"done with (double) or without (single) the use of the whole cell measurements.\n" +
				"Measurement of the reference area is not obliged for normalization.\n \n"+
				"Use the \"Reset\" button to erase all measurements.Use the \"New image\" button after\n" +
				"loading a new image for resetting all.Using this button will not erase the setted ROIs! \n \n" +
				"---------------------------------------------------------------------------------------\n \n"+
				"written by Joris FA Meys (2009). More info : jorismeys@gmail.com\n" +
				"Canny-deriche and hysteresis plugins written by Thomas Boudier\n"+
				"This plugin is part of the public domain");
	} // End ShowAbout FRAP_Analysis
	
	private void showPic() {
		ImageJ ij = IJ.getInstance();
		
		URL url = this.getClass().getResource("/aboutFA.jpg");
		if (url!=null) {
			Image img = null;
			try {img = ij.createImage((ImageProducer)url.getContent());}
			catch(Exception e) {}
			if (img!=null) {
				ImagePlus imp = new ImagePlus("", img);
				ImageWindow.centerNextImage();
				imp.show();
			}
		}
		else showAbout();
	}

}

