package ru.mgs.games.kidpuzzle.screens;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.input.GestureDetector;

import ru.mgs.games.kidpuzzle.KidPuzzleGame;
import ru.mgs.games.kidpuzzle.KidPuzzleUtil;

import static ru.mgs.games.kidpuzzle.GameConfig.MENU_BG_FILENAME;

/**
 * Created by Дмитрий Малышев on 20.11.2017.
 * Email: dmitry.malyshev@gmail.com
 */

public class MenuScreen extends BaseScreen {

    public MenuScreen(KidPuzzleGame game){
        super(game);
    }

    @Override
    public void render(float delta) {
        game.clearScreen();
        game.cam.update();
        game.batchBegin();
        getBgSprite().draw(game.batch);
        game.batchEnd();
    }

    protected InputProcessor initInputProcessor() {
        return new GestureDetector(new GestureDetector.GestureAdapter() {
            @Override
            public boolean touchDown(float x, float y, int pointer, int button) {
                game.gameScreen = new GameScreen(game);
                game.setScreen(game.gameScreen);
                game.setInputProcessor(game.gameScreen.getInputProcessor());
                return true;
            }
        });
    }

    @Override
    protected Sprite initBgSprite() {
        return KidPuzzleUtil.createBgSprite(game.cam, MENU_BG_FILENAME);
    }
}