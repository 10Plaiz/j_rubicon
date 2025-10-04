package com.gabriel.drawfx.model;

import com.gabriel.drawfx.service.RendererService;
import lombok.Data;

import java.awt.*;
@Data
public abstract class Shape {
    int id;
    private Point location;
    //private Point end;
    int width;
    int height;
    private Color color;
    private Color fill;
    private RendererService rendererService;
    private boolean selected = false;
    public Shape(Point location){
        this.setLocation(location);
        width = 0;
        height = 0;
    }
    public Shape(Point location, Point endpoint){
        this.setLocation(location);
        width = endpoint.x - location.x;
        height = endpoint.y - location.y;
    }
    public Shape(Point location, int width, int height){
        this.setLocation(location);
        this.width = width;
        this.height = height;
    }
    
    public boolean contains(Point point) {
        int x = location.x;
        int y = location.y;
        int w = width;
        int h = height;
        
        // Handle negative dimensions
        if (w < 0) {
            x += w;
            w = Math.abs(w);
        }
        if (h < 0) {
            y += h;
            h = Math.abs(h);
        }
        
        return point.x >= x && point.x <= x + w && 
               point.y >= y && point.y <= y + h;
    }
}
