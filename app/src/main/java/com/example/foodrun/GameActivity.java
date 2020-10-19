package com.example.foodrun;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.graphics.Point;
import android.util.Log;
import android.view.WindowManager;

public class GameActivity extends AppCompatActivity {

    private GameView gameView;
    private MediaPlayer bgmusic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("activitycheck", "oncreate");

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);
        GameSound gs = GameSound.getInstance();
//        gs.destroyBgmusic();
        gs.init(getApplicationContext());

        bgmusic = GameSound.getbgmusic();
        // bgmusic = MediaPlayer.create(this, R.raw.bgmusic);
        gameView = new GameView(this, point.x, point.y, getResources(), 1);

        //Log.d("screenx", Integer.toString(point.x));

        setContentView(gameView);
    }

    @Override
    protected void onStart() {
        super.onStart();
        bgmusic.start();
        Log.d("activitycheck", "onstart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameView.pause();
        bgmusic.stop();
        Log.d("activitycheck", "onpause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        gameView.pause();
        bgmusic.stop();
        Log.d("activitycheck", "onpause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameView.resume();
        bgmusic.start();
        Log.d("activitycheck", "onresume");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("activitycheck", "ondestroy");
    }
}