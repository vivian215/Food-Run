package com.example.foodrun;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

public class Ingredient implements GameItem {
    private String type;
    private Bitmap flour, choc;
    private Bitmap[] sprinkleCups;
    private Bitmap yellow, orange, white, green, blue, pink, purple;
    private int width, height;
    private int x, y;
    private boolean collected;
    private Bitmap chocbar;
    private GameSound gameSound;

    public Ingredient (Resources res, String type, int x, int y) {
        this.type = type;
        this.x = x;
        this.y = y;

        width = 100;
        height = width;

        flour = BitmapFactory.decodeResource(res, R.drawable.flour);
        choc = BitmapFactory.decodeResource(res, R.drawable.chocolate);
        chocbar = BitmapFactory.decodeResource(res, R.drawable.chocbar);

        flour = Bitmap.createScaledBitmap(flour, 200, 200, false);
        choc = Bitmap.createScaledBitmap(choc, width, height, false);
        chocbar = Bitmap.createScaledBitmap(chocbar, 200, 200, false);

        yellow = BitmapFactory.decodeResource(res, R.drawable.yellowsprinklescup);
        orange = BitmapFactory.decodeResource(res, R.drawable.orangesprinklescup);
        white = BitmapFactory.decodeResource(res, R.drawable.whitesprinklescup);
        green = BitmapFactory.decodeResource(res, R.drawable.greensprinklescup);
        blue = BitmapFactory.decodeResource(res, R.drawable.bluesprinklescup);
        pink = BitmapFactory.decodeResource(res, R.drawable.pinksprinklescup);
        purple = BitmapFactory.decodeResource(res, R.drawable.purplesprinklescup);

        sprinkleCups = new Bitmap[] {purple, pink, blue, green, white, orange, yellow};

        for (int i = 0; i < sprinkleCups.length; i++) {
            sprinkleCups[i] = Bitmap.createScaledBitmap(sprinkleCups[i], 150, 200, false);
        }

        collected = false;

        gameSound = new GameSound();
    }

    private Bitmap getIngredient() {
        switch (type) {
            case "flour":
                return flour;
            case "choc":
                return chocbar;
            case "purple":
                return sprinkleCups[0];
            case "pink":
                return sprinkleCups[1];
            case "blue":
                return sprinkleCups[2];
            case "green":
                return sprinkleCups[3];
            case "white":
                return sprinkleCups[4];
            case "orange":
                return sprinkleCups[5];
            case "yellow":
                return sprinkleCups[6];
            default:
                return null;
        }
    }

    public void draw(Canvas c, Paint p, int phoneX) {
        Log.d("lag", "" + (x-phoneX) + " x " + x + " phonex " + phoneX);
        if (!collected && x-phoneX < 2000 && x-phoneX > -100) {
            Log.d("lag", "drew ingredient");
            c.drawBitmap(getIngredient(), x - phoneX, y, p);
        }
    }

    public Rect getRect() {
        return new Rect(x + Constants.RECT_ERROR, y, x + width - Constants.RECT_ERROR, y + height - Constants.RECT_ERROR);
    }

    public void update() { }

    public String getType() {
        return "ingredient";
    }

    public void updateGameState(GameState gameState) {
        gameSound.makeIngredientSound();
        switch (type) {
            case "flour":
                Log.d("choc", "ingredient FLOUR");
                gameState.setFlour(true);
                gameState.setTransitioning(true);
                Log.d("poof", Boolean.toString(gameState.isTransitioning()));
                break;
            case "choc":
                Log.d("choc", "ingredient choc");
                gameState.setChoc(true);
                gameState.setTransitioning(true);
                Log.d("poof", Boolean.toString(gameState.isTransitioning()));
                break;
            case "purple":
                gameState.setColorMask(Constants.PURPLE_MASK);
                gameState.setTransitioning(true);
                break;
            case "pink":
                gameState.setColorMask(Constants.PINK_MASK);
                gameState.setTransitioning(true);
                break;
            case "blue":
                gameState.setColorMask(Constants.BLUE_MASK);
                gameState.setTransitioning(true);
                break;
            case "green":
                gameState.setColorMask(Constants.GREEN_MASK);
                gameState.setTransitioning(true);
                break;
            case "white":
                gameState.setColorMask(Constants.WHITE_MASK);
                gameState.setTransitioning(true);
                break;
            case "orange":
                gameState.setColorMask(Constants.ORANGE_MASK);
                gameState.setTransitioning(true);
                break;
            case "yellow":
                gameState.setColorMask(Constants.YELLOW_MASK);
                gameState.setTransitioning(true);
                break;
        }
    }

    public void makeSound(GameSound gameSound) { }

    public void setCollected(boolean isCollected) {
        collected = isCollected;
    }

    public boolean getCollected() {
        return collected;
    }

}
