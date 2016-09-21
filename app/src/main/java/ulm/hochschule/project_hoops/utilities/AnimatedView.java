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
    private int tempTimerx = 10;
    private int tempTimery = 5;
    private Handler handler;
    private final int FRAME_RATE = 30;


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
        System.out.println("velocityy: " + yVelocityDirection + "       y: " + y);
        if(xVelocityDirection != 0) {
            x += xVelocityDirection;
            if ((x > this.getWidth() - drawableball.getBitmap().getWidth()) || (x < 0)) {
                xVelocityDirection = xVelocityDirection *-1;
            }
            if(--tempTimerx == 0) {
                if(xVelocityDirection > 0)
                    xVelocityDirection--;
                else
                    xVelocityDirection++;
                tempTimerx = 10;
            }
        }

        if(yVelocityDirection != 0) {
            if ((y > this.getHeight() - drawableball.getBitmap().getHeight())) {
                yVelocityDirection = (yVelocityDirection *-1);
                y += yVelocityDirection;
            } else if(y < 0) {
                yVelocityDirection = (yVelocityDirection *-1);
                y += yVelocityDirection+1;
            } else
                y += yVelocityDirection;

        }
        if(y < this.getHeight() - drawableball.getBitmap().getHeight())
            if(yVelocityDirection < 0)
                --tempTimery;
            if(--tempTimery <= 0) {
                yVelocityDirection++;
                tempTimery = 5;
            }

        /*if (x<0 && y <0) {
            x = this.getWidth()/2;
            y = this.getHeight()/2;
        } else {
            x += xVelocityDirection;
            y += yVelocityDirection;
            if ((x > this.getWidth() - drawableball.getBitmap().getWidth()) || (x < 0)) {
                xVelocityDirection = xVelocityDirection *-1;
            }
            if ((y > this.getHeight() - drawableball.getBitmap().getHeight()) || (y < 0)) {
                yVelocityyDirection = yVelocityDirection *-1;
            }
        }*/
        canvas.drawBitmap(drawableball.getBitmap(), x, y, null);
        handler.postDelayed(r, FRAME_RATE);
    }

}