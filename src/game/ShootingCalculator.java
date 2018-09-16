/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import static game.Game.deltaX;
import static game.Game.deltaY;
import static game.Game.mapHight;
import static game.Game.mapWidth;
import static game.Game.shooterX;
import static game.Game.shooterY;
import static game.Game.terrain;
import static game.Game.velocity;
import static game.Game.windAzimuth;
import static game.Terrain.mmToM;
import java.util.ArrayList;

/**
 *
 * @author Radek Švub
 */
public class ShootingCalculator {
    
    public double xHitDistance, yHitDistance;
    public int xHitSpot, yHitSpot;
    private double gx, gy, gz, b, deltaT;
    private double x, y, z, vx, vy, vz, tz;
    public double vwx, vwy, vwz;
    public static int shootArraySize;
    public static ArrayList<Double> trajektorie;
    public static int sGTrajectorySize;
    public static ArrayList<Double> sGTrajectory;
    public static int sGTerrainSize;
    public static ArrayList<Double> sGTerrain;  
    public static double sz;
    Wind wind = new Wind();
    
    public ShootingCalculator(){
        gx = 10;
        gy = 10;
        gz = 10;
        b = 0.05;
        deltaT = 0.01;
    }
    
    public void shoot(double azimuth, double elevace, double rychlost){
            x = shooterX * deltaX / mmToM;
            y = shooterY * deltaY / mmToM;
            z = terrain[shooterY][shooterX]/mmToM;
            vx = (rychlost * Math.cos(elevace) * Math.cos(azimuth)* -1);
            vy = (rychlost * Math.cos(elevace) * Math.sin(azimuth)* -1);
            vz = (rychlost * Math.sin(elevace)*gz);       
            vwx = velocity * Math.cos(windAzimuth);
            vwy = velocity * Math.sin(windAzimuth);
            vwz = 0;
            double time = 0;
            sz = z;
            Trajectory addArray = new Trajectory(); 
            SecondGraf addSGTrajectory = new SecondGraf();
            SecondGraf addSGTerrain = new SecondGraf();
        while(true) {
            time = time + deltaT;
            vx = ((vx) + (((vwx - vx) * b * deltaT)));
            vy = ((vy) + (((vwy - vy) * b * deltaT)));
            vz = ((vz) + (((-1 * gz * deltaT) + (vwz - vz) * b * deltaT)));
            x = (x + (vx * deltaT));
            y = (y + (vy * deltaT));
            z = (z + (vz * deltaT));
            addArray.add(x, y, z);
            addSGTrajectory.addSecondGrafTrajectory(z);
            addSGTerrain.addSecondGrafTerrain(tz);
            double okamzitaRychlost = Math.sqrt((Math.pow(vx,2)+(Math.pow(vy,2)+(Math.pow(vz,2)))));
            //System.out.println(x + " = x," + y + " = y," + z + " = z");
            if(okamzitaRychlost<=0){
                this.xHitSpot = (int) x;
                this.yHitSpot = (int) y;
                break;
            }
            if (x >= mapWidth || y >= mapHight || x < 0 || y < 0){
                this.xHitSpot = (int) x;
                this.yHitSpot = (int) y;
		System.out.println("Strela dopadla mimo mapu");
		break;
            }
            if (terrain[(int)(y / deltaY * mmToM)][(int)(x / deltaX * mmToM)]/mmToM > z ){
                this.xHitSpot = (int) x;
                this.yHitSpot = (int) y;
                break;
            }
            tz = terrain[(int)(y / deltaY * mmToM)][(int)(x / deltaX * mmToM)]/mmToM;

        }
        
        shootArraySize = addArray.size();
        //System.out.println("shootArraySize = " + shootArraySize);
        trajektorie = addArray.getTrajectory();
        //System.out.println(trajektorie);
        sGTrajectorySize = addSGTrajectory.sizeSecondGrafTrajectory();
        sGTrajectory = addSGTrajectory.getSecondGrafTrajectory();
        //System.out.println(sGTrajectory);
        sGTerrainSize = addSGTerrain.sizeSecondGrafTerrain();
        sGTerrain = addSGTerrain.getSecondGrafTerrain();
    }    
    
    public int getXHitSpot(){
        return xHitSpot;
    }  
    
    public int getYHitSpot(){
        return yHitSpot;
    }  
   
    public void testTargetHit(double distanceHitSpotTarget){
        
        if(distanceHitSpotTarget<=30){
            System.out.println("ZASAH");
        }else{
            System.out.println("VEDLE");
        }
        
    }
    
}
