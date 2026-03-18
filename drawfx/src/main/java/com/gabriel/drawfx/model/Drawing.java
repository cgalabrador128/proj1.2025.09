package com.gabriel.drawfx.model;

import com.gabriel.drawfx.DrawMode;
import com.gabriel.drawfx.ShapeMode;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
@Data
public class Drawing {
    Point location;
    Point end;
    private String filename;
    private String imageFilename;
    private Color color;
    private Color fill;
    int thickness  = 1;
    private ShapeMode shapeMode = ShapeMode.Rectangle;
    private DrawMode drawMode = DrawMode.Idle;
    private int searchRadius = 5;
    private Font font;
    int width;
    int height;
    List<Shape> shapes;
    Shape selectedShape;
    String text = "Default test";

    @Getter @Setter
    private boolean useGradient = false;
    private Color startGradientColor;
    private Color endGradientColor;

    public Drawing(){
        location  = new Point(0,0);
        end = new Point(0, 0);
        color = Color.RED;
        fill = Color.WHITE;
        font = new Font("Serif", Font.BOLD, 24);
        shapes = new ArrayList<>();
        imageFilename = null;
        filename = "Untitled";
        width = 100;
        height = 100;
        useGradient = false;
        startGradientColor = Color.WHITE;
        endGradientColor = Color.BLACK;
    }


    public boolean getUseGradient() {
        return useGradient;
    }
}