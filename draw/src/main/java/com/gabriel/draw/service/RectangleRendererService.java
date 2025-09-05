package com.gabriel.draw.service;

import com.gabriel.draw.model.Rectangle;
import com.gabriel.drawfx.model.Shape;
import com.gabriel.drawfx.service.RendererService;

import java.awt.*;

public class RectangleRendererService implements RendererService {
    @Override
    public void render(Graphics g, Shape shape, boolean xor) {
        Rectangle rectangle = (Rectangle) shape;

        int x = shape.getLocation().x;
        int y = shape.getLocation().y;
        int width = shape.getEnd().x-shape.getLocation().x;
        int height = shape.getEnd().y-shape.getLocation().y;
        if(width < 0) {
            x = shape.getEnd().x;
            width = -width;
        }
        if(height < 0) {
            y = shape.getEnd().y ;
            height = -height;
        }

        if(xor) {
            g.setXORMode(shape.getColor());
        } else {
            // Draw fill first if fill color is set
            if (shape.getFill() != null) {
                g.setColor(shape.getFill());
                g.fillRect(x, y, width, height);
            }
            
            // Draw outline
            g.setColor(shape.getColor());
        }

        g.drawRect(x, y, width, height);
    }
}
