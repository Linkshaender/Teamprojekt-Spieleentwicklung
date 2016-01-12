import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

public class Gui {

	private int status = 0;  // 0 = Hauptmenü; 1 = im Spiel

	private int xButton;
	private int hoeheButton = 50;
	private int breiteButton = 120;

	Image[] bild;

	Image hintergrundbild;
	private int hoeheBild;

	private Sound hintergrundmusik;
	private Sound click;

	Shape[] buttons;


	public void render(GameContainer container, Graphics g) throws SlickException {

		if (status == 1) {

			g.setColor(Color.white);
			g.texture(buttons[2], bild[2], true);

		}

		if(status == 0){

			hintergrundbild.drawCentered(container.getWidth()/2, container.getHeight()/2);

			g.setColor(Color.red);
			g.drawString("Spielmenü", xButton, 50);

			for (int i = 0; i < buttons.length-1; i++) {
				g.setColor(Color.white);
				g.texture(buttons[i], bild[i], true);
			}
		}

	}


	public void init(GameContainer container) throws SlickException {

		hoeheBild = container.getHeight();
		xButton = container.getWidth() / 2 - breiteButton/2;


		hintergrundbild = new Image("res/hintergrundbild.png");
		hintergrundbild = hintergrundbild.getScaledCopy((int)((int) hoeheBild/0.76), hoeheBild ); //breite ergibt sich aus dem Seitenverhältniss

		hintergrundmusik = new Sound("res/hintergrundmusik.ogg");
		hintergrundmusik.play(-3, 2);
		click = new Sound("res/klick.wav");


		Rectangle start = new Rectangle(xButton, 100, breiteButton, hoeheButton);
		Rectangle beenden = new Rectangle(xButton, 200, breiteButton, hoeheButton);
		Circle kreis = new Circle(container.getWidth()-40, 40, 20);

		buttons = new Shape[3];
		buttons[0]= start;
		buttons[1]= beenden;
		buttons[2] = kreis;

		bild = new Image[buttons.length];
		bild[0] = new Image("res/start.png");
		bild[1] = new Image("res/ende.png");   //hatte kein Exit Button
		bild[2] = new Image("res/stop.png");



	}

	public int update(GameContainer container, int delta, Shape hitboxMaus) {

		for (int i = 0; i < buttons.length; i++) {

			if(hitboxMaus.intersects(buttons[i]) && container.getInput().isMouseButtonDown(0)){

				if(i == 0 && status == 0){
					click.play(-4, 2);
					hintergrundmusik.stop();
					status = 1;
				}
				if(i == 1 && status == 0){
					click.play(-4, 2);

					container.exit();
				}
				if(i == 2 && status == 1){
					click.play(-4, 2);
					hintergrundmusik.play(-3, 2);

					status = 0;

				}	
			}
		}

		return status;
	}


	public int getX() {
		return xButton;
	}


	public void setX(int x) {
		this.xButton = x;
	}


	public int getHoehe() {
		return hoeheButton;
	}


	public void setHoehe(int hoehe) {
		this.hoeheButton = hoehe;
	}


	public int getBreite() {
		return breiteButton;
	}


	public void setBreite(int breite) {
		this.breiteButton = breite;
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
