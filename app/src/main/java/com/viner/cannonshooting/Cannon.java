package com.viner.cannonshooting;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

/**
 * Created by Whiskey on 08.11.2017.
 */

public class Cannon {
    private int baseRadius;
    private int barrelLength;
    private Point barrelEnd = new Point();
    private double barrelAngle;
    private CannonBall cannonBall;
    private Paint paint = new Paint();
    private CannonView cannonView;

    public Cannon(CannonView view, int baseRadius, int barrelLength, int barrelWidth) {
        this.cannonView = view;
        this.baseRadius = baseRadius;
        this.barrelLength = barrelLength;
        paint.setStrokeWidth(barrelWidth);
        paint.setColor(Color.BLACK);
        align(Math.PI / 2);
    }

    public void align(double barrelAngle) {
        this.barrelAngle = barrelAngle;
        barrelEnd.x = (int) (barrelLength * Math.sin(barrelAngle));
        barrelEnd.y = (int) (-barrelLength * Math.cos(barrelAngle) + cannonView.getScreenHeight() / 2);
    }

    public void fireCannonball() {
        int velocityX = (int) (CannonView.CANNONBALL_SPEED_PERCENT *
                cannonView.getScreenWidth() * Math.sin(barrelAngle));
        int velocityY = (int) (CannonView.CANNONBALL_SPEED_PERCENT *
                cannonView.getScreenWidth() * -Math.cos(barrelAngle));
        int radius = (int) (cannonView.getScreenHeight() * CannonView.CANNONBALL_RADIUS_PERCENT);

        cannonBall = new CannonBall(cannonView, Color.BLACK, CannonView.CANNON_SOUND_ID, -radius,
                cannonView.getScreenHeight() / 2 - radius, radius, velocityX, velocityY);

        cannonBall.playSound();
    }

    public void draw(Canvas canvas) {
        canvas.drawLine(0, cannonView.getScreenHeight() / 2,
                barrelEnd.x, barrelEnd.y, paint);
        canvas.drawCircle(0, (int) cannonView.getScreenHeight() / 2, baseRadius, paint);
    }

    public CannonBall getCannonBall() {
        return cannonBall;
    }

    public void removeCannonball() {
        cannonBall = null;
    }
}
