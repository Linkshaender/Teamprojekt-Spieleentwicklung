package de.tgirobertosan.suiweed;

import java.util.ArrayList;
import java.util.Arrays;
import de.tgirobertosan.suiweed.spielwelt.Gegner;
import de.tgirobertosan.suiweed.spielwelt.Spielwelt;

public class Kampfsystem {

	private int waffenschaden;
	private int maxKampfdistanz = 50;
	private int gegnerAnzahl;
	private int gegnerLeben;
	private float charakterX;
	private float charakterY;
	private float gegnerX;
	private float gegnerY;
	private float abstandX;
	private float abstandY;
	private double abstandXY;
	private ArrayList<Gegner> gegner;
	private Spielwelt spielwelt;
	private Gegner ausgewaehlterGegner;



	/**Es wird ein Angriffsversuch gestartet. �bergeben wird der Waffenschaden des Spielers, seine x und y Position so wie die Spielwelt.**/
	public void gegnerAngreifen(int gegebenerWaffenschaden, float gegebenesX, float gegebenesY, Spielwelt spielwelt){

		waffenschaden = gegebenerWaffenschaden;
		charakterX = gegebenesX;
		charakterY = gegebenesY;
		this.spielwelt = spielwelt;
		int i = 0;

		gegner = spielwelt.getGegner();


		gegnerAnzahl = gegner.size();

		if(gegnerAnzahl > 0){

			double[] abstand = new double[gegnerAnzahl];
			double[] abstandZuordnen = new double[gegnerAnzahl];  //Array zum speichern der Abst�nde der Gegner zum Spieler
			while(i < gegnerAnzahl){

				ausgewaehlterGegner = gegner.get(i);
				gegnerY = ausgewaehlterGegner.getGegnerY();
				gegnerX = ausgewaehlterGegner.getGegnerX();
				abstandBerechnen();
				abstand[i] = abstandXY;
				abstandZuordnen[i] = abstandXY;

				i++;
			}

			Arrays.sort(abstand); 								//Sortieren der im Array gespeicherten Abst�nde nach Gr��e
			for (int n = 0; n < abstand.length; n++) { 
			}
			System.out.println(abstand[0]);
			if(abstand[0] < maxKampfdistanz){				//Pr�fen ob Gegner in Reichweite ist
				i = 0;

				while(i < gegnerAnzahl){

					if(abstand[0] == abstandZuordnen[i]){			//Kleinster Abstand wird wieder dem passenden Gegner zugeordnet

						ausgewaehlterGegner = gegner.get(i);
						gegnerLeben = ausgewaehlterGegner.getGegnerLeben();
						gegnerLeben = gegnerLeben - waffenschaden;
						ausgewaehlterGegner.setLeben(gegnerLeben);
						if(gegnerLeben <= 0){						//=Tot
							gegner.remove(i);
							break;
						}
						System.out.println("GegnerLeben:"+" "+gegnerLeben);



					}i++;

				}
			} 
		} 
	}

	/**Berechnet aus X und Y werten den Abstand zwischen Spieler und momentan ausgew�hlten Gegner.**/
	private void abstandBerechnen(){
		abstandX = charakterX - gegnerX;
		abstandY = charakterY - gegnerY;
		abstandXY = Math.sqrt(abstandX*abstandX+abstandY*abstandY); //Berechnen des Abstandes mithilfe von Pythagoras

	}

}
