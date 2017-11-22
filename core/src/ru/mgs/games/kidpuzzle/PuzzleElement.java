package ru.mgs.games.kidpuzzle;

import com.badlogic.gdx.graphics.OrthographicCamera;
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
    public boolean fixed;
    private OrthographicCamera cam;

    public PuzzleElement(PuzzleElementInfo info, OrthographicCamera cam) {
        this.info = info;
        this.cam = cam;
        sprite = createPuzzleSprite(cam.unproject(info.getStartPosition()));
        spriteDisable = createPuzzleSpriteDisable(cam.unproject(info.getPositionDisabled()));
    }

    public Sprite createPuzzleSprite(Vector3 unprojectPosition) {
        return KidPuzzleUtil.createSprite(info.getFilename(), unprojectPosition, info.getSize());
    }

    public Sprite createPuzzleSpriteDisable(Vector3 unprojectPosition) {
        return KidPuzzleUtil.createSprite(info.getFilenameDisable(), unprojectPosition, info.getSize());
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
