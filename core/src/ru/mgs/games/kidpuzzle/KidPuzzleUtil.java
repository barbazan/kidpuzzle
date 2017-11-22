package ru.mgs.games.kidpuzzle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Дмитрий Малышев on 31.10.2017.
 * Email: dmitry.malyshev@gmail.com
 */

public class KidPuzzleUtil {

    public static Sprite createSprite(String filename) {
        Texture texture = new Texture(Gdx.files.internal(filename));
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        return new Sprite(texture);
    }

    public static Sprite createSprite(String filename, Vector3 startPosition, float size) {
        Sprite sprite = createSprite(filename);
        sprite.setPosition(startPosition.x, startPosition.y);
        sprite.setSize(size, size);
        return sprite;
    }

    public static Sprite initBg(OrthographicCamera cam, String bgFileName) {
        Sprite bgSprite = createSprite(bgFileName);
        float xScale = cam.viewportWidth / bgSprite.getWidth();
        float yScale = cam.viewportHeight / bgSprite.getHeight();
        float scale = Math.max(xScale, yScale);
        bgSprite.setScale(scale);
        bgSprite.setPosition(-bgSprite.getWidth()/2, -bgSprite.getHeight()/2);
        return bgSprite;
    }

}
