package com.example.foodrun;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class Flag implements GameItem {
    private int x, y, width, height;
    private Bitmap flag;
    private Rect rect;
    private boolean collected;

    public Flag (Resources res, int x) {
        width = 450;
        height = 700;
        flag = BitmapFactory.decodeResource(res, R.drawable.flag);
        flag = Bitmap.createScaledBitmap(flag, width, height, false);

        this.x = x;
        y = 260;

        rect = new Rect(x + Constants.RECT_ERROR, y, x + width - Constants.RECT_ERROR, y + height - Constants.RECT_ERROR);

        collected = false;
    }

    public void draw(Canvas c, Paint p, int phoneX) {
       // c.drawRect(new Rect(x - phoneX + Constants.RECT_ERROR, y, x - phoneX + width - Constants.RECT_ERROR, y + height - Constants.RECT_ERROR), p);
        c.drawBitmap(flag, x - phoneX, y, p);
    }

    public Rect getRect() {
        return rect;
    }

    public void update() { }

    public String getType() {
        return "flag";
    }

    public void updateGameState(GameState gameState) {
        gameState.setGameOver(true);
        gameState.setWin(true);
    }

    public void makeSound(GameSound gameSound) {
        gameSound.makeVictorySound();
    }

    public void setCollected(boolean isCollected) {
        collected = isCollected;
    }

    public boolean getCollected() {
        return collected;
    }
}
