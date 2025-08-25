package com.gabriel.draw.model;


import com.gabriel.draw.service.LineRendererService;
import com.gabriel.drawfx.model.Shape;
import lombok.Data;
import java.awt.*;


@Data
public class Line extends Shape {

    public Line(Point start, Point end){
        super(start);
        this.setEnd(end);
        this.setColor(Color.RED);
        this.setRendererService(new LineRendererService());
    }
}
