package ru.mgs.games.kidpuzzle.screens;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.input.GestureDetector;

import ru.mgs.games.kidpuzzle.KidPuzzleGame;
import ru.mgs.games.kidpuzzle.KidPuzzleUtil;

import static ru.mgs.games.kidpuzzle.GameConfig.MENU_BG_FILENAME;

/**
 * Created by Дмитрий Малышев on 20.11.2017.
 * Email: dmitry.malyshev@gmail.com
 */

public class MenuScreen implements Screen {

    private KidPuzzleGame game;
    private Sprite bgSprite;
    private InputProcessor inputProcessor;

    //в конструктор передаём ссылку на KidPuzzleGame
    public MenuScreen(KidPuzzleGame game){
        this.game = game;
        bgSprite = KidPuzzleUtil.initBg(game.cam, MENU_BG_FILENAME);
    }

    @Override
    public void render(float delta) {
        game.clearScreen();
        game.cam.update();
        game.batchBegin();
        bgSprite.draw(game.batch);
        game.batchEnd();
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
            public boolean touchDown(float x, float y, int pointer, int button) {
                //меняем экран
                game.setScreen(game.gameScreen);
                game.setInputProcessor(game.gameScreen.getInputProcessor());
                return true;
            }
        });
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

    @Override
    public void dispose() {

    }
}