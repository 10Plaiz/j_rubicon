package com.gabriel.draw.view;

import com.gabriel.drawfx.ActionCommand;
import com.gabriel.drawfx.ShapeMode;
import com.gabriel.drawfx.service.AppService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class DrawingMenuBar extends JMenuBar {

    public DrawingMenuBar( ActionListener actionListener ){
        super();
        JMenuItem lineMenuItem = new JMenuItem("Line");
        lineMenuItem.setActionCommand(ActionCommand.LINE);
        lineMenuItem.addActionListener(actionListener);
        add(lineMenuItem);
        JMenuItem rectangleMenuItem = new JMenuItem("Rectangle");
        rectangleMenuItem.setActionCommand(ActionCommand.RECT);
        rectangleMenuItem.addActionListener(actionListener);
        add(rectangleMenuItem);
        JMenuItem ellipseMenuItem = new JMenuItem("Ellipse");
        ellipseMenuItem.setActionCommand(ActionCommand.ELLIPSE);
        ellipseMenuItem.addActionListener(actionListener);
        add(ellipseMenuItem);
        JMenuItem undoMenuItem = new JMenuItem("Undo");
        undoMenuItem.setActionCommand(ActionCommand.UNDO);
        undoMenuItem.addActionListener(actionListener);
        add(undoMenuItem);
        JMenuItem redoMenuItem = new JMenuItem("Redo");
        redoMenuItem.setActionCommand(ActionCommand.REDO);
        redoMenuItem.addActionListener(actionListener);
        add(redoMenuItem);
        JMenuItem colorMenuItem = new JMenuItem("Color");
        colorMenuItem.setActionCommand(ActionCommand.SETCOLOR);
        colorMenuItem.addActionListener(actionListener);
        add(colorMenuItem);

         JMenu editMenu = new JMenu("Edit");
        editMenu.setMnemonic(KeyEvent.VK_E);
        add(editMenu);
        undoMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK));
        undoMenuItem.addActionListener(actionListener);
        undoMenuItem.setActionCommand(ActionCommand.UNDO);
        editMenu.add(undoMenuItem);
        redoMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK));
        redoMenuItem.addActionListener(actionListener);
        redoMenuItem.setActionCommand(ActionCommand.REDO);
        editMenu.add(redoMenuItem);

        JMenu drawMenu = new JMenu("Draw");
        drawMenu.setMnemonic(KeyEvent.VK_D);
        editMenu.add(drawMenu);
        drawMenu.add(lineMenuItem);
        lineMenuItem.setActionCommand(ActionCommand.LINE);
        lineMenuItem.addActionListener(actionListener);
        drawMenu.add(rectangleMenuItem);
        rectangleMenuItem.addActionListener(actionListener);
        drawMenu.add(ellipseMenuItem);
        ellipseMenuItem.addActionListener(actionListener);

        JMenu propMenu = new JMenu("Properties");
        propMenu.setMnemonic(KeyEvent.VK_P);
        propMenu.add(colorMenuItem);
        this.add(propMenu);
        colorMenuItem.setActionCommand(ActionCommand.SETCOLOR);
        colorMenuItem.addActionListener(actionListener);
    }
}
