/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package us.justg.gus.java.drawingApplication;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

/**
 *
 * @author hfs5022
 */
public class Line extends Shape {

    public Line(Point start, Point end, boolean filled, boolean gradient, Color color1, Color color2, int lineWidth, int dashLength, boolean dashed) {
        super(start, end, filled, gradient, color1, color2, lineWidth, dashLength, dashed);
    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawLine(start.x, start.y, end.x, end.y);
    }
    
    
    
}
