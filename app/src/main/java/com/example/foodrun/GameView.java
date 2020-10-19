package com.example.foodrun;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.graphics.Canvas;
import android.graphics.Paint;

public class GameView extends SurfaceView implements Runnable {
    private Thread thread;
    private Bitmap gameOver;
    private int gameOverWidth, gameOverHeight;
    private Bitmap finishFlag;
    private Paint halfPaint;
    private Paint textPaint;
    private int level;
    private GameLevel gameLevel;
    private boolean isPlaying;

    public GameView(Context context, int screenX, int screenY, Resources res, int currentLevel) {
        super(context);

        halfPaint = new Paint();
        halfPaint.setAlpha(150);

        gameOverWidth = 750;
        gameOverHeight = (int) (gameOverWidth / Constants.GAME_OVER_RATIO);
        gameOver = BitmapFactory.decodeResource(getResources(), R.drawable.gameoverwhite);
        gameOver = Bitmap.createScaledBitmap(gameOver, gameOverWidth, gameOverHeight, false);

        textPaint = new Paint();
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(80);

       // bgmusic= MediaPlayer.create(getContext(), R.raw.bgmusic);

        level = getActivity().getIntent().getIntExtra("level", 1);

        if (level == 1) {
            gameLevel = new NewLevelOne(getContext(), screenX, screenY, getResources(), this, this.getActivity());
        } else if (level == 2) {
            gameLevel = new LevelTwo(getContext(), screenX, screenY, getResources(), this, this.getActivity());
        }

        isPlaying = true;
    }

    @Override
    public void run() {
        //MediaPlayer bgmusic= MediaPlayer.create(getContext(), R.raw.bgmusic);
        while (gameLevel.getIsPlaying()) {
            //update();
            //draw();
            long startTime = System.currentTimeMillis();
            Log.d("lagging", "***************************");
            gameLevel.update();
            gameLevel.draw();
            Log.d("lagging", "--------------end------------" + (System.currentTimeMillis() - startTime));
            sleep();

        }
    }

    private void sleep() {
        try {
            Thread.sleep(Constants.DELTA_T);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void resume () {
        isPlaying = true;
        thread = new Thread(this);
        thread.start();
    }

    public void pause () {
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Activity getActivity() {
        Context context = getContext();
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity)context;
            }
            context = ((ContextWrapper)context).getBaseContext();
        }
        return null;
    }

    // handles when user clicks (makes donut jump)
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (! gameLevel.onPressEvent(event))
        {

            Activity gameActivity = getActivity();
            gameActivity.startActivity(new Intent(gameActivity, MainActivity.class));

        }
        return true;
    }
}
