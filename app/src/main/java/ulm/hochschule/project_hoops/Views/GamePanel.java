package ulm.hochschule.project_hoops.views;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;

import ulm.hochschule.project_hoops.activities.GameEndActivity;
import ulm.hochschule.project_hoops.objects.Ball;
import ulm.hochschule.project_hoops.objects.Hoop;
import ulm.hochschule.project_hoops.sonstige.GameModel;
import ulm.hochschule.project_hoops.sonstige.GameThread;

/**
 * Created by Johann on 19.09.2016.
 */
//Fläche auf der das Spiel abläuft
public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

    private GameThread thread;
    public GameModel model;

    private float x,y,x2,y2;
    private float xP, yP;
    private float xV, yV;
    private boolean drawPoint = false;

    private TextView scoreText;
    private TextView attemptText;
    private final TextView velocityText;

    private float xVel;
    private float yVel;

    public GamePanel(Context context, float width, float height, TextView scoreText, TextView attemptText, TextView velocityText, String mode){
        super(context);
        model = new GameModel(context, width, height);
        xP = model.ball.xCenter;
        yP = model.ball.yCenter;
        this.scoreText = scoreText;
        this.attemptText = attemptText;
        this.velocityText = velocityText;

        getHolder().addCallback(this);
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(!model.ball.ok) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            model.ball.ok = false;
                            drawPoint = true;
                            //Koordianten der Berührung werden gespeichert
                            x = event.getX();
                            y = event.getY();
                            break;
                        case MotionEvent.ACTION_UP:
                            model.ball.ok = true;
                            drawPoint = false;
                            model.ball.xVelocity = (x - event.getX()) / 5 * model.distance;
                            model.ball.yVelocity = (y - event.getY()) / 5 * model.distance;
                            xVel = model.ball.xVelocity;
                            yVel = model.ball.yVelocity;
                            //Für tests: bei Wurf nach hinten wird der gespeicherte Wurf ausgeführt
                            if(x-event.getX() < 0) {
                                model.ball.xVelocity = 67.68638f;
                                model.ball.yVelocity = -101.455536f;
                                //model.ball.xVelocity = 83.625f;
                                //model.ball.yVelocity = -102.02344f;

                            }
                            //Testzwecke
                            //----------
                            System.out.println("xVel: " + model.ball.xVelocity);
                            System.out.println("yVel: " + model.ball.yVelocity);
                            System.out.println("dist: " + model.distance);
                            //----------
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

    public void update(){
        xV = (x - x2) / 5 * model.distance;
        yV = (y - y2) / 5 * model.distance;
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
        thread = new GameThread(getHolder(), this, model);
        //start Game
        thread.setRunning(true);
        thread.start();
    }

    //Zeichnet Ball, Korb und ballTrajectory
    @Override
    public void draw(Canvas canvas) {
        Paint p = new Paint();
        p.setColor(Color.BLUE);
        if(canvas != null){
            super.draw(canvas);
            canvas.drawColor(Color.WHITE);
            model.ball.myDraw(canvas);
            model.hoop.myDraw(canvas);
            //Testzwecke
            //-----------------------------------------------------------------------
            velocityText.post(new Runnable() {
                public void run() {
                    velocityText.setText("     xVel: " + xVel + "   yVel: " + yVel);
                }
            });

            attemptText.post(new Runnable() {
                public void run() {
                    attemptText.setText("    attempt: " + model.attempt + "/10");
                }
            });

            scoreText.post(new Runnable() {
                public void run() {
                    scoreText.setText("Score: " + model.score);
                }
            });
            //------------------------------------------------------------------------

            if(drawPoint) {
                for (int i = 0; i < 25; i++) {
                    yV += model.ball.gravity * 0.5f;
                    xP += (int) (0.5f * (xV));
                    yP += (int) (0.5f * (yV + model.ball.gravity * 0.5f));

                    if(xP < model.ball.RADIUS)
                    {
                        xP = model.ball.RADIUS;
                        xV = -xV * 0.75f;
                    }


                    if(xP > model.ball.widthScreen - model.ball.RADIUS)
                    {
                        xP = model.ball.widthScreen - model.ball.RADIUS;
                        xV = -xV * 0.75f;
                    }


                    if(yP > model.ball.heightScreen - model.ball.RADIUS)
                    {
                        yP = model.ball.heightScreen - model.ball.RADIUS;
                        yV = -yV * 0.75f;
                        if(xV < 0){
                            xV+=1;
                        }else if(xV > 0){
                            xV-=1;
                        }
                    }

                    canvas.drawCircle(xP, yP, 10, p);
                }
                xP = model.ball.xCenter;yP = model.ball.yCenter;
            }
        }
    }
}
