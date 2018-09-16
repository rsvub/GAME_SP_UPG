/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.util.ArrayList;

/**
 *
 * @author Radek
 */
public class Trajectory {
    
    public double x, y, z;
    
    ArrayList<Double> trajectory;

    public Trajectory() {
        this.trajectory = new ArrayList<Double>();
    }
    
    public void add(double x, double y, double z){
        trajectory.add(x);
        trajectory.add(y);
        trajectory.add(z);
        //System.out.println(trajectory);
    }
    
    public int size(){
        int size = trajectory.size();
        return size;
    }
    
    public double getX(int index){
        return x;
    }

    public double getY(int index){
        return y;
    }    
    
    public double getZ(int index){
        return z;
    }    
    
    public ArrayList<Double> getTrajectory(){
        this.trajectory = trajectory;
        return trajectory;
    }
    
}
