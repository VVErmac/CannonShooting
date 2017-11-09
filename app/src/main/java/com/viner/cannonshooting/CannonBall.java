package com.viner.cannonshooting;

import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by Whiskey on 08.11.2017.
 */

public class CannonBall extends GameElement {
    private float velosityX;
    private boolean onScreen;

    public CannonBall(CannonView view, int color, int soundId, int x,
                      int y, int radius, float velosityX, float velosityY) {
        super(view, color, soundId, x, y, 2* radius, 2 * radius, velosityY);
        this.velosityX = velosityX;
        onScreen = true;
    }

    private int getRadius() {
        return (mShape.right - mShape.left) / 2;
    }

    public boolean collidesWith(GameElement gameElement) {
        return (Rect.intersects(mShape, gameElement.mShape) && velosityX > 0);
    }

    public boolean isOnScreen() {
        return onScreen;
    }

    public void reverseVelocity() {
        velosityX *= -1;
    }

    @Override
    public void update(double interval) {
        super.update(interval);
        mShape.offset((int) (velosityX * interval), 0);

        if (mShape.top < 0 || mShape.left < 0 || mShape.bottom > mView.getScreenHeight() ||
                mShape.right > mView.getScreenWidth()) {
            onScreen = false;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawCircle(mShape.left + getRadius(), mShape.top + getRadius(),
                getRadius(), mPaint);
    }
}
