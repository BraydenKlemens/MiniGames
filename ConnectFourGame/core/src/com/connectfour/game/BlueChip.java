package com.connectfour.game;

import com.badlogic.gdx.graphics.Texture;

public class BlueChip extends Chip {

	/**
	 * @param tile
	 */
	public BlueChip(Tile tile) {
		super(new Texture("blueTeam.png"), tile);
	}

	@Override
	public int getType() {
		return 0;
	}

}
