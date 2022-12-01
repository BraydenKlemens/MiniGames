package com.connectfour.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Tile {
	
	//variables
	private final Rectangle rect;
	private final int x;
	private final int y;
	private final int j;
	private final int i;
	private Chip chip;

	/**
	 * @param x
	 * @param y
	 * @param i
	 * @param j
	 */
	public Tile(int x, int y, int i, int j) {
		this.i = i;
		this.j = j;
		this.x = x;
		this.y = y;
		this.rect = new Rectangle(x, y, 80, 80);
	}

	/**
	 * @return
	 */
	public int getY() {
		return y;
	}

	/**
	 * @return
	 */
	public int getX() {
		return x;
	}

	/**
	 * @return
	 */
	public Rectangle getRectangle() {
		return rect;
	}

	/**
	 * @return
	 */
	public boolean hasChip() {
		return chip != null;
	}

	/**
	 * @return
	 */
	public Chip getChip() {
		return chip;
	}

	/**
	 * @param chip
	 */
	public void setChip(Chip chip) {
		this.chip = chip;
	}

	/**
	 * @param batch
	 */
	public void draw(SpriteBatch batch) {
		if (hasChip()) {
			chip.draw(batch);
		}
	}

	/**
	 * @return
	 */
	public int getRow() {
		return i;
	}

	/**
	 * @return
	 */
	public int getColumn() {
		return j;
	}

}
