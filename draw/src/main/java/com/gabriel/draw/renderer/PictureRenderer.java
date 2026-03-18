package com.gabriel.draw.renderer;

import com.gabriel.draw.model.Picture;
import com.gabriel.drawfx.model.Shape;
import com.gabriel.drawfx.renderer.ShapeRenderer;

import javax.swing.ImageIcon;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Color;

public class PictureRenderer extends ShapeRenderer {

    @Override
    public void render(Graphics g, Shape shape, boolean xor) {
        if (!shape.isVisible()) {
            return;
        }

        Picture picture = (Picture) shape;

        int x = picture.getLocation().x;
        int y = picture.getLocation().y;
        int width = picture.getWidth();
        int height = picture.getHeight();

        if (xor) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setXORMode(Color.WHITE);
            g2.drawRect(x, y, width, height);
            g2.setPaintMode();
        } else {
            Image img = new ImageIcon(picture.getFilename()).getImage();
            if (img != null) {
                g.drawImage(img, x, y, width, height, null);
            }
        }

        super.render(g, shape, xor);
    }
}