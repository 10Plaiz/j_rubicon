package com.gabriel.draw.component;

import com.gabriel.drawfx.ShapeMode;
import com.gabriel.drawfx.service.AppService;
import com.gabriel.drawfx.model.Shape;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class DrawingMenuBar extends JMenuBar implements ActionListener {
    private AppService appService;

    private final JMenuItem lineMenuItem = new JMenuItem("Line");
    private final JMenuItem rectangleMenuItem = new JMenuItem("Rectangle");
    private final JMenuItem ellipseMenuItem = new JMenuItem("Ellipse");

    private final JMenuItem undoMenuItem = new JMenuItem("Undo");
    private final JMenuItem redoMenuItem = new JMenuItem("Redo");
    private final JMenuItem deleteMenuItem = new JMenuItem("Delete");
    private final JMenuItem colorMenuItem = new JMenuItem("Color");

    public DrawingMenuBar(AppService appService ){
        super();
        this.appService = appService;
        JMenu editMenu = new JMenu("Edit");
        editMenu.setMnemonic(KeyEvent.VK_E);
        add(editMenu);
        undoMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK));
        undoMenuItem.addActionListener(this);
        editMenu.add(undoMenuItem);
        redoMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK));;
        redoMenuItem.addActionListener(this);
        editMenu.add(redoMenuItem);
        deleteMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0));
        deleteMenuItem.addActionListener(this);
        editMenu.add(deleteMenuItem);

        JMenu drawMenu = new JMenu("Draw");
        drawMenu.setMnemonic(KeyEvent.VK_D);
        editMenu.add(drawMenu);
        drawMenu.add(lineMenuItem);
        lineMenuItem.addActionListener(this);
        drawMenu.add(rectangleMenuItem);
        rectangleMenuItem.addActionListener(this);
        drawMenu.add(ellipseMenuItem);
        ellipseMenuItem.addActionListener(this);

        JMenu propMenu = new JMenu("Properties");
        propMenu.setMnemonic(KeyEvent.VK_P);
        propMenu.add(colorMenuItem);
        this.add(propMenu);
        colorMenuItem.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == undoMenuItem) {
            appService.undo();
        }
        else if(e.getSource() == redoMenuItem) {
            appService.redo();
        }
        else if(e.getSource() == deleteMenuItem) {
            // Wait lang ha
            Shape selectedShape = appService.getSelectedShape();
            if (selectedShape != null) {
                appService.delete(selectedShape);
                appService.clearSelection();
                appService.repaint();
            }
        }
        else if(e.getSource() == lineMenuItem){
            appService.setShapeMode( ShapeMode.Line);
        }
        else if(e.getSource() == rectangleMenuItem){
            appService.setShapeMode( ShapeMode.Rectangle);
        }
        else if(e.getSource() == ellipseMenuItem) {
            appService.setShapeMode(ShapeMode.Ellipse);
        }
        else if(e.getSource() == colorMenuItem) {
                Color selectedColor = JColorChooser.showDialog(null, "Choose a color", appService.getColor());
                appService.setColor(selectedColor);
        }
    }
}
