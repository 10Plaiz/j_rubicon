package com.gabriel.draw.service;

import com.gabriel.draw.model.Ellipse;
import com.gabriel.drawfx.model.Shape;
import com.gabriel.drawfx.service.RendererService;

import java.awt.*;

public class EllipseRendererService implements RendererService {

    @Override
    public void render(Graphics g, Shape shape, boolean xor) {
        Ellipse ellipse = (Ellipse) shape;

        if (xor) {
            g.setXORMode(Color.WHITE);
            g.setColor(ellipse.getColor());
        } else {
            g.setPaintMode();
            g.setColor(ellipse.getColor());
        }

        Point start = ellipse.getLocation();
        Point end = ellipse.getEnd();

        int x = Math.min(start.x, end.x);
        int y = Math.min(start.y, end.y);
        int width = Math.abs(end.x - start.x);
        int height = Math.abs(end.y - start.y);

        g.drawOval(x, y, width, height);
    }
}