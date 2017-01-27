package ulm.hochschule.project_hoops.objects;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;

import ulm.hochschule.project_hoops.R;

/**
 * Created by Johann on 27.09.2016.
 */

//Basketballkorb Objekt für das Basketballspiel
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

    public float yNetCollBot;

    public BitmapDrawable bitmapD;
    public Bitmap bitmap;

    public Hoop(float widthScreen, float heightScreen, Context context){
        bitmapD = (BitmapDrawable) context.getResources().getDrawable(R.drawable.basketball_hoop);
        bitmap = Bitmap.createScaledBitmap(bitmapD.getBitmap(), (int) widthScreen * 5 / 20, (int) heightScreen * 9 / 10, true);
        this.widthScreen = widthScreen;
        this.heightScreen = heightScreen;

        initCollPos();
    }

    public Hoop(float widthScreen, float heightScreen, Context context, float distance){
        bitmapD = (BitmapDrawable) context.getResources().getDrawable(R.drawable.basketball_hoop);
        bitmap = Bitmap.createScaledBitmap(bitmapD.getBitmap(), (int) (widthScreen * 5 / 20 * distance), (int) (heightScreen * 9 / 10 * distance), true);
        this.widthScreen = widthScreen;
        this.heightScreen = heightScreen;

        initCollPos();
    }

    //Hier werden die Kollisionspunkte vom Basketballkorb festgelegt
    private void initCollPos() {
        xBasketCollFront = widthScreen - bitmap.getWidth() + 5;
        yBasketCollFront = heightScreen - bitmap.getHeight()*80/121;

        xBasketCollBack = widthScreen - bitmap.getWidth()*50/100;
        yBasketCollBack = yBasketCollFront;

        basketCollRadius = bitmap.getHeight()/50;

        xBackboardTop = widthScreen - bitmap.getWidth()*50/122;
        yBackboardTop = heightScreen - bitmap.getHeight() + 5;
        xBackboardBot = xBackboardTop;
        yBackboardBot = heightScreen - bitmap.getHeight()*53/90;

        xPoleCollTop = widthScreen - bitmap.getWidth()*10/62;
        yPoleCollTop = yBackboardBot;
        xPoleCollBot = xPoleCollTop;
        yPoleCollBot = heightScreen;

        yNetCollBot = heightScreen - bitmap.getHeight() *11/ 24;
    }

    //Hier wird der Basketballkorb gezeichnet
    public void myDraw(Canvas canvas){
        canvas.drawBitmap(bitmap, widthScreen - bitmap.getWidth(), heightScreen - bitmap.getHeight(), new Paint());
        Paint p = new Paint();
        p.setColor(Color.BLUE);
        p.setAlpha(100);
    }
}