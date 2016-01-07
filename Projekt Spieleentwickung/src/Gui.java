import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

public class Gui {
	
	 private int status = 0;  // 0 = Hauptmenü; 1 = im Spiel
		
		private int x;
	    private int hoehe = 50;
	    private int breite = 120;
		
	    Image[] bild;
	  
	    
		Shape[] buttons;

		
		public void render(GameContainer container, Graphics g) throws SlickException {
			
			if (status == 1) {
				
			g.setColor(Color.white);
			g.texture(buttons[2], bild[2], true);
			
			}
			if(status == 0){
				g.setColor(Color.red);
				g.drawString("Spielmenü", x, 50);
				
				for (int i = 0; i < buttons.length-1; i++) {
					g.setColor(Color.white);
					g.texture(buttons[i], bild[i], true);
				}
			}
			
			
			
		}

		
		public void init(GameContainer container) throws SlickException {
			
			x = container.getWidth() / 2 - breite/2;
			
			
			Rectangle start = new Rectangle(x, 100, breite, hoehe);
			Rectangle beenden = new Rectangle(x, 200, breite, hoehe);
			Circle kreis = new Circle(container.getWidth()-40, 40, 20);

			buttons = new Shape[3];
			
			buttons[0]= start;
			buttons[1]= beenden;
			buttons[2] = kreis;
			
			bild = new Image[buttons.length];
			
			bild[0] = new Image("res/play.png");
			bild[1] = new Image("res/play.png");   //hatte kein Exit Button
			bild[2] = new Image("res/stop.png");
		}

		public int getX() {
			return x;
		}


		public void setX(int x) {
			this.x = x;
		}


		public int getHoehe() {
			return hoehe;
		}


		public void setHoehe(int hoehe) {
			this.hoehe = hoehe;
		}


		public int getBreite() {
			return breite;
		}


		public void setBreite(int breite) {
			this.breite = breite;
		}


		public Shape[] getMainmenu() {
			return buttons;
		}


		public void setMainmenu(Shape[] mainmenu) {
			this.buttons = mainmenu;
		}


		public int getStatus() {
			return status;
		}


		public void setStatus(int status) {
			this.status = status;
		}


		public Shape[] getButtons() {
			return buttons;
		}


		public void setButtons(Shape[] buttons) {
			this.buttons = buttons;
		}



}
