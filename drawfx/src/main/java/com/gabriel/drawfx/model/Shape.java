package com.gabriel.drawfx.model;

import com.gabriel.drawfx.service.RendererService;
import lombok.Data;

import java.awt.*;
import java.awt.geom.Rectangle2D;

@Data
public abstract class Shape {
    int id;
    private Point location;
    private Point end;
    private Color color;
    private Color fill;
    private RendererService rendererService;
    public Shape(Point location){
        this.setLocation(location);
        this.setEnd(location);
  }

  // Hit testing method
  public boolean contains(Point point) {
      Rectangle2D bounds = getBounds();
      return bounds.contains(point);
  }
  
  // Get bounding rectangle for hit testing
  public Rectangle2D getBounds() {
      int x = Math.min(location.x, end.x);
      int y = Math.min(location.y, end.y);
      int width = Math.abs(end.x - location.x);
      int height = Math.abs(end.y - location.y);
      return new Rectangle2D.Double(x, y, width, height);
  }
}
