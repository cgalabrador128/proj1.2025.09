package com.gabriel.draw.command;

import com.gabriel.drawfx.command.Command;
import com.gabriel.drawfx.model.Shape;
import com.gabriel.drawfx.service.AppService;

import java.awt.*;

public class OutlineColorCommand implements Command {
    AppService appService;
    private final Shape shape;
    private final Color newColor;
    private final Color oldColor;
    private final Color newFill;
    private final Color oldFill;

    public OutlineColorCommand(AppService appService, Shape shape, Color newColor, Color newFill) {
        this.appService = appService;
        this.shape = shape;
        this.newColor = newColor;
        this.oldColor = shape.getColor();
        this.newFill = newFill;
        this.oldFill = shape.getFill();
    }
    @Override
    public void execute() {
        appService.setColor(newColor);
        appService.setFill(newFill);
//        shape.setColor(newColor);
//        shape.setFill(newFill);
    }

    @Override
    public void undo() {
        appService.setColor(oldColor);
        appService.setFill(oldFill);
//        shape.setColor(oldColor);
//        shape.setFill(oldFill);
    }

    @Override
    public void redo() {
        execute();
    }
}
