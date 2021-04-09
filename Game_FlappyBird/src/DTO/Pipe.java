/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

/**
 *
 * @author tanta
 */
public class Pipe extends Rectangle {

    String image;

    public Pipe() {
        super();
        this.image = image;
    }

    public Pipe(String image, int x, int y, int width, int heigth) {
        super(x, y, width, heigth);
        this.image = image;
    }

    public void drawTopPipe(Graphics c) {
        ImageIcon icon = new ImageIcon(image);
        Image img = icon.getImage();
//        c.drawImage(img, x, y, null);
//        c.drawImage(img, x, y, width, height, null);
        c.drawImage(img, x, y, x + width, y + height, 0, img.getHeight(null) - height, img.getWidth(null), img.getHeight(null), null);

    }

    public void drawBotPipe(Graphics c) {
        ImageIcon icon = new ImageIcon(image);
        Image img = icon.getImage();
        c.drawImage(img, x, y, x + width, y + height, 0, 0, img.getWidth(null), height, null);
    }

}
