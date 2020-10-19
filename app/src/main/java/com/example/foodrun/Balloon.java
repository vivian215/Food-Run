package com.example.foodrun;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class Balloon implements GameItem {
    private int x, y, width, height;
    private Bitmap balloon;
    private Rect rect;
    private boolean collected;

    public Balloon (Resources res, int x) {
        width = Constants.BALLOON_WIDTH;
        height = Constants.BALLOON_HEIGHT;
        balloon = BitmapFactory.decodeResource(res, R.drawable.balloon);
        balloon = Bitmap.createScaledBitmap(balloon, width, height, false);

        this.x = x;
        y = 400;

        rect = new Rect(x + Constants.RECT_ERROR, y, x + width - Constants.RECT_ERROR, y + height - Constants.RECT_ERROR-50);

        collected = false;
    }

    public void draw(Canvas c, Paint p, int phoneX) {
        // c.drawRect(new Rect(x - phoneX + Constants.RECT_ERROR, y, x - phoneX + width - Constants.RECT_ERROR, y + height - Constants.RECT_ERROR), p);
        if (!collected) {
            c.drawBitmap(balloon, x - phoneX, y, p);
        }
    }

    public Rect getRect() {
        return rect;
    }

    public void update() {
    }

    public String getType() {
        return "balloon";
    }

    public void updateGameState(GameState gameState) {
        gameState.setHasBalloon(true);
    }

    public void makeSound(GameSound gameSound) {

    }

    public void setCollected(boolean isCollected) {
        collected = isCollected;
    }

    public boolean getCollected() {
        return collected;
    }
}
