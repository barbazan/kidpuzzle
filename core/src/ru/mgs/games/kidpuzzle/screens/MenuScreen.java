package ru.mgs.games.kidpuzzle.screens;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;
import java.util.List;

import ru.mgs.games.kidpuzzle.KidPuzzleGame;
import ru.mgs.games.kidpuzzle.Puzzle;

import static ru.mgs.games.kidpuzzle.GameConfig.MENU_BG_FILENAME;

/**
 * Created by Дмитрий Малышев on 20.11.2017.
 * Email: dmitry.malyshev@gmail.com
 */

public class MenuScreen extends BaseScreen {

    private List<Sprite> btnSprites = new ArrayList<Sprite>();

    public MenuScreen(KidPuzzleGame game){
        super(game);
        for(Puzzle puzzle : game.puzzles) {
            Sprite spriteMenu = puzzle.puzzleElements.get(0).spriteMenu;
            btnSprites.add(spriteMenu);
        }
    }

    @Override
    public void render(float delta) {
        game.clearScreen();
        game.cam.update();
        game.batchBegin();
        getBgSprite().draw(game.batch);
        for(Sprite sprite : btnSprites) {
            sprite.draw(game.batch);
        }
        game.batchEnd();
    }

    protected InputProcessor initInputProcessor() {
        return new GestureDetector(new GestureDetector.GestureAdapter() {
            @Override
            public boolean touchDown(float x, float y, int pointer, int button) {
                Vector3 coords = game.cam.unproject(new Vector3(x, y , 0));
                for(int i = 0; i < btnSprites.size(); i++) {
                    Sprite sprite = btnSprites.get(i);
                    if(sprite.getBoundingRectangle().contains(coords.x, coords.y)) {
                        game.gameScreen = new GameScreen(game, i);
                        game.setScreen(game.gameScreen);
                        game.setInputProcessor(game.gameScreen.getInputProcessor());
                        return true;
                    }
                }
                return true;
            }
        });
    }

    @Override
    protected Sprite initBgSprite() {
        return KidPuzzleGame.createBgSprite(game.cam, MENU_BG_FILENAME);
    }
}