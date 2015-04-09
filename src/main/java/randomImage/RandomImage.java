package randomImage;

import ij.ImagePlus;
import ij.gui.GUI;
import ij.gui.NewImage;
import ij.plugin.frame.PlugInFrame;
import ij.process.ByteProcessor;

import java.awt.Button;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.TextEvent;
import java.awt.event.TextListener;

public class RandomImage extends PlugInFrame implements ActionListener,
        TextListener {
    private TextField txt_width;
    private TextField txt_height;
    private Button btn;

    public RandomImage() {
        super("RandomImage ");

        btn = new Button("Make a Random Image");
        btn.setEnabled(false);

        setLayout(new FlowLayout());

        txt_width = new TextField("0");
        txt_height = new TextField("0");

        txt_width.addTextListener(this);
        txt_height.addTextListener(this);

        btn.addActionListener(this);
        add(btn);
        add(txt_width);
        add(txt_height);
        pack();
        GUI.center(this);
        setMinimumSize(new Dimension(400, 50));
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        int width = Integer.parseInt(txt_width.getText());
        int height = Integer.parseInt(txt_height.getText());

        ImagePlus imagePlus = NewImage.createByteImage("Signal", width, height,
                1, NewImage.FILL_WHITE);
        ByteProcessor iproc = (ByteProcessor) imagePlus.getProcessor();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height ; j++) {
                iproc.set(i, j, (int) Math.floor(Math.random() * 255));
            }
        }

        imagePlus.show();

    }

    @Override
    public void textValueChanged(TextEvent arg0) {

        if (isValid(txt_width) && isValid(txt_height)) {
            btn.setEnabled(true);
        } else {
            btn.setEnabled(false);
        }
    }

    private boolean isValid(TextField src) {
        String txt = src.getText();
        int value;
        try {
            value = Integer.parseInt(txt);
        } catch (NumberFormatException e) {
            return false;
        }

        return value > 0 && value <= 512;
    }
}
