package com.gabriel.draw.command;

import com.gabriel.drawfx.service.AppService;
import com.gabriel.drawfx.command.Command;

import java.awt.*;

public class SetFillCommand implements Command {
    AppService appService;
    Color fillColor;
    Color prevFillColor;

    public SetFillCommand(AppService appService, Color fillColor){
        this.fillColor = fillColor;
        this.appService = appService;
        this.prevFillColor = appService.getFill();
    }

    @Override
    public void execute() {
        appService.setFill(fillColor);
    }

    @Override
    public void undo() {
        appService.setFill(prevFillColor);
    }

    @Override
    public void redo() {
        appService.setFill(fillColor);
    }
}