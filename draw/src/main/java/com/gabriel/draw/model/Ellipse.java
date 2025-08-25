package com.gabriel.draw.model;


import com.gabriel.draw.service.EllipseRendererService;
import com.gabriel.drawfx.model.Shape;
import lombok.Data;
import java.awt.*;


@Data
public class Ellipse extends Shape {

    public Ellipse(Point start, Point end) {
        super(start);
        this.setEnd(end);
        this.setColor(Color.MAGENTA);
        this.setRendererService(new EllipseRendererService());
    }
}