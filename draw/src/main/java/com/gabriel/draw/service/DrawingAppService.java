package com.gabriel.draw.service;

import com.gabriel.draw.model.Line;
import com.gabriel.draw.view.DrawingView;
import com.gabriel.drawfx.DrawMode;
import com.gabriel.drawfx.SelectionMode;
import com.gabriel.drawfx.ShapeMode;
import com.gabriel.drawfx.model.Drawing;
import com.gabriel.drawfx.model.Shape;
import com.gabriel.drawfx.service.*;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.awt.*;

public class DrawingAppService implements AppService {

    final private Drawing drawing;

    @Setter
    DrawingView drawingView;
    MoverService moverService;
    ScalerService scalerService;
    SearchService searchService;
    XmlDocumentService xmlDocumentService;

    DocumentService documentService;
    public DrawingAppService(){
        drawing = new Drawing();
        moverService = new MoverService();
        scalerService = new ScalerService();
        searchService = new SearchService();
        xmlDocumentService = new XmlDocumentService(drawing);
        drawing.setDrawMode(DrawMode.Idle);
        drawing.setShapeMode(ShapeMode.Ellipse);
    }


    public DrawingView getView() {
        return this.drawingView;
    }

    @Override
    public void undo() {

    }

    @Override
    public void redo() {

    }

    @Override
    public ShapeMode getShapeMode() {
        return drawing.getShapeMode();
    }

    @Override
    public void setShapeMode(ShapeMode shapeMode) {
        drawing.setShapeMode(shapeMode);
    }

    @Override
    public DrawMode getDrawMode() {
        return drawing.getDrawMode();
    }

    @Override
    public void setDrawMode(DrawMode drawMode) {
        this.drawing.setDrawMode(drawMode);
    }

    @Override
    public Color getColor() {
        return drawing.getColor();
    }

    @Override
    public void setColor(Color color) {
        Shape selectedShape = getSelectedShape();
        if(selectedShape == null){
            drawing.setColor(color);
        }
        else {
            selectedShape.setColor(color);
        }
        drawingView.repaint();
    }

    @Override
    public Color getFill(){
        Shape selectedShape = getSelectedShape();
        if (selectedShape == null) {
            return drawing.getFill();
        } else {
            return selectedShape.getFill();
        }
    }

    @Override
    public void setFill(Color color) {
        Shape selectedShape = getSelectedShape();
        if (selectedShape == null) {
            drawing.setFill(color);
        } else {
            selectedShape.setFill(color);
        }
        drawingView.repaint();
    }

    @Override
    public void move(Shape shape, Point start){
        moverService.move(drawing, shape, start);
    }

    @Override
    public void move(Shape shape, Point start, Point end) {
        moverService.move(drawing, start, end);
    }

    @Override
    public void move(Point start, Point newLoc) {
        moverService.move(drawing, start, newLoc);
    }

    @Override
    public void scale(Shape shape, Point start, Point end) {
        scalerService.scale(drawing, start, end);
    }

    @Override
    public void scale(Shape shape, Point end) {
        scalerService.scale(shape, end);
    }

    @Override
    public void create(Shape shape) {
        this.drawing.getShapes().add(shape);
        shape.setColor(drawing.getColor());
        shape.setR(drawing.getSearchRadius());
        shape.setFill(drawing.getFill());
        shape.setThickness(drawing.getThickness());
        shape.setId(this.drawing.getShapes().size());
        shape.setFont(drawing.getFont());
    }

    @Override
    public void delete(Shape shape) {
        drawing.getShapes().remove(shape);
        if (drawing.getSelectedShape() == shape){
            drawing.setSelectedShape(null);
        }
    }

    @Override
    public void close() {
        System.exit(0);
    }

    @Override
    public Drawing getDrawing() {
        return drawing;
    }

    @Override
    public void setDrawing(Drawing drawing) {

    }

    @Override
    public int getSearchRadius() {
        return drawing.getSearchRadius();
    }

    @Override
    public void setSearchRadius(int radius) {
        drawing.setSearchRadius(radius);
    }

    @Override
    public void search(Point p) {
        searchService.search(this,p);
    }

    @Override
    public void search(Point p, boolean single) {
        searchService.search(this,p, single);
    }

    @Override
    public void open(String filename) {
        xmlDocumentService.open(filename);
    }

    @Override
    public void save() {
        xmlDocumentService.saveAs(drawing.getFilename());
    }

    @Override
    public void saveas(String filename) {
        xmlDocumentService.saveAs(filename);
    }

    @Override
    public void newDrawing() {

    }

    @Override
    public String getFileName() {
        return drawing.getFilename();
    }

    @Override
    public void select(Shape selectedShape) {
        List<Shape> selectedShapes = drawing.getShapes();
        for(Shape shape : selectedShapes){
            if(shape.equals(selectedShape)){
                shape.setSelected(true);
            }
            else {
                shape.setSelected(false);
            }
        }
    }

    @Override
    public void unSelect(Shape selectedShape) {
        List<Shape> shapes = drawing.getShapes();
        for (Shape shape : shapes){
            if(shape.getId() == selectedShape.getId()) {
                shape.setSelected(false);
            }
        }
    }

    @Override
    public Shape getSelectedShape() {
        List<Shape> shapes = drawing.getShapes();
        for (Shape shape : shapes){
            if(shape.isSelected()){
                return shape;
            }
        }
        return null;
    }
    @Override
    public List<Shape> getSelectedShapes() {
        List<Shape> shapes = drawing.getShapes();
        List<Shape> selectedShapes = new ArrayList<>();
        for (Shape shape : shapes){
            if(shape.isSelected()){
                selectedShapes.add(shape);
            }
        }
        return selectedShapes;
    }
    @Override
    public void clearSelections(){
        List<Shape> shapes = drawing.getShapes();
        for (Shape shape : shapes){
            shape.setSelected(false);
            shape.setSelectionMode(SelectionMode.None);
        }
        drawing.setSelectedShape(null);
        drawingView.repaint();
    }

    @Override
    public void setThickness(int thickness) {
        Shape seleectedShape = drawing.getSelectedShape();
        if(seleectedShape == null ){
            drawing.setThickness(thickness);
        }
        else {
            seleectedShape.setThickness(thickness);
        }
        drawingView.repaint();
    }

    @Override
    public int getThickness() {
        Shape seleectedShape = drawing.getSelectedShape();
        if(seleectedShape == null ){
            return drawing.getThickness();
        }
        else {
            return seleectedShape.getThickness();
        }
    }

    @Override
    public void setXLocation(int xLocation) {
        Shape seleectedShape = drawing.getSelectedShape();
        if(seleectedShape == null ){
            drawing.getLocation().x = xLocation;
        }
        else {
            seleectedShape.getLocation().x = xLocation;
        }
        drawingView.repaint();
    }

    @Override
    public int getXLocation() {
        Shape selectedShape = drawing.getSelectedShape();
        if(selectedShape == null ){
            return drawing.getLocation().x;
        }
        else {
            return selectedShape.getLocation().x;
        }
    }

    @Override
    public void setYLocation(int yLocation) {
        Shape selectedShape = drawing.getSelectedShape();
        if(selectedShape == null ){
            drawing.getLocation().y = yLocation;
        }
        else {
            selectedShape.getLocation().y = yLocation;
        }
        drawingView.repaint();
    }

    @Override
    public int getYLocation() {
        Shape seleectedShape = drawing.getSelectedShape();
        if(seleectedShape == null ){
            return drawing.getLocation().y;
        }
        else {
            return seleectedShape.getLocation().y;
        }
    }

    @Override
    public void setXEnd(int xEnd){
        Shape selectedShape = drawing.getSelectedShape();
        if(selectedShape == null ){
            if (drawing.getEnd()!=null)  drawing.getEnd().x = xEnd;
        } else if (selectedShape instanceof Line){
            Point endpoint = ((Line) selectedShape).getEndpoint();
            if (endpoint!= null) endpoint.x = xEnd;
        }
        else {
            selectedShape.getLocation().x = xEnd - selectedShape.getWidth();
        }
        drawingView.repaint();
    }

    @Override
    public int getXEnd(){
        Shape seleectedShape = drawing.getSelectedShape();
        if(seleectedShape == null ){
            return (drawing.getEnd() != null) ? drawing.getEnd().x : 0;
        } else if (seleectedShape instanceof Line){
            Point p2 = ((Line) seleectedShape).getEndpoint();
            if (p2 != null){
                return p2.x;}
            else{
                return seleectedShape.getLocation().x;
            }
        }
        else {
            return seleectedShape.getLocation().x + seleectedShape.getWidth();
        }
    }

    @Override
    public void setYEnd(int yEnd){
        Shape selectedShape = drawing.getSelectedShape();
        if(selectedShape == null ){
            if(drawing.getEnd()!=null) drawing.getEnd().y = yEnd;
        }else if (selectedShape instanceof Line){
            Point endpoint = ((Line) selectedShape).getEndpoint();
            if (endpoint!= null) endpoint.y = yEnd;
        }
        else {
            selectedShape.getLocation().y = yEnd - selectedShape.getHeight();
        }
        drawingView.repaint();
    }


    @Override
    public int getYEnd(){
        Shape seleectedShape = drawing.getSelectedShape();
        if(seleectedShape == null ){
            return (drawing.getEnd() != null) ? drawing.getEnd().y : 0;
        } else if (seleectedShape instanceof Line){
            Point p2 = ((Line) seleectedShape).getEndpoint();
            if (p2 != null){
                return p2.y;}
            else{
                return seleectedShape.getLocation().y;
            }
        }
        else {
            return seleectedShape.getLocation().y + seleectedShape.getHeight();
        }
    }

    @Override
    public void setWidth(int width) {
        Shape selectedShape = drawing.getSelectedShape();
        if(selectedShape == null ){
            drawing.setWidth(width);
        }
        else {
            selectedShape.setWidth(width);
        }
        drawingView.repaint();
    }

    @Override
    public int getWidth() {
        Shape selectedShape = drawing.getSelectedShape();
        if(selectedShape == null ){
            return drawing.getWidth();
        }
        else {
            return selectedShape.getWidth();
        }
    }

    @Override
    public void setHeight(int height) {
        Shape selectedShape = drawing.getSelectedShape();
        if(selectedShape == null ){
            drawing.setHeight(height);
        }
        else {
            selectedShape.setHeight(height);
        }
        drawingView.repaint();
    }

    @Override
    public int getHeight() {
        Shape selectedShape = drawing.getSelectedShape();
        if(selectedShape == null ){
            return drawing.getHeight();
        }
        else {
            return selectedShape.getHeight();
        }
    }

    @Override
    public void setImageFileename() {

    }

    @Override
    public void setImageFileename(String filename) {
        drawing.setImageFilename(filename);
    }

    @Override
    public String getImageFileename() {
        return drawing.getImageFilename();
    }

    @Override
    public void setText(String text) {
        Shape selectedShape = drawing.getSelectedShape();
        if(selectedShape == null ){
            drawing.setText(text);
        }
        else {
            selectedShape.setText(text);
        }
        drawingView.repaint();
    }

    @Override
    public void setFontSize(int fontSize) {
        Shape selectedShape = drawing.getSelectedShape();
        if(selectedShape == null ){
            Font font = new Font(drawing.getFont().getFamily(), drawing.getFont().getStyle(), fontSize);
            drawing.setFont(font);
        }
        else {
            Font font = new Font(selectedShape.getFont().getFamily(), selectedShape.getFont().getStyle(), fontSize);
            selectedShape.setFont(font);
        }
        drawingView.repaint();
    }

    @Override
    public void setFontFamily(String fontFamily) {
        Shape selectedShape = drawing.getSelectedShape();
        if(selectedShape == null ){
            Font font = new Font(fontFamily, drawing.getFont().getStyle(), drawing.getFont().getSize());
            drawing.setFont(font);
        }
        else {
            Font font = new Font(fontFamily, selectedShape.getFont().getStyle(), selectedShape.getFont().getSize());
            selectedShape.setFont(font);
        }
        drawingView.repaint();
    }

    @Override
    public void setVisible(boolean visible) {
        Shape selectedShape = getSelectedShape();
        if (selectedShape != null) {
            selectedShape.setVisible(visible);
        }
        drawingView.repaint();
    }

    @Override
    public void setSelected(boolean selected) {
        Shape selectedShape = getSelectedShape();
        if (selectedShape != null) {
            selectedShape.setSelected(selected);
        }
        drawingView.repaint();
    }

    @Override
    public boolean getUseGradient() {
        Shape selectedShape = getSelectedShape();
        if (selectedShape == null) {
            return drawing.getUseGradient();
        } else {
            return selectedShape.getUseGradient();
        }
    }

    @Override
    public void setUseGradient(boolean useGradient) {
        Shape selectedShape = getSelectedShape();
        if (selectedShape == null) {
            drawing.setUseGradient(useGradient);
        } else {
            selectedShape.setUseGradient(useGradient);
        }
        drawingView.repaint();
    }

    @Override
    public Color getStartGradientColor() {
        Shape selectedShape = getSelectedShape();
        if (selectedShape == null) {
            return drawing.getStartGradientColor();
        } else {
            return selectedShape.getStartGradientColor();
        }
    }

    @Override
    public void setStartGradientColor(Color color) {
        Shape selectedShape = getSelectedShape();
        if (selectedShape == null) {
            drawing.setStartGradientColor(color);
        } else {
            selectedShape.setStartGradientColor(color);
        }
        drawingView.repaint();
    }


    @Override
    public Color getEndGradientColor() {
        Shape selectedShape = getSelectedShape();
        if (selectedShape == null) {
            return drawing.getEndGradientColor();
        } else {
            return selectedShape.getEndGradientColor();
        }
    }

    @Override
    public void setEndGradientColor(Color color) {
        Shape selectedShape = getSelectedShape();
        if (selectedShape == null) {
            drawing.setEndGradientColor(color);
        } else {
            selectedShape.setEndGradientColor(color);
        }
        drawingView.repaint();
    }
}