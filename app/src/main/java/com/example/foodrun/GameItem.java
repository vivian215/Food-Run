package com.example.foodrun;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public interface GameItem {
    void draw(Canvas c, Paint p, int phoneX);
    Rect getRect();
    void update();
    String getType();
    void updateGameState(GameState score);
    void makeSound(GameSound gameSound);
    void setCollected(boolean isCollected);
    boolean getCollected();
}
