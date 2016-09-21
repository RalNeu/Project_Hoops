package ulm.hochschule.project_hoops.utilities;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.widget.ImageView;


import ulm.hochschule.project_hoops.R;

/**
 * Created by Maxim on 21.09.2016.
 */
public class AnimatedView extends ImageView {

    private Context context;
    int x = 200;
    int y = 200;
    private int xVelocityDirection = 20;
    private int yVelocityDirection = 20;
    private int tempTimerx = 30;
    private int tempTimery = 5;
    private Handler handler;
    private final int FRAME_RATE = 10;


    public AnimatedView(Context context, AttributeSet attrs)  {
        super(context, attrs);
        this.context = context;
        handler = new Handler();
    }

    private Runnable r = new Runnable() {
        @Override
        public void run() {
            invalidate();
        }
    };

    protected void onDraw(Canvas canvas) {
        BitmapDrawable drawableball = (BitmapDrawable) context.getResources().getDrawable(R.drawable.basketball_small);
        //System.out.println("velocityx: " + xVelocityDirection + "    x: " + x);
        //System.out.println("velocityy: " + yVelocityDirection + "       y: " + y);

        //Bei Kontakt mit seitlichen Wänden wird x Geschwindigkeit umgedreht
        if(xVelocityDirection != 0) {
            if ((x > this.getWidth() - drawableball.getBitmap().getWidth()) || (x < 0)) {
                xVelocityDirection = xVelocityDirection *-1;
            }
            x += xVelocityDirection;
            if(--tempTimerx == 0) {
                if(xVelocityDirection > 1)
                    xVelocityDirection--;
                else if(xVelocityDirection < 1)
                    xVelocityDirection++;
                tempTimerx = 30;
            }
        }

        //Bei Kontakt mit Boden oder Decke wird y Geschwindigkeit umgedreht
        if(yVelocityDirection != 0) {
            if ((y > this.getHeight() - drawableball.getBitmap().getHeight()) || y < 0)
                yVelocityDirection = (yVelocityDirection *-1);
            y += yVelocityDirection;

        }

        //Bei Ball in der Luft soll Gravitation wirken. Fliegt der Ball hoch, wirkt sie doppelt so oft
        if(y < this.getHeight() - drawableball.getBitmap().getHeight()) {
            if (yVelocityDirection < 0)
                --tempTimery;
            if (--tempTimery <= 0) {
                yVelocityDirection++;
                tempTimery = 5;
            }
        } else {
            if(xVelocityDirection > 0)
                xVelocityDirection--;
            else if(xVelocityDirection < 0)
                xVelocityDirection++;
            if(yVelocityDirection < 3 && yVelocityDirection > -3) {
                y = this.getHeight() - drawableball.getBitmap().getHeight();
                yVelocityDirection = 0;
            }
        }

        canvas.drawBitmap(drawableball.getBitmap(), x, y, null);
        handler.postDelayed(r, FRAME_RATE);
    }

}