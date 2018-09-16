/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import static game.Game.firstGrafHight;
import static game.Game.firstGrafWidth;
import static game.Game.maxDostrel;
import static game.Game.sGTrajectoryMaximum;
import static game.Game.scaleFirstGrafY;
import static game.Game.scaleSecondGrafTerrainX;
import static game.Game.scaleSecondGrafTerrainY;
import static game.Game.scaleSecondGrafX;
import static game.Game.scaleSecondGrafY;
import static game.ShootingCalculator.sGTerrain;
import static game.ShootingCalculator.sGTrajectory;
import static game.ShootingCalculator.sz;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 *
 * @author Radek
 */
public class SecondGraf  extends JPanel{
    
    private static final int graphPointWidth = 10;
    private static final int xPointCount = 10;
    private static final int yPointCount = 5;
    
    ArrayList<Double> secondGrafTrajectory = new ArrayList<Double>();
    ArrayList<Double> secondGrafTerrain = new ArrayList<Double>();
    
    public void addSecondGrafTrajectory(double z){
        secondGrafTrajectory.add(z);
        //System.out.println(trajectory);
    }
    
    public int sizeSecondGrafTrajectory(){
        int size = secondGrafTrajectory.size();
        return size;
    }
    
    public ArrayList<Double> getSecondGrafTrajectory(){
        this.secondGrafTrajectory = secondGrafTrajectory;
        return secondGrafTrajectory;
    }
    
    public void addSecondGrafTerrain(double z){
        secondGrafTerrain.add(z);
        //System.out.println(trajectory);
    }
    
    public int sizeSecondGrafTerrain(){
        int size = secondGrafTerrain.size();
        return size;
    }
    
    public ArrayList<Double> getSecondGrafTerrain(){
        this.secondGrafTerrain = secondGrafTerrain;
        return secondGrafTerrain;
    }    
    
    
    public void draw(Graphics g) {
        
        Graphics2D g2 = (Graphics2D)g;

        BasicStroke osaX = new BasicStroke();
        BasicStroke osaY = new BasicStroke();
        BasicStroke trajectory = new BasicStroke();
        BasicStroke terrain = new BasicStroke();        
        BasicStroke xPoint = new BasicStroke();
        BasicStroke yPoint = new BasicStroke();
        BasicStroke labelW = new BasicStroke();        
        BasicStroke labelX = new BasicStroke();
        BasicStroke labelY = new BasicStroke();
        BasicStroke labelZ = new BasicStroke();
        BasicStroke pointLabelY = new BasicStroke();        

        g2.setStroke(osaX);
        g2.setColor(Color.green);
        g2.drawLine(100, 400, 700, 400);
        
        g2.setStroke(osaY);
        g2.setColor(Color.blue);
        g2.drawLine(100, 100, 100, 400);
        
        
        g2.setStroke(trajectory);
        g2.setColor(Color.RED);
        for (int i = 0; i < sGTrajectory.size() - 1; i++) {
            double a = (sGTrajectory.get(i));
            double b = (sGTrajectory.get(i + 1));
            double x1 = 100 + (i * scaleSecondGrafX);
            double y1 = 400 - a * scaleSecondGrafY;
            double x2 = 100 + ((i + 1) * scaleSecondGrafX);
            double y2 = 400 - b * scaleSecondGrafY;
            g2.drawLine((int)x1, (int)y1, (int)x2, (int)y2);
        } 
        
        g2.setStroke(terrain);
        g2.setColor(Color.DARK_GRAY);
        for (int i = 0; i < sGTerrain.size() - 1; i++) {
            double a = (sGTerrain.get(i));
            double b = (sGTerrain.get(i + 1));
            double x1 = 100 + i * scaleSecondGrafX;
            double y1 = 400 + (sz * scaleSecondGrafY) - (a * scaleSecondGrafY);
            double x2 = 100 + (i + 1) * scaleSecondGrafX;
            double y2 = 400 + (sz * scaleSecondGrafY) - (b * scaleSecondGrafY);
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
        g2.setColor(Color.RED);
        g2.drawString("Trajektorie strely", 450, 50);
        
        g2.setStroke(labelY);
        g2.setColor(Color.DARK_GRAY);
        g2.drawString("Bokorys terenu", 250, 50);
        
        g2.setStroke(labelZ);
        g2.setColor(Color.BLUE);
        g2.drawString("Nadmorska vyska", 50, 50);        
        
            
        g2.setStroke(pointLabelY);
        g2.setColor(Color.BLACK);
        for (int i = 1; i <= yPointCount; i++){
            int yPointSize = (int)((sGTrajectoryMaximum - sz) / yPointCount);
            int x = 50;
            int y = 400 - (int)(i * yPointSize * scaleSecondGrafY);
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
