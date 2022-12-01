package com.connectfour.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Chip {
	
	//variables
	private final Sprite sprite;
	private final Tile tile;
	private int speed = -10;

	/**
	 * @param text
	 * @param tile
	 */
	public Chip(Texture text, Tile tile) {
		this.tile = tile;
		sprite = new Sprite(text);
		sprite.setPosition(tile.getX(),500);
		sprite.setSize(tile.getRectangle().getWidth(), tile.getRectangle().getHeight());
	}

	/**
	 * @param tile
	 */
	public void translate(Tile tile) {
		sprite.translateY(speed);
		if (sprite.getY() <= tile.getY()) {
			speed = 0;
		}
	}

	/**
	 * @return
	 */
	public Sprite getSprite() {
		return sprite;
	}

	/**
	 * @return
	 */
	public Tile getTile() {
		return tile;
	}

	public abstract int getType();

	/**
	 * @param batch
	 */
	public void draw(SpriteBatch batch) {
		sprite.draw(batch);
	}
}
