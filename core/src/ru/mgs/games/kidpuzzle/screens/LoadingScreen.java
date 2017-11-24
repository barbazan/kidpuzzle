package ru.mgs.games.kidpuzzle.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import ru.mgs.games.kidpuzzle.KidPuzzleGame;

/**
 * Created by Дмитрий Малышев on 23.11.2017.
 * Email: dmitry.malyshev@gmail.com
 */
public class LoadingScreen  implements Screen {

    private KidPuzzleGame game;
    private Stage stage;
    private Skin skin;
    private Label loading;

    public LoadingScreen(KidPuzzleGame game) {
        this.game = game;
        stage = new Stage(new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        skin = new Skin(Gdx.files.internal("skins/uiskin.json"));
        loading = new Label("Загрузка...", skin);
        loading.setPosition(Gdx.graphics.getWidth() / 2 - loading.getWidth() / 2, Gdx.graphics.getHeight() / 2 - loading.getHeight() / 2);
        stage.addActor(loading);
    }

    @Override
    public void render(float delta) {
        game.clearScreen();
        if(game.assetManager.update()) {
            game.finishLoading();
        } else {
            int progress = (int)(game.assetManager.getProgress() * 100);
            loading.setText("Загрузка... " + progress + "%");
        }
        stage.act();
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
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
