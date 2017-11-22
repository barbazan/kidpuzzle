package ru.mgs.games.kidpuzzle.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector3;

import ru.mgs.games.kidpuzzle.KidPuzzleGame;
import ru.mgs.games.kidpuzzle.KidPuzzleUtil;
import ru.mgs.games.kidpuzzle.Puzzle;
import ru.mgs.games.kidpuzzle.PuzzleElement;
import ru.mgs.games.kidpuzzle.PuzzleElementInfo;

import static ru.mgs.games.kidpuzzle.GameConfig.BTN_HOME_FILENAME;
import static ru.mgs.games.kidpuzzle.GameConfig.BTN_MUSIC_FILENAME;
import static ru.mgs.games.kidpuzzle.GameConfig.BTN_SOUND_FILENAME;
import static ru.mgs.games.kidpuzzle.GameConfig.GAME_BG_FILENAME;
import static ru.mgs.games.kidpuzzle.KidPuzzleGame.SOUND_ON;

/**
 * Created by Дмитрий Малышев on 20.11.2017.
 * Email: dmitry.malyshev@gmail.com
 */

public class GameScreen implements Screen {

    private KidPuzzleGame game;
    private Sound bgSound;
    private Sound rightSound;
    private Sound wrongSound;
    private Sprite bgSprite;
    private Sprite homeBtnSprite;
    private Sprite soundBtnSprite;
    private Sprite musicBtnSprite;
    private ParticleEffect particleEffect;
    private InputProcessor inputProcessor;

    private Puzzle puzzle;
    private PuzzleElement selectedPuzzleElement;
    private boolean isWin = false;

    //в конструктор передаём ссылку на KidPuzzleGame
    public GameScreen(KidPuzzleGame game){
        this.game = game;
        bgSprite = KidPuzzleUtil.initBg(game.cam, GAME_BG_FILENAME);
        initButtons();
        initSound();
        initParticles();
       	puzzle = Puzzle.PUZZLE_2; //todo
		puzzle.initPuzzle(game.cam);

    }

    private boolean p = false;

    @Override
    public void render(float delta) {
		if(!p && SOUND_ON) {
			bgSound.loop(0.3f);
			p = true;
		}
		game.clearScreen();
		game.cam.update();
		particleEffect.update(Gdx.graphics.getDeltaTime());

		game.batchBegin();
		bgSprite.draw(game.batch);
        drawPuzzle();
		drawButtons();
		if(isWin) {
            drawParticles();
		}
        game.batchEnd();
    }

    @Override
    public void dispose () {
        disposeSound();
        disposeParticles();
    }

    public InputProcessor getInputProcessor() {
        if(inputProcessor == null) {
            initInputProcessor();
        }
        return inputProcessor;
    }

    private void initInputProcessor() {
		inputProcessor = new GestureDetector(new GestureDetector.GestureAdapter() {
            @Override
            public boolean longPress(float x, float y) {
                //меняем экран
                game.setScreen(game.menuScreen);
                game.setInputProcessor(game.menuScreen.getInputProcessor());
                return super.longPress(x, y);
            }

            @Override
			public boolean tap(float x, float y, int count, int button) {
				return super.tap(x, y, count, button);
			}

			@Override
			public boolean touchDown(float x, float y, int pointer, int button) {
				Vector3 coords = game.cam.unproject(new Vector3(x, y , 0));
				for(PuzzleElement puzzleElement : puzzle.puzzleElements) {
					if(!puzzleElement.isFixed() && puzzleElement.sprite.getBoundingRectangle().contains(coords.x, coords.y)) {
						selectedPuzzleElement = puzzleElement;
					}
				}
				return super.touchDown(x, y, pointer, button);
			}

			@Override
			public boolean pan(float x, float y, float deltaX, float deltaY) {
				Vector3 coords = game.cam.unproject(new Vector3(x, y , 0));
				if(selectedPuzzleElement != null) {
					selectedPuzzleElement.sprite.setPosition(coords.x - selectedPuzzleElement.sprite.getWidth() / 2, coords.y - selectedPuzzleElement.sprite.getHeight() / 2);
				}
				return super.pan(x, y, deltaX, deltaY);
			}
		}) {
			@Override
			public boolean touchUp(float x, float y, int pointer, int button) {
				if(selectedPuzzleElement != null) {
					if(selectedPuzzleElement.isInPlace()) {
						selectedPuzzleElement.setFixed(true);
						rightSound.play(0.3f);
					} else {
						selectedPuzzleElement.resetPosition();
						wrongSound.play(0.3f);
					}
					selectedPuzzleElement = null;
				}
				isWin = true;
				for(PuzzleElement puzzleElement : puzzle.puzzleElements) {
					if(!puzzleElement.isInPlace()) {
						isWin = false;
						break;
					}
				}
				return super.touchUp(x, y, pointer, button);
			}
		};
	}

    private void initSound() {
        bgSound = Gdx.audio.newSound(Gdx.files.internal("sound/bg_sound.wav"));
        rightSound = Gdx.audio.newSound(Gdx.files.internal("sound/right.mp3"));
        wrongSound = Gdx.audio.newSound(Gdx.files.internal("sound/wrong.wav"));
    }

    private void initButtons() {
        System.out.println("Gdx.graphics.getHeight() = " + Gdx.graphics.getHeight());
        System.out.println("Gdx.graphics.getWidth() = " + Gdx.graphics.getWidth());
        int size = PuzzleElementInfo.PUZZLE_ELEMENT_1.getSize() / 2;
        System.out.println("size = " + size);
        float x = Gdx.graphics.getWidth() - size * 1.5f;
        float y = size * 1.5f;
        homeBtnSprite = KidPuzzleUtil.createSprite(BTN_HOME_FILENAME, game.cam.unproject(new Vector3(x, y, 0)), size);
        y = size * 3f;
        soundBtnSprite = KidPuzzleUtil.createSprite(BTN_SOUND_FILENAME, game.cam.unproject(new Vector3(x, y, 0)), size);
        y = size * 4.5f;
        musicBtnSprite = KidPuzzleUtil.createSprite(BTN_MUSIC_FILENAME, game.cam.unproject(new Vector3(x, y, 0)), size);
    }

    private void drawPuzzle() {
        for(PuzzleElement puzzleElement : puzzle.puzzleElements) {
            puzzleElement.spriteDisable.draw(game.batch);
        }
        for(PuzzleElement puzzleElement : puzzle.puzzleElements) {
            puzzleElement.sprite.draw(game.batch);
        }
    }

    private void drawButtons() {
        homeBtnSprite.draw(game.batch);
        soundBtnSprite.draw(game.batch);
        musicBtnSprite.draw(game.batch);
    }

    private void initParticles() {
        particleEffect = new ParticleEffect();
        particleEffect.load(Gdx.files.internal("particles/particle_1.pe"), Gdx.files.internal(""));
        particleEffect.getEmitters().first().setPosition(0, -Gdx.graphics.getHeight()/ 2);
        particleEffect.start();
    }

    private void drawParticles() {
        particleEffect.draw(game.batch);
        if (particleEffect.isComplete()) {
            particleEffect.reset();
        }
    }

    private void disposeSound() {
        bgSound.dispose();
        rightSound.dispose();
        wrongSound.dispose();
    }

    private void disposeParticles() {
        particleEffect.dispose();
    }



    @Override
    public void show() {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }
}
