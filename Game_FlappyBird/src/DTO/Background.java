/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

/**
 *
 * @author tanta
 */
public class Background extends Rectangle {
    String image;
    public Background(String image, int x, int y, int width, int heigth) {
        super(x, y, width, heigth);
        this.image = image;
    }
    
    public void drawBackground(Graphics g){
        ImageIcon icon = new ImageIcon(image);
        Image img = icon.getImage();
        g.drawImage(img,x, y, null);
    }
}
