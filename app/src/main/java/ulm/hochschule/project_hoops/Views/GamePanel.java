package ulm.hochschule.project_hoops.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import ulm.hochschule.project_hoops.objects.Ball;
import ulm.hochschule.project_hoops.sonstige.GameThread;

/**
 * Created by Johann on 19.09.2016.
 */
public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

    private GameThread thread;
    private Ball ball;

    private float x,y,x2,y2;
    private float xP = 500, yP = 500;
    private float xV, yV;
    private boolean drawPoint = false;

    public GamePanel(Context context, float width, float height){
        super(context);
        ball = new Ball(width, height, context);

        getHolder().addCallback(this);
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(!ball.ok) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            ball.ok = false;
                            drawPoint = true;
                            x = event.getX();
                            y = event.getY();
                            break;
                        case MotionEvent.ACTION_UP:
                            ball.ok = true;
                            drawPoint = false;
                            ball.xVelocity = (x - event.getX()) / 5;
                            ball.yVelocity = (y - event.getY()) / 5;
                            break;
                        case MotionEvent.ACTION_MOVE:
                            x2 = event.getX();
                            y2 = event.getY();
                            break;
                    }
                }
                return true;
            }
        });
        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while(retry){
            try{
                thread.setRunning(false);
                thread.join();
            }catch(Exception e){}
            retry = false;
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread = new GameThread(getHolder(), this);
        //start Game
        thread.setRunning(true);
        thread.start();
    }

    public void update(){
        if(ball.ok){
            ball.update();
        }
        xV = (x - x2) / 5;
        yV = (y - y2) / 5;
    }

    private void checkCollision() {
            /*float distance = (float) Math.sqrt(Math.pow(xBasketColisFront - xCenter, 2) + Math.pow(yBasketColisTop - yCenter, 2));
            if(distance < RADIUS) {
                xVelocity -=  (xVelocity * (xBasketColisFront - xCenter) / distance);
                yVelocity *= (yBasketColisTop - yCenter) / distance * -1;
            }*/

        /*
        if(yCenter + RADIUS > yBasketColisTop && yCenter - RADIUS < yBasketColisBot && xCenter > xBasketColisFront && xCenter < xBasketColisFront + 50)
            yVelocity *= -1;
        else if (xCenter + RADIUS > xBasketColisFront && xCenter - RADIUS < xBasketColisFront && yCenter > yBasketColisTop && yCenter < yBasketColisBot)
            xVelocity *= -1;
        else if(Math.sqrt(Math.pow(xBasketColisFront + 25 - xCenter, 2) + Math.pow(yBasketColisTop + 25 - yCenter, 2)) < RADIUS + 25) {
            xVelocity *= -1;
            yVelocity *= -1;
        }
        */

    }

    @Override
    public void draw(Canvas canvas) {
        Paint p = new Paint();
        p.setColor(Color.BLUE);
        if(canvas != null){
            super.draw(canvas);
            canvas.drawColor(Color.WHITE);
            ball.myDraw(canvas);
            if(drawPoint) {
                for (int i = 0; i < 25; i++) {
                    yV += 9.81f * 0.5f;
                    xP += (int) (0.5f * (xV));
                    yP += (int) (0.5f * (yV + 9.81f * 0.5f));

                    if(xP < ball.RADIUS)
                    {
                        xP = ball.RADIUS;
                        xV = -xV * 0.75f;
                    }


                    if(xP > ball.widthScreen - ball.RADIUS)
                    {
                        xP = ball.widthScreen - ball.RADIUS;
                        xV = -xV * 0.75f;
                    }


                    if(yP > ball.heightScreen - 2 * ball.RADIUS)
                    {
                        yP = ball.heightScreen - 2 * ball.RADIUS;
                        yV = -yV * 0.75f;
                        if(xV < 0){
                            xV+=1;
                        }else if(xV > 0){
                            xV-=1;
                        }
                    }

                    canvas.drawCircle(xP, yP, 10, p);
                }
                xP = 500;yP = 500;
            }
        }
    }
}
