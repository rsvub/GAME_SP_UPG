/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.awt.Color;

/**
 *
 * @author Radek
 */
public class NamedPosition {
    
    public final double mmToM = 1000.0;
    
    public double x, y;
    public final int size;
    public final Color color;
    
    NamedPosition (double x, double y, int dX, int dY, Color color, int size) {
        this.x = x * dX / mmToM;    // převod na metry
        this.y = y * dY / mmToM;    // převod na metry
        this.color = color;
        this.size = size;
    }

    public double getX () {
        return x;
    }
    
    public double getY () {
        return y;
    }
    
    public Color getColor () {
        return color;
    }

    public int getSize () {
        return size;
    }    
    

    /**
     *
     * @param a
     * @param b
     * @return
     */
    public static double distance (NamedPosition a, NamedPosition b) {
        
        double dx = b.x - a.x;
        double dy = b.y - a.y;
        return Math.sqrt(dx * dx + dy * dy);
        
    }
    
   
}
