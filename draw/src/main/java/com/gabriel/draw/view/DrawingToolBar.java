package com.gabriel.draw.view;

import com.gabriel.draw.controller.ActionController;
import com.gabriel.drawfx.ActionCommand;
import com.gabriel.drawfx.service.AppService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.net.URL;

public class DrawingToolBar extends JToolBar {

    protected JTextArea textArea;
    ActionListener mainActionListener;
    ActionListener selectImageAction;

    public DrawingToolBar(ActionListener mainActionListener, ActionListener selectImageAction) {
        setFloatable(false);
        setRollover(true);
        this.mainActionListener = mainActionListener;
        this.selectImageAction = selectImageAction;
        addButtons();

        textArea = new JTextArea(5, 30);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);

        setPreferredSize(new Dimension(200, 30));
        setBackground(Color.GREEN);
    }

    protected void addButtons() {
        JButton button = null;

        button = makeNavigationButton("undo", ActionCommand.UNDO, "Undo", ActionCommand.UNDO);
        button.addActionListener(mainActionListener);
        add(button);

        button = makeNavigationButton("redo", ActionCommand.REDO, "Redo", ActionCommand.REDO);
        button.addActionListener(mainActionListener);
        add(button);

        button = makeNavigationButton("rect", ActionCommand.RECT, "Draw a rectangle", ActionCommand.RECT);
        button.addActionListener(mainActionListener);
        add(button);

        button = makeNavigationButton("line", ActionCommand.LINE, "Draw a line", ActionCommand.LINE);
        button.addActionListener(mainActionListener);
        add(button);

        button = makeNavigationButton("ellipse", ActionCommand.ELLIPSE, "Draw an ellipse", ActionCommand.ELLIPSE);
        button.addActionListener(mainActionListener);
        add(button);

        button = makeNavigationButton("text", ActionCommand.TEXT, "Add a text", ActionCommand.TEXT);
        button.addActionListener(mainActionListener);
        add(button);

        button = makeNavigationButton("image", ActionCommand.IMAGE, "Draw an image", ActionCommand.IMAGE);
        button.addActionListener(mainActionListener);
        add(button);

        button = makeNavigationButton("select", ActionCommand.SELECT, "Switch to select", ActionCommand.SELECT);
        button.addActionListener(mainActionListener);
        add(button);

        button = makeNavigationButton("delete", ActionCommand.DELETE, "Delete a shape", ActionCommand.DELETE);
        button.addActionListener(mainActionListener);
        add(button);

        addSeparator();

        button = makeNavigationButton("imageselector", ActionCommand.IMAGESELECT, "Select an Image", ActionCommand.IMAGESELECT);
        button.addActionListener(selectImageAction);
        add(button);

        JTextField textField = new JTextField("");
        textField.setColumns(10);
        textField.addActionListener(mainActionListener);
        textField.setActionCommand("TEXT_ENTERED");
        add(textField);
    }

    protected JButton makeNavigationButton(String imageName,
                                           String actionCommand,
                                           String toolTipText,
                                           String altText) {
        String imgLocation = "images/"
                + imageName
                + ".png";
        URL imageURL = DrawingToolBar.class.getResource(imgLocation);

        JButton button = new JButton();
        button.setActionCommand(actionCommand);
        button.setToolTipText(toolTipText);

        if (imageURL != null) {
            button.setIcon(new ImageIcon(imageURL, altText));
        } else {
            button.setText(altText);
            System.err.println("Resource not found: "
                    + imgLocation);
        }
        return button;
    }
}