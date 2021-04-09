/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import DTO.*;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.io.File;
import java.util.Random;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.swing.ImageIcon;
import sound.GameSound;

/**
 *
 * @author tanta
 */
public class BluePanel extends javax.swing.JPanel {

    /**
     * Creates new form BluePanel
     */
    Pipe[] topPipes;
    Pipe[] botPipes;
    Cu bird;
    boolean isPlaying = true;
    boolean jump = false;
    boolean canJump = true;
    Background background, background2;
    private static int distance = 410;
    private static int width = 180;
    private final static int totalHeight = 600;
    private static int gravity = 20;// gia toc trong truong (m/s)
    int tmpH, tmpH1, score;
    int tmpScore = 0;
    int rad = 0;
    int v0 = 0;
    double t = 0;

    public BluePanel() {
        init();
        initComponents();
        t1.start();
        t2.start();
        t3.start();
        GameSound.getInstance().getAudio(GameSound.MENU).loop();
    }

    void init() {
        background = new Background("src/IMG/background.png", 0, 0, 1920, 1080);
        background2 = new Background("src/IMG/background.png", 2304, 0, 1920, 1080);
        topPipes = new Pipe[4];

        topPipes[0] = new Pipe("src/IMG/topPipe.png", 1060, 0, width, randomHeight());
        topPipes[1] = new Pipe("src/IMG/topPipe.png", 1720, 0, width, randomHeight());
        topPipes[2] = new Pipe("src/IMG/topPipe.png", -500, 0, width, randomHeight());
        topPipes[3] = new Pipe("src/IMG/topPipe.png", -500, 0, width, randomHeight());
        botPipes = new Pipe[4];
        botPipes[0] = new Pipe("src/IMG/botPipe.png", 1060, topPipes[0].height + 190, width, totalHeight - topPipes[0].height);
        botPipes[1] = new Pipe("src/IMG/botPipe.png", 1720, topPipes[1].height + 190, width, totalHeight - topPipes[1].height);
        botPipes[2] = new Pipe("src/IMG/botPipe.png", -500, topPipes[2].height + 190, width, totalHeight - topPipes[2].height);
        botPipes[3] = new Pipe("src/IMG/botPipe.png", -500, topPipes[3].height + 190, width, totalHeight - topPipes[3].height);

        bird = new Cu("src/IMG/chim1.png", 200, 350, 50, 50);
    }

    void drawPipes(Graphics g) {

        for (Pipe topPipe : topPipes) {
            topPipe.drawTopPipe(g);
        }
        for (Pipe botPipe : botPipes) {
            botPipe.drawBotPipe(g);
        }

    }

    int randomHeight() {
        Random generator = new Random();
        int min = 50;
        int max = 500;
        return generator.nextInt((max - min) + 1) + min;
    }

    void resume() {

        t1.resume();
        t2.resume();
        t3.resume();
        isPlaying = true;
    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        super.paintComponent(grphcs); //To change body of generated methods, choose Tools | Templates.
        this.setBackground(Color.CYAN);
        background.drawBackground(grphcs);
        background2.drawBackground(grphcs);
        drawPipes(grphcs);
        rad = bird.drawCu(grphcs, jump, rad);
        if (isPlaying == false) {
            ImageIcon icon = new ImageIcon("src/IMG/gameover.png");
            Image img = icon.getImage();
            grphcs.drawImage(img, 610, 100, null);
            grphcs.setFont(new Font("Courier New", 1, 80));
            grphcs.drawString(String.valueOf(score), 1150, 480);
            grphcs.setFont(new Font("Courier New", 1, 30));
            grphcs.drawString("Press SPACE to play new game", 690, 600);
        }

    }
    // tao mot thread de dich chuyen cac pipe di qua trai

    Thread t1 = new Thread(new Runnable() {
        @Override
        public void run() {

            while (isPlaying) {
                for (Pipe toppipe : topPipes) {
                    toppipe.x = toppipe.x - 4;
                }
                for (Pipe botpipe : botPipes) {
                    botpipe.x = botpipe.x - 4;
                }
                background.x = background.x - 4;

                for (Pipe toppipe : topPipes) {
                    if (bird.getMaxX() < toppipe.getMaxX() && (bird.getMaxX() > toppipe.getMaxX() - 5)) {
                        score++;
                        GameSound.getInstance().getAudio(GameSound.POINT).play();
//                        playSound("point.wav");

                    }
                }
                background2.x = background2.x - 4;

                for (int i = 0; i < topPipes.length; i++) {
                    if ((topPipes[i].x + topPipes[i].width > getWidth() - distance - 3)
                            && (topPipes[i].x + topPipes[i].width < getWidth() - distance + 3)) {
                        int nextI = i + 1;
                        if (nextI == 4) {
                            nextI = 0;
                        }
                        topPipes[nextI].x = getWidth();
                        topPipes[nextI].height = randomHeight();
                        botPipes[nextI].x = getWidth();
                        botPipes[nextI].height = totalHeight - topPipes[nextI].height;
                        botPipes[nextI].y = topPipes[nextI].height + 190;
                        i = 10;
                    }
                }

                if (background.x + 2204 == getWidth()) {
                    background2.x = getWidth();
                }
                if (background2.x + 2204 == getWidth()) {
                    background.x = getWidth();
                }
                repaint(); //ham nay se goi ham paint,

                try {
                    Thread.sleep(16);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    }
    );

    boolean isGameOver() {
        for (Pipe topPipe : topPipes) {
            if ((topPipe.contains(new Point((int) bird.getMaxX() + 10, (int) bird.getMinY() + 10)))
                    || (topPipe.contains(new Point((int) bird.getMaxX(), (int) bird.getMaxY())))) {
                return false;
            }
        }
        for (Pipe botPipe : botPipes) {
            if ((botPipe.contains(new Point((int) bird.getMaxX() + 15, (int) bird.getMinY() + 10)))
                    || (botPipe.contains(new Point((int) bird.getMaxX(), (int) bird.getMaxY() + 10)))) {
                return false;
            }
        }
        if (bird.getLocation().y < -5 || bird.getLocation().y > 730) {
            return false;
        }
        return true;
    }
    Thread t2 = new Thread(new Runnable() {

        @Override
        public void run() {
            while (isPlaying) {
                txtScore.setText("Score : " + score);
                if (isGameOver() == false) {
                    isPlaying = false;
                    GameSound.getInstance().getAudio(GameSound.MENU).stop();
                    repaint();
                }
                try {
                    Thread.sleep(10);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }

    });

    Thread t3 = new Thread(new Runnable() {
        @Override
        public void run() {

            while (isPlaying) {
                int v1 = (int) ( (v0 + gravity * t)  );
                if (jump == false) {
                    bird.y = bird.y + v1/60;
                    tmpH = bird.y;
                    v0 = v1;
                    t = t + 0.016;
                }

                if (jump == true) {
                    bird.y = bird.y - 15;
                    if (bird.y < tmpH - 80) {
                        jump = false;
                        v0 = 130;
                        t = 0;
                    }
                }
                repaint(); //ham nay se goi ham paint,

                try {
                    Thread.sleep(16);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    }
    );

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtScore = new javax.swing.JLabel();

        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });

        txtScore.setFont(new java.awt.Font("UD Digi Kyokasho NK-B", 1, 36)); // NOI18N
        txtScore.setForeground(new java.awt.Color(255, 255, 255));
        txtScore.setText("Score : 0");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(txtScore, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(508, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(328, Short.MAX_VALUE)
                .addComponent(txtScore, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(68, 68, 68))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_formMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel txtScore;
    // End of variables declaration//GEN-END:variables
}
