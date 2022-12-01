package com.connectfour.game;

import com.badlogic.gdx.graphics.Texture;

public class RedChip extends Chip {

	/**
	 * @param tile
	 */
	public RedChip(Tile tile) {
		super(new Texture("redTeam.png"), tile);
	}
	@Override
	public int getType() {
		return 1;
	}

}
