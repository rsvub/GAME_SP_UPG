/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import static game.Game.windAzimuth;
import static game.Game.deltaX;
import static game.Game.deltaY;
import static game.Game.hitX;
import static game.Game.hitY;
import static game.Game.mapHight;
import static game.Game.mapWidth;
import static game.Game.scale;
import static game.Game.shooterX;
import static game.Game.shooterY;
import static game.Game.targetX;
import static game.Game.targetY;
import static game.Game.velocity;
import static game.ShootingCalculator.trajektorie;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 *
 * @author Radek Švub
 */
public class GamePanel extends JPanel {
    
    
    /**
     *
     * @param g
     */
    
    public void draw(Graphics g) {
        
        Color shooterColor = Color.blue;     // barva střelce
        Color targetColor = Color.red;          // barva terče
        Color hitColor = Color.orange;     // barva oblasti dopadu střely
        
        Graphics2D g2 = (Graphics2D)g;
        int xShooter = (int) (shooterX * deltaX / 1000.0 * scale) + 10;
        int yShooter = (int) (shooterY * deltaY / 1000.0 * scale) + 10;
        int xTarget = (int) (targetX * deltaX / 1000.0 * scale) + 10;
        int yTarget = (int) (targetY * deltaY / 1000.0 * scale) + 10;

        
        BasicStroke field = new BasicStroke();
        BasicStroke windBag = new BasicStroke();
        BasicStroke trasaStrely = new BasicStroke();
        BasicStroke shooter = new BasicStroke();
        BasicStroke target = new BasicStroke();   
        BasicStroke hitSpot = new BasicStroke();  

        
        g2.setStroke(field);
        //g2.setColor(Color.WHITE);
        //g2.fillRect(10, 10, (int) (columns * deltaX / 1000.0 * scale), (int) (rows * deltaY / 1000.0 * scale));
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("pole.png"));
        } catch (IOException e) {
        }
        g2.drawImage(img, 10, 10, (int) (mapWidth * scale), (int) (mapHight * scale), null);

        g2.setStroke(windBag);
        BufferedImage bag = null;
        try {
            bag = ImageIO.read(new File("arrow.png"));
        } catch (IOException e) {
        }
        AffineTransform af = AffineTransform.getTranslateInstance((mapWidth * scale)/10, (mapHight * scale)/10);
        af.rotate(windAzimuth);
        af.scale(velocity / 7, velocity / 7);
        g2.drawImage(bag, af, null);
        
        g2.setStroke(shooter);
        g2.setColor(shooterColor);
        g2.drawLine(xShooter - 5, yShooter, xShooter + 5, yShooter);
        g2.drawLine(xShooter, yShooter - 5, xShooter, yShooter + 5);
        
        g2.setStroke(target);
        g2.setColor(targetColor);
        g2.drawLine(xTarget - 5, yTarget, xTarget + 5, yTarget);
        g2.drawLine(xTarget, yTarget - 5, xTarget, yTarget + 5);       

        if(hitX != 99999999){
            int xHit = (int)(hitX * deltaX / 1000.0 * scale) + 10;
            int yHit = (int)(hitY * deltaY / 1000.0 * scale) + 10;        
            //System.out.println(xHit + " xHit," + yHit + " yHit");
            g2.setStroke(hitSpot);
            g2.setColor(hitColor);
            g2.fillOval(xHit  - (int)(30 * scale), yHit  - (int)(30 * scale), (int)(60 * scale), (int)(60 * scale));
        }        
        
        //Trajektorie
        g2.setStroke(trasaStrely);
        ArrayList<Double> trajektorie1 = ShootingCalculator.trajektorie;
        g2.setColor(Color.green);
        if (trajektorie1 == null){
            int w = 1;
        }
        else{
            //int velikost = trajektorie1.size();
            //System.out.println("Velikost pole = " + velikost);    
            double a, b, c;
            int x, y, z;
            
            for (int i = 0; i < trajektorie.size() - 1; i++) {
                a = (trajektorie.get(i));
                b = (trajektorie.get(i + 1));
                c = (trajektorie.get(i + 2));
                i = i + 2;
                x = (int)(a * scale) + 10;
                y = (int)(b * scale) + 10;
                //x = (int)(a * deltaX / mmToM * scale);
                //y = (int)(b * deltaY / mmToM * scale);
                g2.fillRect(x, y, 2, 2);
                //System.out.println("x = " + x + ",y = " + y);
            }            
        }        
        
        g2.dispose();
        
    }
    

  
    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        draw(g);
    }    
    

    
}
