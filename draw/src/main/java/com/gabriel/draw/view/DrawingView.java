package com.gabriel.draw.view;

import com.gabriel.draw.controller.DrawingController;
import com.gabriel.draw.controller.DrawingWindowController;
import com.gabriel.drawfx.model.Drawing;
import com.gabriel.drawfx.model.Shape;
import com.gabriel.drawfx.service.AppService;

import javax.swing.*;
import java.awt.*;

public class DrawingView extends JPanel {
    Drawing drawing;

    public DrawingView(Drawing drawing){
        this.drawing  = drawing;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        for(Shape shape : drawing.getShapes()){
            shape.getRendererService().render(g, shape, false);
        }
    }
}
