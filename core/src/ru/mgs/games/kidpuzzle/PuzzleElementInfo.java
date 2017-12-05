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
    PUZZLE_ELEMENT_8(),
    PUZZLE_ELEMENT_9(),
    PUZZLE_ELEMENT_10(),
    PUZZLE_ELEMENT_11(),
    PUZZLE_ELEMENT_12(),
    PUZZLE_ELEMENT_13(),
    PUZZLE_ELEMENT_14(),
    PUZZLE_ELEMENT_15(),
    PUZZLE_ELEMENT_16(),
    PUZZLE_ELEMENT_17(),
    PUZZLE_ELEMENT_18(),
    PUZZLE_ELEMENT_19(),
    PUZZLE_ELEMENT_20(),
    PUZZLE_ELEMENT_21(),
    PUZZLE_ELEMENT_22(),
    PUZZLE_ELEMENT_23(),
    PUZZLE_ELEMENT_24(),
    PUZZLE_ELEMENT_25(),
    PUZZLE_ELEMENT_26(),
    PUZZLE_ELEMENT_27(),
    PUZZLE_ELEMENT_28(),
    PUZZLE_ELEMENT_29(),
    PUZZLE_ELEMENT_30(),
    PUZZLE_ELEMENT_31(),
    PUZZLE_ELEMENT_32(),
    PUZZLE_ELEMENT_33(),
    PUZZLE_ELEMENT_34(),
    PUZZLE_ELEMENT_35(),
    PUZZLE_ELEMENT_36(),
    PUZZLE_ELEMENT_37(),
    PUZZLE_ELEMENT_38(),
    PUZZLE_ELEMENT_39(),
    PUZZLE_ELEMENT_40();

    public String getFilename() {
        return "images/elem_" + (ordinal() + 1) + ".png";
    }

    public String getFilenameDisable() {
        return "images/elem_" + (ordinal() + 1) + "_dis.png";
    }

    public int getSizeH() {
        return Gdx.graphics.getHeight() / 17;
    }

    public int getSizeW() {
        return Gdx.graphics.getWidth() / 6;
    }

    public int getSize() {
        return getSizeH() * 3;
    }

    public int getSizeDisabled() {
        return getSizeH() * 6;
    }

    public Vector3 getStartPosition() {
        return new Vector3(getSizeH(), getSizeH() * (1 + ordinal() % 4) * 4, 0);
    }

    public Vector3 getPositionDisabled() {
        return new Vector3(getSizeW() * getMultX(), getSizeH() * getMultY(), 0);
    }

    public Vector3 getPositionForMenu() {
        int row;
        int col;
        if(ordinal() < 20) {
            row = 2;
        } else {
            row = 3;
        }
        if(row == 2) {
            col = 1 + 2 * (ordinal() / 4);
        } else {
            col = 1 + 2 * (ordinal() - 20) / 4;
        }
        float padX = getSizeForMenu() / 2;
        float deltaY = Gdx.graphics.getHeight() / 4;
        return new Vector3(getSizeForMenu() * col - padX, deltaY * row, 0);
    }

    public float getSizeForMenu() {
        return Gdx.graphics.getWidth() / 10;
    }

    private float getMultX() {
        int num = 1 + ordinal() % 4;
        switch (num) {
            case 1:
            case 3: return 1.5f;
            case 2:
            case 4: return 3.5f;
        }
        return 0;
    }

    private float getMultY() {
        int num = 1 + ordinal() % 4;
        switch (num) {
            case 1:
            case 2: return 8;
            case 3:
            case 4: return 16;
        }
        return 0;
    }
}
