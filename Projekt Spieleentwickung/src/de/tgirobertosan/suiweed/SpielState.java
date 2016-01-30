package de.tgirobertosan.suiweed;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.command.InputProvider;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import de.tgirobertosan.suiweed.charakter.InputHandler;

public class SpielState extends BasicGameState {

	private int id = 1;

	private InputHandler inputHandler;

	private Spielwelt startWelt = null;

	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {

		inputHandler = new InputHandler(new InputProvider(container.getInput()));
		inputHandler.init();
		// container.getInput().enableKeyRepeat();

		startWelt = new Spielwelt("/res/tilemaps/tilemap1.tmx");
		startWelt.init(inputHandler);

		startWelt.getCharakter().init(container);
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		startWelt.renderWithObjects(container, g);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {

		inputHandler.handleInput();

	}

	@Override
	public int getID() {

		return id;
	}

}
