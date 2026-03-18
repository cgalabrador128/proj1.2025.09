package com.gabriel.draw.component;

import com.gabriel.draw.controller.PropertyEventListener;
import com.gabriel.draw.model.Rectangle;
import com.gabriel.drawfx.ShapeMode;
import com.gabriel.property.PropertyOptions;
import com.gabriel.property.PropertyPanel;
import com.gabriel.property.cell.SelectionCellComponent;
import com.gabriel.property.property.*;
import com.gabriel.property.property.selection.Item;
import com.gabriel.property.property.selection.SelectionProperty;
import com.gabriel.property.validator.CompoundValidator;
import com.gabriel.property.validator.StringValidator;
import com.gabriel.property.validator.doubleNumber.DoubleRangeValidator;
import com.gabriel.property.validator.doubleNumber.DoubleValidator;
import com.gabriel.property.validator.doubleNumber.DoubleZeroPolicyValidator;
import com.gabriel.drawfx.model.Shape;
import com.gabriel.drawfx.service.AppService;

import java.util.ArrayList;
import java.util.Arrays;
import java.awt.Color;

public class PropertySheet extends PropertyPanel {
    PropertyPanel propertyTable;
    private SelectionProperty shapeProp;
    Item RectangleItem;
    Item EllipseItem;
    Item LineItem;
    Item TextItem;
    Item SelectItem;
    Item ImageItem;


    public void setShapeProp(ShapeMode shapeMode ){
        SelectionCellComponent  selectionComponent =  propertyTable.getSelectionCellComponent();
        if (shapeMode ==ShapeMode.Rectangle) {
            selectionComponent.setCellEditorValue(RectangleItem);
        } else if (shapeMode == ShapeMode.Ellipse) {
            selectionComponent.setCellEditorValue(EllipseItem);
        } else if (shapeMode == ShapeMode.Line) {
            selectionComponent.setCellEditorValue(LineItem);
        } else if (shapeMode == ShapeMode.Select) {
            selectionComponent.setCellEditorValue(SelectItem);
        } else if (shapeMode == ShapeMode.Image) {
            selectionComponent.setCellEditorValue(ImageItem);
        }
    }

    public PropertySheet(PropertyOptions options){
        super(options);
        shapeProp = new SelectionProperty<>(
                "Current Shape",
                new ArrayList<>(Arrays.asList(
                        new Item<>(ShapeMode.Rectangle, "Rectangle"),
                        new Item<>(ShapeMode.Ellipse, "Ellipse"),
                        new Item<>(ShapeMode.Line, "Line"),
                        new Item<>(ShapeMode.Text, "Text"),
                        new Item<>(ShapeMode.Select, "Select"),
                        new Item<>(ShapeMode.Image, "Image")
                ))
        );
    }

    public void populateTable(AppService appService) {
        propertyTable = this;
        propertyTable.addEventListener(new PropertyEventListener(appService));

        Shape shape  = appService.getSelectedShape();

        if ( shape == null) {
            propertyTable.clear();
            String objectType = "Drawing";
            StringProperty targetProp = new StringProperty("Object Type", objectType);
            propertyTable.addProperty(targetProp);

            RectangleItem = new Item<ShapeMode>(ShapeMode.Rectangle, "Rectangle");
            EllipseItem = new Item<ShapeMode>(ShapeMode.Ellipse, "Ellipse");
            LineItem =    new Item<ShapeMode>(ShapeMode.Line, "Line");
            SelectItem =    new Item<ShapeMode>(ShapeMode.Select, "Select");
            TextItem =    new Item<ShapeMode>(ShapeMode.Text, "Text");
            Item DeleteItem = new Item<ShapeMode>(ShapeMode.Delete, "Delete");
            ImageItem = new Item<ShapeMode>(ShapeMode.Image, "Image");

            shapeProp = new SelectionProperty<>(
                    "Current Shape",
                    new ArrayList<>(Arrays.asList(
                            RectangleItem,
                            EllipseItem,
                            LineItem,
                            SelectItem,
                            TextItem,
                            DeleteItem,
                            ImageItem
                    ))
            );
            propertyTable.addProperty(shapeProp);

            SelectionCellComponent  selectionComponent =  propertyTable.getSelectionCellComponent();

            ShapeMode shapeMode = appService.getShapeMode();
            if(shapeMode == ShapeMode.Rectangle) {
                selectionComponent.setCellEditorValue(RectangleItem);
            }
            else if(shapeMode == ShapeMode.Ellipse) {
                selectionComponent.setCellEditorValue(EllipseItem);
            }
            else if(shapeMode == ShapeMode.Line) {
                selectionComponent.setCellEditorValue(LineItem);
            }
            else if(shapeMode == ShapeMode.Select) {
                selectionComponent.setCellEditorValue(SelectItem);
            }
            else if (shapeMode == ShapeMode.Delete) {
                selectionComponent.setCellEditorValue(DeleteItem);
            }
            else if (shapeMode == ShapeMode.Image) {
                selectionComponent.setCellEditorValue(ImageItem);
            }

            shapeProp.setValue(shape);
            ColorProperty currentColorProp = new ColorProperty("Fore color", appService.getColor());
            propertyTable.addProperty(currentColorProp);

            ColorProperty currentFillProp = new ColorProperty("Fill color", appService.getFill());
            propertyTable.addProperty(currentFillProp);

            IntegerProperty lineThicknessProp = new IntegerProperty("Line Thickness", appService.getThickness());
            propertyTable.addProperty(lineThicknessProp);

            BooleanProperty gradientProp = new BooleanProperty("Use Gradient", appService.getUseGradient());
            propertyTable.addProperty(gradientProp);

            ColorProperty startGradientProp = new ColorProperty("Start Gradient", appService.getStartGradientColor());
            propertyTable.addProperty(startGradientProp);

            ColorProperty endGradientProp = new ColorProperty("End Gradient", appService.getEndGradientColor());
            propertyTable.addProperty(endGradientProp);

            IntegerProperty xlocPropstart = new IntegerProperty("X Start Location", appService.getXLocation());
            propertyTable.addProperty(xlocPropstart);

            IntegerProperty ylocPropstart = new IntegerProperty("Y Start Location", appService.getYLocation());
            propertyTable.addProperty(ylocPropstart);

            IntegerProperty xlocPropend = new IntegerProperty("X End Location", appService.getXEnd());
            propertyTable.addProperty(xlocPropend);

            IntegerProperty ylocPropend = new IntegerProperty("Y End Location", appService.getYEnd());
            propertyTable.addProperty(ylocPropend);

            IntegerProperty width = new IntegerProperty("Width", appService.getWidth());
            propertyTable.addProperty(width );

            IntegerProperty height = new IntegerProperty("Height", appService.getHeight());
            propertyTable.addProperty(height);

            BooleanProperty prop3 = new BooleanProperty("Boolean", true);
            propertyTable.addProperty(prop3);

            FloatProperty prop4 = new FloatProperty("Float", 1.2f);
            propertyTable.addProperty(prop4);

            StringProperty prop5 = new StringProperty("String", "Test string");
            propertyTable.addProperty(prop5);

            StringProperty prop6 = new StringProperty("String 2", "test", new StringValidator(
                    new String[]{"test", "test 2", "foo"}
            ));
            propertyTable.addProperty(prop6);

            DoubleProperty prop8 = new DoubleProperty("Double", 2.34,
                    new CompoundValidator(
                            new DoubleValidator(),
                            new DoubleRangeValidator(-1.2, 45.33, true, false),
                            new DoubleZeroPolicyValidator(false)
                    )
            );
            ActionProperty prop9 = new ActionProperty("Press me", "Press", () -> {
                System.out.println("Pressed");
            });
            propertyTable.addProperty(prop9);
        }
        else {
            propertyTable.clear();
            StringProperty targetProp = new StringProperty("Object Type", "Shape");
            propertyTable.addProperty(targetProp);

            RectangleItem = new Item<ShapeMode>(ShapeMode.Rectangle, "Rectangle");
            EllipseItem = new Item<ShapeMode>(ShapeMode.Ellipse, "Ellipse");
            LineItem =    new Item<ShapeMode>(ShapeMode.Line, "Line");
            SelectItem =    new Item<ShapeMode>(ShapeMode.Select, "Select");
            Item DeleteItem = new Item<ShapeMode>(ShapeMode.Delete, "Delete");
            ImageItem = new Item<ShapeMode>(ShapeMode.Image, "Image");

            shapeProp = new SelectionProperty<>(
                    "Current Shape",
                    new ArrayList<>(Arrays.asList(
                            RectangleItem,
                            EllipseItem,
                            LineItem,
                            SelectItem,
                            DeleteItem,
                            ImageItem
                    ))
            );
            propertyTable.addProperty(shapeProp);

            SelectionCellComponent  selectionComponent =  propertyTable.getSelectionCellComponent();

            String className = shape.getClass().getSimpleName();
            if(className.equals("Rectangle")) {
                selectionComponent.setCellEditorValue(RectangleItem);
            }
            else if(className.equals("Ellipse")) {
                selectionComponent.setCellEditorValue(EllipseItem);
            }
            else if(className.equals("Line")) {
                selectionComponent.setCellEditorValue(LineItem);
            }
            else if(className.equals("Picture")) {
                selectionComponent.setCellEditorValue(ImageItem);
            }

            IntegerProperty xlocPropstart = new IntegerProperty("X Start Location", appService.getXLocation());
            propertyTable.addProperty(xlocPropstart);

            IntegerProperty ylocPropstart = new IntegerProperty("Y Start Location", appService.getYLocation());
            propertyTable.addProperty(ylocPropstart);

            IntegerProperty xlocPropend = new IntegerProperty("X End Location", appService.getXEnd());
            propertyTable.addProperty(xlocPropend);

            IntegerProperty ylocPropend = new IntegerProperty("Y End Location", appService.getYEnd());
            propertyTable.addProperty(ylocPropend);

            IntegerProperty widthProp = new IntegerProperty("Width", shape.getWidth());
            propertyTable.addProperty(widthProp);

            IntegerProperty heightProp = new IntegerProperty("Height", shape.getHeight());
            propertyTable.addProperty(heightProp);

            ColorProperty currentColorProp = new ColorProperty("Fore color", shape.getColor());
            propertyTable.addProperty(currentColorProp);

            ColorProperty currentFillProp = new ColorProperty("Fill color", shape.getFill());
            propertyTable.addProperty(currentFillProp);

            IntegerProperty lineThicknessProp = new IntegerProperty("Line Thickness", shape.getThickness());
            propertyTable.addProperty(lineThicknessProp);

            BooleanProperty visibleProp = new BooleanProperty("Visible", shape.isVisible());
            propertyTable.addProperty(visibleProp);

            BooleanProperty selectedProp = new BooleanProperty("Selected", shape.isSelected());
            propertyTable.addProperty(selectedProp);

            BooleanProperty gradientProp = new BooleanProperty("Use Gradient", shape.getUseGradient());
            propertyTable.addProperty(gradientProp);

            ColorProperty startGradientProp = new ColorProperty("Start Gradient", shape.getStartGradientColor());
            propertyTable.addProperty(startGradientProp);

            ColorProperty endGradientProp = new ColorProperty("End Gradient", shape.getEndGradientColor());
            propertyTable.addProperty(endGradientProp);

            StringProperty textProp= new StringProperty("Text", shape.getText());
            propertyTable.addProperty(textProp);

            StringProperty fontFamilyProp= new StringProperty("Font Family", shape.getFont().getFamily());
            propertyTable.addProperty(fontFamilyProp);

            IntegerProperty fontStyleProp= new IntegerProperty("Font Style", shape.getFont().getStyle());
            propertyTable.addProperty(fontStyleProp);

            IntegerProperty fontSizeProp= new IntegerProperty("Font size", shape.getFont().getSize());
            propertyTable.addProperty(fontSizeProp);

            BooleanProperty prop3 = new BooleanProperty("Boolean", true);
            FloatProperty prop4 = new FloatProperty("Float", 1.2f);
            StringProperty prop5 = new StringProperty("String", "Test string");
            StringProperty prop6 = new StringProperty("String 2", "test", new StringValidator(
                    new String[]{"test", "test 2", "foo"}
            ));
            DoubleProperty prop8 = new DoubleProperty("Double", 2.34,
                    new CompoundValidator(
                            new DoubleValidator(),
                            new DoubleRangeValidator(-1.2, 45.33, true, false),
                            new DoubleZeroPolicyValidator(false)
                    )
            );
            ActionProperty imageProp = new ActionProperty("Image", "Image", () -> {
                System.out.println("Pressed");
                appService.setImageFileename();
            });
            propertyTable.addProperty(imageProp);
        }
    }
}