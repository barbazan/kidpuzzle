package ru.mgs.games.kidpuzzle;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.util.ArrayList;
import java.util.List;

import ru.mgs.games.kidpuzzle.screens.GameScreen;
import ru.mgs.games.kidpuzzle.screens.LoadingScreen;
import ru.mgs.games.kidpuzzle.screens.MenuScreen;

import static ru.mgs.games.kidpuzzle.GameConfig.BTN_HOME_FILENAME;
import static ru.mgs.games.kidpuzzle.GameConfig.BTN_MUSIC_FILENAME;
import static ru.mgs.games.kidpuzzle.GameConfig.BTN_PAY_FILENAME;
import static ru.mgs.games.kidpuzzle.GameConfig.BTN_SOUND_FILENAME;
import static ru.mgs.games.kidpuzzle.GameConfig.DEFAULT_CAMERA_ZOOM;
import static ru.mgs.games.kidpuzzle.GameConfig.DEFAULT_SOUND_VOLUME;
import static ru.mgs.games.kidpuzzle.GameConfig.GAME_BG_FILENAME;
import static ru.mgs.games.kidpuzzle.GameConfig.MENU_BG_FILENAME;
import static ru.mgs.games.kidpuzzle.GameConfig.PARTICLE_WIN_FILENAME;
import static ru.mgs.games.kidpuzzle.GameConfig.SOUND_BG_FILENAME;
import static ru.mgs.games.kidpuzzle.GameConfig.SOUND_RIGHT_FILENAME;
import static ru.mgs.games.kidpuzzle.GameConfig.SOUND_WIN_FILENAME;
import static ru.mgs.games.kidpuzzle.GameConfig.SOUND_WRONG_FILENAME;

/**
 * Created by Дмитрий Малышев on 20.11.2017.
 * Email: dmitry.malyshev@gmail.com
 */

public class KidPuzzleGame extends Game {

	public static boolean MUSIC_ON = false;
	public static boolean SOUND_ON = true;

	public static AssetManager assetManager = new AssetManager();
	private LoadingScreen loadingScreen;
	public MenuScreen menuScreen;
	public GameScreen gameScreen;
	public OrthographicCamera cam;
	public List<Puzzle> puzzles = new ArrayList<Puzzle>();
	public SpriteBatch batch;
	public Sound bgSound;
	public AdHandler adHandler;

	public KidPuzzleGame(AdHandler adHandler) {
		this.adHandler = adHandler;
	}

	@Override
	public void create () {
		initCam();
		batch = new SpriteBatch();
		loadResources();
		loadingScreen = new LoadingScreen(this);
		setScreen(loadingScreen);
	}

	private void loadResources() {
		assetManager.load(SOUND_BG_FILENAME, Sound.class);
		assetManager.load(SOUND_RIGHT_FILENAME, Sound.class);
		assetManager.load(SOUND_WRONG_FILENAME, Sound.class);
		assetManager.load(SOUND_WIN_FILENAME, Sound.class);
		assetManager.load(PARTICLE_WIN_FILENAME, ParticleEffect.class);
		assetManager.load(MENU_BG_FILENAME, Texture.class);
		assetManager.load(GAME_BG_FILENAME, Texture.class);
		assetManager.load(BTN_HOME_FILENAME, Texture.class);
		assetManager.load(BTN_SOUND_FILENAME, Texture.class);
		assetManager.load(BTN_MUSIC_FILENAME, Texture.class);
		assetManager.load(BTN_PAY_FILENAME, Texture.class);
		for(Puzzle puzzle : Puzzle.values()) {
			for(PuzzleElementInfo info : puzzle.puzzleElementInfos) {
				assetManager.load(info.getFilename(), Texture.class);
				assetManager.load(info.getFilenameDisable(), Texture.class);
			}
		}
	}

	public void finishLoading() {
		for(Puzzle puzzle : Puzzle.values()) {
			puzzle.initPuzzle(this);
			puzzles.add(puzzle);
		}
		bgSound = assetManager.get(SOUND_BG_FILENAME, Sound.class);
		if(MUSIC_ON) {
			bgSound.loop(DEFAULT_SOUND_VOLUME);
		}
		menuScreen = new MenuScreen(this);
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

	@Override
	public void dispose() {
		bgSound.dispose();
		assetManager.dispose();
		loadingScreen.dispose();
		menuScreen.dispose();
		gameScreen.dispose();
		super.dispose();
	}

	public static Sprite createSprite(String filename) {
		Texture texture = assetManager.get(filename, Texture.class);
		texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		return new Sprite(texture);
	}

	public static Sprite createSprite(String filename, Vector3 startPosition, float size) {
		Sprite sprite = createSprite(filename);
		sprite.setPosition(startPosition.x, startPosition.y);
		sprite.setSize(size, size);
		return sprite;
	}

	public static Sprite createBgSprite(OrthographicCamera cam, String bgFileName) {
		Sprite bgSprite = createSprite(bgFileName);
		float xScale = cam.viewportWidth / bgSprite.getWidth();
		float yScale = cam.viewportHeight / bgSprite.getHeight();
		float scale = Math.max(xScale, yScale);
		bgSprite.setScale(scale);
		bgSprite.setPosition(-bgSprite.getWidth() / 2, -bgSprite.getHeight() / 2);
		return bgSprite;
	}

	public static ImageButton createButton(String imageUpFilename, String imageDownFilename, Vector3 position, float size) {
		Drawable imageUp = new TextureRegionDrawable(new TextureRegion(assetManager.get(imageUpFilename, Texture.class)));
		Drawable imageDown = new TextureRegionDrawable(new TextureRegion(assetManager.get(imageDownFilename, Texture.class)));
		ImageButton button = new ImageButton(imageUp, imageDown);
		button.setPosition(position.x, position.y);
		button.setSize(size, size);
		return button;
	}
}
