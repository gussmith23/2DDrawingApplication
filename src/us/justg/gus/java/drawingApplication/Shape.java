package us.justg.gus.java.drawingApplication;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import javax.swing.JComponent;

/**
 * Shape represents the items to be drawn in the DrawPanel. This abstract class
 * contains a general constructor which initializes the shape's properties, bec-
 * ause all three shapes use the exact same set of properties. Additionally, it
 * contains a general paintComponent method which sets up the Graphics2D object
 * for the subclasses to use when painted. 
 * 
 * @author hfs5022
 */
public abstract class Shape extends JComponent {
    
    // Shape properties.
    Point start;
    Point end;
    boolean filled;
    boolean gradient;
    Color color1;
    Color color2;
    int lineWidth;
    int dashLength;
    boolean dashed;

    public Shape(Point start, Point end, boolean filled, boolean gradient, Color color1, Color color2, int lineWidth, int dashLength, boolean dashed) {
        this.start = start;
        this.end = end;
        this.filled = filled;
        this.gradient = gradient;
        this.color1 = color1;
        this.color2 = color2;
        this.lineWidth = lineWidth;
        this.dashLength = dashLength;
        this.dashed = dashed;
    }
    
    /**
     * This generalized paintComponent class sets up the Graphics2D object for 
     * the calling subclass to use. This class is useful because all shapes have
     * the same properties, which are applied in the same ways to the Graphics 2D
     * object.
     * 
     * @param g 
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // Cast the Graphics object.
        Graphics2D g2 = (Graphics2D) g;
                
        // Create a dashed stroke, if dashed.
        if (dashed) {
            g2.setStroke(
                    new BasicStroke(
                            lineWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL,
                            10.0f, new float[]{(float)dashLength}, 0.0f)
            );
        } else {
            g2.setStroke( 
                    new BasicStroke(
                            lineWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL)
            );
            
        }
        
        // Create a gradient paint, if gradient.
        if(gradient) {
            g2.setPaint(
                    new GradientPaint(start, color1, end, color2)
            );
        } else {
            g2.setPaint(color1);
        }
        
    }
    
    // Helper function - takes two points and returns the top left and bottom
    //      right points.
    public Point[] findTopLeft(Point start, Point end) {
        Point newStart = new Point(Math.min(start.x, end.x),
                Math.min(start.y, end.y));
        Point newEnd = new Point(newStart.x + Math.abs(start.x - end.x),
                newStart.y + Math.abs(start.y - end.y));
        
        return new Point[]{newStart, newEnd};
    }

    public Point getStart() {
        return start;
    }

    public void setStart(Point start) {
        this.start = start;
    }

    public Point getEnd() {
        return end;
    }

    public void setEnd(Point end) {
        this.end = end;
    }

    public boolean isFilled() {
        return filled;
    }

    public void setFilled(boolean filled) {
        this.filled = filled;
    }

    public boolean isGradient() {
        return gradient;
    }

    public void setGradient(boolean gradient) {
        this.gradient = gradient;
    }

    public Color getColor1() {
        return color1;
    }

    public void setColor1(Color color1) {
        this.color1 = color1;
    }

    public Color getColor2() {
        return color2;
    }

    public void setColor2(Color color2) {
        this.color2 = color2;
    }

    public int getLineWidth() {
        return lineWidth;
    }

    public void setLineWidth(int lineWidth) {
        this.lineWidth = lineWidth;
    }

    public int getDashLength() {
        return dashLength;
    }

    public void setDashLength(int dashLength) {
        this.dashLength = dashLength;
    }

    public boolean isDashed() {
        return dashed;
    }

    public void setDashed(boolean dashed) {
        this.dashed = dashed;
    }    
}
