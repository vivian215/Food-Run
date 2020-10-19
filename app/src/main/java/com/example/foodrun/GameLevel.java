package com.example.foodrun;

import android.view.MotionEvent;

public interface GameLevel {
    void update();
    void draw();
    boolean getIsPlaying();
    boolean onPressEvent(MotionEvent event);
}
