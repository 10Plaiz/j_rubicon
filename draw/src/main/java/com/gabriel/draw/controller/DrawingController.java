package com.gabriel.draw.controller;

import com.gabriel.draw.command.MoveShapeCommand;
import com.gabriel.draw.command.ResizeShapeCommand;
import com.gabriel.draw.model.Ellipse;
import com.gabriel.draw.model.Line;
import com.gabriel.draw.model.Rectangle;
import com.gabriel.drawfx.DrawMode;
import com.gabriel.drawfx.ShapeMode;
import com.gabriel.drawfx.command.CommandService;
import com.gabriel.draw.view.DrawingView;
import com.gabriel.drawfx.service.AppService;
import com.gabriel.drawfx.model.Drawing;
import com.gabriel.drawfx.model.Shape;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;

public class DrawingController  implements MouseListener, MouseMotionListener {
    private Point end;
    private Point lastDragPoint;
    private boolean isDragging = false;
    private boolean isResizing = false;
    private int resizeHandle = -1;
    private Point originalLocation;
    private Point originalEnd;
    private Point startMoveLocation;
    private Point startMoveEnd;
    final private DrawingView drawingView;

    Shape currentShape;
    AppService appService;
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
        
        if(appService.getDrawMode() == DrawMode.Idle) {
            // First check if we clicked on an existing shape for selection
            Drawing drawing = (Drawing) appService.getModel();
            Shape clickedShape = null;

            // Check if we clicked on a resize handle first
            if (appService.getSelectedShape() != null) {
                resizeHandle = drawingView.getResizeHandle(start, appService.getSelectedShape());
                if (resizeHandle != -1) {
                    isResizing = true;
                    originalLocation = new Point(appService.getSelectedShape().getLocation());
                    originalEnd = new Point(appService.getSelectedShape().getEnd());
                    lastDragPoint = start;
                    return;
                }
            }

            // Check shapes in reverse order (top to bottom)
            for (int i = drawing.getShapes().size() - 1; i >= 0; i--) {
                Shape shape = drawing.getShapes().get(i);
                if (shape.contains(start)) {
                    clickedShape = shape;
                    break;
                }
            }
            
            if (clickedShape != null) {
                // Select the clicked shape and prepare for potential moving
                appService.selectShape(clickedShape);
                lastDragPoint = start;
                isDragging = false;
                // Store starting position for potential move command
                startMoveLocation = new Point(clickedShape.getLocation());
                startMoveEnd = new Point(clickedShape.getEnd());
                appService.repaint();
            } else {
                // Clear selection and create new shape
                appService.clearSelection();
                isDragging = false;
                
                switch (appService.getShapeMode()){
                    case Line:  
                        currentShape = new Line(start, start);
                        currentShape.setColor(appService.getColor());
                        break;
                    case Rectangle:
                        currentShape = new Rectangle(start, start);
                        currentShape.setColor(appService.getColor());
                        currentShape.setFill(appService.getFill());
                        break;
                    case Ellipse:
                        currentShape = new Ellipse(start, start);
                        currentShape.setColor(appService.getColor());
                        currentShape.setFill(appService.getFill());
                        break;
                }
                currentShape.getRendererService().render(drawingView.getGraphics(), currentShape, false);
                appService.setDrawMode(DrawMode.MousePressed);
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(appService.getDrawMode() == DrawMode.MousePressed){
            end = e.getPoint();
            currentShape.getRendererService().render(drawingView.getGraphics(), currentShape,true );
            appService.scale(currentShape,end);
            currentShape.getRendererService().render(drawingView.getGraphics(), currentShape,false );
            appService.create(currentShape);
            appService.setDrawMode(DrawMode.Idle);
        } else if (isDragging) {
            // Create move command when drag is finished
            Shape selectedShape = appService.getSelectedShape();
            if (selectedShape != null) {
                Point finalLocation = selectedShape.getLocation();
                Point finalEnd = selectedShape.getEnd();
                
                // Only create command if the shape actually moved
                if (!startMoveLocation.equals(finalLocation) || !startMoveEnd.equals(finalEnd)) {
                    MoveShapeCommand moveCommand = new MoveShapeCommand(
                        appService, selectedShape, 
                        startMoveLocation, startMoveEnd, 
                        finalLocation, finalEnd
                    );
                    CommandService.ExecuteCommand(moveCommand);
                }
            }
            isDragging = false;
            appService.repaint();
        } else if (isResizing) {
            // Create resize command when resize is finished
            Shape selectedShape = appService.getSelectedShape();
            if (selectedShape != null) {
                Point finalLocation = selectedShape.getLocation();
                Point finalEnd = selectedShape.getEnd();
                
                // Only create command if the shape actually changed
                if (!originalLocation.equals(finalLocation) || !originalEnd.equals(finalEnd)) {
                    ResizeShapeCommand resizeCommand = new ResizeShapeCommand(
                        appService, selectedShape,
                        originalLocation, originalEnd,
                        finalLocation, finalEnd
                    );
                    CommandService.ExecuteCommand(resizeCommand);
                }
            }
            isResizing = false;
            resizeHandle = -1;
            appService.repaint();
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
    if(appService.getDrawMode() == DrawMode.MousePressed) {
        // Drawing a new shape
        end = e.getPoint();
        currentShape.getRendererService().render(drawingView.getGraphics(), currentShape,true );
        appService.scale(currentShape,end);
        currentShape.getRendererService().render(drawingView.getGraphics(), currentShape,true );
    } else if (isResizing && appService.getSelectedShape() != null) {
        // Resizing an existing shape
        Point currentPoint = e.getPoint();
        Shape selectedShape = appService.getSelectedShape();
        
        Point newLocation = new Point(originalLocation);
        Point newEnd = new Point(originalEnd);
        
        switch (resizeHandle) {
            case 0: // Top-left
                newLocation.setLocation(currentPoint.x, currentPoint.y);
                break;
            case 1: // Top-right
                newLocation.setLocation(originalLocation.x, currentPoint.y);
                newEnd.setLocation(currentPoint.x, originalEnd.y);
                break;
            case 2: // Bottom-left
                newLocation.setLocation(currentPoint.x, originalLocation.y);
                newEnd.setLocation(originalEnd.x, currentPoint.y);
                break;
            case 3: // Bottom-right
                newEnd.setLocation(currentPoint.x, currentPoint.y);
                break;
        }
        
        selectedShape.setLocation(newLocation);
        selectedShape.setEnd(newEnd);
        appService.repaint();

    } else if (appService.getSelectedShape() != null && lastDragPoint != null && !isResizing) {
        // Moving an existing selected shape
        isDragging = true;
        Point currentPoint = e.getPoint();
        
        // Calculate the offset from last drag point
        int dx = currentPoint.x - lastDragPoint.x;
        int dy = currentPoint.y - lastDragPoint.y;
        
        Shape selectedShape = appService.getSelectedShape();
        
        // Calculate new location
        Point newLocation = new Point(
            selectedShape.getLocation().x + dx,
            selectedShape.getLocation().y + dy
        );
        
        // Move the shape directly (without using command service yet)
        selectedShape.setLocation(newLocation);
        Point newEnd = new Point(selectedShape.getEnd().x + dx, selectedShape.getEnd().y + dy);
        selectedShape.setEnd(newEnd);
                    
        // Update last drag point
        lastDragPoint = currentPoint;
        
        // Repaint to show the updated position
        appService.repaint();

        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // Change cursor when hovering over resize handles
        if (appService.getSelectedShape() != null) {
            int handle = drawingView.getResizeHandle(e.getPoint(), appService.getSelectedShape());
            if (handle != -1) {
                drawingView.setCursor(Cursor.getPredefinedCursor(Cursor.SE_RESIZE_CURSOR));
            } else {
                drawingView.setCursor(Cursor.getDefaultCursor());
            }
        } else {
            drawingView.setCursor(Cursor.getDefaultCursor());
        }
    }
}
