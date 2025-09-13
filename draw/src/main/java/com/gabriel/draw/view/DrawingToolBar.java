package com.gabriel.draw.view;

import com.gabriel.draw.controller.ActionController;
import com.gabriel.drawfx.ActionCommand;
import com.gabriel.drawfx.service.AppService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

public class DrawingToolBar extends JToolBar {
        
        public DrawingToolBar(ActionListener actionListener){
        setFloatable(false);
        // Line button
        JButton button = new JButton();
        String imgLocation = "images/"
                + "line"
                + ".png";
        URL imageURL = DrawingToolBar.class.getResource(imgLocation);

        button.setActionCommand(ActionCommand.LINE);
        button.setToolTipText("Draw Line");
        button.addActionListener(actionListener);
        button.setIcon(new ImageIcon(imageURL, "Line"));
        add(button);

        // Rectangle button
        button = new JButton();
        imgLocation = "images/"
                + "rectangle"
                + ".png";
        imageURL = DrawingToolBar.class.getResource(imgLocation);

        button.setActionCommand(ActionCommand.RECT);
        button.setToolTipText("Draw Rectangle");
        button.addActionListener(actionListener);
        button.setIcon(new ImageIcon(imageURL, "Rectangle"));
        add(button);

        // Ellipse button
        button = new JButton();
        imgLocation = "images/"
                + "ellipse"
                + ".png";
        imageURL = DrawingToolBar.class.getResource(imgLocation);

        button.setActionCommand(ActionCommand.ELLIPSE);
        button.setToolTipText("Draw Ellipse");
        button.addActionListener(actionListener);
        button.setIcon(new ImageIcon(imageURL, "Ellipse"));
        add(button);

        // Set Color button
        button = new JButton();
        imgLocation = "images/"
                + "setcolor"
                + ".png";
        imageURL = DrawingToolBar.class.getResource(imgLocation);

        button.setActionCommand(ActionCommand.SETCOLOR);
        button.setToolTipText("Set Color");
        button.addActionListener(actionListener);
        button.setIcon(new ImageIcon(imageURL, "SetColor"));
        add(button);

        // Undo button
        button = new JButton();
        imgLocation = "images/"
                + "undo"
                + ".png";
        imageURL = DrawingToolBar.class.getResource(imgLocation);

        button.setActionCommand(ActionCommand.UNDO);
        button.setToolTipText("Undo");
        button.addActionListener(actionListener);
        button.setIcon(new ImageIcon(imageURL, "Undo"));
        add(button);

        // Redo button
        button = new JButton();
        imgLocation = "images/"
                + "redo"
                + ".png";
        imageURL = DrawingToolBar.class.getResource(imgLocation);

        button.setActionCommand(ActionCommand.REDO);
        button.setToolTipText("Redo");
        button.addActionListener(actionListener);
        button.setIcon(new ImageIcon(imageURL, "Redo"));
        add(button);
    }
}
