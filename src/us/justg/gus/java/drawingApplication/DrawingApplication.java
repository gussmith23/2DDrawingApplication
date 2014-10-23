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
    //-end row 1----------------------------------------------------------------
    
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
    //-end row 2----------------------------------------------------------------
            
    //-ROW 3--------------------------------------------------------------------
    DrawPanel drawPane;
    //-end row 3----------------------------------------------------------------
    
    //-ROW 4--------------------------------------------------------------------
    JPanel row4;
    JLabel coords;
    //-end row 4----------------------------------------------------------------

    public DrawingApplication() {
        
        // Various important initialization functions.
        super("Java 2D Drawings");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setLayout(new BoxLayout(getContentPane(),BoxLayout.PAGE_AXIS));
        
        //-Create row 1---------------------------------------------------------
        // Initializations.
        row1 = new JPanel(new FlowLayout());
        undoButton = new JButton("Undo");
        clearButton = new JButton("Clear");
        shapeLabel = new JLabel("Shape:");
        shapeChooser = new JComboBox(new String[]{"Rectangle","Oval","Line"});
        filledCheckBox = new JCheckBox("Filled");
        // Adding to panel.
        row1.add(undoButton);
        row1.add(clearButton);
        row1.add(shapeLabel);
        row1.add(shapeChooser);
        row1.add(filledCheckBox);
        //-End create row 1-----------------------------------------------------
        
        //-Create row 2---------------------------------------------------------
        // Initializations.
        row2 = new JPanel(new FlowLayout());
        gradientCheckBox = new JCheckBox("Use Gradient");
        color1Button = new JButton("1st Color...");
        color2Button = new JButton("2nd Color...");
        widthLabel = new JLabel("Line Width:");
        widthTextField = new JTextField(2);
        dashLengthLabel = new JLabel("Dash Length:");
        dashLengthTextField = new JTextField(2);
        dashedCheckBox = new JCheckBox("Dashed");
        
        // Setting defaults.
        widthTextField.setText("" + DEFAULT_WIDTH);
        dashLengthTextField.setText("" + DEFAULT_DASH_LENGTH);
        dashLengthTextField.setEditable(dashedCheckBox.isSelected());
        color1 = DEFAULT_COLOR_1;
        color2 = DEFAULT_COLOR_2;
        color1Button.setBackground(color1);
        color2Button.setBackground(color2);
        
        
        //Adding listeners.
        dashedCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // When we're not making dashed strokes, disable dash length input.
                dashLengthTextField.setEditable(dashedCheckBox.isSelected());
            }
        });
        color1Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Creating a frame and getting the response.
                color1 = JColorChooser.showDialog(new JFrame(), "Pick a color.", DEFAULT_COLOR_1);
                if (color1 == null) color1 = DEFAULT_COLOR_1;
                color1Button.setBackground(color1);
            }
        });
        color2Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Creating a frame and getting the response.
                color2 = JColorChooser.showDialog(new JFrame(), "Pick a color.", DEFAULT_COLOR_2);
                if (color2 == null) color2 = DEFAULT_COLOR_2;
                color2Button.setBackground(color2);
            }
        });
        
        // Adding to panel.
        row2.add(gradientCheckBox);
        row2.add(color1Button);
        row2.add(color2Button);
        row2.add(widthLabel);
        row2.add(widthTextField);
        row2.add(dashLengthLabel);
        row2.add(dashLengthTextField);
        row2.add(dashedCheckBox);
        //-End create row 2-----------------------------------------------------

        
        
        //-Create row 3---------------------------------------------------------
        drawPane = new DrawPanel(new Dimension(630, 365));
        //-End create row 3-----------------------------------------------------
        
        //-Create row 4---------------------------------------------------------
        row4 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        coords = new JLabel("Coords");
        row4.add(coords);
        //-End create row 4-----------------------------------------------------

        // Add all four rows.
        add(row1);
        add(row2);
        add(drawPane);
        add(row4);
        
        // Resize, lock, and show the window.
        pack();
        setResizable(false);
        setVisible(true);
        
    }
    
    // Main class simply opens a new window.
    public static void main(String[] args) {
        new DrawingApplication();
    }
    
    /**
     * The DrawPanel class represents the drawing panel contained within the ma-
     * in DrawingApplication window. The bulk of its code implements listeners
     * on the panel, which respond to user interaction.
     */
    private class DrawPanel extends JPanel {
        
        // Shapes with have been drawn. This list will be redrawn on repaint().
        ArrayList<Shape> shapes;

        public DrawPanel(Dimension d) {

            // Initialization stuff.
            super();
            setPreferredSize(d);
            setBackground(Color.white);
            shapes = new ArrayList<>();
            
            //-Listeners--------------------------------------------------------
            // The custom DrawPanelMouseListener listens for drawing events.
            DrawPanelMouseListener listener = new DrawPanelMouseListener();
            addMouseListener(listener);
            addMouseMotionListener(listener);
            
            // The clear/undo actions are defined here, as they must have access
            //      to the shapes ArrayList.
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
            //-End listeners----------------------------------------------------
        }
        
        /**
         * addObject converts an inputted string to a class, and gets the const-
         * ructor of this class. It then passes to this constructor all of the
         * parameters currently defined in the DrawingApplication object's fields.
         * 
         * @param type      The type of shape to create. Refer to subclasses of Shape. 
         * @param start     The first point defined for this shape.
         * @return 
         */
        private boolean addObject(String type, Point start) {
            
            Shape shapeToAdd = null;
            
            //-Parse ints from text boxes---------------------------------------
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
            //-End parse ints from text boxes-----------------------------------
            
            //-Instantiate object from "type" variable--------------------------
            try {
                // Get the class object reffered to by type.
                Class c = Class.forName("us.justg.gus.java.drawingApplication." + type);
                // Pull the constructor.
                Constructor constructor = c.getConstructors()[0];
                // Instantiate a new object using the current settings.
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
            //-End instantiate object from "type" variable----------------------

            // Add shape to list
            shapes.add(shapeToAdd);
            
            return true;
        }
        
        /**
         * paintComponent for the DrawPanel simply draws all shapes in shapes.
         * @param g 
         */
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            
            // Paint all shapes.
            for(Shape shape:shapes) {
                shape.paintComponent(g);
            }
        }
        
        /**
         * DrawPanelMouseListener handles the more technical listeners needed for
         * the DrawPanel class.
         */
        private class DrawPanelMouseListener implements MouseListener, MouseMotionListener{
            
            // Indicates whether or not the user is currently drawing an object.
            boolean drawing = false;

            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                // We add a new object, which currently has the same start/end point.
                //      NOTE: while this creates a shape without size (start/end
                //      points are the same), the shape will start growing pretty
                //      much immediately, as the mouseDragged() event is called
                //      as soon as the mouse moves after the initial press.
                addObject((String)shapeChooser.getSelectedItem(), e.getPoint());
                // The user has begun to draw.
                drawing = true;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                // The user has stopped drawing.
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
                
                // Update coords.
                updateCoords(e.getPoint());
                
                if(drawing) {
                    // Get the last shape added to the list, which is the shape
                    //      currently being drawn.
                    Shape shape = shapes.get(shapes.size()-1);
                    // Update with end point.
                    shape.setEnd(e.getPoint());
                    
                    // Call repaint liberally...
                    repaint();
                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                updateCoords(e.getPoint());
            }
            
            // Updates the coords at the bottom of the screen. 
            private void updateCoords(Point newCoords) {
                coords.setText(String.format("(%d, %d)", newCoords.x, newCoords.y));
            }
            
        }
    
}
}
