import org.newdawn.slick.geom.Shape;


public class Held {


	private Shape placeHolderShape = null;
	
	//xDir = 1 -> bewegung "nach rechts". xDir = -1 -> "nach links. yDir = -1 -> "nach oben". yDir = 1 -> "nach unten".
	public void move(int xDir, int yDir) {	
		//TODO: Collision detection. Am besten in anderer Klasse. (Methodenaufruf)
		placeHolderShape.setCenterX(placeHolderShape.getCenterX()+xDir);
		placeHolderShape.setCenterY(placeHolderShape.getCenterY()+yDir);
	}
	
	
	public void attack() {
		
	}
	
	
	

	public Shape getPlaceHolderShape() {
		return placeHolderShape;
	}

	public void setPlaceHolderShape(Shape placeHolderShape) {
		this.placeHolderShape = placeHolderShape;
	}
	

}
