package com.gabriel.draw.controller;

import com.gabriel.draw.command.MoveShapeCommand;
import com.gabriel.draw.command.ResizeShapeCommand;
import com.gabriel.draw.model.Ellipse;
import com.gabriel.draw.model.Line;
import com.gabriel.draw.model.Rectangle;
import com.gabriel.drawfx.DrawMode;
import com.gabriel.drawfx.ShapeMode;
import com.gabriel.drawfx.command.CommandService;
import com.gabriel.drawfx.ResizeHandle;
import com.gabriel.draw.view.DrawingView;
import com.gabriel.drawfx.service.AppService;
import com.gabriel.drawfx.model.Shape;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class DrawingController implements MouseListener, MouseMotionListener {
    private Point end;
    private Point dragStart;
    private Point shapeStartPoint;
    private Point originalMoveLocation;
    private ResizeHandle activeHandle = ResizeHandle.NONE;
    private Point originalLocation;
    private int originalWidth;
    private int originalHeight;
    final private DrawingView drawingView;

    Shape currentShape;
    private final AppService appService;
    
    public DrawingController(AppService appService, DrawingView drawingView){
        this.appService = appService;
        this.drawingView = drawingView;
        drawingView.addMouseListener(this);
        drawingView.addMouseMotionListener(this);
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        Point start = e.getPoint();
        
        if (appService.getDrawMode() == DrawMode.Idle) {
            Shape clickedShape = appService.findShapeAt(start);
            
            if (clickedShape != null && clickedShape.isSelected()) {
                // Check if clicking on a resize handle
                activeHandle = ResizeHandle.getHandleAt(clickedShape, start);
                
                if (activeHandle != ResizeHandle.NONE) {
                    // Starting a resize operation
                    dragStart = start;
                    originalLocation = new Point(clickedShape.getLocation());
                    originalWidth = clickedShape.getWidth();
                    originalHeight = clickedShape.getHeight();
                    appService.setDrawMode(DrawMode.MousePressed); // Reuse for resizing
                    return;
                }
            }
            
            if (clickedShape != null) {
                appService.selectShape(clickedShape);
                appService.setDrawMode(DrawMode.Moving);
                dragStart = start;
                originalMoveLocation = new Point(clickedShape.getLocation()); // Store original location for undo
                appService.repaint();
            } else {
                appService.deselectAllShapes();
                // Store the start point for shape creation
                shapeStartPoint = start;
                switch (appService.getShapeMode()) {
                    case Line:
                        currentShape = new Line(start, start);
                        currentShape.setColor(appService.getColor());
                        currentShape.setFill(appService.getFill());
                        appService.setDrawMode(DrawMode.MousePressed);
                        break;
                    case Rectangle:
                        currentShape = new Rectangle(start, start);
                        currentShape.setColor(appService.getColor());
                        currentShape.setFill(appService.getFill());
                        appService.setDrawMode(DrawMode.MousePressed);
                        break;
                    case Ellipse:
                        currentShape = new Ellipse(start, start);
                        currentShape.setColor(appService.getColor());
                        currentShape.setFill(appService.getFill());
                        appService.setDrawMode(DrawMode.MousePressed);
                        break;
                }
                if (currentShape != null) {
                    currentShape.getRendererService().render(drawingView.getGraphics(), currentShape, false);
                }
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (appService.getDrawMode() == DrawMode.MousePressed) {
            if (activeHandle != ResizeHandle.NONE) {
                // Finished resizing - create command for undo/redo
                Shape selectedShape = appService.getSelectedShape();
                if (selectedShape != null) {
                    Point currentLocation = selectedShape.getLocation();
                    int currentWidth = selectedShape.getWidth();
                    int currentHeight = selectedShape.getHeight();
                    
                    // Only create command if there was an actual change
                    boolean hasChanged = !originalLocation.equals(currentLocation) || 
                                    originalWidth != currentWidth || 
                                    originalHeight != currentHeight;
                    
                    if (hasChanged) {
                        ResizeShapeCommand command = new ResizeShapeCommand(
                            appService, selectedShape,
                            originalLocation, currentLocation,
                            originalWidth, currentWidth,
                            originalHeight, currentHeight
                        );
                        CommandService.ExecuteCommand(command);
                    }
                }
                activeHandle = ResizeHandle.NONE;
                appService.setDrawMode(DrawMode.Idle);
                appService.repaint();
            } else if (currentShape != null) {
                // Finished creating new shape
                end = e.getPoint();
                currentShape.getRendererService().render(drawingView.getGraphics(), currentShape, true);
                appService.scale(currentShape, end);
                
                if (dimensionChecker(shapeStartPoint, end)) {
                    appService.create(currentShape);
                }

                appService.setDrawMode(DrawMode.Idle);
                appService.repaint();
                currentShape = null; 
                shapeStartPoint = null;
            }
        } else if (appService.getDrawMode() == DrawMode.Moving) {
            // Finished moving - create command for undo/redo
            Shape selectedShape = appService.getSelectedShape();
            if (selectedShape != null && originalMoveLocation != null) {
                Point finalLocation = selectedShape.getLocation();
                
                // Only create command if there was an actual move
                if (!originalMoveLocation.equals(finalLocation)) {
                    MoveShapeCommand command = new MoveShapeCommand(
                        appService, selectedShape,
                        originalMoveLocation, finalLocation
                    );
                    CommandService.ExecuteCommand(command);
                }
            }
            appService.setDrawMode(DrawMode.Idle);
            originalMoveLocation = null; // Reset for next operation
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (appService.getDrawMode() == DrawMode.MousePressed) {
            if (activeHandle != ResizeHandle.NONE) {
                // Resizing selected shape
                Shape selectedShape = appService.getSelectedShape();
                if (selectedShape != null) {
                    Point currentPoint = e.getPoint();
                    resizeShape(selectedShape, currentPoint);
                    appService.repaint();
                }
            } else if (currentShape != null) {
                // Creating new shape
                end = e.getPoint();
                currentShape.getRendererService().render(drawingView.getGraphics(), currentShape, true);
                appService.scale(currentShape, end);
                currentShape.getRendererService().render(drawingView.getGraphics(), currentShape, true);
            }
        } else if (appService.getDrawMode() == DrawMode.Moving) {
            Shape selectedShape = appService.getSelectedShape();
            if (selectedShape != null) {
                Point currentPoint = e.getPoint();
                int dx = currentPoint.x - dragStart.x;
                int dy = currentPoint.y - dragStart.y;
                
                Point newLocation = new Point(
                    selectedShape.getLocation().x + dx,
                    selectedShape.getLocation().y + dy
                );
                
                appService.move(selectedShape, newLocation);
                dragStart = currentPoint;
                appService.repaint();
            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // Change cursor based on handle hover
        if (appService.getDrawMode() == DrawMode.Idle) {
            Shape selectedShape = appService.getSelectedShape();
            if (selectedShape != null) {
                ResizeHandle handle = ResizeHandle.getHandleAt(selectedShape, e.getPoint());
                setCursorForHandle(handle);
            } else {
                drawingView.setCursor(Cursor.getDefaultCursor());
            }
        }
    }
    
    private void setCursorForHandle(ResizeHandle handle) {
        Cursor cursor;
        switch (handle) {
            case TOP_LEFT:
            case BOTTOM_RIGHT:
                cursor = Cursor.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR);
                break;
            case TOP_RIGHT:
            case BOTTOM_LEFT:
                cursor = Cursor.getPredefinedCursor(Cursor.NE_RESIZE_CURSOR);
                break;
            case TOP_CENTER:
            case BOTTOM_CENTER:
                cursor = Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR);
                break;
            case LEFT_CENTER:
            case RIGHT_CENTER:
                cursor = Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR);
                break;
            default:
                cursor = Cursor.getDefaultCursor();
                break;
        }
        drawingView.setCursor(cursor);
    }
    
    private void resizeShape(Shape shape, Point currentPoint) {
        int dx = currentPoint.x - dragStart.x;
        int dy = currentPoint.y - dragStart.y;
        
        Point newLocation = new Point(originalLocation);
        int newWidth = originalWidth;
        int newHeight = originalHeight;
        
        switch (activeHandle) {
            case TOP_LEFT:
                newLocation.x = originalLocation.x + dx;
                newLocation.y = originalLocation.y + dy;
                newWidth = originalWidth - dx;
                newHeight = originalHeight - dy;
                break;
            case TOP_CENTER:
                newLocation.y = originalLocation.y + dy;
                newHeight = originalHeight - dy;
                break;
            case TOP_RIGHT:
                newLocation.y = originalLocation.y + dy;
                newWidth = originalWidth + dx;
                newHeight = originalHeight - dy;
                break;
            case RIGHT_CENTER:
                newWidth = originalWidth + dx;
                break;
            case BOTTOM_RIGHT:
                newWidth = originalWidth + dx;
                newHeight = originalHeight + dy;
                break;
            case BOTTOM_CENTER:
                newHeight = originalHeight + dy;
                break;
            case BOTTOM_LEFT:
                newLocation.x = originalLocation.x + dx;
                newWidth = originalWidth - dx;
                newHeight = originalHeight + dy;
                break;
            case LEFT_CENTER:
                newLocation.x = originalLocation.x + dx;
                newWidth = originalWidth - dx;
                break;
        }
        
        // Apply minimum size constraints
        if (Math.abs(newWidth) < 10) {
            newWidth = newWidth < 0 ? -10 : 10;
        }
        if (Math.abs(newHeight) < 10) {
            newHeight = newHeight < 0 ? -10 : 10;
        }
        
        shape.setLocation(newLocation);
        shape.setWidth(newWidth);
        shape.setHeight(newHeight);
    }

    private boolean dimensionChecker(Point start, Point end) {
        if (start == null || end == null) {
            return false;
        }
        
        int minDistance = 2; 
        int dx = Math.abs(end.x - start.x);
        int dy = Math.abs(end.y - start.y);
        
        return dx > minDistance || dy > minDistance;
    }
}