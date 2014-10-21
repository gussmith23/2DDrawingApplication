package us.justg.gus.java.drawingApplication;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class DrawingApplication extends JFrame {
    
    
    // Row 1
    JPanel row1;
    JButton undoButton;
    JButton clearButton;
    JLabel shapeLabel;
    JComboBox<String> shapeChooser;
    JCheckBox filledCheckBox;
    
    // Row 2
    JPanel row2;
    JCheckBox gradientCheckBox;
    JButton color1Button;
    JButton color2Button;
    JLabel widthLabel;
    JTextField widthTextField;
    JLabel dashLengthLabel;
    JTextField dashLengthTextField;
    JCheckBox dashedCheckBox;
    
    // Row 3 (Drawing Pane)
    DrawPanel drawPane;
    
    // Row 4 (Coordinates label)
    JPanel row4;
    JLabel coords;
    

    public DrawingApplication() {
        
        super("Java 2D Drawings");
        
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        // Outer layout will be box.
        getContentPane().setLayout(new BoxLayout(getContentPane(),BoxLayout.PAGE_AXIS));
        
        // Create row 1.
        row1 = new JPanel(new FlowLayout());
        undoButton = new JButton("Undo");
        clearButton = new JButton("Clear");
        shapeLabel = new JLabel("Shape:");
        shapeChooser = new JComboBox<String>();
        filledCheckBox = new JCheckBox("Filled");
        row1.add(undoButton);
        row1.add(clearButton);
        row1.add(shapeLabel);
        row1.add(shapeChooser);
        row1.add(filledCheckBox);
        
        // Create row 2.
        row2 = new JPanel(new FlowLayout());
        gradientCheckBox = new JCheckBox("Use Gradient");
        color1Button = new JButton("1st Color...");
        color2Button = new JButton("2nd Color...");
        widthLabel = new JLabel("Line Width:");
        widthTextField = new JTextField(2);
        dashLengthLabel = new JLabel("Dash Length:");
        dashLengthTextField = new JTextField(2);
        dashedCheckBox = new JCheckBox("Dashed");
        row2.add(gradientCheckBox);
        row2.add(color1Button);
        row2.add(color2Button);
        row2.add(widthLabel);
        row2.add(widthTextField);
        row2.add(dashLengthLabel);
        row2.add(dashLengthTextField);
        row2.add(dashedCheckBox);
        
        // Create row 3.
        drawPane = new DrawPanel(new Dimension(630, 365));
        
        // Create row 4.
        row4 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        coords = new JLabel("Coords");
        row4.add(coords);
        
        
        add(row1);
        add(row2);
        add(drawPane);
        add(row4);
        pack();
        setVisible(true);
        
    }
    
    public static void main(String[] args) {
        new DrawingApplication();
    }
    
    
    private class DrawPanel extends JPanel {

        public DrawPanel(Dimension d) {

            // Initialization stuff.
            super();
            setPreferredSize(d);
            setBackground(Color.white);

        }
    
    
    
}
    
}
