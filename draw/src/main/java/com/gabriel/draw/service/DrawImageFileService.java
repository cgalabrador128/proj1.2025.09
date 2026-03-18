package com.gabriel.draw.service;

import com.gabriel.drawfx.ShapeMode;
import com.gabriel.drawfx.service.AppService;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DrawImageFileService implements ActionListener {

    private final AppService appService;

    public DrawImageFileService(AppService appService) {
        this.appService = appService;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        appService.setShapeMode(ShapeMode.Image);
    }
}