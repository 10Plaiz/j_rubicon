package com.gabriel.drawfx;

import com.gabriel.drawfx.model.Shape;
import java.awt.*;

public enum ResizeHandle {
    NONE,
    TOP_LEFT,
    TOP_CENTER,
    TOP_RIGHT,
    RIGHT_CENTER,
    BOTTOM_RIGHT,
    BOTTOM_CENTER,
    BOTTOM_LEFT,
    LEFT_CENTER;
    
    private static final int HANDLE_SIZE = 6;
    
    public static ResizeHandle getHandleAt(Shape shape, Point point) {
        if (!shape.isSelected()) {
            return NONE;
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
        
        // Check each handle
        if (isPointInHandle(point, x - 2, y - 2)) return TOP_LEFT;
        if (isPointInHandle(point, x + width/2 - HANDLE_SIZE/2, y - 2)) return TOP_CENTER;
        if (isPointInHandle(point, x + width + 2 - HANDLE_SIZE, y - 2)) return TOP_RIGHT;
        if (isPointInHandle(point, x + width + 2 - HANDLE_SIZE, y + height/2 - HANDLE_SIZE/2)) return RIGHT_CENTER;
        if (isPointInHandle(point, x + width + 2 - HANDLE_SIZE, y + height + 2 - HANDLE_SIZE)) return BOTTOM_RIGHT;
        if (isPointInHandle(point, x + width/2 - HANDLE_SIZE/2, y + height + 2 - HANDLE_SIZE)) return BOTTOM_CENTER;
        if (isPointInHandle(point, x - 2, y + height + 2 - HANDLE_SIZE)) return BOTTOM_LEFT;
        if (isPointInHandle(point, x - 2, y + height/2 - HANDLE_SIZE/2)) return LEFT_CENTER;
        
        return NONE;
    }
    
    private static boolean isPointInHandle(Point point, int handleX, int handleY) {
        return point.x >= handleX && point.x <= handleX + HANDLE_SIZE &&
               point.y >= handleY && point.y <= handleY + HANDLE_SIZE;
    }
}