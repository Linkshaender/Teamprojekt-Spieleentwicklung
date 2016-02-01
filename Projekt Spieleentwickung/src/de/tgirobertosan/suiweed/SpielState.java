package de.tgirobertosan.suiweed;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.command.InputProvider;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import de.tgirobertosan.suiweed.charakter.InputHandler;
import de.tgirobertosan.suiweed.spielwelt.Spielwelt;

public class SpielState extends BasicGameState {

	private int id = 1;

	private InputHandler inputHandler;

	private Spielwelt spielWelt = null;

	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {

		inputHandler = new InputHandler(new InputProvider(container.getInput()));
		inputHandler.init();
		// container.getInput().enableKeyRepeat();

		spielWelt = new Spielwelt("/res/tilemaps/tilemap1.tmx");
		spielWelt.init(this);

		if(spielWelt.getCharakter() != null)
			spielWelt.getCharakter().init(container);
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		spielWelt.renderWithObjects(container, g);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {

		inputHandler.handleInput();
		spielWelt.focusCharakter(container.getWidth(), container.getHeight());
		spielWelt.checkTriggers();
	}

	@Override
	public int getID() {

		return id;
	}
	
	public void changeSpielwelt(Spielwelt spielwelt) {
		try {
			spielwelt.init(this);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		this.spielWelt = spielwelt;
	}
	
	public InputHandler getInputHandler() {
		return inputHandler;
	}

}
