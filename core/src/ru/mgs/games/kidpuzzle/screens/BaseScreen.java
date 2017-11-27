package ru.mgs.games.kidpuzzle.screens;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Sprite;

import ru.mgs.games.kidpuzzle.KidPuzzleGame;

/**
 * Created by Дмитрий Малышев on 27.11.2017.
 * Email: dmitry.malyshev@gmail.com
 */
public abstract class BaseScreen implements Screen {

    protected KidPuzzleGame game;
    protected InputProcessor inputProcessor;
    protected Sprite bgSprite;

    public BaseScreen(KidPuzzleGame game) {
        this.game = game;
    }

    public InputProcessor getInputProcessor() {
        if(inputProcessor == null) {
            inputProcessor = initInputProcessor();
        }
        return inputProcessor;
    }

    protected abstract InputProcessor initInputProcessor();

    public Sprite getBgSprite() {
        if(bgSprite == null) {
            bgSprite = initBgSprite();
        }
        return bgSprite;
    }

    protected abstract Sprite initBgSprite();

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

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
        if(bgSprite != null) {
            bgSprite.getTexture().dispose();
        }
    }
}
