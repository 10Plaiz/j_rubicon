package com.gabriel.draw.service;

import com.gabriel.draw.model.Rectangle;
import com.gabriel.drawfx.model.Shape;
import com.gabriel.drawfx.service.RendererService;

import java.awt.*;

public class RectangleRendererService implements RendererService {
    private static final int HANDLE_SIZE = 6;
    
    @Override
    public void render(Graphics g, Shape shape, boolean xor) {
        Rectangle line = (Rectangle) shape;
        if(xor) {
            g.setXORMode(shape.getColor());
        }
        else {
            g.setColor(shape.getColor());
        }
        int x = shape.getLocation().x;
        int y = shape.getLocation().y;
        int width = shape.getWidth();
        int height = shape.getHeight();

        if (width < 0) {
            x += width;
            width = Math.abs(width);
        }
        if (height < 0) {
            y += height;
            height = Math.abs(height);
        }

        if (shape.getFill() != null) {
            if (xor) {
                g.setXORMode(shape.getFill());
            } else {
                g.setColor(shape.getFill());
            }
            g.fillOval(x, y, width, height);
            
            // Reset XOR mode for outline
            if (xor) {
                g.setXORMode(shape.getColor());
            } else {
                g.setColor(shape.getColor());
            }
        }

        // Draw the shape first
        g.drawRect(x, y, width, height);

        // Then draw selection handles on top
        if (shape.isSelected() && !xor) {
            Graphics2D g2d = (Graphics2D) g;
            Stroke originalStroke = g2d.getStroke();
            
            float[] dashPattern = {5.0f, 5.0f};
            Stroke dashedStroke = new BasicStroke(1.0f, BasicStroke.CAP_BUTT, 
                                                 BasicStroke.JOIN_MITER, 10.0f, 
                                                 dashPattern, 0.0f);
            
            g2d.setColor(Color.BLUE);
            g2d.setStroke(dashedStroke);
            g2d.drawRect(x - 2, y - 2, width + 4, height + 4);
            
            // Draw 8 resize handles with blue color - these will be on top
            g2d.setStroke(originalStroke);
            g2d.setColor(Color.BLUE);
            
            // Corner handles
            drawHandle(g2d, x - 2, y - 2); // Top-left
            drawHandle(g2d, x + width + 2 - HANDLE_SIZE, y - 2); // Top-right
            drawHandle(g2d, x - 2, y + height + 2 - HANDLE_SIZE); // Bottom-left
            drawHandle(g2d, x + width + 2 - HANDLE_SIZE, y + height + 2 - HANDLE_SIZE); // Bottom-right
            
            // Edge handles
            drawHandle(g2d, x + width/2 - HANDLE_SIZE/2, y - 2); // Top-center
            drawHandle(g2d, x + width/2 - HANDLE_SIZE/2, y + height + 2 - HANDLE_SIZE); // Bottom-center
            drawHandle(g2d, x - 2, y + height/2 - HANDLE_SIZE/2); // Left-center
            drawHandle(g2d, x + width + 2 - HANDLE_SIZE, y + height/2 - HANDLE_SIZE/2); // Right-center
        }
    }
    
    private void drawHandle(Graphics2D g2d, int x, int y) {
        g2d.fillRect(x, y, HANDLE_SIZE, HANDLE_SIZE);
    }
}