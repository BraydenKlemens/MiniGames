package com.flappybird.game;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class FlappyBird extends ApplicationAdapter implements InputProcessor {

	// sprite and textures
	private SpriteBatch batch;

	private Texture pipeItUp;
	private Texture pipeItDown;
	private Texture hideRect;

	private Sprite night;
	private Sprite flappy;
	private Sprite over;
	private Sprite start;
	private Sprite playButton;
	private Sprite heart1;
	private Sprite heart2;
	private Sprite heart3;
	private Sprite heart4;
	private Sprite ground;
	private Sprite ground2;

	// variables
	private double vy;
	private int score = 0;
	private double health = 0;
	private long lastTime, totalTime, timeLapsed, previousTime;
	private int level = 1;

	private String healthTitle;
	private String levelTitle;
	private String timerTitle;

	// booleans
	private boolean gameOver = false;
	private boolean gameStart = false;

	// array lists
	private final ArrayList<Sprite> hiddenBlock = new ArrayList<>();
	private final ArrayList<Sprite> pipesUp = new ArrayList<>();
	private final ArrayList<Sprite> pipesDown = new ArrayList<>();
	private final ArrayList<Rectangle> pipesUpRect = new ArrayList<>();
	private final ArrayList<Rectangle> hiddenBlockRect = new ArrayList<>();
	private final ArrayList<Rectangle> pipesDownRect = new ArrayList<>();

	// displays
	private BitmapFont displayScores;
	private BitmapFont displayHealth;
	private BitmapFont displayLevel;
	private BitmapFont displayTime;

	// rectangle
	private Rectangle birdRect;

	// Audio
	private Sound point;
	private Sound hit;
	private Sound flap;

	@Override
	public void create() {

		// Audio
		hit = Gdx.audio.newSound(Gdx.files.internal("hit.mp3"));
		flap = Gdx.audio.newSound(Gdx.files.internal("flap.mp3"));
		point = Gdx.audio.newSound(Gdx.files.internal("point.mp3"));

		// creates textures
		batch = new SpriteBatch();
		Texture backGround = new Texture("Day.png");
		pipeItUp = new Texture("pipe.png");
		pipeItDown = new Texture("pipe.png");
		Texture bird = new Texture("Bird.png");
		hideRect = new Texture("invisable.png");
		Texture startScreen = new Texture("FlappyStart.jpg");
		Texture overScreen = new Texture("FlappyOver.jpg");
		Texture heartHealth = new Texture("heartHealth.png");
		Texture play = new Texture("PlayButton.png");
		Texture flapGround = new Texture("Birdground.png");

		// set sprite to texture
		night = new Sprite(backGround);
		flappy = new Sprite(bird);
		start = new Sprite(startScreen);
		over = new Sprite(overScreen);
		playButton = new Sprite(play);
		ground = new Sprite(flapGround);
		ground2 = new Sprite(flapGround);

		// hearts
		heart1 = new Sprite(heartHealth);
		heart2 = new Sprite(heartHealth);
		heart3 = new Sprite(heartHealth);
		heart4 = new Sprite(heartHealth);

		// Display
		displayScores = new BitmapFont();
		displayHealth = new BitmapFont();
		displayLevel = new BitmapFont();
		displayTime = new BitmapFont();

		// SIZES
		flappy.setSize(48, 45);
		over.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		start.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		night.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight() + 25);
		playButton.setSize(100, 80);
		ground.setSize(800, 50);
		ground2.setSize(800, 50);

		// hearts sizes
		heart1.setSize(30, 30);
		heart2.setSize(30, 30);
		heart3.setSize(30, 30);
		heart4.setSize(30, 30);

		// POSITIONS
		night.setPosition(0, -25);
		flappy.setPosition(Gdx.graphics.getWidth() / 3, (float) 300);
		playButton.setPosition(270, 30);
		ground2.setPosition(700, 0);

		// heart positions
		heart1.setPosition(30, 365);
		heart2.setPosition(60, 365);
		heart3.setPosition(90, 365);
		heart4.setPosition(120, 365);

		// pipe hidden block positioning
		for (int i = 0; i < 200; i++) {
			pipesUpSetUp();
			float yPos = (float) (Math.random() * 200);
			pipesUp.get(i).setPosition((float) (i + 610 + i * 280), yPos + 230);
			pipesDownSetUp();
			pipesDown.get(i).setPosition((float) (i + 610 + i * 280), yPos - 200);
			hiddenBlockSetUp();
			hiddenBlock.get(i).setPosition((float) (i + 675 + i * 280), 0);
		}

		// timers
		lastTime = System.currentTimeMillis();
		previousTime = System.currentTimeMillis();

		// bird rectangle
		birdRect = flappy.getBoundingRectangle();

		// mouse click
		Gdx.input.setInputProcessor(this);

	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// Game timer
		if (gameStart) {
			timeLapsed += System.currentTimeMillis() - previousTime;
		}
		previousTime = System.currentTimeMillis();

		// Timer for jumping
		if (gameStart) {
			totalTime += System.currentTimeMillis() - lastTime;
		}
		// Display
		// Strings
		String scoreTitle = ("SCORE: " + score);
		healthTitle = ("HEALTH: " + (health / 4) * 100 + "%");
		levelTitle = (" USE (UP/ DOWN) ARROWS TO SET HEALTH: " + level);
		timerTitle = ("TIME: " + timeLapsed / 1000);

		// choose health
		if (!gameStart) {
			if (flappy != null) {
				if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
					level += 1;
				}
				if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
					level -= 1;
				}
				if (level >= 4) {
					level = 4;
				}
				if (level <= 1) {
					level = 1;
				}
				if (level == 1) {
					health = 1;
				}
				if (level == 2) {
					health = 2;
				}
				if (level == 3) {
					health = 3;
				}
				if (level == 4) {
					health = 4;
				}
				if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
					gameStart = true;
				}
			}
		}

		// lOOSING
		// fall to ground during play
		if (flappy != null) {
			if (flappy.getY() <= 50) {
				health -= 100;
				gameOver = true;
				loose();
			}
		}
		
		// fall to ground after losing health (this is so flappyBird dies like
		// in the actual game)
		if (flappy != null) {
			if (health <= 0 && flappy.getY() <= 50) {
				gameOver = true;
				loose();
			}
		}
		// Stop movement
		if (health <= 0) {
			
			// stop pipes up
			for (int i = pipesUp.size() - 1; i >= 0; i--) {
				if (pipesUp.get(i) != null) {
					pipesUp.get(i).translateX(3);
				}
			}
			
			// stop pipes down
			for (int i = pipesDown.size() - 1; i >= 0; i--) {
				if (pipesDown.get(i) != null) {
					pipesDown.get(i).translateX(3);
				}
			}
			
			// stop hidden blocks
			for (int i = hiddenBlock.size() - 1; i >= 0; i--) {
				if (hiddenBlock.get(i) != null) {
					hiddenBlock.get(i).translateX(3);
				}
			}

			// stop hidden blocks
			for (int i = 0; i < pipesDownRect.size(); i++) {
				if (pipesDownRect.get(i) != null) {
					pipesDownRect.set(i, null);
				}
			}
			
			// stops the ground after flappyBird dies
			ground.translateX(3);
			ground2.translateX(3);
		}
		
		// play fail 1 time
		//if (health <= 0 && counter == 0) {
			//fail.play();
			//counter += 1;
		//}

		// MOVEMENT
		// Boundary top
		if (flappy != null) {
			if (flappy.getY() >= Gdx.graphics.getHeight() - flappy.getHeight()) {
				flappy.setY((Gdx.graphics.getHeight() - flappy.getHeight()));
			}
		}
		
		// moves bird down always
		if (gameStart) {
			if (flappy != null) {
				double vx = 0;
				flappy.translate((float) vx, (float) vy);
				if (totalTime > 500) {
					vy -= 0.6;
					totalTime -= 500;

					// boundary on bottom
					if (flappy.getY() < 40) {
						flappy.setY(40);
					}
				}
			}
		}

		// move pipesUp
		if (gameStart) {
			for (int i = pipesUp.size() - 1; i >= 0; i--) {
				if (pipesUp.get(i) != null) {
					pipesUp.get(i).translateX(-3);
					if (pipesUp.get(i).getX() < -80) {
						pipesUp.set(i, null);
					}
				}
			}

			// move pipesDown
			for (int i = pipesDown.size() - 1; i >= 0; i--) {
				if (pipesDown.get(i) != null) {
					pipesDown.get(i).translateX(-3);
					if (pipesDown.get(i).getX() < -80) {
						pipesDown.set(i, null);
					}
				}
			}

			// move hidden blocks
			for (int i = hiddenBlock.size() - 1; i >= 0; i--) {
				if (hiddenBlock.get(i) != null) {
					hiddenBlock.get(i).translateX(-3);
				}
			}
		}

		// COLLISION
		if (flappy != null && birdRect != null) {
			birdRect = flappy.getBoundingRectangle();
		}

		// set pipe rectangles to pipe up and down arrays
		//up pipes
		for (int i = 0; i < pipesUpRect.size(); i++) {
			if (pipesUpRect.get(i) != null && pipesUp.get(i) != null) {
				pipesUpRect.set(i, pipesUp.get(i).getBoundingRectangle());
			}
		}
		
		//down pipes
		for (int i = 0; i < pipesDownRect.size(); i++) {
			if (pipesDownRect.get(i) != null && pipesDown.get(i) != null) {
				pipesDownRect.set(i, pipesDown.get(i).getBoundingRectangle());
			}
		}
		
		//hidden blocks
		for (int i = 0; i < hiddenBlockRect.size(); i++) {
			if (hiddenBlock.get(i) != null && hiddenBlockRect.get(i) != null) {
				hiddenBlockRect.set(i, hiddenBlock.get(i).getBoundingRectangle());
			}
		}

		// Collision with pipesUp and bird
		for (int i = 0; i < pipesUpRect.size(); i++) {
			if (pipesUpRect.get(i) != null && birdRect != null) {
				if (birdRect.overlaps(pipesUpRect.get(i))) {
					hit.play();
					pipesUpRect.set(i, null);
					health -= 1;
				}
			}
		}

		// Collision with pipesDown and bird
		for (int i = 0; i < pipesDownRect.size(); i++) {
			if (pipesDownRect.get(i) != null && birdRect != null) {
				if (birdRect.overlaps(pipesDownRect.get(i))) {
					hit.play();
					pipesDownRect.set(i, null);
					health -= 1;
				}
			}
		}

		// Collision bird and invisible rectangle
		for (int i = hiddenBlock.size() - 1; i >= 0; i--) {
			if (birdRect != null && hiddenBlockRect.get(i) != null) {
				if (birdRect.overlaps(hiddenBlockRect.get(i))) {
					point.play();
					hiddenBlockRect.set(i, null);
					score++;
				}
			}
		}

		// ground scrolling
		ground.translateX(-3);
		ground2.translateX(-3);

		if (ground.getX() < -700) {
			ground.setX(700);
		}
		if (ground2.getX() < -700) {
			ground2.setX(700);
		}

		// draw all sprite
		batch.begin();
		if (gameStart) {
			
			// background
			if (night != null) {
				night.draw(batch);
			}

			// top pipes
			for (Sprite sprite : pipesUp) {
				if (sprite != null) {
					sprite.draw(batch);
				}
			}

			// bottom pipes
			for (Sprite sprite : pipesDown) {
				if (sprite != null) {
					sprite.draw(batch);
				}
			}

			// hearts
			drawHearts();

			// bird
			if (flappy != null) {
				flappy.draw(batch);
			}

			// display (health,time,score)
			if (displayScores != null) {
				displayScores.draw(batch, scoreTitle, 30, 450);
			}
			if (displayHealth != null && healthTitle != null) {
				displayHealth.draw(batch, healthTitle, 30, 410);
			}
			if (displayTime != null && timerTitle != null) {
				displayTime.draw(batch, timerTitle, 30, 430);
			}

			// draw ground - game play
			ground.draw(batch);
			ground2.draw(batch);

			// draw game over screen and the player score
			if (gameOver) {
				flap.stop();
				if (over != null) {
					over.draw(batch);
				}
				if (displayScores != null) {
					displayScores.draw(batch, scoreTitle, 390, 240);
				}

				// draw ground - game over
				ground.translateX(-3);
				ground2.translateX(-3);
				ground.draw(batch);
				ground2.draw(batch);
			}

			// components of start screen
		} else {
			start.draw(batch);
			if (displayLevel != null && levelTitle != null) {
				displayLevel.draw(batch, levelTitle, 120, 450);
			}

			// draw ground - game start
			ground.draw(batch);
			ground2.draw(batch);

			if (playButton != null) {
				playButton.draw(batch);
			}
		}

		batch.end();
	}

	private void pipesUpSetUp() {
		Sprite pipeU = new Sprite(pipeItUp);
		pipeU.setSize(70, 300);
		Rectangle upRect = pipeU.getBoundingRectangle();
		pipesUpRect.add(upRect);
		pipesUp.add(pipeU);
	}

	private void pipesDownSetUp() {
		Sprite pipeD = new Sprite(pipeItDown);
		pipeD.setSize(70, 300);
		Rectangle downRect = pipeD.getBoundingRectangle();
		pipesDownRect.add(downRect);
		pipesDown.add(pipeD);
	}

	private void hiddenBlockSetUp() {
		Sprite invisible = new Sprite(hideRect);
		invisible.setSize(30, 480);
		Rectangle hideRect = invisible.getBoundingRectangle();
		hiddenBlockRect.add(hideRect);
		hiddenBlock.add(invisible);
	}

	private void drawHearts() {
		
		// removes hearts from screen as bird loses health
		if (heart1 != null) {
			heart1.draw(batch);
		}
		if (heart2 != null) {
			heart2.draw(batch);
		}
		if (heart3 != null) {
			heart3.draw(batch);
		}
		if (heart4 != null) {
			heart4.draw(batch);
		}
		if (health == 3) {
			heart4 = null;
		} else if (health == 2) {
			heart3 = null;
			heart4 = null;
		} else if (health == 1) {
			heart2 = null;
			heart3 = null;
			heart4 = null;
		} else if (health == 0) {
			heart1 = null;
			heart2 = null;
			heart3 = null;
			heart4 = null;
		}
	}

	private void loose() {
		
		// sets everything that is on screen to null
		for (int i = 0; i < pipesUp.size(); i++) {
			pipesUp.set(i, null);
		}
		for (int i = 0; i < pipesDown.size(); i++) {
			pipesDown.set(i, null);
		}
		for (int i = 0; i < pipesUpRect.size(); i++) {
			pipesUpRect.set(i, null);
		}
		for (int i = 0; i < pipesDownRect.size(); i++) {
			pipesDownRect.set(i, null);
		}
		for (int i = 0; i < hiddenBlock.size(); i++) {
			hiddenBlock.set(i, null);
		}
		for (int i = 0; i < hiddenBlockRect.size(); i++) {
			hiddenBlockRect.set(i, null);
		}

		flappy = null;
		birdRect = null;
		night = null;
		displayHealth = null;
		displayTime = null;
		displayLevel = null;
		healthTitle = null;
		timerTitle = null;
		levelTitle = null;
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
		// allows user to click button for game start
		if (playButton != null && !gameStart) {
			Rectangle play = playButton.getBoundingRectangle();
			if (play.contains(screenX, Gdx.graphics.getHeight() - screenY)) {
				playButton = null;
				gameStart = true;
			}
		}
		// jumping with click
		if (health > 0) {
			vy = 7.5;
			flap.play();
		}
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {

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
