package com.gabriel.draw.command;

import com.gabriel.drawfx.command.Command;
import com.gabriel.drawfx.model.Shape;
import com.gabriel.drawfx.service.AppService;

import java.awt.*;

public class MoveShapeCommand implements Command {
    private final AppService appService;
    private final Shape shape;
    private final Point oldLocation;
    private final Point oldEnd;
    private final Point newLocation;
    private final Point newEnd;

    public MoveShapeCommand(AppService appService, Shape shape, Point oldLocation, Point oldEnd, Point newLocation, Point newEnd) {
        this.appService = appService;
        this.shape = shape;
        this.oldLocation = new Point(oldLocation);
        this.oldEnd = new Point(oldEnd);
        this.newLocation = new Point(newLocation);
        this.newEnd = new Point(newEnd);
    }

    @Override
    public void execute() {
        shape.setLocation(newLocation);
        shape.setEnd(newEnd);
        appService.repaint();
    }

    @Override
    public void undo() {
        shape.setLocation(oldLocation);
        shape.setEnd(oldEnd);
        appService.repaint();
    }

    @Override
    public void redo() {
        execute();
    }
}