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
//Basketball Objekt für das Basketballspiel
public class Ball {

    public float xCenter;
    public float yCenter;
    public int RADIUS;
    public float yVelocity = 0, xVelocity = 0;
    public float angle = 0;
    public boolean ok;
    public float rotatedBallHalfWidth, rotatedBallHalfHeight;
    private Paint alphaPaint;
    public int alphaValue = 255;
    public float widthScreen, heightScreen;
    public static float gravity;
    private static final float deltaT = 0.5f;
    public static final float FACTOR_BOUNCEBACK = 0.75f;
    public BitmapDrawable bitmapD;
    Bitmap bitmap;

    public float distance;

    public Ball(float widthScreen, float heightScreen, Context context){
        distance = 1;
        bitmapD = (BitmapDrawable) context.getResources().getDrawable(R.drawable.basketball_small);
        bitmap = Bitmap.createScaledBitmap(bitmapD.getBitmap(), (int) widthScreen / 12, (int) widthScreen / 12, true);
        this.widthScreen = widthScreen;
        this.heightScreen = heightScreen;
        alphaPaint = new Paint();
        RADIUS = bitmap.getWidth() / 2;
        xCenter = widthScreen / 4;
        yCenter = heightScreen / 2;
        gravity = 9.81f;
        resetBall();
    }

    public Ball(float widthScreen, float heightScreen, Context context, float distance){
        this.distance = distance;
        bitmapD = (BitmapDrawable) context.getResources().getDrawable(R.drawable.basketball_small);
        bitmap = Bitmap.createScaledBitmap(bitmapD.getBitmap(), (int) (widthScreen / 12 * distance), (int) (widthScreen / 12 * distance), true);
        this.widthScreen = widthScreen;
        this.heightScreen = heightScreen;
        alphaPaint = new Paint();
        RADIUS = bitmap.getWidth() / 2;
        gravity = 9.81f * distance;
        resetBall();
    }

    //Aktualisierung der Ballposition
    public boolean update()
    {
        //xVelocity -= 5f * deltaT;


        xCenter += (int)(deltaT * (xVelocity));

        yCenter += (int) (deltaT * (yVelocity + gravity * deltaT));
        yVelocity += gravity * deltaT;

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

        if(xVelocity < 2 && xVelocity > -2 && yCenter >= heightScreen - RADIUS){
            xVelocity = 0;
        }

        angle += xVelocity/8;

        return true;
    }

    //set Random
    public void setCenter(float x, float y){
        xCenter = x;
        yCenter = y;
    }

    //Ball wieder auf die Anfangsposition
    public void resetBall() {
        xCenter = widthScreen / 4 * distance;
        yCenter = heightScreen / 2 * (1/distance);
        if(yCenter > heightScreen - RADIUS - 100)
            yCenter = heightScreen - RADIUS - 100;
    }

    //Ball zeichnen
    public void myDraw(Canvas canvas){

        Bitmap rotatedBitmap = RotateBitmap(bitmap, angle);
        /*if(xVelocity < 25){
            alphaPaint.setAlpha((int)xVelocity*4);
        }else if(xVelocity > -25){
            alphaPaint.setAlpha((int)xVelocity*4*(-1));
        }*/
        rotatedBallHalfWidth = rotatedBitmap.getWidth()/2;
        rotatedBallHalfHeight = rotatedBitmap.getHeight()/2;
        alphaPaint.setAlpha(alphaValue);
        canvas.drawBitmap(rotatedBitmap, xCenter - rotatedBallHalfWidth, yCenter - rotatedBallHalfHeight, alphaPaint);
    }

    //Ball rotieren lassen(fürs Rollen zuständig)
    public Bitmap RotateBitmap(Bitmap source, float angle)
    {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }
}
