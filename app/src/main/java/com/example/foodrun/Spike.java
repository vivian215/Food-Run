package com.example.foodrun;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

public class Spike implements GameItem {
    int x;
    int y;
    static int width;
    int height;
    Bitmap spike;
    Rect rect;
    private boolean collected;

    public Spike(Resources res, int x) {
        spike = BitmapFactory.decodeResource(res, R.drawable.spike);

        width = spike.getWidth();
        height = spike.getHeight();

        width /= 3;
        height /= 5;

        spike = Bitmap.createScaledBitmap(spike, width, height, false);

        this.x = x;
        moveTo(Constants.SPIKE_INIT_Y);

        this.rect = new Rect(x + Constants.RECT_ERROR, y, x + width - Constants.RECT_ERROR, y + height - Constants.RECT_ERROR);

        collected = false;
    }

    public Bitmap getSpike() {
        return spike;
    }

    public static int getWidth() {
        return width;
    }

    public void moveTo (int distance) {
        this.y = 1080 - distance;
    }

    public Rect getRect() {
        return new Rect(x + Constants.RECT_ERROR, y, x + width - Constants.RECT_ERROR, y + height - Constants.RECT_ERROR);
    }

    public void draw(Canvas c, Paint p, int phoneX) {
        if (x-phoneX < 2000 && x-phoneX > -100) {
            c.drawBitmap(spike, x - phoneX, y, p);
        }
    }

    public void update() { }

    public String getType() {
        return "spike";
    }

    public void updateGameState(GameState gameState) {
        gameState.setGameOver(true);
    }

    public void makeSound(GameSound gameSound) {
        gameSound.makeDieSound();
    }

    public void setCollected(boolean isCollected) {
        collected = isCollected;
    }

    public boolean getCollected() {
        return collected;
    }
}
