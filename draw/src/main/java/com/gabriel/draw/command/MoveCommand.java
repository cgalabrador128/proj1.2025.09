package com.gabriel.draw.command;

import com.gabriel.drawfx.command.Command;
import com.gabriel.drawfx.service.AppService;
import com.gabriel.drawfx.model.Shape;
import com.gabriel.draw.model.Line;

import java.awt.*;

public class MoveCommand implements Command {
    AppService appService;
    Shape shape;
    Point start;
    Point end;
    Point oldS;
    Point oldE;

    public MoveCommand(AppService appService,
                       Shape shape,
                       Point start,
                       Point end,
                       Point oldS,
                       Point oldE){
        this.appService = appService;
        this.shape = shape;
        this.start = start;
        this.end = end;
        this.oldS  = oldS;
        this.oldE = oldE;
  /*      this.oldLoc = new Point(oldLoc);
        this.oldEnd = (oldEnd != null) ? new Point(oldEnd) : null;
        this.newLoc = new Point(newLoc);
        this.newEnd = (newEnd != null) ? new Point(newEnd) : null;*/

    }

    @Override
    public void execute() {
        /*selectShape.setLocation(new Point(newLoc));
        if (selectShape instanceof Line && newEnd != null) {
            ((Line) selectShape).setEndpoint(new Point(newEnd));
        }*/
        appService.move(shape, start, end);
    }

    @Override
    public void undo() {
        /*selectShape.setLocation(new Point(oldLoc));
        if (selectShape instanceof Line && oldEnd != null) {
            ((Line) selectShape).setEndpoint(new Point(oldEnd));
        }*/
        appService.move(shape, oldS, oldE);
    }

    @Override
    public void redo() {
        execute();
    }
}