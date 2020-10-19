package com.example.foodrun;

public interface Constants {
    int BG_WIDTH = 3960;
    int BG_SPEED = 15;
    int DELTA_T = 17;
    double INIT_SPEED = 50.0/DELTA_T;
    int INIT_X = 200;
    int INIT_Y = 275;
    double ACCEL = -.015;
    double ACCEL_BALLOON = .008;
    double ACCEL_BALLOON_DOWN = 0;

    int SPIKE_INIT_Y = 230;
    int RECT_ERROR = 20;
    double GAME_OVER_RATIO = 1.903614;
    int FLAG_POS = 2000;
    double YOU_WIN_RATIO = 5.179372;

    int COIN_WIDTH = 89;
    int COIN_SPACE = 120;

    int SCREEN_X = 2160;

    int BALLOON_WIDTH = 100;
    int BALLOON_HEIGHT = 250;
    int BALLOON_SPEED_DOWN = 20;

    int PURPLE_MASK = 1;
    int PINK_MASK = 2;
    int BLUE_MASK = 4;
    int GREEN_MASK = 8;
    int WHITE_MASK = 16;
    int ORANGE_MASK = 32;
    int YELLOW_MASK = 64;

    int INGREDIENT_INIT_Y = 1080-300;
}
