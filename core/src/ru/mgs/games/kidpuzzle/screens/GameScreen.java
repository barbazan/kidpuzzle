package ru.mgs.games.kidpuzzle.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
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
import static ru.mgs.games.kidpuzzle.GameConfig.DEFAULT_SOUND_VOLUME;
import static ru.mgs.games.kidpuzzle.GameConfig.GAME_BG_FILENAME;
import static ru.mgs.games.kidpuzzle.GameConfig.PARTICLE_WIN_FILENAME;
import static ru.mgs.games.kidpuzzle.GameConfig.SOUND_RIGHT_FILENAME;
import static ru.mgs.games.kidpuzzle.GameConfig.SOUND_WIN_FILENAME;
import static ru.mgs.games.kidpuzzle.GameConfig.SOUND_WRONG_FILENAME;
import static ru.mgs.games.kidpuzzle.KidPuzzleGame.MUSIC_ON;
import static ru.mgs.games.kidpuzzle.KidPuzzleGame.SOUND_ON;

/**
 * Created by Дмитрий Малышев on 20.11.2017.
 * Email: dmitry.malyshev@gmail.com
 */

public class GameScreen extends BaseScreen {

    private Sound rightSound;
    private Sound wrongSound;
    private Sound winSound;
    private Sprite homeBtnSprite;
    private Sprite soundBtnSprite;
    private Sprite musicBtnSprite;
    private ParticleEffect particleEffect;

    private Puzzle puzzle;
    private PuzzleElement selectedPuzzleElement;
    public boolean isWin = false;
    private boolean winSoundPlay = false;

    public GameScreen(KidPuzzleGame game) {
        super(game);
        this.game = game;
        initButtons();
        initSounds();
        initParticles();
       	puzzle = Puzzle.PUZZLE_2; //todo
		puzzle.initPuzzle(game.cam);
    }

    @Override
    public void render(float delta) {
		game.clearScreen();
		game.cam.update();

		game.batchBegin();
		getBgSprite().draw(game.batch);
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
        super.dispose();
    }

    protected InputProcessor initInputProcessor() {
		return new GestureDetector(new GestureDetector.GestureAdapter() {
			@Override
			public boolean touchDown(float x, float y, int pointer, int button) {
				Vector3 coords = game.cam.unproject(new Vector3(x, y , 0));
                if(homeBtnSprite.getBoundingRectangle().contains(coords.x, coords.y)) {
                    game.setScreen(game.menuScreen);
                    game.setInputProcessor(game.menuScreen.getInputProcessor());
                    winSound.stop();
                    return true;
                }
                if(soundBtnSprite.getBoundingRectangle().contains(coords.x, coords.y)) {
                    SOUND_ON = !SOUND_ON;
                    return true;
                }
                if(musicBtnSprite.getBoundingRectangle().contains(coords.x, coords.y)) {
                    MUSIC_ON = !MUSIC_ON;
                    if(MUSIC_ON) {
                        game.bgSound.loop(DEFAULT_SOUND_VOLUME);
                    } else {
                        game.bgSound.stop();
                    }
                    return true;
                }
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
                        if(SOUND_ON) {
                            rightSound.play(DEFAULT_SOUND_VOLUME);
                        }
					} else {
						selectedPuzzleElement.resetPosition();
                        if(SOUND_ON) {
                            wrongSound.play(DEFAULT_SOUND_VOLUME);
                        }
					}
					selectedPuzzleElement = null;
				}
				isWin = true;
				for(PuzzleElement puzzleElement : puzzle.puzzleElements) {
					if(!puzzleElement.isInPlace()) {
						isWin = false;
						return true;
					}
				}
				if(isWin && SOUND_ON && !winSoundPlay) {
                    winSoundPlay = true;
                    winSound.play(DEFAULT_SOUND_VOLUME);
                }
				return super.touchUp(x, y, pointer, button);
			}
		};
	}

    @Override
    protected Sprite initBgSprite() {
        return KidPuzzleUtil.createBgSprite(game.cam, GAME_BG_FILENAME);
    }

    private void drawPuzzle() {
        for(PuzzleElement puzzleElement : puzzle.puzzleElements) {
            puzzleElement.spriteDisable.draw(game.batch);
        }
        for(PuzzleElement puzzleElement : puzzle.puzzleElements) {
            puzzleElement.sprite.draw(game.batch);
        }
    }

    private void initSounds() {
        rightSound = game.assetManager.get(SOUND_RIGHT_FILENAME, Sound.class);
        wrongSound = game.assetManager.get(SOUND_WRONG_FILENAME, Sound.class);
        winSound = game.assetManager.get(SOUND_WIN_FILENAME, Sound.class);
    }

    private void disposeSound() {
        rightSound.dispose();
        wrongSound.dispose();
        winSound.dispose();
    }

    private void initButtons() {
        int size = PuzzleElementInfo.PUZZLE_ELEMENT_1.getSize() / 2;
        float x = Gdx.graphics.getWidth() - size * 1.5f;
        float y = size * 1.5f;
        homeBtnSprite = KidPuzzleUtil.createSprite(BTN_HOME_FILENAME, game.cam.unproject(new Vector3(x, y, 0)), size);
        y = size * 3f;
        soundBtnSprite = KidPuzzleUtil.createSprite(BTN_SOUND_FILENAME, game.cam.unproject(new Vector3(x, y, 0)), size);
        y = size * 4.5f;
        musicBtnSprite = KidPuzzleUtil.createSprite(BTN_MUSIC_FILENAME, game.cam.unproject(new Vector3(x, y, 0)), size);
    }

    private void drawButtons() {
        homeBtnSprite.draw(game.batch);
        soundBtnSprite.draw(game.batch);
        musicBtnSprite.draw(game.batch);
    }

    private void initParticles() {
        particleEffect = game.assetManager.get(PARTICLE_WIN_FILENAME, ParticleEffect.class);
        particleEffect.getEmitters().first().setPosition(0, -Gdx.graphics.getHeight() / 2);
        particleEffect.reset();
    }

    private void drawParticles() {
        particleEffect.start();
        particleEffect.update(Gdx.graphics.getDeltaTime());
        particleEffect.draw(game.batch);
//        if (particleEffect.isComplete()) {
//            particleEffect.reset();
//        }
    }

    private void disposeParticles() {
        particleEffect.dispose();
    }
}
