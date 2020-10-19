package com.example.foodrun;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import java.io.FileOutputStream;

public class Donut {
    int x, y;
    int width, height;
    Bitmap donut, donut1, donut2, donut3, donut4, donut5, donut6, donut7, donut8, donutEgg;
    int angleCount, drawDonutInterval;
    Bitmap[] donuts;
    boolean isJumping;
    boolean hasBalloon;
    private int totalAngles;
    private Bitmap balloon;
    private int balloonWidth;
    private double accel;
    private Matrix matrix;
    private boolean isBread;
    private boolean hasChoc;
    private Bitmap[] donutEggs;
    private boolean isEgg;
    private Paint lineP;
    private Bitmap donutBread;
    private Bitmap[] donutBreads;
    private GameState gameState;
    private Bitmap choc;
    private Bitmap[] chocs;
    private Bitmap[] sprinkles;
    private Bitmap[][] sprinklesRotations;
    private Bitmap yellow, orange, white, green, blue, pink, purple;
    private Bitmap poof1, poof2, poof3, poof4, poof5, poof6, poof7, poof8, poof9, poof10;
    private Bitmap[] poofs;
    private int poofCount;
    private int[] sprinkleNums;
    private Bitmap shine;
    //private boolean transitioning;

    public Donut (Resources res, GameState gameState) {
        this.gameState = gameState;

        isBread = false;
        hasChoc = false;

        // original donut that's not used
        donut = BitmapFactory.decodeResource(res, R.drawable.donut);

        donut1 = BitmapFactory.decodeResource(res, R.drawable.donut1);
        donut2 = BitmapFactory.decodeResource(res, R.drawable.donut2);
        donut3 = BitmapFactory.decodeResource(res, R.drawable.donut3);
        donut4 = BitmapFactory.decodeResource(res, R.drawable.donut4);
        donut5 = BitmapFactory.decodeResource(res, R.drawable.donut5);
        donut6 = BitmapFactory.decodeResource(res, R.drawable.donut6);
        donut7 = BitmapFactory.decodeResource(res, R.drawable.donut7);
        donut8 = BitmapFactory.decodeResource(res, R.drawable.donut8);

        donuts = new Bitmap[] {donut1, donut2, donut3, donut4, donut5, donut6, donut7, donut8};

        width = donut1.getWidth();
        height = donut1.getHeight();

        width /= 3;
        height /= 3;

        for (int i = 0; i < donuts.length; i++) {
            donuts[i] = Bitmap.createScaledBitmap(donuts[i], width, height, false);
        }

        x = Constants.INIT_X;
        moveTo(Constants.INIT_Y);

        isJumping = false;

        hasBalloon = false;

        totalAngles = 8;

        balloonWidth = Constants.BALLOON_WIDTH;

        balloon = BitmapFactory.decodeResource(res, R.drawable.balloon);
        balloon = Bitmap.createScaledBitmap(balloon, balloonWidth, Constants.BALLOON_HEIGHT, false);

        accel = Constants.ACCEL;

        matrix = new Matrix();
        donutEgg = BitmapFactory.decodeResource(res, R.drawable.egg);
        Log.d("egg", x + " " + width);
        donutEgg = Bitmap.createScaledBitmap(donutEgg, width, width, false);
     //   donutEgg = Bitmap.createBitmap(donutEgg, x, y, donutEgg.getWidth(), donutEgg.getHeight(), matrix, true);

        donutEggs = new Bitmap[8];
        for (int i = 0; i < 8; i++) {
            donutEggs[i] = rotateBitmap(donutEgg, (float) (45 * i));
            //donutEggs[i] = Bitmap.createBitmap(donutEgg, x, y+i, width, width, matrix, false);
        }

        donutBread = BitmapFactory.decodeResource(res, R.drawable.donutbread);
        donutBread = Bitmap.createScaledBitmap(donutBread, width, width, false);

        choc = BitmapFactory.decodeResource(res, R.drawable.chocolate);
        choc = Bitmap.createScaledBitmap(choc, width, width, false);

        Log.d("bread", donutBread.getWidth() + " " + donutBread.getHeight());
        donutBreads = new Bitmap[8];
        chocs = new Bitmap[8];
        for (int i = 0; i < donutBreads.length; i++) {
            donutBreads[i] = rotateBitmap(donutBread, (float) (45 * i));
            chocs[i] = rotateBitmap(choc, (float) (45 * i));
        }

        lineP = new Paint();
        lineP.setStyle(Paint.Style.STROKE);

        yellow = BitmapFactory.decodeResource(res, R.drawable.yellowsprinkles);
        orange = BitmapFactory.decodeResource(res, R.drawable.orangesprinkles);
        white = BitmapFactory.decodeResource(res, R.drawable.whitesprinkles);
        green = BitmapFactory.decodeResource(res, R.drawable.greensprinkles);
        blue = BitmapFactory.decodeResource(res, R.drawable.bluesprinkles);
        pink = BitmapFactory.decodeResource(res, R.drawable.pinksprinkles);
        purple = BitmapFactory.decodeResource(res, R.drawable.purplesprinkles);

        sprinkles = new Bitmap[] {purple, pink, blue, green, white, orange, yellow};
        sprinklesRotations = new Bitmap[sprinkles.length][8];
        for (int i = 0; i < sprinkles.length; i++) {
            sprinkles[i] = Bitmap.createScaledBitmap(sprinkles[i], width, width, false);
            for (int j = 0; j < 8; j++) {
                sprinklesRotations[i][j] = rotateBitmap(sprinkles[i], (float) (45*j));
            }
        }

        int poofCount = 0;
        poof1 = BitmapFactory.decodeResource(res, R.drawable.poof1);
        poof2 = BitmapFactory.decodeResource(res, R.drawable.poof2);
        poof3 = BitmapFactory.decodeResource(res, R.drawable.poof3);
        poof4 = BitmapFactory.decodeResource(res, R.drawable.poof4);
        poof5 = BitmapFactory.decodeResource(res, R.drawable.poof5);
        poof6 = BitmapFactory.decodeResource(res, R.drawable.poof6);
        poof7 = BitmapFactory.decodeResource(res, R.drawable.poof7);
        poof8 = BitmapFactory.decodeResource(res, R.drawable.poof8);
        poof9 = BitmapFactory.decodeResource(res, R.drawable.poof9);
        poof10 = BitmapFactory.decodeResource(res, R.drawable.poof10);
        poofs = new Bitmap[] {poof1, poof2, poof3, poof4, poof5, poof6, poof7, poof8, poof9, poof10};
        for (int i = 0; i < poofs.length; i++) {
            poofs[i] = Bitmap.createScaledBitmap(poofs[i], width + 300, width + 300, false);
        }

        //transitioning = false;
        sprinkleNums = new int[] {0,0,0,0,0,0,0};

        shine = BitmapFactory.decodeResource(res, R.drawable.shine);
        shine =  Bitmap.createScaledBitmap(shine, width + 250, width + 250, false);
    }

    public int[] getSprinkleNums() {
        return sprinkleNums;
    }

    public boolean hasChoc() {
        return hasChoc;
    }

    public boolean isEgg() {
        return isEgg;
    }

    public boolean isBread() {
        return isBread;
    }

    private Bitmap rotateBitmap(Bitmap source, float angle)
    {
        Matrix matrix = new Matrix();
        Log.d("bread", source.getWidth()/2 + " " + source.getHeight()/2);
        matrix.setRotate(angle,source.getWidth()/2,source.getHeight()/2);
        Log.d("egg", "width " + source.getWidth() + " height " + source.getHeight());
        double constant = (Math.sqrt(2.0) - 1) / 2.0;

        Bitmap result = Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
        Log.d("bread", "resultwidth " + result.getWidth());
        Log.d("bread", "resultheight " + result.getHeight());

       if (!(angle % 90 == 0)) {
           result = Bitmap.createBitmap(result, (int) ((result.getWidth()-source.getWidth())/ 2.0), (int) ((result.getHeight()-source.getHeight())/ 2.0), source.getWidth(), source.getWidth());
       }

        return result;
    }

    public double getAccel() {
        return accel;
    }

    public void setAccel(double accelNum) {
        accel = accelNum;
    }

    public void update(int timerCount) {
        if (isEgg) {
            if (timerCount % 4 == 0) {
                angleCount++;
            }
        } else if (timerCount % 2 == 0) {
            angleCount++;
        }
        if (gameState.isTransitioning()) {
            poofCount++;
        }

        if (angleCount >= totalAngles) {
            /*if (isBread || hasChoc) {
                Log.d("anglecount", "isreset bc isbread/haschoc");
                angleCount = 0;
            } else if (isEgg && angleCount >= 16) {*/
            angleCount = 0;
                /*Log.d("anglecount", "isreset bc isegg");
            }*/
        }
        Log.d("anglecount", Integer.toString(angleCount));

        if (gameState.getChoc()) {
            hasChoc = true;
            //transitioning = true;
        }
        if (!gameState.getFlour()) {
            isEgg = true;
        } else {
            Log.d("bread", "isbread");
            isBread = true;
            isEgg = false;
            //transitioning = true;
        }
        if (poofCount >= 10) {
            poofCount = 0;
            gameState.setTransitioning(false);
        }

        Log.d("poof", poofCount + "");
    }

    public void draw(Canvas c, Paint p, boolean isStill) {
        int pos = angleCount;
        int tempX = x;
        int tempY = y;
        if (isStill) {
            pos = 0;
            tempX = 1920/2;
            tempY = 1080/2;
            c.drawBitmap(shine, tempX-130, tempY-140, p);
        }
        c.drawBitmap(getDonut(pos), tempX, tempY, p);
            //Log.d("pink", gameState.getColorMask())
        if (hasBalloon) {
            c.drawBitmap(balloon, x + width / 2 - balloonWidth / 2, y - 200, p);
            //c.drawBitmap(getDonut(angleCount), x, y, p);
        }
        if (gameState.getChoc()) {
            Log.d("donutstill", "haschoc");
            //c.drawBitmap(getDonut(angleCount), x, y, p);
            if (hasBalloon) {
                c.drawBitmap(chocs[0], tempX, tempY, p);
            } else {
                c.drawBitmap(chocs[pos], tempX, tempY, p);
            }
        }
        if ((gameState.getColorMask() & Constants.PURPLE_MASK) != 0) {
            sprinkleNums[0] = 1;
            if (hasBalloon) {
                c.drawBitmap(sprinklesRotations[0][0], tempX, tempY, p);
            } else {
                c.drawBitmap(sprinklesRotations[0][pos], tempX, tempY, p);
            }
        }
        if ((gameState.getColorMask() & Constants.PINK_MASK) != 0) {
            sprinkleNums[1] = 1;
            if (hasBalloon) {
                c.drawBitmap(sprinklesRotations[1][0], tempX, tempY, p);
            } else {
                Log.d("pink", "inhere");
                c.drawBitmap(sprinklesRotations[1][pos], tempX, tempY, p);
            }
        }
        if ((gameState.getColorMask() & Constants.BLUE_MASK) != 0) {
            sprinkleNums[2] = 1;
            if (hasBalloon) {
                c.drawBitmap(sprinklesRotations[2][0], tempX, tempY, p);
            } else {
                c.drawBitmap(sprinklesRotations[2][pos], tempX, tempY, p);
            }
        }
        if ((gameState.getColorMask() & Constants.GREEN_MASK) != 0) {
            sprinkleNums[3] = 1;
            if (hasBalloon) {
                c.drawBitmap(sprinklesRotations[3][0],tempX, tempY, p);
            } else {
                c.drawBitmap(sprinklesRotations[3][pos], tempX, tempY, p);
            }
        }
        if ((gameState.getColorMask() & Constants.WHITE_MASK) != 0) {
            sprinkleNums[4] = 1;
            if (hasBalloon) {
                c.drawBitmap(sprinklesRotations[4][0], tempX, tempY, p);
            } else {
                c.drawBitmap(sprinklesRotations[4][pos], tempX, tempY, p);
            }
        }
        if ((gameState.getColorMask() & Constants.ORANGE_MASK) != 0) {
            sprinkleNums[5] = 1;
            if (hasBalloon) {
                c.drawBitmap(sprinklesRotations[5][0], tempX, tempY, p);
            } else {
                c.drawBitmap(sprinklesRotations[5][pos], tempX, tempY, p);
            }
        }
        if ((gameState.getColorMask() & Constants.YELLOW_MASK) != 0) {
            sprinkleNums[6] = 1;
            if (hasBalloon) {
                c.drawBitmap(sprinklesRotations[6][0],tempX, tempY, p);
            } else {
                c.drawBitmap(sprinklesRotations[6][pos], tempX, tempY, p);
            }
        }
        if (gameState.isTransitioning()) {
            c.drawBitmap(poofs[poofCount], x - 150, y - 150, p);
        }
        /*else {
            Bitmap drawing = getDonut(angleCount);
            //c.drawRect(x, y, x+drawing.getWidth(), y + drawing.getHeight(), lineP);
            c.drawBitmap(drawing, x, y, p);
        }*/
    }

    public boolean isHasBalloon() {
        return hasBalloon;
    }

    public void setHasBalloon(boolean hasBalloon) {
        this.hasBalloon = hasBalloon;
        if (hasBalloon) {
            accel = Constants.ACCEL_BALLOON;
        } else {
            accel = Constants.ACCEL;
        }
    }

    public Bitmap getDonut (int angleCount) {
        int n = angleCount;
        if (hasBalloon) {
            if (isBread) {
                return donutBreads[n];
            } else {
                return donuts[0];
            }
        } else if (isBread) {
            return donutBreads[n];
        } else if (isEgg) {
            return donutEggs[n];
        }
        return donuts[n];
    }

    public void moveUp (int distance) {
        this.y -= distance;
    }
    public void moveDown (int distance) {
        this.y += distance;
    }
    public void moveTo (int distance) {
        this.y = 1080 - distance;
    }
    public int getY () {
        return this.y;
    }

    public Rect getRect(int x) {
        return new Rect(x + Constants.RECT_ERROR, y, x + width - Constants.RECT_ERROR, y + height - Constants.RECT_ERROR);
    }
}
