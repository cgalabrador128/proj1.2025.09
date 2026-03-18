package com.gabriel.draw.command;

import com.gabriel.drawfx.command.Command;
import com.gabriel.drawfx.model.Shape;
import com.gabriel.drawfx.service.AppService;

import java.awt.*;

public class ScaleCommand implements Command {

    Shape shape;
    Point start;
    Point end;
    Point oldE;
    AppService appService;

    public ScaleCommand(AppService appService, Shape shape,  Point start, Point end, Point oldE){
        this.shape = shape;
        this.appService = appService;
        this.start = start;
        this.end = end;
        this.oldE = oldE;
    }

    @Override
    public void execute() {
        appService.scale(shape, start, end);
    }

    @Override
    public void undo() {
        appService.scale(shape, start, oldE);
    }

    @Override
    public void redo() {
        execute();
    }
}