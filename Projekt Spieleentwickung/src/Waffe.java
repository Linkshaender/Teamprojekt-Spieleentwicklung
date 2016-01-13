import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;


public class Waffe {

	private Image schwert;
	
	public Waffe() throws SlickException{
		
		schwert = new Image("images/Schwert.png");
	}
	
	
	public Image getWaffe(){
		return schwert;
	}
	
}
