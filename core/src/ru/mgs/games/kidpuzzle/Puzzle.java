package ru.mgs.games.kidpuzzle;

import com.badlogic.gdx.graphics.OrthographicCamera;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static ru.mgs.games.kidpuzzle.PuzzleElementInfo.PUZZLE_ELEMENT_1;
import static ru.mgs.games.kidpuzzle.PuzzleElementInfo.PUZZLE_ELEMENT_2;
import static ru.mgs.games.kidpuzzle.PuzzleElementInfo.PUZZLE_ELEMENT_3;
import static ru.mgs.games.kidpuzzle.PuzzleElementInfo.PUZZLE_ELEMENT_4;
import static ru.mgs.games.kidpuzzle.PuzzleElementInfo.PUZZLE_ELEMENT_5;
import static ru.mgs.games.kidpuzzle.PuzzleElementInfo.PUZZLE_ELEMENT_6;
import static ru.mgs.games.kidpuzzle.PuzzleElementInfo.PUZZLE_ELEMENT_7;
import static ru.mgs.games.kidpuzzle.PuzzleElementInfo.PUZZLE_ELEMENT_8;

/**
 * Created by mit on 14.11.2017.
 */

public enum Puzzle {

    PUZZLE_1(Arrays.asList(PUZZLE_ELEMENT_1, PUZZLE_ELEMENT_2, PUZZLE_ELEMENT_3, PUZZLE_ELEMENT_4)),
    PUZZLE_2(Arrays.asList(PUZZLE_ELEMENT_5, PUZZLE_ELEMENT_6, PUZZLE_ELEMENT_7, PUZZLE_ELEMENT_8));

    private List<PuzzleElementInfo> puzzleElementInfos;
    public List<PuzzleElement> puzzleElements = new ArrayList<PuzzleElement>();

    Puzzle(List<PuzzleElementInfo> puzzleElementInfos) {
        this.puzzleElementInfos = puzzleElementInfos;
    }

    public void initPuzzle(OrthographicCamera cam) {
        for(PuzzleElementInfo info : puzzleElementInfos) {
            PuzzleElement puzzleElement = new PuzzleElement(info, cam);
            puzzleElements.add(puzzleElement);
        }
    }
}
