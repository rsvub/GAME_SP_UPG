/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

/**
 *
 * @author Radek
 */
public class Wind {

    private final double maxVelocity = 32.7;
    public double velocity;
    public double azimuth;
    public double vx, vy, vz;
    
    /**
     *
     * @param azimuth
     * @param velocity
     */
    
    public void generateParams(double azimuth, double velocity){
        velocity = velocity + Math.random()*3.6;
        if(maxVelocity<=velocity) {
            this.velocity = velocity - 3.6;                
        }
        else{
        this.velocity = velocity;            
        }
        azimuth = azimuth + Math.toRadians(Math.random()*15);
        if(azimuth>=2*Math.PI){
            this.azimuth = azimuth - Math.PI;
        }
        else{
            this.azimuth = azimuth;
        }
        
    }
    
    public double getVelocity(){
        return velocity;
    }
    public double getAzimuth(){
        return azimuth;
    }
    
}
