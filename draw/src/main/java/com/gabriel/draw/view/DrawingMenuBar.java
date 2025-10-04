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

    private final JMenuItem lineMenuItem = new JMenuItem("Line");
    private final JMenuItem rectangleMenuItem = new JMenuItem("Rectangle");
    private final JMenuItem ellipseMenuItem = new JMenuItem("Ellipse");

    private final JMenuItem undoMenuItem = new JMenuItem("Umdo");
    private final JMenuItem redoMenuItem = new JMenuItem("Redo");
    private final JMenuItem colorMenuItem = new JMenuItem("Color");
    private final JMenuItem fillMenuItem = new JMenuItem("Fill");

    ActionListener actionListener;

    public DrawingMenuBar( ActionListener actionListener ){
        super();
        this.actionListener =actionListener;
         JMenu editMenu = new JMenu("Edit");
        editMenu.setMnemonic(KeyEvent.VK_E);
        add(editMenu);
        undoMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK));
        undoMenuItem.addActionListener(actionListener);
        undoMenuItem.setActionCommand(ActionCommand.UNDO);
        editMenu.add(undoMenuItem);
        redoMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK));;
        redoMenuItem.addActionListener(actionListener);
        redoMenuItem.setActionCommand(ActionCommand.REDO);
        editMenu.add(redoMenuItem);


        JMenu drawMenu = new JMenu("Draw");
        drawMenu.setMnemonic(KeyEvent.VK_D);
        this.add(drawMenu);
        drawMenu.add(lineMenuItem);
        lineMenuItem.setActionCommand(ActionCommand.LINE);
        lineMenuItem.addActionListener(actionListener);
        drawMenu.add(rectangleMenuItem);
        rectangleMenuItem.setActionCommand(ActionCommand.RECT);
        rectangleMenuItem.addActionListener(actionListener);
        drawMenu.add(ellipseMenuItem);
        ellipseMenuItem.setActionCommand(ActionCommand.ELLIPSE);
        ellipseMenuItem.addActionListener(actionListener);

        JMenu propMenu = new JMenu("Properties");
        propMenu.setMnemonic(KeyEvent.VK_P);
        propMenu.add(colorMenuItem);
        this.add(propMenu);
        colorMenuItem.setActionCommand(ActionCommand.COLOR);
        colorMenuItem.addActionListener(actionListener);

        propMenu.add(fillMenuItem);
        fillMenuItem.setActionCommand(ActionCommand.FILL);
        fillMenuItem.addActionListener(actionListener);
    }
}
