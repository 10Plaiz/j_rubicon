package com.gabriel.draw;

import com.gabriel.draw.controller.ActionController;
import com.gabriel.draw.view.DrawingMenuBar;
import com.gabriel.draw.service.DeawingCommandAppService;
import com.gabriel.draw.service.DrawingAppService;
import com.gabriel.draw.controller.DrawingController;
import com.gabriel.draw.view.DrawingToolBar;
import com.gabriel.draw.view.DrawingView;
import com.gabriel.draw.view.DrawingFrame;
import com.gabriel.drawfx.ActionCommand;
import com.gabriel.drawfx.service.AppService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        AppService drawingAppService = new DrawingAppService();
        AppService appService = new DeawingCommandAppService(drawingAppService);

        DrawingFrame drawingFrame = new DrawingFrame(appService);
        ActionListener actionListener = new ActionController(appService);
        DrawingMenuBar drawingMenuBar = new DrawingMenuBar( actionListener);
        DrawingToolBar drawingToolBar = new DrawingToolBar(actionListener);
        DrawingView drawingView = new DrawingView(appService);
        DrawingController drawingController = new DrawingController(appService, drawingView);
        drawingView.addMouseMotionListener(drawingController);
        drawingView.addMouseListener(drawingController);
        drawingFrame.setContentPane(drawingView);


        ActionController actionController = (ActionController) actionListener;
        actionController.setToolbar(drawingToolBar);

        // Create a small toggle button panel
        JPanel togglePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 2, 2));
        togglePanel.setPreferredSize(new Dimension(30, 30));
        
        JButton toggleButton = new JButton("≡");
        toggleButton.setActionCommand(ActionCommand.TOGGLE_TOOLBAR);
        toggleButton.setToolTipText("Toggle Toolbar");
        toggleButton.addActionListener(actionListener);
        toggleButton.setPreferredSize(new Dimension(25, 25));
        toggleButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
        
        togglePanel.add(toggleButton);

        // Create a container panel for both toolbar and toggle button
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(drawingToolBar, BorderLayout.CENTER);
        topPanel.add(togglePanel, BorderLayout.EAST);

        drawingMenuBar.setVisible(true);
        drawingFrame.setJMenuBar(drawingMenuBar);
        drawingFrame.getContentPane().add(topPanel, BorderLayout.PAGE_START);


        drawingMenuBar.setVisible(true);
        drawingFrame.setJMenuBar(drawingMenuBar);
        drawingFrame.getContentPane().add(drawingToolBar, BorderLayout.PAGE_START);

        drawingFrame.setVisible(true);
        drawingFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        drawingFrame.setSize(500,500);
    }
}