import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.command.InputProvider;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

public class Kontrolle extends BasicGame{

	
	private AppGameContainer spiel = null;
	
	private Shape hitboxMaus;
	private boolean spielGestartet = false;

	private Gui gui;
	private InputHandler inputHandler;
	
	private Spielwelt startWelt = null;
	private Held held;

	public Kontrolle(String title) {
		super(title);
	}


	public void spielErstellen(){
		try {
			spiel = new AppGameContainer(new Kontrolle("Test"));
			spiel.setDisplayMode(1000, 800, false);//Breite,Hoehe,Fullscreen
			spiel.setTargetFrameRate(60);
			spiel.start();

		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

	public void spielStarten(){
		try {
			startWelt = new Spielwelt("/res/tilemaps/tilemap1.tmx");
			startWelt.init();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {

		g.setBackground(Color.white);

		g.setColor(Color.blue);
		g.fill(hitboxMaus);
		g.draw(hitboxMaus);
		if(spielGestartet) {
			startWelt.render(0, 0);
			g.fill(held.getPlaceHolderShape());
		}

		gui.render(container, g);

	}

	@Override
	public void init(GameContainer container) throws SlickException {

		hitboxMaus = new Circle(0, 0, 4);

		gui = new Gui();
		gui.init(container);
		
		inputHandler = new InputHandler(new InputProvider(container.getInput()));
		inputHandler.init();
		container.getInput().enableKeyRepeat(); //Taste gedrückt halten zum bewegen
		
		held = new Held(this);
		held.setPlaceHolderShape(new Circle(0, 0, 10));
		inputHandler.setHeld(held);

	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException {

		hitboxMaus.setCenterX(container.getInput().getMouseX());
		hitboxMaus.setCenterY(container.getInput().getMouseY());

		if(gui.update(container, delta, hitboxMaus) == 1 && !spielGestartet){
			spielStarten();
			spielGestartet = true;
		}
		
		if(spielGestartet) {
			inputHandler.handleInput();
		}


	}
	
	public boolean checkCollision(Shape shape) {
		if(!spielGestartet)
			return false;
		for(Rectangle movementBlocker : startWelt.getMovementBlockers()) {
			if(shape.intersects(movementBlocker) || shape.contains(movementBlocker))
				return true;
		}
		return false;
	}

}
