package com.connectfour.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class MasterClass implements Screen, InputProcessor {
	
	//CONFIG.WIDTH = 800

	// variables
	public Launcher game;
	private final Tile[][] grid = new Tile[6][7];
	private int x = 0;
	private int y = 0;
	private Sprite sprite;
	private boolean turn = true;
	private String winner = " ";
	// display
	private final BitmapFont font = new BitmapFont();
	private final BitmapFont restart = new BitmapFont();
	// Array list
	private final ArrayList<Tile> lastMoves = new ArrayList<>();

	public MasterClass(Launcher game) {
		this.game = game;
	}

	@Override
	public void show() {

		sprite = new Sprite(new Texture("Connect4Board.png"));
		sprite.setSize(560, 480);

		// creates a grid of tiles starting in bottom left corner
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				Tile tile = new Tile(x, y, i, j);
				grid[i][j] = tile;
				x += 80;
			}
			x = 0;
			y += 80;
		}

		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// sets font size
		font.getData().setScale(2, 2);
		// method calls
		clearBoard();
		moveChips();
		draw();
	}

	public void draw() {
		game.batch.begin();

		// draw chips
		for (Tile[] tiles : grid) {
			for (Tile tile : tiles) {
				tile.draw(game.batch);
			}
		}

		// displays which player turn it is
		if (turn) {
			font.draw(game.batch, "Blue's Turn", 580, 400);
		} else {
			font.draw(game.batch, "Red's Turn", 580, 400);
		}

		// draws the last moves
		int y = 200;
		for (Tile move : lastMoves) {
			font.draw(game.batch, "Last Move: " + move.getRow() + "," + move.getColumn(), 580, y);
			y += 30;
		}

		restart.draw(game.batch, "Press SPACE to reset game", 580, 100);
		font.draw(game.batch, "Winner: " + winner, 580, 150);

		sprite.draw(game.batch);

		game.batch.end();
	}

	public void clearBoard() {
		// remove all chips from board
		for (Tile[] tiles : grid) {
			for (Tile tile : tiles) {
				if (tile.hasChip()) {
					if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
						tile.setChip(null);
						winner = " ";
					}
				}
			}
		}
	}

	public void moveChips() {
		// moves chips down to a grid location then stops
		for (Tile[] tiles : grid) {
			for (Tile tile : tiles) {
				if (tile.hasChip()) {
					tile.getChip().translate(tile);
				}
			}
		}
	}

	public void detectWins() {
		boolean stop = false;
		int num = 0;
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				// column check
				for (int k = 1; k <= 3; k++) {
					if (grid[i][j].hasChip() && !stop) {
						if (checkChip(i + k, j, grid[i][j].getChip().getType())) {
							num++;
						} else {
							stop = true;
						}
					}
				}
				Winner(num, i, j);
				// row check
				stop = false;
				num = 0;
				for (int k = 1; k <= 3; k++) {
					if (grid[i][j].hasChip() && !stop) {
						if (checkChip(i, j + k, grid[i][j].getChip().getType())) {
							num++;
						} else {
							stop = true;
						}
					}
				}
				Winner(num, i, j);
				// diagonal up right (from left to right)
				stop = false;
				num = 0;
				for (int k = 1; k <= 3; k++) {
					if (grid[i][j].hasChip() && !stop) {
						if (checkChip(i + k, j + k, grid[i][j].getChip().getType())) {
							num++;
						} else {
							stop = true;
						}
					}
				}
				Winner(num, i, j);
				// diagonal down right (from left to right)
				stop = false;
				num = 0;
				for (int k = 1; k <= 3; k++) {
					if (grid[i][j].hasChip() && !stop) {
						if (checkChip(i + k, j - k, grid[i][j].getChip().getType())) {
							num++;
						} else {
							stop = true;
						}
					}
				}
				Winner(num, i, j);
				stop = false;
				num = 0;

			}
		}
	}

	/**
	 * @param num
	 * @param i
	 * @param j
	 */
	public void Winner(int num, int i, int j) {
		if (num > 2) {
			if (grid[i][j].getChip().getType() == 0) {
				winner = "Blue";
				return;
			} else if (grid[i][j].getChip().getType() == 1) {
				winner = "Red";
				return;
			}
		}
	}

	/**
	 * @param i
	 * @param j
	 * @param type
	 * @return
	 */
	public boolean checkChip(int i, int j, int type) {
		return grid.length > i && grid[i].length > j && j >= 0 && grid[i][j].hasChip() && grid[i][j].getChip().getType() == type;
	}

	public void lastMoves() {
		// first in first out - keeps size at 4
		if (lastMoves.size() > 4) {
			lastMoves.remove(0);
		}
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		for (Tile[] tiles : grid) {
			for (Tile tile : tiles) {
				// checks the column mouse is in
				if (tile.getX() < screenX && screenX < tile.getX() + tile.getRectangle().getWidth()) {
					// if no chip
					if (!tile.hasChip()) {
						// blues turn
						if (turn) {
							/*
							 * places blue chip at grid location, adds that
							 * location to array list, keeps array size at 4, only
							 * detect wins when chips are placed, sets turn false
							 * so red can go next
							 */
							tile.setChip(new BlueChip(tile));
							lastMoves.add(tile);
							lastMoves();
							detectWins();
							turn = false;
						} else {
							/*
							 * places red chip at grid location, adds that
							 * location to array list, keeps array size at 4, only
							 * detect wins when chips are placed, sets turn true
							 * so blue can go again
							 */
							tile.setChip(new RedChip(tile));
							lastMoves.add(tile);
							lastMoves();
							detectWins();
							turn = true;
						}
						return true;
					}
				}
			}
		}
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(float amountX, float amountY) {
		return false;
	}

}
