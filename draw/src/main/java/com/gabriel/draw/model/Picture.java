package com.gabriel.draw.model;

import com.gabriel.draw.renderer.PictureRenderer;
import com.gabriel.drawfx.model.Shape;
import java.awt.Point;

public class Picture extends Shape {

    private String filename;

    public Picture(Point start, Point end, String filename) {
        super(start, end);
        this.filename = filename;
        setRendererService(new PictureRenderer());
    }

    public String getFilename() {
        return filename;
    }
}