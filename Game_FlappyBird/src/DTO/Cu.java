/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import javafx.scene.transform.Rotate;
import javax.swing.ImageIcon;

/**
 *
 * @author tanta
 */
//private static final String EMAIL_REGEX = "^[A-Za-z0-9.+-_%]+@[A-Za-z.-]+\\.[A-Za-z]{2,4}$";
public class Cu extends Rectangle {
    
    String image;

    public Cu(String image, int x, int y, int width, int heigth) {
        super(x, y, width, heigth);
        this.image = image;
    }

    public int drawCu(Graphics g, boolean jump, int rad) {
        
        ImageIcon icon = new ImageIcon(image);
        Image img = icon.getImage();
        AffineTransform at = AffineTransform.getTranslateInstance(x, y);
//        if (jump == false){
//            at.rotate(Math.toRadians(rad = rad-5));
//            
//        }
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.drawImage(img, at, null);
        return rad;
    }

}
