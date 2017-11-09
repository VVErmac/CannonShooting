package com.viner.cannonshooting;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.SparseIntArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Whiskey on 08.11.2017.
 */

public class CannonView extends SurfaceView implements SurfaceHolder.Callback {
    private static final String TAG = "CannonView";

    public static final int MISS_PENALTY = 2;
    public static final int HIT_REWARD = 3;

    public static final double CANNON_BASE_RADIUS_PERCENT = 3.0 / 40;
    public static final double CANNON_BARREL_WIDTH_PERCENT = 3.0 / 40;
    public static final double CANNON_BARREL_LENGTH_PERCENT = 1.0 / 10;

    public static final double CANNONBALL_RADIUS_PERCENT = 3.0 / 80;
    public static final double CANNONBALL_SPEED_PERCENT = 3.0 / 2;

    public static final double TARGET_WIDTH_PERCENT = 1.0 / 40;
    public static final double TARGET_LENGTH_PERCENT = 3.0 / 20;
    public static final double TARGET_FIRST_X_PERCENT = 3.0 / 5;
    public static final double TARGET_SPACING_PERCENT = 1.0 / 60;
    public static final double TARGET_PIECES = 9;
    public static final double TARGET_MIN_SPEED_PERCENT = 3.0 / 4;
    public static final double TARGET_MAX_SPEED_PERCENT = 6.0 / 4;

    public static final double BLOCKER_WIDTH_PERCENT = 1.0 / 40;
    public static final double BLOCKER_LENGTH_PERCENT = 1.0 / 4;
    public static final double BLOCKER_X_PERCENT = 1.0 / 2;
    public static final double BLOCKER_SPEED_PERCENT = 1.0;

    public static final double TEXT_SIZE_PERCENT = 1.0 / 18;

    private CannonThread mCannonThread;
    private Activity mActivity;
    private boolean mDialogIsDisplayed = false;

    private Cannon mCannon;
    private Blocker mBlocker;
    private ArrayList<Target> mTargets;

    private int screenWidth;
    private int screenHeight;

    private boolean mGameOver;
    private double mTimeLeft;
    private int mShotsFired;
    private double mTotalElapsedTime;

    public static final int TARGET_SOUND_ID = 0;
    public static final int CANNON_SOUND_ID = 1;
    public static final int BLOCKER_SOUND_ID = 2;

    private SoundPool mSoundPool;
    private SparseIntArray mSoundMap;

    private Paint mTextPaint;
    private Paint mBackGroundPaint;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CannonView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mActivity = (Activity) context;

        getHolder().addCallback(this);
        AudioAttributes.Builder attrBuilder = new AudioAttributes.Builder();
        attrBuilder.setUsage(AudioAttributes.USAGE_GAME);
        SoundPool.Builder builder = new SoundPool.Builder();
        builder.setMaxStreams(1);
        builder.setAudioAttributes(attrBuilder.build());
        mSoundPool = builder.build();

        /*mSoundMap = new SparseIntArray(3); TODO
        mSoundMap.put();*/

        mTextPaint = new Paint();
        mBackGroundPaint = new Paint();
        mBackGroundPaint.setColor(Color.WHITE);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        screenHeight = h;
        screenWidth = w;

        mTextPaint.setTextSize((int) (TEXT_SIZE_PERCENT * screenHeight));
        mTextPaint.setAntiAlias(true);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public void playSound(int soundId) {
        //TODO
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void newGame() {
        mCannon = new Cannon(this, (int) CANNON_BASE_RADIUS_PERCENT * screenHeight,
                (int) CANNON_BARREL_LENGTH_PERCENT * screenWidth,
                (int) CANNON_BARREL_WIDTH_PERCENT * screenWidth);
        Random random = new Random();
        mTargets = new ArrayList<>();

        int targetX = (int) (TARGET_FIRST_X_PERCENT * screenWidth);
        int targetY = (int) (0.5 - TARGET_LENGTH_PERCENT / 2 * screenHeight);

        for (int i = 0; i < TARGET_PIECES; i++) {
            double velocity = screenHeight * (random.nextDouble() *
                    (TARGET_MAX_SPEED_PERCENT - TARGET_MIN_SPEED_PERCENT) +
                    TARGET_MIN_SPEED_PERCENT);

            int color = (i % 2 == 0) ?
                    getResources().getColor(R.color.dark,
                            getContext().getTheme()) :
                    getResources().getColor(R.color.light,
                            getContext().getTheme());

            velocity *= -1;

            mTargets.add(new Target(this, color, HIT_REWARD, targetX,
                    targetY, (int)(TARGET_WIDTH_PERCENT * screenWidth),
                    (int)(TARGET_LENGTH_PERCENT * screenHeight ), (int)velocity));

            targetX += (TARGET_WIDTH_PERCENT + TARGET_SPACING_PERCENT) * screenWidth;
        }
        mBlocker = new Blocker(this, Color.BLACK, MISS_PENALTY,
                (int) (BLOCKER_X_PERCENT * screenWidth),
                (int) ((0.5 - BLOCKER_LENGTH_PERCENT / 2)* screenHeight),
                (int) (BLOCKER_WIDTH_PERCENT * screenWidth),
                (int) (BLOCKER_LENGTH_PERCENT * screenHeight),
                (float) (BLOCKER_SPEED_PERCENT * screenHeight));

        mTimeLeft = 10;
        mShotsFired = 0;
        mTotalElapsedTime = 0.0;

        if(mGameOver){
            mGameOver = false;
            mCannonThread = new CannonThread(getHolder());
            mCannonThread.start();
        }
        hideSystemBars();
    }
}