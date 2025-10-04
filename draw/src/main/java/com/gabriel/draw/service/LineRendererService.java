package com.gabriel.draw.service;

import com.gabriel.draw.model.Line;
import com.gabriel.drawfx.service.RendererService;
import com.gabriel.drawfx.model.Shape;

import java.awt.*;

public class LineRendererService implements RendererService {
    private static final int HANDLE_SIZE = 6;

    @Override
    public void render(Graphics g, Shape shape, boolean xor) {
        if(xor) {
            g.setXORMode(shape.getColor());
        }
        else {
            g.setColor(shape.getColor());
        }
        int x1 = shape.getLocation().x;
        int y1= shape.getLocation().y;
        int x2 = x1 + shape.getWidth();
        int y2 = y1 + shape.getHeight();

        g.drawLine(x1, y1, x2, y2);

        if (shape.isSelected() && !xor) {
            Graphics2D g2d = (Graphics2D) g;

            Stroke originalStroke = g2d.getStroke();

            float[] dashPattern = {5.0f, 5.0f};
            Stroke dashedStroke = new BasicStroke(1.0f, BasicStroke.CAP_BUTT, 
                                                 BasicStroke.JOIN_MITER, 10.0f, 
                                                 dashPattern, 0.0f);

            g.setColor(Color.BLUE);
            g2d.setStroke(dashedStroke);
            int minX = Math.min(x1, x2);
            int minY = Math.min(y1, y2);
            int width = Math.abs(x2 - x1);
            int height = Math.abs(y2 - y1);
            
            g2d.drawRect(minX - 2, minY - 2, width + 4, height + 4);
            
            // Draw 8 resize handles with blue color - these will be on top
            g2d.setStroke(originalStroke);
            g2d.setColor(Color.BLUE);
            
            int x = minX;
            int y = minY;
            
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