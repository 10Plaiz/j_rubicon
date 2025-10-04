package com.gabriel.draw.command;

import com.gabriel.drawfx.command.Command;
import com.gabriel.drawfx.model.Shape;
import com.gabriel.drawfx.service.AppService;

import java.awt.*;

public class MoveShapeCommand implements Command {
    private final Shape shape;
    private final Point oldLocation;
    private final Point newLocation;
    private final AppService appService;

    public MoveShapeCommand(AppService appService, Shape shape, Point oldLocation, Point newLocation) {
        this.appService = appService;
        this.shape = shape;
        this.oldLocation = new Point(oldLocation);
        this.newLocation = new Point(newLocation);
    }

    @Override
    public void execute() {
        shape.setLocation(newLocation);
        appService.repaint();
    }

    @Override
    public void undo() {
        shape.setLocation(oldLocation);
        appService.repaint();
    }

    @Override
    public void redo() {
        execute();
    }
}