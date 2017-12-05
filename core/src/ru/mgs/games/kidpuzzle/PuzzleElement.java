package ru.mgs.games.kidpuzzle;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Дмитрий Малышев on 01.11.2017.
 * Email: dmitry.malyshev@gmail.com
 */

public class PuzzleElement {

    public PuzzleElementInfo info;
    public Sprite sprite;
    public Sprite spriteDisable;
    public Sprite spriteMenu;
    private boolean fixed;
    private KidPuzzleGame game;

    public PuzzleElement(PuzzleElementInfo info, KidPuzzleGame game) {
        this.info = info;
        this.game = game;
        sprite = createPuzzleSprite(game.cam.unproject(info.getStartPosition()));
        spriteDisable = createPuzzleSpriteDisable(game.cam.unproject(info.getPositionDisabled()));
        spriteMenu = createPuzzleSpriteMenu(game.cam.unproject(info.getPositionForMenu()));
        spriteMenu.setSize(info.getSizeForMenu(), info.getSizeForMenu());
    }

    public Sprite createPuzzleSprite(Vector3 unprojectPosition) {
        return KidPuzzleGame.createSprite(info.getFilename(), unprojectPosition, info.getSize());
    }

    public Sprite createPuzzleSpriteDisable(Vector3 unprojectPosition) {
        return KidPuzzleGame.createSprite(info.getFilenameDisable(), unprojectPosition, info.getSizeDisabled());
    }

    public Sprite createPuzzleSpriteMenu(Vector3 unprojectPosition) {
        return KidPuzzleGame.createSprite(info.getFilename(), unprojectPosition, info.getSize());
    }

    public void resetPosition() {
        sprite.setPosition(game.cam.unproject(info.getStartPosition()).x, game.cam.unproject(info.getStartPosition()).y);
    }

    public void setSize(float size) {
        sprite.setSize(size, size);
    }

    public void setPosition(float x, float y) {
        sprite.setPosition(x, y);
    }

    public boolean isInPlace() {
        boolean isX = sprite.getX() >= spriteDisable.getX() - spriteDisable.getWidth() / 2 && sprite.getX() <= spriteDisable.getX() + spriteDisable.getWidth() / 2;
        boolean isY = sprite.getY() >= spriteDisable.getY() - spriteDisable.getHeight() / 2 && sprite.getY() <= spriteDisable.getY() + spriteDisable.getHeight() / 2;
        return isX && isY;
    }

    public void setFixed(boolean fixed) {
        this.fixed = fixed;
        sprite.setPosition(spriteDisable.getX(), spriteDisable.getY());
    }

    public boolean isFixed() {
        return fixed;
    }
}
