package com.gabriel.draw.command;

import com.gabriel.drawfx.command.Command;
import com.gabriel.drawfx.model.Shape;
import com.gabriel.drawfx.service.AppService;

public class DeleteCommand implements Command {
    AppService appService;
    Shape shape;
    public DeleteCommand (AppService appService, Shape shape){
        this.appService = appService;
        this.shape = shape;
    }

    @Override
    public void execute() {
        appService.delete(shape);
    }

    @Override
    public void undo() {
        appService.create(shape);
    }

    @Override
    public void redo() {
        execute();
    }
}
