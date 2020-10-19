package com.example.foodrun;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

public class Coin implements GameItem {
    Bitmap coin;
    Bitmap coin1, coin2, coin3, coin4, coin5, coin6, coin7;
    Bitmap[] coins;
    int x, y;
    int width, height;
    private boolean collected;
    Rect rect;
    private int coinN;

    public Coin(Resources res, int x) {
        width = Constants.COIN_WIDTH;
        height = Constants.COIN_WIDTH - 3;

        coin = BitmapFactory.decodeResource(res, R.drawable.coin);
        coin = Bitmap.createScaledBitmap(coin, width,height, false);

        coin1 = BitmapFactory.decodeResource(res, R.drawable.coin1);
        coin2 = BitmapFactory.decodeResource(res, R.drawable.coin2);
        coin3 = BitmapFactory.decodeResource(res, R.drawable.coin3);
        coin4 = BitmapFactory.decodeResource(res, R.drawable.coin4);
        coin5 = BitmapFactory.decodeResource(res, R.drawable.coin5);
        coin6 = BitmapFactory.decodeResource(res, R.drawable.coin6);
        coin7 = BitmapFactory.decodeResource(res, R.drawable.coin7);

        coins = new Bitmap[] {coin1, coin2, coin3, coin4, coin5, coin6, coin7};
        for (int i = 0; i < coins.length; i++) {
            coins[i] = Bitmap.createScaledBitmap(coins[i], width,height, false);
        }

        this.x = x;
        //x = startX;
        y = 870;

        this.rect = new Rect(x + Constants.RECT_ERROR, y, x + width - Constants.RECT_ERROR, y + height - Constants.RECT_ERROR);

        coinN = 0;
    }

    public Bitmap getCoin (int coinNum) {
        return coins[coinNum];
    }

   /* public Bitmap getCoin() {
        return coin;
    }*/

    /*public static Coin[] getCoinLine(int coinsStartX, int numOfCoins, Resources res) {
        Log.d("coinline", "hi");
        Coin[] coins = new Coin[numOfCoins];
        for (int i = 0; i < numOfCoins; i++) {
            coins[i] = new Coin(res,coinsStartX + i * Constants.COIN_SPACE);
        }
        return coins;
    }*/

    public static void draw(Coin[] coins, Canvas canvas, Paint paint, int coinNum) {
        for (int i = 0; i < coins.length; i++) {
            if (coins[i].x <= Constants.SCREEN_X + 20 && coins[i].x >= -100 && coins[i].collected != true) {
                canvas.drawBitmap(coins[i].getCoin(coinNum), coins[i].x, coins[i].y, paint);
            }
            Log.d("coinnum", "" + coinNum);
        }
    }

    public void setCoinN(int num) {
        coinN = num;
    }

    public void draw(Canvas c, Paint p, int phoneX) {
        if (!collected && x-phoneX <= 2000 && x-phoneX > -100) {
            c.drawBitmap(coins[coinN / 2], x - phoneX, y, p);
        }
    }

    public static void move() {
        /*for (int i = 0; i < coins.length; i++) {
            coins[i].x -= Constants.BG_SPEED;
        }*/

    }

    public Rect getRect() {
        //return new Rect(x + Constants.RECT_ERROR, y, x + width - Constants.RECT_ERROR, y + height - Constants.RECT_ERROR);
        return rect;
    }

    public void update() {
        coinN++;
        if (coinN >= 14) {
            coinN = 0;
        }
    }

    public String getType() {
        return "coin";
    }

    public void updateGameState(GameState gameState) {
        gameState.updateCoinScore(1);
    }

    public void makeSound(GameSound gameSound) {
        gameSound.makeCoinSound();
    }

    public boolean getCollected() {
        return collected;
    }

    public void setCollected(boolean isCollected) {
        collected = isCollected;
    }
}
