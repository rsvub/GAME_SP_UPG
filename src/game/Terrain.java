/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import static game.Game.columns;
import static game.Game.rows;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Radek Švub
 */
public class Terrain {
    
    public static double mmToM = 1000.0;
    
   
    /**
     *
     * @param x
     * @param y
     * @return
     */
    public double getAltitudeInM(double x, double y) {
        return 0;
        
    }
    
    boolean isPointInside(double x, double y) {
        return false;
        
    }
    
    /** 
     * 2D pole nadmořských výšek; 
     * indexování: teren[Y][X]
     * najde nejnižší hodnotu
     * @param teren
     * @return 
     */
    public static double maximum(int [][] teren) {
	int maximum = Integer.MIN_VALUE;
	int c = 0;
	int d = 0;
	for (int i = 0; i < teren.length; i++){
		for (int j = 0; j < teren[i].length; j++){
            
                    if (teren[i][j] >= maximum) {
                            maximum = teren[i][j];
                            c = i;
                            d = j;
                    }
                }
        }
    	return maximum / mmToM;
    }
	
    /** 
     * 2D pole nadmořských výšek; 
     * indexování: teren[Y][X]
     * najde nejnižší hodnotu
     * @param teren
     * @return 
     */
    public static double minimum(int [][] teren) {
    	int minimum = Integer.MAX_VALUE;
	int a = 0;
	int b = 0;
	for (int i = 0; i < teren.length; i++){
            for (int j = 0; j < teren[i].length; j++){
                
		if (teren[i][j] <= minimum) {
			minimum = teren[i][j];
			a = i;
			b = j;
		}
            }    
	}
	return minimum / mmToM;
    }    

    public void makeImage(int [][] teren, double maxVyska, double minVyska){
        
        BufferedImage img = new BufferedImage(columns, rows, BufferedImage.TYPE_INT_RGB);
        File obrazekPole = null;
        double diff = maxVyska - minVyska;
        double ratio = 255 / diff;
        for (int x = 0; x < columns; x++) {
            for (int y = 0; y < rows; y++) {
            	int barva = (int)(((teren[y][x] / mmToM) - (int)minVyska) * ratio);
                if(minVyska == maxVyska)barva = 128;
            	if(barva<0)barva = 0;
            	if(barva>255)barva = 255;
                int r = barva;// red component 0...255
                int g = barva;// green component 0...255
                int b = barva;// blue component 0...255
                int col = (r << 16) | (g << 8) | b;
                img.setRGB(x, y, col);
            }
        }
        try{
            obrazekPole = new File("pole.png");
            ImageIO.write(img, "png", obrazekPole);
        }catch(IOException e){
        System.out.println("Error: " + e);
        }
    
    } 
    
}

