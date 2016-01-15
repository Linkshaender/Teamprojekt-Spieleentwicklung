import org.newdawn.slick.geom.Shape;


public class Held {


	private Kontrolle kontrolle;
	private Shape placeHolderShape = null;
	
	public Held(Kontrolle kontrolle) {
		this.kontrolle = kontrolle;
	}
	
	//xDir = 1 -> bewegung "nach rechts". xDir = -1 -> "nach links. yDir = -1 -> "nach oben". yDir = 1 -> "nach unten".
	public void move(int xDir, int yDir) {
		//TODO: Kollisionshandling effizienter gestalten
		placeHolderShape.setCenterX(placeHolderShape.getCenterX()+xDir);
		if(kontrolle.checkCollision(getPlaceHolderShape()))
			placeHolderShape.setCenterX(placeHolderShape.getCenterX()-xDir);
		placeHolderShape.setCenterY(placeHolderShape.getCenterY()+yDir);
		if(kontrolle.checkCollision(getPlaceHolderShape()))
			placeHolderShape.setCenterY(placeHolderShape.getCenterY()-yDir);
		
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
