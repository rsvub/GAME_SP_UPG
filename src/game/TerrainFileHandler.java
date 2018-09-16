/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author Radek Švub
 */
public class TerrainFileHandler {
    
    public int[][] terrain;
    public int columns, rows;
    public int deltaX, deltaY;
    public int shooterX, shooterY;
    public int targetX, targetY;
    static final double mmToM = 1000.0;
    
    /**
     *
     * @param fileName
     * @throws java.io.FileNotFoundException
     */
    public void loadTerFile (String fileName) throws FileNotFoundException, IOException {
        		int[][] teren = null;
		DataInputStream fin = null;
		try {
			// Čtení dat
			fin = new DataInputStream(new FileInputStream(fileName));
			
			this.columns = fin.readInt();
			this.rows = fin.readInt();
                	this.deltaX = fin.readInt();
			this.deltaY = fin.readInt();
                        this.shooterX = fin.readInt();
			this.shooterY = fin.readInt();
			this.targetX = fin.readInt();
			this.targetY = fin.readInt();
		
			terrain = new int[rows][columns];
			for (int y = 0; y < rows; ++y) {
				for (int x = 0; x < columns; ++x) {
					this.terrain[y][x] = fin.readInt();
				}
				
			}
		} 
		finally
        {
             if(fin!=null)
            {
                fin.close();
            }
        }

    }

    public int getColumns () {
        return columns;
    }
    public int getRows () {
        return rows;
    }    
    public int getDeltaX () {
        return deltaX;
    }    
    public int getDeltaY () {
        return deltaY;
    }
    public int getShooterX () {
        return shooterX;
    }
    public int getShooterY () {
        return shooterY;
    }
    public int getTargetX () {
        return targetX;
    }    
    public int getTargetY () {
        return targetY;
    }
    public int[][] getTerrain () {
        return terrain;
    }


    /**
     *
     */
    public void printTerrainData() 	{
		int rowsCount = rows;
		int columnsCount = columns;
		
		System.out.println(String.format("Pocet sloupcu: %d, pocet radku: %d", columnsCount, rowsCount));
		System.out.println(String.format("Rozestup mezi sloupci %.3f m, mezi radky %.3f m", 
				deltaX / mmToM, deltaY / mmToM));
		System.out.println(String.format("Rozmery oblasti: sirka %.3f m, vyska %.3f m" , 
				columnsCount * deltaX / mmToM, rowsCount * deltaY / mmToM));
		System.out.println(String.format("Poloha strelce: sloupec %d, radek %d, tj. x = %.3f m, y = %.3f m", 
				shooterX, shooterY, shooterX * deltaX / mmToM, shooterY * deltaY / mmToM));
		
		if (shooterX < 0 || shooterX >= columnsCount ||
     		shooterY < 0 || shooterY >= rowsCount) 
		{
			System.out.println("STRELEC JE MIMO MAPU !");
		} else {
			System.out.println(String.format("   nadmorska vyska strelce %.3f m", terrain[shooterY][shooterX] / mmToM));
		}
		
		
		System.out.println(String.format("Poloha cile: sloupec %d, radek %d, tj. x = %.3f m, y = %.3f m", 
				targetX, targetY, targetX * deltaX / mmToM, targetY * deltaY / mmToM));

		if (targetX < 0 || targetX >= columnsCount ||
	     	targetY < 0 || targetY >= rowsCount) 
			{
				System.out.println("CIL JE MIMO MAPU !");
			} else {
				System.out.println(String.format("   nadmorska vyska cile %.3f m", terrain[targetY][targetX] / mmToM));
			}
	}

    
}
