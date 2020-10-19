package com.example.foodrun;

import android.graphics.Rect;
import android.media.MediaPlayer;
import android.util.Log;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.MotionEvent;
import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.ArrayList;

public class LevelOne implements GameLevel {
    private Thread thread;
    private boolean isPlaying;
    private boolean isGameOver;
    private Background background1, background2;
    private int screenX, screenY;
    private Paint paint;
    private float screenRatioX, screenRatioY;
    private Donut donut;
    private int angleCount, angleInterval;
    private int timeIntervalNum;
    private Spike spike;
    private Bitmap gameOver;
    private int gameOverWidth, gameOverHeight;
    private Bitmap finishFlag;
    private int phoneX;
    private int flagX, flagY;
    private boolean win;
    private Bitmap winText;
    private double winTextWidth, winTextFinalWidth, winTextFinalHeight, winTextHeight;
    private Bitmap blackBg;
    private Paint halfPaint;
    private Coin[] coins1;
    private Coin coin;
    private int coinCount, totalCoinNum;
    private Paint textPaint;
    private int coinPos;
    private MediaPlayer bgmusic;
    private int level;
    private MediaPlayer victoryMusic;
    private Context context;
    private GameView gameView;
    private Activity activity;
    //private Rect donutRect;
    private GameState gameState;
    private int currentDonutX;
    private GameSound gameSound;

    private ArrayList<GameItem> gameItems;

    public LevelOne (Context ctxt, int screenX, int screenY, final Resources res, GameView gameView, Activity activity) {
        this.gameView = gameView;
        context = ctxt;
        this.activity = activity;
        this.screenX = screenX;
        this.screenY = screenY;
        isPlaying = true;

        background1 = new Background(screenX, screenY, res);
        background2 = new Background(screenX, screenY, res);

        background2.x = Constants.BG_WIDTH;

        paint = new Paint();
        halfPaint = new Paint();
        halfPaint.setAlpha(150);

        gameState = new GameState();

        donut = new Donut(res, gameState);
        angleInterval = 3;

        timeIntervalNum = 0;

        //spike = new Spike(res);

        isGameOver = false;

        gameOverWidth = 750;
        gameOverHeight = (int) (gameOverWidth / Constants.GAME_OVER_RATIO);
        gameOver = BitmapFactory.decodeResource(res, R.drawable.gameoverwhite);
        gameOver = Bitmap.createScaledBitmap(gameOver, gameOverWidth, gameOverHeight, false);

        //finishFlag = BitmapFactory.decodeResource(res, R.drawable.flag);
        //finishFlag = Bitmap.createScaledBitmap(finishFlag, 450, 700, false);

        phoneX = 0;

        flagX = Constants.FLAG_POS;

        flagY = 260;

        win = false;

        winTextFinalHeight = 300;
        winTextFinalWidth = (int) (winTextFinalHeight * Constants.YOU_WIN_RATIO);
        winText = BitmapFactory.decodeResource(res, R.drawable.youwin);

        blackBg = BitmapFactory.decodeResource(res, R.drawable.black);

        //coins1 = Coin.getCoinLine(1000, 5, res);
        coin = new Coin(res, 600);
        coinCount = 0;
        totalCoinNum = 10;

        textPaint = new Paint();
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(80);

        coinPos = 0;

        bgmusic = GameSound.getbgmusic();
        //bgmusic = MediaPlayer.create(context, R.raw.bgmusic);

        victoryMusic = MediaPlayer.create(context, R.raw.victorymusic);

        gameItems = new ArrayList<GameItem>() {
            {
                for (int i = 0; i < 5; i++) {
                    add (new Coin(res, i*120 + 500));
                }
                add(new Spike(res, 1200));
                for (int i = 0; i < 5; i++) {
                    add (new Coin(res, i*120 + 1400));
                }
                add(new Flag(res, 2300));
            }
        };

        //gameItems.add(new Spike(res));


        currentDonutX = donut.x;

        gameSound = new GameSound();
    }

    public boolean getIsPlaying() {
        return isPlaying;
    }

    @Override
    public void update() {
        // scrolls background
        background1.x -= Constants.BG_SPEED;
        background2.x -= Constants.BG_SPEED;
        phoneX += Constants.BG_SPEED;
        currentDonutX += Constants.BG_SPEED;

        // lets the 2 background take turns
        if (background1.x + background1.background.getWidth() < 0) {
            background1.x = Constants.BG_WIDTH;
        }
        if (background2.x + background2.background.getWidth() < 0) {
            background2.x = Constants.BG_WIDTH;
        }

        // makes donut jump
        if (donut.isJumping) {
            Log.d("CLICKED", "HI IM HERE");
            int position = (int) (Constants.INIT_SPEED * timeIntervalNum * Constants.DELTA_T + .5 * Constants.ACCEL * timeIntervalNum * timeIntervalNum * Constants.DELTA_T * Constants.DELTA_T + Constants.INIT_Y);
            //Log.d("CLICKED", Constants.INIT_SPEED + " * " + timeIntervalNum + " * " +  Constants.DELTA_T + " + " + ".5" + " * " + Constants.ACCEL + " * " + timeIntervalNum + " * " + timeIntervalNum);
            //Log.d("CLICKED", Double.toString(position));
            donut.moveTo(position);
            timeIntervalNum++;
        }

        // checks if the donut had landed
        if (donut.y > 1080-Constants.INIT_Y) {
            donut.moveTo(Constants.INIT_Y);
            donut.isJumping = false;
            Log.d("donuty", "finishedjumping");
        }

        // checks if donut is ever out of screen
        if (donut.getY() > 1080-Constants.INIT_Y) {
            Log.d("donuty", "outofscreen");
            donut.moveTo(Constants.INIT_Y);
        }
        if (donut.getY() < 0) {
            donut.moveTo(1080-Constants.BALLOON_HEIGHT+50);
        }

        // checks if donut collides with gameitems
        for (int i = 0; i < gameItems.size(); i++) {
            GameItem item = gameItems.get(i);
            item.update();

            if (Rect.intersects(donut.getRect(currentDonutX), item.getRect())) {
                if (item.getCollected()) {
                    continue;
                }

                Log.d("collided", item.getType());
                item.setCollected(true);
                item.updateGameState(gameState);
                item.makeSound(gameSound);
            }
        }
    }

    @Override
    public void draw() {
        if (gameView.getHolder().getSurface().isValid()) {
            Canvas canvas = gameView.getHolder().lockCanvas();

            // draws each rotation of donut to make it spin
            int n = angleCount / angleInterval;
            if (n == 8) {
                n = 0;
                angleCount = 3;
            }

            if (gameState.isGameOver()) {
                gameSound.stopBgMusic();
                isPlaying = false;
                if (gameState.isWin()) {
                    blackBg = Bitmap.createScaledBitmap(blackBg, 2500, 1500, false);
                    canvas.drawBitmap(blackBg, -10, -10, halfPaint);
                    Log.d("youwindim", "width: " + winTextWidth  + " height: " + winTextHeight);
                    winText = Bitmap.createScaledBitmap(winText, (int) winTextFinalWidth, (int) winTextFinalHeight, false);
                    canvas.drawBitmap(winText, screenX / 2 - (int) winTextFinalWidth / 2, screenY / 2 - (int) winTextFinalHeight / 2, paint);

                    //winText = Bitmap.createScaledBitmap(winText, (int) winTextWidth, (int) winTextHeight, false);
                    //canvas.drawBitmap(winText, screenX / 2 - (int) winTextWidth / 2, screenY / 2 - (int) winTextHeight / 2, paint);
                    gameView.getHolder().unlockCanvasAndPost(canvas);
                } else {
                    canvas.drawBitmap(blackBg, -10, -10, halfPaint);
                    canvas.drawBitmap(gameOver, screenX / 2 - gameOverWidth / 2, screenY / 2 - gameOverHeight / 2, paint);
                    gameView.getHolder().unlockCanvasAndPost(canvas);
                }
                return;
            }


            // draws all objects
            canvas.drawBitmap(background1.background, background1.x, background1.y, paint);
            canvas.drawBitmap(background2.background, background2.x, background2.y, paint);

            donut.draw(canvas, paint, false);

            // draw gameitems
            for (int i = 0; i < gameItems.size(); i++) {
                GameItem item = gameItems.get(i);
                item.draw(canvas, paint, phoneX);
            }

            if (gameState.getCoinScore() == totalCoinNum) {
                textPaint.setColor(Color.GREEN);
            }
            canvas.drawText("Coins: " + gameState.getCoinScore() + " / " + totalCoinNum, 1600, 100, textPaint);

            gameView.getHolder().unlockCanvasAndPost(canvas);

            angleCount++;
            coinPos++;
        }
    }

    public boolean onPressEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (gameState.isGameOver()) {
                    gameSound.stopVictorySound();
                    gameSound.stopBgMusic();
                    return false;
                } else if (!donut.isJumping) {
                    donut.isJumping = true;
                    Log.d("CLICKED", "hi");
                    timeIntervalNum = 0;
                    MediaPlayer jumpSound = MediaPlayer.create(context, R.raw.jumpsound);
                    jumpSound.setVolume((float) .5, (float) .5);
                    jumpSound.start();
                }
                break;
        }
        return true;
    }
}
