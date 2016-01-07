import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Shape;

public class Kontrolle extends BasicGame{
	
	private Shape hitboxMaus;

	private Gui gui;
	
	private Sound click;

	

	public Kontrolle(String title) {
		super(title);
	}


	public void spielStarten(){
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

	public void spielStart(){
		
		
		
		
		

	}

	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {
		
		g.setBackground(Color.white);
		

		gui.render(container, g);



	}

	@Override
	public void init(GameContainer container) throws SlickException {
		
		
		
		click = new Sound("res/klicken.wav");

		hitboxMaus = new Circle(0, 0, 4);

		gui = new Gui();
		gui.init(container);

	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException {

		
		hitboxMaus.setCenterX(container.getInput().getMouseX());
		hitboxMaus.setCenterY(container.getInput().getMouseY());

		for (int i = 0; i < gui.buttons.length; i++) {

			if(hitboxMaus.intersects(gui.buttons[i]) && container.getInput().isMouseButtonDown(0)){
				
				

				if(i == 0 && gui.getStatus() != 1){
					click.play(-4, 30);

					spielStart();
					gui.setStatus(1);
					
				}
				if(i == 1 && gui.getStatus() != 1){
					click.play(-4, 30);

					container.exit();

				}
				if(i == 2 && gui.getStatus() != 0){
					click.play(-4, 30);

					gui.setStatus(0);

				}

			}

		}



		
	}

}
