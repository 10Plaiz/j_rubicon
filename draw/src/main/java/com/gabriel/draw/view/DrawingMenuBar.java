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
        
        // Create shape menu items
        JMenuItem lineMenuItem = new JMenuItem("Line");
        lineMenuItem.setActionCommand(ActionCommand.LINE);
        lineMenuItem.addActionListener(actionListener);
        
        JMenuItem rectangleMenuItem = new JMenuItem("Rectangle");
        rectangleMenuItem.setActionCommand(ActionCommand.RECT);
        rectangleMenuItem.addActionListener(actionListener);
        
        JMenuItem ellipseMenuItem = new JMenuItem("Ellipse");
        ellipseMenuItem.setActionCommand(ActionCommand.ELLIPSE);
        ellipseMenuItem.addActionListener(actionListener);
        
        JMenuItem colorMenuItem = new JMenuItem("Color");
        colorMenuItem.setActionCommand(ActionCommand.SETCOLOR);
        colorMenuItem.addActionListener(actionListener);

        // Create Edit Menu with Undo/Redo AND Draw submenu
        JMenu editMenu = new JMenu("Edit");
        editMenu.setMnemonic(KeyEvent.VK_E);
        
        JMenuItem undoMenuItem = new JMenuItem("Undo");
        undoMenuItem.setActionCommand(ActionCommand.UNDO);
        undoMenuItem.addActionListener(actionListener);
        undoMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK));
        editMenu.add(undoMenuItem);
        
        JMenuItem redoMenuItem = new JMenuItem("Redo");
        redoMenuItem.setActionCommand(ActionCommand.REDO);
        redoMenuItem.addActionListener(actionListener);
        redoMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK));
        editMenu.add(redoMenuItem);
        
        
        // Create Draw submenu under Edit
        JMenu drawMenu = new JMenu("Draw");
        drawMenu.setMnemonic(KeyEvent.VK_D);
        drawMenu.add(lineMenuItem);
        drawMenu.add(rectangleMenuItem);
        drawMenu.add(ellipseMenuItem);
        
        // Add Draw submenu to Edit menu
        editMenu.add(drawMenu);
        
        this.add(editMenu);

        // Create Properties Menu
        JMenu propMenu = new JMenu("Properties");
        propMenu.setMnemonic(KeyEvent.VK_P);
        propMenu.add(colorMenuItem);
        this.add(propMenu);

        // Create View Menu
        JMenu viewMenu = new JMenu("View");
        viewMenu.setMnemonic(KeyEvent.VK_V);
        
        JMenuItem toggleToolbarItem = new JMenuItem("Toggle Toolbar");
        toggleToolbarItem.setActionCommand(ActionCommand.TOGGLE_TOOLBAR);
        toggleToolbarItem.addActionListener(actionListener);
        toggleToolbarItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, InputEvent.CTRL_DOWN_MASK));
        
        viewMenu.add(toggleToolbarItem);
        this.add(viewMenu);
    }
}