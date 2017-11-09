package com.viner.cannonshooting;

/**
 * Created by Whiskey on 08.11.2017.
 */

public class Target extends GameElement {
    private int hitReward;
    public Target(CannonView view, int color, int hitReward,
                  int x, int y, int width, int length, float velosityY) {
        super(view, color, CannonView.TARGET_SOUND_ID, x, y, width, length, velosityY);
        this.hitReward = hitReward;
    }

    public int getHitReward() {
        return hitReward;
    }
}
