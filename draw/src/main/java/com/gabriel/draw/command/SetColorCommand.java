package com.gabriel.draw.command;

import com.gabriel.drawfx.service.AppService;
import com.gabriel.drawfx.command.Command;

import java.awt.*;

public class SetColorCommand implements Command {
    AppService appService;
    Color color;
    Color prevColor;
    public SetColorCommand(AppService appService, Color color){
        this.color = color;
        this.appService = appService;
        this.prevColor = appService.getColor();
    }

    @Override
    public void execute() {
        appService.setColor(color);
    }

    @Override
    public void undo() {
        appService.setColor(prevColor);
    }

    @Override
    public void redo() {
        appService.setColor(color);
    }
}
