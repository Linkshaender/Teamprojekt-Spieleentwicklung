import org.newdawn.slick.Input;
import org.newdawn.slick.command.BasicCommand;
import org.newdawn.slick.command.Command;
import org.newdawn.slick.command.ControllerButtonControl;
import org.newdawn.slick.command.ControllerDirectionControl;
import org.newdawn.slick.command.InputProvider;
import org.newdawn.slick.command.InputProviderListener;
import org.newdawn.slick.command.KeyControl;


public class InputHandler implements InputProviderListener {
	
	private InputProvider provider;
	
	private Command attack = new BasicCommand("attack");
	private Command up = new BasicCommand("up");
	private Command left = new BasicCommand("left");
	private Command right = new BasicCommand("right");
	private Command down = new BasicCommand("down");
	
	private Held held;
	
	
	public InputHandler(InputProvider provider) {
		this.provider = provider;
	}

	@Override
	public void controlPressed(Command cmd) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void controlReleased(Command cmd) {
		// TODO Auto-generated method stub
		
	}
	
	public void init() {
		provider.addListener(this);
		
		provider.bindCommand(new KeyControl(Input.KEY_LEFT), left);
		provider.bindCommand(new KeyControl(Input.KEY_A), left);
		provider.bindCommand(new KeyControl(Input.KEY_RIGHT), right);
		provider.bindCommand(new KeyControl(Input.KEY_D), right);
		provider.bindCommand(new ControllerDirectionControl(0, ControllerDirectionControl.LEFT), left);
		provider.bindCommand(new KeyControl(Input.KEY_UP), up);
		provider.bindCommand(new KeyControl(Input.KEY_W), up);
		provider.bindCommand(new KeyControl(Input.KEY_DOWN), down);
		provider.bindCommand(new KeyControl(Input.KEY_S), down);
		provider.bindCommand(new ControllerDirectionControl(0, ControllerDirectionControl.UP), up);
		provider.bindCommand(new KeyControl(Input.KEY_SPACE), attack);
		provider.bindCommand(new ControllerButtonControl(0, 1), attack);
	}
	
	
	public void handleInput() {
		if(held == null)
			return;
		handleMovement();
		handleAttacks();
	}
	
	private void handleAttacks() {
		if(provider.isCommandControlDown(attack))
			held.attack();
	}
	
	private void handleMovement() {
		int xDir = 0, yDir = 0;
		
		if(provider.isCommandControlDown(left))
			xDir--;
		if(provider.isCommandControlDown(right))
			xDir++;
		if(provider.isCommandControlDown(up))
			yDir--;
		if(provider.isCommandControlDown(down))
			yDir++;
		if(xDir != 0 || yDir != 0)
			held.move(xDir, yDir);
	}
	

	public void setHeld(Held held) {
		this.held = held;
	}
	
	public Held getHeld() {
		return held;
	}

}
