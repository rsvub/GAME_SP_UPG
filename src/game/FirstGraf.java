/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import static game.Game.dostrel;
import static game.Game.firstGrafHight;
import static game.Game.firstGrafWidth;
import static game.Game.maxDostrel;
import static game.Game.rychlostStrelby;
import static game.Game.scaleFirstGrafX;
import static game.Game.scaleFirstGrafY;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Arrays;
import javax.swing.JPanel;

/**
 *
 * @author Radek
 */
public class FirstGraf extends JPanel{
    
    private static final int graphPointWidth = 10;
    private static final int xPointCount = 10;
    private static final int yPointCount = 5;
    
    public static int[] rychlostElevaceDostrel(double rychlostStrely, double elevace){
	int[] dostrel = new int[(int)rychlostStrely];
	dostrel[0] = 0; 
	for (int i = 1; i < dostrel.length; i++){
            int rychlost = i;
            int rychlostNaDruhou = (rychlost * rychlost);
	    dostrel[i] = (int) ((rychlostNaDruhou/10) * (Math.sin(2 * elevace)));
	}
        //System.out.println(Arrays.toString(dostrel));
	return dostrel;

    }    
    
    public static int maxDostrel(int [] dostrel){
        
        int max = dostrel[0];
        
        for (int i = 1; i < dostrel.length; i++){
            if(dostrel[i] > max){
                max = dostrel[i];
            }
        }
        return max;
        
    }
    
    
    public void draw(Graphics g) {
        
        Graphics2D g2 = (Graphics2D)g;

        BasicStroke osaX = new BasicStroke();
        BasicStroke osaY = new BasicStroke();
        BasicStroke krivka = new BasicStroke();
        BasicStroke xPoint = new BasicStroke();
        BasicStroke yPoint = new BasicStroke();
        BasicStroke labelX = new BasicStroke();
        BasicStroke labelY = new BasicStroke();
        BasicStroke pointLabelX = new BasicStroke();
        BasicStroke pointLabelY = new BasicStroke();        

        g2.setStroke(osaX);
        g2.setColor(Color.green);
        g2.drawLine(100, 400, 700, 400);
        
        g2.setStroke(osaY);
        g2.setColor(Color.blue);
        g2.drawLine(100, 100, 100, 400);
        
        
        g2.setStroke(krivka);
        g2.setColor(Color.red);
        for (int i = 0; i < dostrel.length - 1; i++){
            double x1 = (100 + (i * scaleFirstGrafX));
            double y1 = (400 - (dostrel[i] * scaleFirstGrafY));
            double x2 = (100 + ((i + 1) * scaleFirstGrafX));
            double y2 = (400 - (dostrel[i + 1] * scaleFirstGrafY));
            g2.drawLine((int)x1, (int)y1, (int)x2, (int)y2);
        } 
        
        g2.setStroke(xPoint);
        g2.setColor(Color.black);
        for (int i = 1; i <= xPointCount; i++){
            int x1 = 100 + i * ((firstGrafWidth - 200)/xPointCount);
            int y1 = 400;
            int x2 = x1;
            int y2 = y1 - graphPointWidth;
            g2.drawLine(x1, y1, x2, y2);
        }
        
        g2.setStroke(yPoint);
        g2.setColor(Color.black);
        for (int i = 1; i <= yPointCount; i++){
            int x1 = 100;
            int y1 = 400 - i * ((firstGrafHight - 300)/yPointCount);
            int x2 = x1 + graphPointWidth;
            int y2 = y1;
            g2.drawLine(x1, y1, x2, y2);
        }
        
        g2.setStroke(labelX);
        g2.setColor(Color.GREEN);
        g2.drawString("Pocatecni rychlost strely v m/s", 200, 500);
        
        g2.setStroke(labelY);
        g2.setColor(Color.blue);
        g2.drawString("Dostrel v m", 10, 250);
        
        g2.setStroke(pointLabelX);
        g2.setColor(Color.BLACK);
        for (int i = 1; i <= xPointCount; i++){
            int xPointSize = (int)(rychlostStrelby / xPointCount);
            int x = 100 + (int)(i * xPointSize * scaleFirstGrafX);
            int y = 415;
            String xValue = "" + i * xPointSize;
            g2.drawString(xValue, x, y);
            
        }
            
        g2.setStroke(pointLabelY);
        g2.setColor(Color.BLACK);
        for (int i = 1; i <= yPointCount; i++){
            int yPointSize = (int)(maxDostrel / yPointCount);
            int x = 50;
            int y = 400 - (int)(i * yPointSize * scaleFirstGrafY);
            String yValue = "" + i * yPointSize;
            g2.drawString(yValue, x, y);
            
        }
        

        g2.dispose();
        
    }
    

  
    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        draw(g);
    }    
    
    
}