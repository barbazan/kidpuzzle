package ru.mgs.games.kidpuzzle;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by mit on 01.11.2017.
 */

public class PuzzleElement {

    public PuzzleElementInfo info;
    public Sprite sprite;
    public Sprite spriteDisable;
    public boolean fixed;
    private OrthographicCamera cam;

    public PuzzleElement(PuzzleElementInfo info, OrthographicCamera cam) {
        this.info = info;
        this.cam = cam;
        sprite = createPuzzleSprite(cam.unproject(info.getStartPosition()));
        spriteDisable = createPuzzleSpriteDisable(cam.unproject(info.getPositionDisabled()));
    }

    public Sprite createPuzzleSprite(Vector3 unprojectPosition) {
        Sprite sprite = KidPuzzleUtil.createSprite(info.getFilename());
        sprite.setSize(info.getSize(), info.getSize());
        sprite.setPosition(unprojectPosition.x, unprojectPosition.y);
        return sprite;
    }

    public Sprite createPuzzleSpriteDisable(Vector3 unprojectPosition) {
        Sprite sprite = KidPuzzleUtil.createSprite(info.getFilenameDisable());
        sprite.setSize(info.getSize(), info.getSize());
        sprite.setPosition(unprojectPosition.x, unprojectPosition.y);
        return sprite;
    }

    public void resetPosition() {
        sprite.setPosition(cam.unproject(info.getStartPosition()).x, cam.unproject(info.getStartPosition()).y);
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
