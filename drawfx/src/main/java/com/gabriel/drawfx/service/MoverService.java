package com.gabriel.drawfx.service;

import com.gabriel.drawfx.model.Shape;

import java.awt.*;

public final class MoverService {
    public void  move(Shape shape, Point newLoc){

        Point currentLoc = shape.getLocation();
        int dx = newLoc.x - currentLoc.x;
        int dy = newLoc.y - currentLoc.y;

        Point newEnd = new Point(shape.getEnd().x + dx, shape.getEnd().y + dy);
        
        // Original line not changed
        shape.setLocation( newLoc);
        
        shape.setEnd(newEnd);
     }
}
