package com.example.foodrun;

import android.content.Context;
import android.media.MediaPlayer;

public class GameSound {
    static MediaPlayer bgmusic = null;
    private static GameSound ourInstance = new GameSound();
    private Context appContext;
    private static MediaPlayer victoryMusic = null;
    private static MediaPlayer coinSound = null;
    private static MediaPlayer dieSound = null;
    private static MediaPlayer ingredientSound = null;

    public static Context get() {
        return getInstance().getContext();
    }

    public static synchronized GameSound getInstance() {
        return ourInstance;
    }

    public void init(Context context) {
        if (appContext == null) {
            this.appContext = context;
        }
        else {
            this.appContext = context;
            bgmusic.release();
            bgmusic = null;
        }
    }

    private Context getContext() {
        return appContext;
    }


    public static MediaPlayer getbgmusic() {
        if (bgmusic == null) {
            bgmusic = MediaPlayer.create(get(), R.raw.bgmusic);
            bgmusic.setVolume((float) 0.1, (float) 0.1);
            bgmusic.setLooping(true);
            return bgmusic;
        }
        return bgmusic;
    }

    public void makeCoinSound() {
       // if (coinSound == null) {
            coinSound = MediaPlayer.create(get(), R.raw.collectcoin);
            coinSound.setVolume((float) .5, (float) .5);
    //    }
        coinSound.start();
    }

    public void makeDieSound() {
        if (dieSound == null) {
            dieSound = MediaPlayer.create(get(), R.raw.gameoversound);
            dieSound.setVolume((float) .5, (float) .5);
        }
        dieSound.start();
    }

    public void makeVictorySound() {
        //if (victoryMusic == null) {
            victoryMusic = MediaPlayer.create(get(), R.raw.victorymusic);
            victoryMusic.setVolume((float) .2, (float) .2);
        //}
        victoryMusic.start();
    }

    public void makeIngredientSound() {
        ingredientSound = MediaPlayer.create(get(), R.raw.ingredientsound);
        ingredientSound.setVolume((float) .3, (float) .3);
        ingredientSound.start();
    }

    public void stopIngredientSound() {
        ingredientSound.stop();
    }

    public void stopVictorySound() {
        victoryMusic.stop();
    }

    public void stopBgMusic() {
        bgmusic.stop();
    }
}