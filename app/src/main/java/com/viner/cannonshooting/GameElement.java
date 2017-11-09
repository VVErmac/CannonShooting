package com.viner.cannonshooting;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by Whiskey on 08.11.2017.
 */

public class GameElement {
    protected CannonView mView;
    protected Paint mPaint = new Paint();
    protected Rect mShape;
    protected float mVelocityY;
    private int mSoundID;

    public GameElement(CannonView view, int color, int soundId, int x, int y,
                       int width, int length, float velosityY) {
        this.mView = view;
        mPaint.setColor(color);
        mShape = new Rect(x, y, x + width, y + length);
        this.mSoundID = soundId;
        this.mVelocityY = velosityY;
    }

    public void update(double interval){
        mShape.offset(0, (int) (mVelocityY  * interval));
        if(mShape.top < 0 && mVelocityY < 0 ||
                mShape.bottom > mView.getScreenHeight() && mVelocityY > 0){
            mVelocityY *= -1;
        }
    }
    public void draw(Canvas canvas){
        canvas.drawRect(mShape, mPaint);
    }
    public void playSound(){
        mView.playSound(mSoundID);
    }
}
