package com.gabriel.draw.model;


import com.gabriel.draw.service.RectangleRendererService;
import com.gabriel.drawfx.model.Shape;
import lombok.Data;
import java.awt.*;


@Data
public class Rectangle extends Shape {

    public Rectangle(Point start, Point end) {
        super(start);
        this.setEnd(end);
        this.setColor(Color.BLUE);
        this.setRendererService(new RectangleRendererService());
    }
}