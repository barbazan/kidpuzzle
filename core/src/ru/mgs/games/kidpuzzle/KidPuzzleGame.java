package ru.mgs.games.kidpuzzle;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import ru.mgs.games.kidpuzzle.screens.GameScreen;
import ru.mgs.games.kidpuzzle.screens.LoadingScreen;
import ru.mgs.games.kidpuzzle.screens.MenuScreen;

import static ru.mgs.games.kidpuzzle.GameConfig.DEFAULT_CAMERA_ZOOM;

/**
 * Created by Дмитрий Малышев on 20.11.2017.
 * Email: dmitry.malyshev@gmail.com
 */

public class KidPuzzleGame extends Game {

	public static boolean MUSIC_ON = true;
	public static boolean SOUND_ON = true;

	public LoadingScreen loadingScreen;
	public MenuScreen menuScreen;
	public GameScreen gameScreen;
	public OrthographicCamera cam;
	public SpriteBatch batch;
	public AssetManager assetManager;

	@Override
	public void create () {
		initCam();
		batch = new SpriteBatch();
		assetManager = new AssetManager();
		assetManager.load("sound/bg_sound.wav", Sound.class);
		assetManager.load("sound/right.mp3", Sound.class);
		assetManager.load("sound/wrong.wav", Sound.class);
		assetManager.load("sound/win.mp3", Sound.class);
		loadingScreen = new LoadingScreen(this);
		setScreen(loadingScreen);
	}

	public void finishLoading() {
		menuScreen = new MenuScreen(this);
		gameScreen = new GameScreen(this);
		Gdx.input.setInputProcessor(menuScreen.getInputProcessor());
		setScreen(menuScreen);
	}

	public void clearScreen() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}

	public void batchBegin() {
		batch.setProjectionMatrix(cam.combined);
		batch.begin();
	}

	public void batchEnd() {
		batch.end();
	}

	public void setInputProcessor(InputProcessor inputProcessor) {
		Gdx.input.setInputProcessor(inputProcessor);
	}

	private void initCam() {
		cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.position.set(0, 0, 0);
		cam.zoom = DEFAULT_CAMERA_ZOOM;
		cam.update();
	}
}
