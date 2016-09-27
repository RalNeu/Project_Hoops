package ulm.hochschule.project_hoops.objects;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;

import ulm.hochschule.project_hoops.R;

/**
 * Created by Johann on 27.09.2016.
 */


public class Hoop {

    private float widthScreen;
    private float heightScreen;

    public float xBasketCollFront;
    public float yBasketCollFront;
    public float xBasketCollBack;
    public float yBasketCollBack;

    public float basketCollRadius;

    public float xBackboardTop;
    public float yBackboardTop;
    public float xBackboardBot;
    public float yBackboardBot;

    public float xPoleCollTop;
    public float yPoleCollTop;
    public float xPoleCollBot;
    public float yPoleCollBot;

    public BitmapDrawable bitmap;
    Context context;

    public Hoop(float widthScreen, float heightScreen, Context context){
        bitmap = (BitmapDrawable) context.getResources().getDrawable(R.drawable.basketball_hoop);
        this.widthScreen = widthScreen;
        this.heightScreen = heightScreen;

        xBasketCollFront = widthScreen - bitmap.getMinimumWidth() + 5;
        yBasketCollFront = heightScreen - bitmap.getMinimumHeight()*80/121;

        xBasketCollBack = widthScreen - bitmap.getMinimumWidth()*50/100;
        yBasketCollBack = yBasketCollFront;

        basketCollRadius = bitmap.getMinimumHeight()/50;

        xBackboardTop = widthScreen - bitmap.getMinimumWidth()*50/122;
        yBackboardTop = heightScreen - bitmap.getMinimumHeight() + 5;
        xBackboardBot = xBackboardTop;
        yBackboardBot = heightScreen - bitmap.getMinimumHeight()*53/90;

        xPoleCollTop = widthScreen - bitmap.getMinimumWidth()*10/62;
        yPoleCollTop = yBackboardBot;
        xPoleCollBot = xPoleCollTop;
        yPoleCollBot = heightScreen;
    }

    public void myDraw(Canvas canvas){
        canvas.drawBitmap(bitmap.getBitmap(), widthScreen - bitmap.getMinimumWidth(), heightScreen - bitmap.getMinimumHeight(), new Paint());
        Paint p = new Paint();
        p.setColor(Color.BLUE);
        p.setAlpha(100);
        /*canvas.drawCircle(xBasketCollFront, yBasketCollFront, basketCollRadius, p);
        canvas.drawCircle(xBasketCollBack, yBasketCollBack, basketCollRadius, p);
        canvas.drawCircle(xBackboardTop, yBackboardTop, basketCollRadius, p);
        canvas.drawCircle(xBackboardBot, yBackboardBot, basketCollRadius, p);
        canvas.drawCircle(xPoleCollTop, yPoleCollTop, basketCollRadius, p);
        canvas.drawCircle(xPoleCollBot, yPoleCollBot, basketCollRadius, p);*/

    }
}