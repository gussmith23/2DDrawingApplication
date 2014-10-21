package us.justg.gus.java.drawingApplication;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class DrawingApplication extends JFrame {
    
    // Constants
    private final int DEFAULT_WIDTH = 5;
    private final int DEFAULT_DASH_LENGTH = 10;
    private final Color DEFAULT_COLOR_1 = Color.CYAN;
    private final Color DEFAULT_COLOR_2 = Color.GREEN;
    
    //-ROW 1--------------------------------------------------------------------
    JPanel row1;
    JButton undoButton;
    JButton clearButton;
    JLabel shapeLabel;
    JComboBox<String> shapeChooser;
    JCheckBox filledCheckBox;
    
    //-ROW 2--------------------------------------------------------------------
    JPanel row2;
    JCheckBox gradientCheckBox;
    JButton color1Button; Color color1;
    JButton color2Button; Color color2;
    JLabel widthLabel;
    JTextField widthTextField;
    JLabel dashLengthLabel;
    JTextField dashLengthTextField;
    JCheckBox dashedCheckBox;
    
            
    //-ROW 3--------------------------------------------------------------------
    DrawPanel drawPane;
    
    //-ROW 4--------------------------------------------------------------------
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
        shapeChooser = new JComboBox(new String[]{"Rectangle","Oval","Line"});
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
        // Set text  box defaults.
        widthTextField.setText("" + DEFAULT_WIDTH);
        dashLengthTextField.setText("" + DEFAULT_DASH_LENGTH);
        // Tie dashed checkbox to its textbox.
        dashLengthTextField.setEditable(dashedCheckBox.isSelected());
        dashedCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dashLengthTextField.setEditable(dashedCheckBox.isSelected());
            }
        });
        
        color1Button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        
        // Set color defaults.
        color1 = DEFAULT_COLOR_1;
        color2 = DEFAULT_COLOR_2;
        color1Button.setBackground(color1);
        color2Button.setBackground(color2);
        
        // Set color button listeners.
        color1Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                color1 = JColorChooser.showDialog(new JFrame(), "Pick a color.", DEFAULT_COLOR_1);
                if (color1 == null) color1 = DEFAULT_COLOR_1;
                color1Button.setBackground(color1);
            }
        });
        color2Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                color2 = JColorChooser.showDialog(new JFrame(), "Pick a color.", DEFAULT_COLOR_2);
                if (color2 == null) color2 = DEFAULT_COLOR_2;
                color2Button.setBackground(color2);
            }
        });
        
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
        setResizable(false);
        setVisible(true);
        
    }
    
    public static void main(String[] args) {
        new DrawingApplication();
    }
    
    
    private class DrawPanel extends JPanel {
        
        ArrayList<Shape> shapes;

        public DrawPanel(Dimension d) {

            // Initialization stuff.
            super();
            setPreferredSize(d);
            setBackground(Color.white);
            
            shapes = new ArrayList<>();
            
            DrawPanelMouseListener listener = new DrawPanelMouseListener();
            addMouseListener(listener);
            addMouseMotionListener(listener);
            
            undoButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(shapes.size()>0) shapes.remove(shapes.size()-1);
                    repaint();
                }
            });
            
            clearButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    shapes = new ArrayList<>();
                    repaint();
                }
            });

        }
        
        private boolean addObject(String type, Point start) {
            
            Shape shapeToAdd = null;
            
            // Parse ints from text boxes.
            int width, dashLength;
            
            try {
                width = Integer.parseInt(widthTextField.getText());
            } catch (NumberFormatException e) {
                width = DEFAULT_WIDTH;
                widthTextField.setText("");
            }
            
            try {
                dashLength = Integer.parseInt(dashLengthTextField.getText());
            } catch (NumberFormatException e) {
                dashLength = DEFAULT_DASH_LENGTH;
                dashLengthTextField.setText("");
            }
            
            // Running checks on the text field inputs.
            width = (width >= 0) ? width : DEFAULT_WIDTH;
            dashLength = (dashLength >= 1) ? dashLength : DEFAULT_DASH_LENGTH;
            widthTextField.setText("" + width);
            dashLengthTextField.setText("" + dashLength);
            
            try {
                Class c = Class.forName("us.justg.gus.java.drawingApplication." + type);
                Constructor constructor = c.getConstructors()[0];
                shapeToAdd = (Shape) constructor.newInstance(start, start, 
                            filledCheckBox.isSelected(), 
                            gradientCheckBox.isSelected(), color1, 
                            color2, Integer.parseInt(widthTextField.getText()), 
                            Integer.parseInt(dashLengthTextField.getText()), 
                            dashedCheckBox.isSelected());
            } catch (ClassNotFoundException e) {
                return false;
            } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                return false;
            }
            
            
            
            
            /*
            switch (type){
                case "Rectangle":
                    shapeToAdd = new Rectangle(start, start, 
                            filledCheckBox.isSelected(), 
                            gradientCheckBox.isSelected(), Color.black, 
                            Color.black, Integer.parseInt(widthTextField.getText()), 
                            Integer.parseInt(dashLengthTextField.getText()), 
                            dashedCheckBox.isSelected());
                    break;
                case "Oval":
                    shapeToAdd = new Oval(start, start, 
                            filledCheckBox.isSelected(), 
                            gradientCheckBox.isSelected(), Color.black, 
                            Color.black, Integer.parseInt(widthTextField.getText()), 
                            Integer.parseInt(dashLengthTextField.getText()), 
                            dashedCheckBox.isSelected());
                    break;
                case "Line":
                    shapeToAdd = new Line(start, start, 
                            filledCheckBox.isSelected(), 
                            gradientCheckBox.isSelected(), Color.black, 
                            Color.black, Integer.parseInt(widthTextField.getText()), 
                            Integer.parseInt(dashLengthTextField.getText()), 
                            dashedCheckBox.isSelected());
                    break;                
            }*/
            
            shapes.add(shapeToAdd);
            
            return true;
        }
        
        
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            
            setBackground(Color.white);
            for(Shape shape:shapes) {
                shape.paintComponent(g);
            }
        }
        
        private class DrawPanelMouseListener implements MouseListener, MouseMotionListener{
            
            boolean drawing = false;

            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                addObject((String)shapeChooser.getSelectedItem(), e.getPoint());
                drawing = true;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                drawing = false;
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
                
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                if(drawing) {
                    // Get the last shape.
                    Shape shape = shapes.get(shapes.size()-1);
                    // Update with end point.
                    shape.setEnd(e.getPoint());
                    
                    repaint();
                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                coords.setText(String.format("(%d, %d)", e.getX(), e.getY()));
            }
            
            
        }
    
    
    
}
    
}
