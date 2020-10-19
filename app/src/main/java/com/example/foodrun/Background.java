package com.example.foodrun;

import android.graphics.Bitmap;
import android.content.res.Resources;
import android.graphics.BitmapFactory;


public class Background {
    int x=0, y=0;
    Bitmap background;

    Background (int screenX, int screenY, Resources res) {
        background = BitmapFactory.decodeResource(res, R.drawable.bg);
        background = Bitmap.createScaledBitmap(background, 3960, 1080, false);
    }
}
