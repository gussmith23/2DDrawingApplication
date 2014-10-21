/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package us.justg.gus.java.drawingApplication;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import javax.swing.JComponent;
import us.justg.gus.java.drawingApplication.Line;
import us.justg.gus.java.drawingApplication.Oval;
import us.justg.gus.java.drawingApplication.Rectangle;

/**
 *
 * @author hfs5022
 */
public abstract class Shape extends JComponent {
    
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
    
    @Override
    public abstract void paintComponent(Graphics g);
    
    // Takes two points and returns the top left and bottom right points.
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
