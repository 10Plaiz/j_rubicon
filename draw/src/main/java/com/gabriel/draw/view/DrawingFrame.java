package com.gabriel.draw.view;

import com.gabriel.draw.controller.DrawingWindowController;
import com.gabriel.drawfx.model.Drawing;
import com.gabriel.drawfx.ShapeMode;
import com.gabriel.drawfx.service.AppService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DrawingFrame extends JFrame {


    public DrawingFrame(AppService appService){
        DrawingWindowController drawingWindowController = new DrawingWindowController(appService);
        this.addWindowListener(drawingWindowController);
        this.addWindowFocusListener(drawingWindowController);
        this.addWindowStateListener(drawingWindowController);

        JToolBar toolbar = new JToolBar();
        toolbar.setFloatable(false);

        JButton lineButton = new JButton("Line");
        lineButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                appService.setShapeMode(ShapeMode.Line);
            }
        });

        JButton rectangleButton = new JButton("Rectangle");
        rectangleButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                appService.setShapeMode(ShapeMode.Rectangle);
            }
        });

        JButton ellipseButton = new JButton("Ellipse");
        ellipseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                appService.setShapeMode(ShapeMode.Ellipse);
            }
        });

        toolbar.add(lineButton);
        toolbar.add(rectangleButton);
        toolbar.add(ellipseButton);

        DrawingView drawingView = new DrawingView(appService);

        this.setLayout(new BorderLayout());
        this.add(toolbar, BorderLayout.NORTH);
        this.add(drawingView, BorderLayout.CENTER);
    }
}