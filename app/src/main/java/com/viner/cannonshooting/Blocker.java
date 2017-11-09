package com.viner.cannonshooting;

/**
 * Created by Whiskey on 08.11.2017.
 */

public class Blocker extends GameElement {
    private int missPenalty;

    public Blocker(CannonView view, int color, int missPenalty,
                   int x, int y, int width, int length, float velosityY) {
        super(view, color, CannonView.BLOCKER_SOUND_ID, x, y, width, length, velosityY);

        this.missPenalty = missPenalty;
    }

    public int getMissPenalty() {
        return missPenalty;
    }
}
