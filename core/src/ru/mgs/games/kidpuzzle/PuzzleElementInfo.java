package ru.mgs.games.kidpuzzle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Дмитрий Малышев on 12.10.2017.
 * Email: dmitry.malyshev@gmail.com
 */

public enum PuzzleElementInfo {

    PUZZLE_ELEMENT_1(),
    PUZZLE_ELEMENT_2(),
    PUZZLE_ELEMENT_3(),
    PUZZLE_ELEMENT_4(),
    PUZZLE_ELEMENT_5(),
    PUZZLE_ELEMENT_6(),
    PUZZLE_ELEMENT_7(),
    PUZZLE_ELEMENT_8();

    public static final int sizeH = Gdx.graphics.getHeight() / 17;
    public static final int sizeW = Gdx.graphics.getWidth() / 6;

    public String getFilename() {
        return "images/elem_" + (ordinal() + 1) + ".png";
    }

    public String getFilenameDisable() {
        return "images/elem_" + (ordinal() + 1) + "_dis.png";
    }

    public int getSize() {
        return sizeH * 3;
    }

    public Vector3 getStartPosition() {
        return new Vector3(sizeH, sizeH * (1 + ordinal() % 4) * 4, 0);
    }

    public Vector3 getPositionDisabled() {
        return new Vector3(sizeW * getMultX(), sizeH * getMultY(), 0);
    }

    private float getMultX() {
        int num = 1 + ordinal() % 4;
        switch (num) {
            case 1:
            case 3: return 2;
            case 2:
            case 4: return 4;
        }
        return 0;
    }

    private float getMultY() {
        int num = 1 + ordinal() % 4;
        switch (num) {
            case 1:
            case 2: return 8;
            case 3:
            case 4: return 12;
        }
        return 0;
    }
}
