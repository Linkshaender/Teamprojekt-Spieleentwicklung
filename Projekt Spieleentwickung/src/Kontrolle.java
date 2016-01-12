import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Shape;

public class Kontrolle extends BasicGame{

	private Shape hitboxMaus;
	private boolean spielGestartet = false;

	private Gui gui;

	public Kontrolle(String title) {
		super(title);
	}


	public void spielErstellen(){
		try {
			AppGameContainer spiel = new AppGameContainer(new Kontrolle("Test"));
			spiel.setDisplayMode(1000, 500, false);//Breite,Hoehe,Fullscreen
			spiel.setTargetFrameRate(60);
			spiel.start();

		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

	public void spielStarten(){

	}

	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {

		g.setBackground(Color.white);

		g.setColor(Color.blue);
		g.fill(hitboxMaus);
		g.draw(hitboxMaus);

		gui.render(container, g);

	}

	@Override
	public void init(GameContainer container) throws SlickException {

		hitboxMaus = new Circle(0, 0, 4);

		gui = new Gui();
		gui.init(container);

	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException {

		hitboxMaus.setCenterX(container.getInput().getMouseX());
		hitboxMaus.setCenterY(container.getInput().getMouseY());

		if(gui.update(container, delta, hitboxMaus) == 1 && !spielGestartet){
			spielStarten();
			spielGestartet = true;
		}


	}

}
