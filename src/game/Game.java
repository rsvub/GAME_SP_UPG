/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import static game.ShootingCalculator.sGTerrain;
import static game.ShootingCalculator.sGTerrainSize;
import static game.ShootingCalculator.sGTrajectory;
import static game.ShootingCalculator.sGTrajectorySize;
import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.Scanner;
import javax.swing.JFrame;

/**
 *
 * @author Radek Svub A15B0080K
 * @version 1.0
 */
public class Game extends JFrame {
    
    private static final Scanner sc = new Scanner(System.in);
    
    public static final int NUMBER_OF_PARAMS = 1;    
    
    JFrame shoot;
    JFrame firstGraf;
    JFrame secondGraf;
    public static double mmToM = 1000.0;
    public static int windowWidth;
    public static int windowHight;
    public static double mapWidth;
    public static double mapHight;
    public static int firstGrafWidth = 800;
    public static int firstGrafHight = 600;
    public static int secondGrafWidth = 800;
    public static int secondGrafHight = 600;   
    public static double scaleFirstGrafX;
    public static double scaleFirstGrafY;
    public static double scaleSecondGrafX;
    public static double scaleSecondGrafY;    
    public static double scaleSecondGrafTerrainX;
    public static double scaleSecondGrafTerrainY;        
    public static double scale;
    public static int columns;
    public static int rows;
    public static int shooterX;
    public static int shooterY;
    public static int targetX;
    public static int targetY;    
    public static int deltaX;    
    public static int deltaY;
    public static int[][] terrain;
    public static int[] dostrel;
    public static int maxDostrel;
    public static double sGTrajectoryMaximum;            
    public static Object sGTrajectoryMax;    
    public static double azimuthStrelby;
    public static double elevaceStrelby;
    public static double rychlostStrelby;
    public static double hitDistance;
    public static int hitX = 99999999;
    public static int hitY;
    public static double maxVyska;
    public static double minVyska;
    private static int pokracovat;
    public static double windAzimuth = 0.0;
    public static double velocity = 0.0;
    public static int volba = 1;
    
    public Game(){

        paint();
       
    }

    private void paint() {

        if(volba == 1){
            shoot = new JFrame();    
            shoot.add(new GamePanel());
            shoot.setTitle("GAME - Radek Svub A15B0080K");
            shoot.setSize(windowWidth+40, windowHight+60);
            shoot.setLocationRelativeTo(null);
            shoot.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            shoot.setVisible(true);
        }

        
        if (volba == 2){
           
            secondGraf = new JFrame();    
            secondGraf.add(new SecondGraf());
            secondGraf.setTitle("Graficke zobrazeni rezu terénem a bokorysu trajektorie strely");
            secondGraf.setSize(secondGrafWidth, secondGrafHight);
            secondGraf.setLocation(100, 100);
            secondGraf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            secondGraf.setVisible(true);            
            
            firstGraf = new JFrame();    
            firstGraf.add(new FirstGraf());
            firstGraf.setTitle("Graficke zobrazeni zavislosti dostrelu na pocatecni rychlosti strely");
            firstGraf.setSize(firstGrafWidth, firstGrafHight);
            firstGraf.setLocation(50, 50);
            firstGraf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            firstGraf.setVisible(true);            
            
        }


    }
    
    public void shootRepaint(){
        shoot.repaint();
    }
    
    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        
	//Výpis vzoru spouštění z příkazové řádky, při nedodržení správného tvaru.
	if (args.length < NUMBER_OF_PARAMS) {
            System.out.println("Game");
            System.out.println("Pouziti: java game.Game <nazev>");
            System.out.println("<nazev>   - nazev vstupniho souboru");
            System.out.println();
            System.out.println("Priklad: java game.Game teren.ter");
            System.exit(0);
        }        
        
        
        String fileName = args[0];
        System.out.println(String.format("Jmeno souboru: %s", fileName));
        TerrainFileHandler terrainFile = new TerrainFileHandler();
        
        try {
            terrainFile.loadTerFile(fileName);            
        } catch(FileNotFoundException e){
            System.out.println("Soubor neexistuje!");
            System.exit(0);
        }

        columns = terrainFile.getColumns();
        rows = terrainFile.getRows();
        shooterX = terrainFile.getShooterX();
        shooterY = terrainFile.getShooterY();
        targetX = terrainFile.getTargetX();
        targetY = terrainFile.getTargetY();
        deltaX = terrainFile.getDeltaX();
        deltaY = terrainFile.getDeltaY();
        terrain = terrainFile.getTerrain();
        mapWidth = columns * deltaX / mmToM;
        mapHight = rows * deltaY / mmToM;
        System.out.println("Zadejte sirku okna v 'px'");
        windowWidth = sc.nextInt();
        System.out.println("Zadejte vusku okna v 'px'");
        windowHight = sc.nextInt();                  
        if(windowWidth/mapWidth <= windowHight/mapHight) scale = windowWidth/mapWidth;
        if(windowWidth/mapWidth > windowHight/mapHight) scale = windowHight/mapHight;  
        System.out.println(scale + " scale");
        Color shooterColor = Color.blue;     // barva střelce
        Color targetColor = Color.red;          // barva terče
        Color hitSpotColor = Color.orange;     // barva oblasti dopadu střely
        int kriz = 10;                  // velikost kříže (střelec a cíl)
        int oval = 60;                  // velikost zasažené oblasti
        terrainFile.printTerrainData();
        NamedPosition shooter = new NamedPosition (shooterX, shooterY, deltaX, deltaY, shooterColor, kriz);
        NamedPosition target = new NamedPosition (targetX, targetY, deltaX, deltaY, targetColor, kriz);
        double distanceShooterTarget = NamedPosition.distance(shooter, target);
        System.out.println("Vzdalenost mezi strelcem a cilem je: " + distanceShooterTarget + " metru");
        System.out.println("Strelec je modry krizek a cil je cerveny krizek.");
        Terrain terrainImage = new Terrain();        
        maxVyska = Terrain.maximum(terrain);
        minVyska = Terrain.minimum(terrain);
        terrainImage.makeImage(terrain, maxVyska, minVyska);
        Wind wind = new Wind();
        wind.generateParams(windAzimuth, velocity);
        windAzimuth = wind.getAzimuth();
        velocity = wind.getVelocity();        
        Game game = new Game();
        //game.setVisible(true);
        System.out.println("Zadejte volbu (zadejte zvolenou cislici a enter):");
        System.out.println("1) Strelba");
        System.out.println("2) Vizualizace");
        volba = sc.nextInt();
        switch (volba) {
            case 1:        
                do {
                    game.shootRepaint();
                    System.out.println("Zadejte azimut vystrelu (0 = vychod, 90 = sever, -90 = jih, 180 = zapad)");
                    azimuthStrelby = Math.toRadians(sc.nextDouble());
                    System.out.println("Zadejte elevaci (–90 = svisle dolu, 0 = vodorovne, 90 = svisle nahoru)");
                    elevaceStrelby = Math.toRadians(sc.nextDouble());          
                    System.out.println("Zadejte pocatecni rychlost strely (v m/s)");
                    rychlostStrelby = sc.nextDouble(); 
                    //System.out.println("Zadejte vzdalenost dopadu strely v metrech");
                    //hitDistance = sc.nextDouble();
                    ShootingCalculator shootingCalculator = new ShootingCalculator();
                    shootingCalculator.shoot(azimuthStrelby, elevaceStrelby, rychlostStrelby);
                    double zasahX = shootingCalculator.getXHitSpot();
                    double zasahY = shootingCalculator.getYHitSpot();
                    hitX = (int)(zasahX / deltaX * mmToM);
                    hitY = (int)(zasahY / deltaY * mmToM);
                    //System.out.println(hitX + " = hitX," + hitY + " = hitY");
                    NamedPosition hitSpot = new NamedPosition(hitX, hitY, deltaX, deltaY, hitSpotColor, oval);
                    double distanceHitSpotTarget = NamedPosition.distance(target, hitSpot);
                    System.out.println("Vzdalenost mezi dopadem a cilem je: " + distanceHitSpotTarget + " metru");
                    shootingCalculator.testTargetHit(distanceHitSpotTarget);
                    game.shootRepaint();
                    System.out.println("Pro pokracovani ve strelbe zadej 1 a enter, pro ukonceni strelby zadej 2 a enter.");
                    pokracovat = sc.nextInt();
                    wind.generateParams(windAzimuth, velocity);
                    windAzimuth = wind.getAzimuth();
                    velocity = wind.getVelocity();        
                }while(pokracovat == 1);
                System.exit(1);
            case 2:
                System.out.println("Zadejte azimut vystrelu (0 = vychod, 90 = sever, -90 = jih, 180 = zapad)");
                azimuthStrelby = Math.toRadians(sc.nextDouble());
                System.out.println("Zadejte elevaci (–90 = svisle dolu, 0 = vodorovne, 90 = svisle nahoru)");
                elevaceStrelby = Math.toRadians(sc.nextDouble());  
                System.out.println("Zadejte pocatecni rychlost strely (v m/s)");
                rychlostStrelby = sc.nextDouble();
                ShootingCalculator shootingCalculator = new ShootingCalculator();
                shootingCalculator.shoot(azimuthStrelby, elevaceStrelby, rychlostStrelby);
                double zasahX = shootingCalculator.getXHitSpot();
                double zasahY = shootingCalculator.getYHitSpot();
                hitX = (int)(zasahX / deltaX * mmToM);
                hitY = (int)(zasahY / deltaY * mmToM);
                NamedPosition hitSpot = new NamedPosition(hitX, hitY, deltaX, deltaY, hitSpotColor, oval);
                double distanceHitSpotTarget = NamedPosition.distance(target, hitSpot);
                System.out.println("Vzdalenost mezi dopadem a cilem je: " + distanceHitSpotTarget + " metru");
                shootingCalculator.testTargetHit(distanceHitSpotTarget);
                dostrel = FirstGraf.rychlostElevaceDostrel(rychlostStrelby, elevaceStrelby);
                maxDostrel = FirstGraf.maxDostrel(dostrel);
                scaleFirstGrafX = (firstGrafWidth - 200) / rychlostStrelby;
                scaleFirstGrafY = (firstGrafHight - 300) / (double)maxDostrel;
                Object sGTrajectoryMax = Collections.max(sGTrajectory);
                sGTrajectoryMaximum = (double)sGTrajectoryMax;
                scaleSecondGrafX = (secondGrafWidth - 200) / (double)sGTrajectorySize;
                scaleSecondGrafY = (secondGrafHight - 300) / (double)sGTrajectoryMax;
                Object sGTerrainMax = Collections.max(sGTerrain);
                scaleSecondGrafTerrainX = (secondGrafWidth - 200) / (double)sGTerrainSize;
                scaleSecondGrafTerrainY = (secondGrafHight - 300) / (double)sGTerrainMax;                
                game.paint();
                break;
            default:
                System.exit(1);

        }
    }

}
