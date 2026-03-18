package com.gabriel.draw.controller;

import com.gabriel.draw.command.MoveCommand;
import com.gabriel.draw.command.ScaleCommand;
import com.gabriel.draw.component.PropertySheet;
import com.gabriel.draw.model.*;
import com.gabriel.draw.model.Rectangle;
import com.gabriel.draw.view.DrawingStatusPanel;
import com.gabriel.drawfx.DrawMode;
import com.gabriel.drawfx.command.Command;
import com.gabriel.drawfx.command.CommandService;
import com.gabriel.drawfx.model.Drawing;
import com.gabriel.drawfx.service.MoverService;
import com.gabriel.drawfx.util.Normalizer;
import com.gabriel.drawfx.SelectionMode;
import com.gabriel.drawfx.ShapeMode;
import com.gabriel.draw.view.DrawingView;
import com.gabriel.drawfx.service.AppService;
import com.gabriel.drawfx.model.Shape;
import lombok.Setter;

import javax.swing.*;
import java.util.List;
import java.awt.*;
import java.awt.event.*;

public class DrawingController  implements MouseListener, MouseMotionListener, KeyListener {
    Point start;
    private Point end;
    private Point oldS;
    private Point oldE;
    private final AppService appService;
    private final Drawing drawing;

    @Setter
    private DrawingView drawingView;

    @Setter
    private DrawingStatusPanel drawingStatusPanel;

    @Setter
    private PropertySheet propertySheet;

    private Shape currentShape = null;

    public DrawingController(AppService appService, DrawingView drawingView){
        this.appService = appService;
        this.drawing = appService.getDrawing();
        this.drawingView = drawingView;
        drawingView.addMouseListener(this);
        drawingView.addMouseMotionListener(this);
        drawingView.addKeyListener(this);
    }
    @Override
    public void mouseClicked(MouseEvent e) {

    }
    @Override
    public void mousePressed(MouseEvent e) {
        oldE = e.getPoint();
        if(appService.getDrawMode() == DrawMode.Idle) {
            start = e.getPoint();
            ShapeMode currentShapeMode = appService.getShapeMode();
            if(currentShapeMode == ShapeMode.Select) {
                appService.search(start, !e.isControlDown());
            } else if (currentShapeMode == ShapeMode.Delete){
                List<Shape> shapes = drawing.getShapes();
                for (int i = shapes.size() - 1; i >= 0; i--) {
                    Shape a = shapes.get(i);
                    java.awt.Rectangle bounds = getBounds(a);
                    if (bounds.contains(start)){
                        appService.delete(a);
                        break;
                    }
                }
                drawingView.repaint();
                propertySheet.populateTable(appService);
            }
            else {
                if(currentShape!=null){
                    currentShape.setSelected(false);
                }
                switch (currentShapeMode) {
                    case Line:
                        currentShape = new Line(start);
                        currentShape.setColor(appService.getColor());
                        currentShape.getRendererService().render(drawingView.getGraphics(), currentShape, false);
                        break;
                    case Rectangle:
                        currentShape = new Rectangle(start);
                        currentShape.setColor(appService.getColor());
                        currentShape.getRendererService().render(drawingView.getGraphics(), currentShape, false);
                        break;
                    case Text:
                        currentShape = new Text(start);
                        currentShape.setColor(appService.getColor());
                        currentShape.getRendererService().render(drawingView.getGraphics(), currentShape, true);
                        appService.setDrawMode(DrawMode.MousePressed);
                        break;
                    case Ellipse:
                        currentShape = new Ellipse(start);
                        currentShape.setColor(appService.getColor());
                        currentShape.getRendererService().render(drawingView.getGraphics(), currentShape, false);
                        break;
                    case Image:
                        currentShape = new Picture(start, start, drawing.getImageFilename());
                        currentShape.setColor(appService.getColor());
                        currentShape.setThickness(appService.getThickness());
                        currentShape.getRendererService().render(drawingView.getGraphics(), currentShape, false);
                        break;
                }
            }
            appService.setDrawMode(DrawMode.MousePressed);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        end = e.getPoint();
        if(appService.getDrawMode() == DrawMode.MousePressed) {

            ShapeMode currentMode = appService.getShapeMode();

            if (currentMode == ShapeMode.Delete){
                appService.setDrawMode(DrawMode.Idle);
            }
            else if (currentMode == ShapeMode.Select) {
                Shape selectedShape = drawing.getSelectedShape();
                if (selectedShape != null) {
                    if (selectedShape.getSelectionMode() == SelectionMode.None) {
                        List<Shape> shapes = drawing.getShapes();
                        for (Shape shape : shapes) {
                            if (shape.isSelected()) {
                                shape.getRendererService().render(drawingView.getGraphics(), shape, true);
                                appService.move(shape, start, end);
                                shape.getRendererService().render(drawingView.getGraphics(), shape, false);
                                Command command  = new MoveCommand(appService, shape, start, end, start, oldE);
                                CommandService.ExecuteCommand(command);
                            }
                        }
                    } else {
                        selectedShape.getRendererService().render(drawingView.getGraphics(), selectedShape, true);
                        appService.scale(selectedShape, start, end);
                        selectedShape.getRendererService().render(drawingView.getGraphics(), selectedShape, true);
                        Normalizer.normalize(selectedShape);
                        Command command  = new ScaleCommand(appService, selectedShape, start, end, oldE);
                        CommandService.ExecuteCommand(command);

                    }
                }
            }
            else {
                if (currentShape != null) {
                    currentShape.getRendererService().render(drawingView.getGraphics(), currentShape, true);
                    currentShape.setText(drawing.getText());
                    currentShape.setFont(drawing.getFont());
                    Normalizer.normalize(currentShape);
                    appService.create(currentShape);
                    currentShape.setSelected(true);
                    drawing.setSelectedShape(currentShape);
                    drawingView.repaint();
                }
            }

            appService.setDrawMode(DrawMode.Idle);
        }
        propertySheet.populateTable(appService);
        drawingView.repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if(appService.getDrawMode() == DrawMode.MousePressed) {
            end = e.getPoint();
            if(drawing.getShapeMode() == ShapeMode.Select){ // move and scale
                Shape selectedShape = drawing.getSelectedShape();
//                oldS = selectedShape.getLocation();
                if(selectedShape != null){
                    if(selectedShape.getSelectionMode() == SelectionMode.None){
                        List<Shape> shapes =drawing.getShapes();
                        for(Shape shape : shapes) {
                            if (shape.isSelected()) {
                                shape.getRendererService().render(drawingView.getGraphics(), shape, true);
//                                MoverService moverService = null;
                                appService.move(shape, start, end);
//                                appService.move(shape, start, e.getPoint());
                                shape.getRendererService().render(drawingView.getGraphics(), shape, true);
                            }
                        }
                    } //None
                    else {
//                        int newX1, newY1, newX2, newY2;
                        selectedShape.getRendererService().render(drawingView.getGraphics(), selectedShape, true);
                        appService.scale(selectedShape, start, end);
                        selectedShape.getRendererService().render(drawingView.getGraphics(), selectedShape, true);
                    }
                }
                start = end;

            }
            else {
                if (currentShape != null) {// create shape DO NOT EDIT
                    currentShape.getRendererService().render(drawingView.getGraphics(), currentShape, true);
                    appService.scale(currentShape, e.getPoint());
                    currentShape.getRendererService().render(drawingView.getGraphics(), currentShape, true);
                }
            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        drawingStatusPanel.setPoint(e.getPoint());
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_DELETE) {
            List<Shape> shapesToDelete = appService.getSelectedShapes();
            for (Shape shape : shapesToDelete) {
                appService.delete(shape);
            }
            drawingView.repaint();
            propertySheet.populateTable(appService);
        } else if (e.getKeyCode() == KeyEvent.VK_Z && (e.getModifiersEx() & InputEvent.CTRL_DOWN_MASK) != 0 && (e.getModifiersEx() & InputEvent.SHIFT_DOWN_MASK) == 0) {
            appService.undo();
            drawingView.repaint();
            propertySheet.populateTable(appService);
        } else if ((e.getKeyCode() == KeyEvent.VK_Y && (e.getModifiersEx() & InputEvent.CTRL_DOWN_MASK) != 0) ||
                (e.getKeyCode() == KeyEvent.VK_Z && (e.getModifiersEx() & InputEvent.CTRL_DOWN_MASK) != 0 && (e.getModifiersEx() & InputEvent.SHIFT_DOWN_MASK) != 0)) {
            appService.redo();
            drawingView.repaint();
            propertySheet.populateTable(appService);
        }
    }


    @Override
    public void keyReleased(KeyEvent e) {

    }

    public java.awt.Rectangle getBounds(Shape shape){
        return new java.awt.Rectangle(shape.getLocation().x, shape.getLocation().y, shape.getWidth(), shape.getHeight());
    }
}