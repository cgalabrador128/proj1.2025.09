package com.gabriel.draw.renderer;

import com.gabriel.draw.model.Ellipse;
import com.gabriel.drawfx.model.Shape;
import com.gabriel.drawfx.renderer.ShapeRenderer;

import java.awt.*;

public class EllipseRenderer extends ShapeRenderer {

    @Override
    public void render(Graphics g,  Shape shape, boolean xor) {
        Ellipse ellipse = (Ellipse) shape;

        if (!shape.isVisible()){
            return;
        }

        int x = shape.getLocation().x;
        int y = shape.getLocation().y;
        int width = shape.getWidth() ;
        int height = shape.getHeight();

        Graphics2D g2 = (Graphics2D) g;

        if (!xor && (shape.getFill() != null || shape.getUseGradient())) {

            if (shape.getUseGradient() && shape.getStartGradientColor() != null && shape.getEndGradientColor() != null) {
                GradientPaint gp = new GradientPaint(
                        x, y, shape.getStartGradientColor(),
                        x, y + height, shape.getEndGradientColor()
                );
                g2.setPaint(gp);
                g2.fillOval(x, y, width, height);

            } else if (shape.getFill() != null) {
                g2.setColor(shape.getFill());
                g2.fillOval(x, y, width, height);
            }
        }

        g2.setStroke(new BasicStroke(shape.getThickness()));
        if (xor) {
            g2.setXORMode(Color.WHITE);
        } else {
            g2.setColor(shape.getColor());
        }

        g2.drawOval(x, y, width, height);

        super.render(g, shape, xor);

        if (xor) {
            g2.setPaintMode();
        }
    }
}