package com.gabriel.draw.controller;

import com.gabriel.drawfx.ActionCommand;
import com.gabriel.drawfx.ShapeMode;
import com.gabriel.drawfx.service.AppService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionController implements ActionListener {
    AppService appService;
    private JToolBar toolbar;
    public  ActionController(AppService appService){
        this.appService = appService;
    }

    public void setToolbar(JToolBar toolbar) {
        this.toolbar = toolbar;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String  cmd = e.getActionCommand();
        if(ActionCommand.UNDO.equals(cmd) ){
            appService.undo();
        }
        else if(ActionCommand.REDO.equals(cmd)) {
            appService.redo();
        }
        else if(ActionCommand.LINE.equals(cmd)) {
            appService.setShapeMode(ShapeMode.Line);
        }
        else if(ActionCommand.RECT.equals(cmd)) {
            appService.setShapeMode(ShapeMode.Rectangle);
        }
        else if(ActionCommand.ELLIPSE.equals(cmd)) {
            appService.setShapeMode(ShapeMode.Ellipse);
        }
        else if(ActionCommand.SETCOLOR.equals(cmd)) {
            Color color = JColorChooser.showDialog(null, "Choose a Color", appService.getColor());
            appService.setColor(color);
        }
        else if(ActionCommand.TOGGLE_TOOLBAR.equals(cmd)) {
            if (toolbar != null) {
                toolbar.setVisible(!toolbar.isVisible());
            }
        }
    }
}
