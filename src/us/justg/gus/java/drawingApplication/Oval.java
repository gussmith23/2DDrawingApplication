/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package us.justg.gus.java.drawingApplication;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

/**
 *
 * @author hfs5022
 */
public class Oval extends Shape {

    public Oval(Point start, Point end, boolean filled, boolean gradient, Color color1, Color color2, int lineWidth, int dashLength, boolean dashed) {
        super(start, end, filled, gradient, color1, color2, lineWidth, dashLength, dashed);
    }

    @Override
    public void paintComponent(Graphics g) {
        
        Graphics2D g2 = (Graphics2D) g;
        
        // Finding the point in the top left
        Point[] newPoints = findTopLeft(start, end);
        
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
        
        if(filled) {
            g2.fillOval(newPoints[0].x, newPoints[0].y, newPoints[1].x-newPoints[0].x, newPoints[1].y-newPoints[0].y);
        } else {
            g2.drawOval(newPoints[0].x, newPoints[0].y, newPoints[1].x-newPoints[0].x, newPoints[1].y-newPoints[0].y);
        }
    }
    
    
    
}
