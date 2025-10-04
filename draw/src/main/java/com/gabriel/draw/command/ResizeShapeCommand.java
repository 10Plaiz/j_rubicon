package com.gabriel.draw.command;

import com.gabriel.drawfx.command.Command;
import com.gabriel.drawfx.model.Shape;
import com.gabriel.drawfx.service.AppService;

import java.awt.*;

public class ResizeShapeCommand implements Command {
    private final Shape shape;
    private final Point oldLocation;
    private final Point newLocation;
    private final int oldWidth;
    private final int newWidth;
    private final int oldHeight;
    private final int newHeight;
    private final AppService appService;

    public ResizeShapeCommand(AppService appService, Shape shape,
                              Point oldLocation, Point newLocation,
                              int oldWidth, int newWidth,
                              int oldHeight, int newHeight) {
        this.appService = appService;
        this.shape = shape;
        this.oldLocation = new Point(oldLocation);
        this.newLocation = new Point(newLocation);
        this.oldWidth = oldWidth;
        this.newWidth = newWidth;
        this.oldHeight = oldHeight;
        this.newHeight = newHeight;
    }

    @Override
    public void execute() {
        shape.setLocation(newLocation);
        shape.setWidth(newWidth);
        shape.setHeight(newHeight);
        appService.repaint();
    }

    @Override
    public void undo() {
        shape.setLocation(oldLocation);
        shape.setWidth(oldWidth);
        shape.setHeight(oldHeight);
        appService.repaint();
    }

    @Override
    public void redo() {
        execute();
    }
}