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

public class LevelTwo implements GameLevel {
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
    private int timerCount;
    private ArrayList<GameItem> gameItems;
    private int currentDonutY;
    private double initSpeed;

    public LevelTwo (Context ctxt, int screenX, int screenY, final Resources res, GameView gameView, Activity activity) {
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

        coin = new Coin(res, 600);
        coinCount = 0;
        totalCoinNum = 6;

        textPaint = new Paint();
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(80);

        coinPos = 0;

        bgmusic = GameSound.getbgmusic();

        victoryMusic = MediaPlayer.create(context, R.raw.victorymusic);

        gameItems = new ArrayList<GameItem>() {
            {
                add (new Ingredient(res, "flour", 1000, Constants.INGREDIENT_INIT_Y));
                add(new Spike(res, 1400));
                for (int i = 0; i < 5; i++) {
                    add (new Coin(res, i*120 + 1600));
                }
                add (new Ingredient(res, "choc", 2300, Constants.INGREDIENT_INIT_Y));
                for (int i = 0; i < 2; i++) {
                    add (new Spike(res, i*Spike.getWidth() + 2700));
                }
                add (new Ingredient(res, "purple", 2700 + Spike.getWidth()/4, Constants.INGREDIENT_INIT_Y-170));
                add (new Coin(res, 3000));
                add (new Balloon(res, 3300));
                for (int i = 0; i < 10; i++) {
                    add (new Spike(res, i*(Spike.getWidth())+3600));
                }
                add (new Ingredient(res, "purple", 4000, 500));
                add (new Ingredient(res, "pink", 4300, 700));
                add (new Ingredient(res, "blue", 4600, 400));
                add (new Ingredient(res, "green", 5000, 300));
                add (new Ingredient(res, "white", 5200,400));
                add (new Ingredient(res, "orange", 5500, 600));
                add (new Ingredient(res, "yellow", 5800,800));
                add(new Flag(res, 6300));

             /*   add(new Balloon(res, 2500));
                add (new Ingredient(res, "choc", 3200, 300));
                for (int i = 0; i < 10; i++) {
                    add (new Spike(res, i*(Spike.getWidth())+3000));
                }
                add (new Ingredient(res, "purple", 3600, 500));
                add(new Flag(res, 5000));*/

                /*for (int i = 0; i < 1; i++) {
                    add (new Coin(res, i*Constants.COIN_SPACE + 800));
                }
                add (new Ingredient(res, "flour", 1100, Constants.INGREDIENT_INIT_Y));
                add (new Ingredient(res, "purple", 1370, Constants.INGREDIENT_INIT_Y));
                for (int i = 0; i < 1; i++) {
                    add (new Coin(res, i*Constants.COIN_SPACE + 1600));
                }
                add(new Spike(res, 2000));
                add (new Ingredient(res, "choc", 1950, Constants.INGREDIENT_INIT_Y - 170));
                for (int i = 0; i < 1; i++) {
                    add (new Coin(res, i*Constants.COIN_SPACE + 2500));
                }
                //add (new Ingredient(res, "flour", 3100, Constants.INGREDIENT_INIT_Y));
                for (int i = 0; i < 1; i++) {
                    add (new Coin(res, i*Constants.COIN_SPACE + 3300));
                }
                add (new Ingredient(res, "pink", 4000, Constants.INGREDIENT_INIT_Y));
                add (new Coin(res, 4200));
                add (new Ingredient(res, "blue", 4400, Constants.INGREDIENT_INIT_Y));
                for (int i = 0; i < 1; i++) {
                    add (new Coin(res, i*Constants.COIN_SPACE + 4600));
                }
                add (new Ingredient(res, "green", 4900, Constants.INGREDIENT_INIT_Y));
                add (new Spike(res, 5100));
                add (new Ingredient(res, "white", 5300, Constants.INGREDIENT_INIT_Y));
                add (new Ingredient(res, "orange", 5200, Constants.INGREDIENT_INIT_Y));
                for (int i = 0; i < 1; i++) {
                    add (new Spike(res, i*Spike.getWidth() + 5400));
                }
                add (new Ingredient(res, "yellow", 6000+Spike.getWidth()/4, Constants.INGREDIENT_INIT_Y - 170));
                add(new Flag(res, 6300));*/
            }
        };

        //gameItems.add(new Spike(res));



        currentDonutX = donut.x;
        currentDonutY = 1080-donut.y;

        gameSound = new GameSound();

        timerCount = 0;

        initSpeed = 0;
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

        timerCount++;

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
          //  int position = (int) (Constants.INIT_SPEED * timeIntervalNum * Constants.DELTA_T + .5 * donut.getAccel() * timeIntervalNum * timeIntervalNum * Constants.DELTA_T * Constants.DELTA_T + Constants.INIT_Y);
            /*if (donut.getAccel() == Constants.ACCEL_BALLOON_DOWN) {
                Log.d("accel", "is0");
                donut.y += Constants.BALLOON_SPEED_DOWN;
            } else {*/
                initSpeed = Constants.INIT_SPEED;
                if (donut.hasBalloon) {
                    initSpeed = -1.8;
                }
                donut.y -= (int) (initSpeed * Constants.DELTA_T + .5 * donut.getAccel() * Constants.DELTA_T * (2 * timeIntervalNum * Constants.DELTA_T + Constants.DELTA_T));
            //}
            //Log.d("CLICKED", Constants.INIT_SPEED + " * " + timeIntervalNum + " * " +  Constants.DELTA_T + " + " + ".5" + " * " + Constants.ACCEL + " * " + timeIntervalNum + " * " + timeIntervalNum);
            //Log.d("CLICKED", Double.toString(position));
            //donut.moveTo(currentDonutY);
            //Log.d("donuty", Integer.toString(donut.y));
            Log.d("pos", "speed " + initSpeed + " accel " + donut.getAccel());
            Log.d("pos", Integer.toString(donut.y));

        }

        // checks if the donut has landed
        if (donut.y >= 1080-Constants.INIT_Y) {
            if (!donut.hasBalloon) {
                donut.moveTo(Constants.INIT_Y);
                donut.isJumping = false;
                timeIntervalNum = 0;
            }
        }

        // checks if donut is ever out of screen
        if (donut.getY() > 1080-Constants.INIT_Y) {
            Log.d("donuty", "outofscreen");
            donut.moveTo(Constants.INIT_Y);
        }
        if (donut.getY()-Constants.BALLOON_HEIGHT+50 < 0) {
            donut.moveTo(1080-Constants.BALLOON_HEIGHT+50);
        }

        donut.update(timerCount);

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

                if (gameState.hasBalloon()) {
                    donut.setHasBalloon(true);
                }
            }
        }
        timeIntervalNum++;
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

            // draws game over and you win screens
            if (gameState.isGameOver()) {
                gameOver(canvas);
                return;
            }

            // draws all objects
            canvas.drawBitmap(background1.background, background1.x, background1.y, paint);
            canvas.drawBitmap(background2.background, background2.x, background2.y, paint);

            donut.draw(canvas, paint, false);
            //canvas.drawBitmap(donut.getDonut(n), donut.x, donut.y, paint);

            // draw gameitems
            for (int i = 0; i < gameItems.size(); i++) {
                GameItem item = gameItems.get(i);
                item.draw(canvas, paint, phoneX);
            }

            if (gameState.getCoinScore() == totalCoinNum) {
                textPaint.setColor(Color.GREEN);
            }
            canvas.drawText("Coins: " + gameState.getCoinScore() + " / " + totalCoinNum, 1500, 100, textPaint);

            gameView.getHolder().unlockCanvasAndPost(canvas);

            angleCount++;
            coinPos++;
        }
    }

    public void gameOver(Canvas canvas) {
        gameSound.stopBgMusic();
        isPlaying = false;
        Log.d("gameOver", "isgameover");
        blackBg = Bitmap.createScaledBitmap(blackBg, 2500, 1500, false);
        canvas.drawBitmap(blackBg, -10, -10, halfPaint);

        int[] nums = donut.getSprinkleNums();
        int bread = 0;
        int choc = 0;
        int purpleNum = nums[0];
        int pinkNum = nums[1];
        int blueNum = nums[2];
        int greenNum = nums[3];
        int whiteNum = nums[4];
        int orangeNum = nums[5];
        int yellowNum = nums[6];

        String[] colors = new String[] {"Purple", "Pink", "Blue", "Green", "White", "Orange", "Yellow"};

        Paint redPaint = new Paint();
        redPaint.setColor(Color.RED);
        redPaint.setStyle(Paint.Style.FILL);
        redPaint.setTextSize(80);

        textPaint.setColor(Color.GREEN);
        if (gameState.getCoinScore() >= totalCoinNum) {
            canvas.drawText("Coins: " + gameState.getCoinScore() + "/" + totalCoinNum, 1250, 900, textPaint);
        } else {
            canvas.drawText("Coins: " + gameState.getCoinScore() + "/" + totalCoinNum, 1250, 900, redPaint);
        }

        if (gameState.isWin()) {
            Log.d("youwindim", "width: " + winTextWidth  + " height: " + winTextHeight);
            winText = Bitmap.createScaledBitmap(winText, (int) winTextFinalWidth, (int) winTextFinalHeight, false);
            canvas.drawBitmap(winText, screenX / 2 - (int) winTextFinalWidth / 2, 50, paint);
            donut.draw(canvas, paint, true);

            if (donut.isBread()) {
                bread = 1;
                canvas.drawText("Flour: " + bread + "/" + "1", 100, 500, textPaint);
            } else {
                canvas.drawText("Flour: " + bread + "/" + "1", 100, 500, redPaint);
            }
            if (donut.hasChoc()) {
                choc = 1;
                canvas.drawText("Chocolate Glaze: " + choc + "/" + "1", 100, 600, textPaint);
            } else {
                canvas.drawText("Chocolate Glaze: " + choc + "/" + "1", 100, 600, redPaint);
            }

            for (int i = 0; i < colors.length; i++) {
                if (nums[i] == 0) {
                    textPaint.setColor(Color.RED);
                }
                if (i < 3) {
                    canvas.drawText(colors[i] + "Sprinkles: " + nums[i] + "/" + "1", 100, 700 + i * 100, textPaint);
                } else {
                    canvas.drawText(colors[i] + "Sprinkles: " + nums[i] + "/" + "1", 1250, 500 + (i - 3) * 100, textPaint);
                }
            }
           /* canvas.drawText("Purple Sprinkles: " + purpleNum + "/" + "1", 400, 700, textPaint);
            canvas.drawText("Pink Sprinkles: " + pinkNum + "/" + "1", 400, 800, textPaint);
            canvas.drawText("Blue Sprinkles: " + blueNum + "/" + "1", 400, 900, textPaint);
            canvas.drawText("Green Sprinkles: " + greenNum + "/" + "1", 1250, 500, textPaint);
            canvas.drawText("White Sprinkles: " + whiteNum + "/" + "1", 1250, 600, textPaint);
            canvas.drawText("Orange Sprinkles: " + orangeNum + "/" + "1", 1250, 700, textPaint);
            canvas.drawText("Yellow Sprinkles: " + yellowNum + "/" + "1", 1250, 800, textPaint);*/
            //winText = Bitmap.createScaledBitmap(winText, (int) winTextWidth, (int) winTextHeight, false);
            //canvas.drawBitmap(winText, screenX / 2 - (int) winTextWidth / 2, screenY / 2 - (int) winTextHeight / 2, paint);
        } else {
            canvas.drawBitmap(gameOver, screenX / 2 - gameOverWidth / 2, screenY / 2 - gameOverHeight / 2, paint);
        }
        gameView.getHolder().unlockCanvasAndPost(canvas);

    }

    public boolean onPressEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (gameState.isGameOver()) {
                    Log.d("gameOver", "wasclikced");
                    if (win) {
                        gameSound.stopVictorySound();
                    }
                    gameSound.stopBgMusic();
                    return false;
                } else if (!donut.isJumping) {
                    donut.isJumping = true;
                    Log.d("CLICKED", "hi");
                    timeIntervalNum = 0;
                    MediaPlayer jumpSound = MediaPlayer.create(context, R.raw.jumpsound);
                    jumpSound.setVolume((float) .5, (float) .5);
                    jumpSound.start();
                } else if (gameState.hasBalloon()) {
                    Log.d("accel", "set0");
                    donut.setAccel(Constants.ACCEL_BALLOON_DOWN);
                    timeIntervalNum = 0;
                }
                break;
            case MotionEvent.ACTION_UP:

                if (gameState.hasBalloon()) {
                    Log.d("pos", "released");
                    donut.setAccel(Constants.ACCEL_BALLOON);
                    timeIntervalNum = 0;
                    initSpeed = 0;
                }
                break;
        }
        return true;
    }
}
