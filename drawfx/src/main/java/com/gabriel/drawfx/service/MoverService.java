package com.gabriel.drawfx.service;

import com.gabriel.drawfx.model.Drawing;
import com.gabriel.drawfx.model.Shape;

import java.awt.*;
import java.util.List;

public final class MoverService {

    public void  move(Shape shape, Point start, Point end){
        int dx = end.x - start.x;
        int dy = end.y - start.y;
        shape.getLocation().x +=dx;
        shape.getLocation().y += dy;
     }
    public void  move(Drawing drawing, Point start, Point end){
        List<Shape> shapes = drawing.getShapes();
        for (Shape shape : shapes ){
            if(shape.isSelected()){
                move(shape, start, end);
            }
        }
    }
    public void move(Shape shape, Point start){
        shape.getLocation().x = start.x;
        shape.getLocation().y = start.y;
    }
    public void move(Drawing drawing, Shape shape, Point start){
        List<Shape> shapes = drawing.getShapes();
        for (Shape a : shapes){
            if (a == shape){
                move (a, start);
            }
        }
    }

}
