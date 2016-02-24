package de.tgirobertosan.suiweed;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import de.tgirobertosan.suiweed.charakter.Charakter;
import de.tgirobertosan.suiweed.spielwelt.Gegner;
import de.tgirobertosan.suiweed.spielwelt.Spielwelt;

public class Kampfsystem {

	private int waffenschaden;
	private int maxKampfdistanz = 50;
	private float charakterX;
	private float charakterY;
	private float gegnerX;
	private float gegnerY;
	private float abstandX;
	private float abstandY;
	private double abstandXY;
	private int gegnerAnzahl;
	private int gegnerLeben;
	private ArrayList<Gegner> gegner;
	private Spielwelt spielwelt;
	private Gegner ausgewaehlterGegner;




	public void gegnerAngreifen(int gegebenerWaffenschaden, float gegebenesX, float gegebenesY, Spielwelt spielwelt){

		waffenschaden = gegebenerWaffenschaden;
		charakterX = gegebenesX;
		charakterY = gegebenesY;
		this.spielwelt = spielwelt;
		int i = 0;

		gegner = spielwelt.getGegner();


		gegnerAnzahl = gegner.size();
		
		double[] abstand = new double[gegnerAnzahl];
		while(i < gegnerAnzahl){
			
			ausgewaehlterGegner = gegner.get(i);
			gegnerY = ausgewaehlterGegner.getGegnerY();
			gegnerX = ausgewaehlterGegner.getGegnerX();
			abstandBerechnen();
			abstand[i] = abstandXY;
			
			i++;
		}
		
		Arrays.sort(abstand); 
        for (int n = 0; n < abstand.length; n++) { 
            
        	if(abstand[n] < maxKampfdistanz){
        		i = 0;
        		
        		while(i < gegnerAnzahl){
        		
        		if(abstand[n] == abstand[i]){
        			
        			ausgewaehlterGegner = gegner.get(i);
        			gegnerLeben = ausgewaehlterGegner.getGegnerLeben();
        			gegnerLeben = gegnerLeben - waffenschaden;
        			ausgewaehlterGegner.setLeben(gegnerLeben);
        			if(gegnerLeben <= 0){
        				gegner.remove(i);
        			}
        			System.out.println("GegnerLeben:"+" "+gegnerLeben);
        			
        			
        			
        		}i++;
        		
        	}
        } 
	} 
	}

	private void abstandBerechnen(){
		abstandX = charakterX - gegnerX;
		abstandY = charakterY - gegnerY;
		abstandXY = Math.sqrt(abstandX*abstandX+abstandY*abstandY);

	}

}
