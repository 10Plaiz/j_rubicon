package com.gabriel.draw.view;

import com.gabriel.draw.controller.DrawingController;
import com.gabriel.draw.controller.DrawingWindowController;
import com.gabriel.drawfx.model.Drawing;
import com.gabriel.drawfx.model.Shape;
import com.gabriel.drawfx.service.AppService;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

public class DrawingView extends JPanel {
    AppService appService;

    public DrawingView(AppService appService){

        this.appService = appService;
        appService.setView(this);

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Drawing drawing = (Drawing) appService.getModel();

        for(Shape shape : drawing.getShapes()){
            shape.getRendererService().render(g, shape, false);

            // Draw selection indicator
            if (shape == drawing.getSelectedShape()) {
                Graphics2D g2d = (Graphics2D) g; // Cast to Graphics2D

                // Save the original stroke
                Stroke originalStroke = g2d.getStroke();

                g2d.setColor(Color.BLUE);
                g2d.setStroke(new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{5}, 0));
                Rectangle2D bounds = shape.getBounds();
                g2d.drawRect((int)bounds.getX() - 2, (int)bounds.getY() - 2,
                        (int)bounds.getWidth() + 4, (int)bounds.getHeight() + 4);

                // Restore the original stroke
                g2d.setStroke(originalStroke);
            }
        }
        appService.setView(this);
    }
}
