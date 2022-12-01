package com.connectfour.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Launcher extends Game {
	public SpriteBatch batch;

	@Override
	public void create() {
		batch = new SpriteBatch();
		setScreen(new startScreen(this));
	}

	@Override
	public void render() {
		super.render();
	}
}
