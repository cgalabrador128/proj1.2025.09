package com.gabriel.drawfx.model;

import com.gabriel.drawfx.SelectionMode;
import com.gabriel.drawfx.renderer.Renderer;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.sound.sampled.Line;
import java.awt.*;

@Data
public abstract class Shape {
    boolean selected = false;
    private SelectionMode selectionMode = SelectionMode.None;
    private int r = 2;
    private int id = 0;
    @Setter
    private Point location;
    private Point endpoint;
    private int width = 0;
    private int height = 0;
    private Color color = Color.RED;
    private Color fill;
    private int thickness;
    String text;
    Font font;

    private Renderer rendererService;

    private boolean isVisible = true;
    @Getter @Setter
    private boolean useGradient = false;
    private Color startGradientColor;
    private Color endGradientColor;

    public Shape(Point location){
        this.setLocation(location);
    }
    public Shape(Point location, Point endpoint){
        width = endpoint.x - location.x;
        height = endpoint.y - location.y;
        if(width<0){
            width = -width;
            endpoint.x -= width;
        }
        if(height < 0){
            height = -height;
            endpoint.y -= height;
        }
        this.setLocation(location);
        this.setEndpoint(getEndPoint(this));
    }
    public Shape(Point location, Point endpoint, int width, int height){
        this.setLocation(location);
        this.width = width;
        this.height = height;
        this.setEndpoint(getEndPoint(this));
    }

    public boolean equals (Shape shape){
        return (this.id == shape.id);
    }


    public Boolean getUseGradient() {
        return useGradient;
    }

    public Point getEndPoint(Shape shape){
        int endX = shape.getLocation().x + shape.getWidth();
        int endY = shape.getLocation().y + shape.getHeight();
        return new Point(endX, endY);
    }
}