package com.gabriel.drawfx.model;

import com.gabriel.drawfx.DrawMode;
import com.gabriel.drawfx.ShapeMode;
import lombok.Data;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
@Data
public class Drawing {

    private Color color;
    private Color fill;
    private ShapeMode shapeMode = ShapeMode.Rectangle;
    private DrawMode drawMode = DrawMode.Idle;
    List<Shape> shapes;
    public Drawing(){
        shapes = new ArrayList<>();
        color = Color.BLACK;
    }
}
