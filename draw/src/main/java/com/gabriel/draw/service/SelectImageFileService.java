package com.gabriel.draw.service;

import com.gabriel.drawfx.model.Drawing;
import com.gabriel.drawfx.service.AppService;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class SelectImageFileService implements ActionListener {

    private AppService appService;
    private Component parent;

    public SelectImageFileService(AppService appService, Component parent) {
        this.appService = appService;
        this.parent = parent;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Drawing drawing = appService.getDrawing();
        String homeFolder;

        if (drawing.getImageFilename() == null) {
            homeFolder = FileSystemView.getFileSystemView().getHomeDirectory().getPath();
        } else {
            File file = new File(drawing.getImageFilename());
            homeFolder = file.getPath();
        }

        JFileChooser fileChooser = new JFileChooser(homeFolder);

        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Image Files (jpg, png, gif)", "jpg", "png", "gif");
        fileChooser.setFileFilter(filter);

        int result = fileChooser.showOpenDialog(parent);
        if (result == JFileChooser.APPROVE_OPTION) {
            String filename = fileChooser.getSelectedFile().getAbsolutePath();
            appService.setImageFileename(filename);
        }
    }
}