package com.example.foodrun;

public class GameState {
    private int coinScore;
    private boolean isGameOver;
    private int accel;
    private boolean flour, choc;
    //private boolean[] sprinkles;
    private int colorMask;
    private boolean transitioning;

    public boolean isTransitioning() {
        return transitioning;
    }

    public void setTransitioning(boolean transitioning) {
        this.transitioning = transitioning;
    }

    public int getColorMask() {
        return colorMask;
    }

    public void setColorMask(int givenColorMask) {
        this.colorMask |= givenColorMask;
    }

    /*public boolean[] getSprinkles() {
        return sprinkles;
    }

    public void setSprinkles(boolean[] sprinkles) {
        this.sprinkles = sprinkles;
    }*/

    public boolean getChoc() {
        return choc;
    }

    public void setChoc(boolean choc) {
        this.choc = choc;
    }

    public boolean getFlour() {
        return flour;
    }

    public void setFlour(boolean flour) {
        this.flour = flour;
    }

    public boolean hasBalloon() {
        return hasBalloon;
    }

    public void setHasBalloon(boolean hasBalloon) {
        this.hasBalloon = hasBalloon;
    }

    private boolean hasBalloon;

    public boolean isWin() {
        return isWin;
    }

    public void setWin(boolean win) {
        isWin = win;
    }

    private boolean isWin;

    public boolean isGameOver() {
        return isGameOver;
    }

    public void setGameOver(boolean gameOver) {
        isGameOver = gameOver;
    }

    public GameState() {
        coinScore = 0;
    }

    public int getCoinScore() {
        return coinScore;
    }

    public void updateCoinScore(int num) {
        coinScore += num;
    }
}
