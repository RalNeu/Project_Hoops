package ulm.hochschule.project_hoops.objects;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;

import ulm.hochschule.project_hoops.R;

/**
 * Created by Johann on 19.09.2016.
 */
public class Ball {

    public float xCenter = 500;
    public float yCenter = 500;
    public int RADIUS;
    public float yVelocity = 0, xVelocity = 0;
    public float angle = 0;
    public boolean ok;
    public float rotatedBallHalfWidth, rotatedBallHalfHeight;
    public Paint alphaPaint;
    public float widthScreen, heightScreen;
    private static final float GRAVITY = 9.81f;
    private static final float deltaT = 0.5f;
    public static final float FACTOR_BOUNCEBACK = 0.75f;
    public BitmapDrawable bitmapD;
    Bitmap bitmap;

    public boolean nothingBelow;

    public Ball(float widthScreen, float heightScreen, Context context){
        bitmapD = (BitmapDrawable) context.getResources().getDrawable(R.drawable.basketball_small);
        bitmap = Bitmap.createScaledBitmap(bitmapD.getBitmap(), (int) widthScreen / 15, (int) widthScreen / 15, true);
        this.widthScreen = widthScreen;
        this.heightScreen = heightScreen;
        alphaPaint = new Paint();
        nothingBelow = true;
        RADIUS = bitmap.getWidth() / 2;
    }

    public boolean update()
    {
        //xVelocity -= 5f * deltaT;


        xCenter += (int)(deltaT * (xVelocity));

        if(nothingBelow) {
            yCenter += (int) (deltaT * (yVelocity + GRAVITY * deltaT));
            yVelocity += GRAVITY * deltaT;
        } else if (yVelocity > 0)
            yVelocity = 0;

        if(xCenter < RADIUS)
        {
            xCenter = RADIUS;
            xVelocity = -xVelocity * FACTOR_BOUNCEBACK;
        }


        if(xCenter > widthScreen - RADIUS)
        {
            xCenter = widthScreen - RADIUS;
            xVelocity = -xVelocity * FACTOR_BOUNCEBACK;
        }


        if(yCenter > heightScreen - RADIUS)
        {
            angle += xVelocity/8;
            yCenter = heightScreen - RADIUS;
            yVelocity = -yVelocity * FACTOR_BOUNCEBACK;
            if(xVelocity < 0){
                xVelocity+=1;
            }else if(xVelocity > 0){
                xVelocity-=1;
            }
        }

        if(xVelocity < 2 && xVelocity > -2){
            xVelocity = 0;
        }
        if(xVelocity == 0 && yVelocity > -2.5f && yCenter + RADIUS == heightScreen){
            ok = false;
            setCenter(500, 500);
        }else
            angle += xVelocity/8;

        return true;
    }

    public void setCenter(float x, float y){
        xCenter = x;
        yCenter = y;
    }

    public void myDraw(Canvas canvas){

        Bitmap rotatedBitmap = RotateBitmap(bitmap, angle);
        /*if(xVelocity < 25){
            alphaPaint.setAlpha((int)xVelocity*4);
        }else if(xVelocity > -25){
            alphaPaint.setAlpha((int)xVelocity*4*(-1));
        }*/
        rotatedBallHalfWidth = rotatedBitmap.getWidth()/2;
        rotatedBallHalfHeight = rotatedBitmap.getHeight()/2;
        canvas.drawBitmap(rotatedBitmap, xCenter - rotatedBallHalfWidth, yCenter - rotatedBallHalfHeight, alphaPaint);
    }

    public Bitmap RotateBitmap(Bitmap source, float angle)
    {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }
}
