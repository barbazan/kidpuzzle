package ru.mgs.games.kidpuzzle;

import com.badlogic.gdx.graphics.OrthographicCamera;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static ru.mgs.games.kidpuzzle.PuzzleElementInfo.*;
import static ru.mgs.games.kidpuzzle.PuzzleElementInfo.PUZZLE_ELEMENT_13;
import static ru.mgs.games.kidpuzzle.PuzzleElementInfo.PUZZLE_ELEMENT_17;
import static ru.mgs.games.kidpuzzle.PuzzleElementInfo.PUZZLE_ELEMENT_2;
import static ru.mgs.games.kidpuzzle.PuzzleElementInfo.PUZZLE_ELEMENT_21;
import static ru.mgs.games.kidpuzzle.PuzzleElementInfo.PUZZLE_ELEMENT_25;
import static ru.mgs.games.kidpuzzle.PuzzleElementInfo.PUZZLE_ELEMENT_29;
import static ru.mgs.games.kidpuzzle.PuzzleElementInfo.PUZZLE_ELEMENT_3;
import static ru.mgs.games.kidpuzzle.PuzzleElementInfo.PUZZLE_ELEMENT_33;
import static ru.mgs.games.kidpuzzle.PuzzleElementInfo.PUZZLE_ELEMENT_4;
import static ru.mgs.games.kidpuzzle.PuzzleElementInfo.PUZZLE_ELEMENT_5;
import static ru.mgs.games.kidpuzzle.PuzzleElementInfo.PUZZLE_ELEMENT_6;
import static ru.mgs.games.kidpuzzle.PuzzleElementInfo.PUZZLE_ELEMENT_7;
import static ru.mgs.games.kidpuzzle.PuzzleElementInfo.PUZZLE_ELEMENT_8;
import static ru.mgs.games.kidpuzzle.PuzzleElementInfo.PUZZLE_ELEMENT_9;

/**
 * Created by Дмитрий Малышев on 20.11.2017.
 * Email: dmitry.malyshev@gmail.com
 */

public enum Puzzle {

    PUZZLE_1(Arrays.asList(PUZZLE_ELEMENT_1, PUZZLE_ELEMENT_2, PUZZLE_ELEMENT_3, PUZZLE_ELEMENT_4)),
    PUZZLE_2(Arrays.asList(PUZZLE_ELEMENT_5, PUZZLE_ELEMENT_6, PUZZLE_ELEMENT_7, PUZZLE_ELEMENT_8)),
    PUZZLE_3(Arrays.asList(PUZZLE_ELEMENT_9, PUZZLE_ELEMENT_10, PUZZLE_ELEMENT_11, PUZZLE_ELEMENT_12)),
    PUZZLE_4(Arrays.asList(PUZZLE_ELEMENT_13, PUZZLE_ELEMENT_14, PUZZLE_ELEMENT_15, PUZZLE_ELEMENT_16)),
    PUZZLE_5(Arrays.asList(PUZZLE_ELEMENT_17, PUZZLE_ELEMENT_18, PUZZLE_ELEMENT_19, PUZZLE_ELEMENT_20)),
    PUZZLE_6(Arrays.asList(PUZZLE_ELEMENT_21, PUZZLE_ELEMENT_22, PUZZLE_ELEMENT_23, PUZZLE_ELEMENT_24)),
    PUZZLE_7(Arrays.asList(PUZZLE_ELEMENT_25, PUZZLE_ELEMENT_26, PUZZLE_ELEMENT_27, PUZZLE_ELEMENT_28)),
    PUZZLE_8(Arrays.asList(PUZZLE_ELEMENT_29, PUZZLE_ELEMENT_30, PUZZLE_ELEMENT_31, PUZZLE_ELEMENT_32)),
    PUZZLE_9(Arrays.asList(PUZZLE_ELEMENT_33, PUZZLE_ELEMENT_34, PUZZLE_ELEMENT_35, PUZZLE_ELEMENT_36));

    private List<PuzzleElementInfo> puzzleElementInfos;
    public List<PuzzleElement> puzzleElements;

    Puzzle(List<PuzzleElementInfo> puzzleElementInfos) {
        this.puzzleElementInfos = puzzleElementInfos;
    }

    public void initPuzzle(OrthographicCamera cam) {
        puzzleElements = new ArrayList<PuzzleElement>();
        for(PuzzleElementInfo info : puzzleElementInfos) {
            PuzzleElement puzzleElement = new PuzzleElement(info, cam);
            puzzleElements.add(puzzleElement);
        }
    }
}
