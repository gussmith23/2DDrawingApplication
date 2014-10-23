package us.justg.gus.java.drawingApplication;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

public class Oval extends Shape {

    public Oval(Point start, Point end, boolean filled, boolean gradient, Color color1, Color color2, int lineWidth, int dashLength, boolean dashed) {
        super(start, end, filled, gradient, color1, color2, lineWidth, dashLength, dashed);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Graphics2D g2 = (Graphics2D) g;
        
        // Finding the point in the top left
        Point[] newPoints = findTopLeft(start, end);
        
        if(filled) {
            g2.fillOval(newPoints[0].x, newPoints[0].y, newPoints[1].x-newPoints[0].x, newPoints[1].y-newPoints[0].y);
        } else {
            g2.drawOval(newPoints[0].x, newPoints[0].y, newPoints[1].x-newPoints[0].x, newPoints[1].y-newPoints[0].y);
        }
    }
    
    
    
}
