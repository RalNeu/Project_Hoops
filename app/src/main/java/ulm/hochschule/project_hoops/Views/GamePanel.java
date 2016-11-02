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

import ulm.hochschule.project_hoops.activities.GameActivity;
import ulm.hochschule.project_hoops.activities.GameEndActivity;
import ulm.hochschule.project_hoops.activities.GameMenuActivity;
import ulm.hochschule.project_hoops.objects.Ball;
import ulm.hochschule.project_hoops.objects.Hoop;
import ulm.hochschule.project_hoops.sonstige.GameThread;
import ulm.hochschule.project_hoops.utilities.UserProfile;

/**
 * Created by Johann on 19.09.2016.
 */
public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

    private GameThread thread;
    private Ball ball;
    private Hoop hoop;

    private float x,y,x2,y2;
    private float xP, yP;
    private float xV, yV;
    private boolean drawPoint = false;

    private int attempt = 1;

    private boolean inHoopZone = false;
    private int score = 0;
    private TextView scoreText;
    private TextView attemptText;
    private boolean scored = false;
    private boolean justBounced = false;

    private float lastBasketColAngle = 90;
    private float lastXCenter = 0;
    private float width;
    private float height;
    private float distance = 1;

    private Context context;
    private Intent intent;

    public GamePanel(Context context, float width, float height, TextView scoreText, TextView attemptText){
        super(context);
        this.width = width;
        this.height = height;
        this.context = context;
        ball = new Ball(width, height, context);
        hoop = new Hoop(width, height, context);
        xP = ball.xCenter;
        yP = ball.yCenter;
        this.scoreText = scoreText;
        this.attemptText = attemptText;

        this.intent = new Intent(context, GameEndActivity.class);

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
                            ball.xVelocity = (x - event.getX()) / 5 * distance;
                            ball.yVelocity = (y - event.getY()) / 5 * distance;
                            if(x-event.getX() < 0) {
                                ball.xVelocity = 92.551796f;
                                ball.yVelocity = -74.53099f;
                            }
                            System.out.println("xVel: " + ball.xVelocity);
                            System.out.println("yVel: " + ball.yVelocity);
                            System.out.println("dist: " + distance);
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
            checkCollisionBasket(hoop.xBasketCollFront, hoop.yBasketCollFront, hoop.basketCollRadius);
            checkCollisionBasket(hoop.xBasketCollBack, hoop.yBasketCollBack, hoop.basketCollRadius);
            checkCollisionBBoard();
            checkCollisionPole();
            checkCollisionNet();
            checkGoal();
            fadeOut();

            if(ball.xVelocity == 0 && ball.yVelocity > -2.5f && ball.yCenter + ball.RADIUS == height) {
                ball.ok = false;
                restartGame();
            }

        }
        xV = (x - x2) / 5 * distance;
        yV = (y - y2) / 5 * distance;
    }

    public void restartGame() {
        if(scored)
            increaseDistance();
        else
            resetDistance();

        attempt++;


        if(attempt > 10) {

            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Bundle b = new Bundle();
            b.putInt("key", score); //Your id
            intent.putExtras(b); //Put your id to your next Intent
            thread.interrupt();
            context.startActivity(intent);
        } else {
            attemptText.post(new Runnable() {
                public void run() {
                    attemptText.setText("    attempt: " + attempt + "/10");
                }
            });

            ball.ok = false;
            ball.resetBall();
            scored = false;
            ball.alphaValue = 255;
        }

    }

    private void increaseDistance() {
        if(distance > 0.3)
            distance *= 0.9;
        ball = new Ball(width, height, context, distance);
        hoop = new Hoop(width, height, context, distance);
    }

    private void resetDistance() {
        distance = 1;
        ball = new Ball(width, height, context);
        hoop = new Hoop(width, height, context);
    }

    private void checkGoal() {
        if(inHoopZone && ball.yCenter > hoop.yBasketCollFront + 50 && !scored) {
            score += (1/(distance*distance*distance));
            scored = true;

            scoreText.post(new Runnable() {
                              public void run() {
                                  scoreText.setText("Score: " + score);
                              }
                          });
            inHoopZone = false;
        }
        if(ball.xCenter > hoop.xBasketCollFront && ball.xCenter < hoop.xBasketCollBack && ball.yCenter >= hoop.yBasketCollFront - 50 && ball.yCenter <= hoop.yBasketCollFront + 50) {
            inHoopZone = true;
        }
        else {
            inHoopZone = false;
        }

    }

    private void fadeOut() {
        if(scored) {
            ball.alphaValue -= 3;
            if (ball.alphaValue <= 0) {
                restartGame();
            }
        }
    }

    private void checkCollisionBasket(float xBasketColl, float yBasketColl, float basketCollRadius) {

        float xBasketColVector;
        float yBasketColVector;
        float basketColAngle = 90;
        float collisionAngle = 0;
        float velocity = (float) Math.sqrt(Math.pow(ball.xVelocity, 2) + Math.pow(ball.yVelocity, 2));

        if(xBasketColl - ball.xCenter != 0) {
            basketColAngle = (float) Math.atan((yBasketColl - ball.yCenter) / (xBasketColl - ball.xCenter));
            if(lastBasketColAngle == 90)
                lastBasketColAngle = basketColAngle;
            xBasketColVector = (float) Math.cos(lastBasketColAngle) * -1;
            yBasketColVector = (float) Math.sin(lastBasketColAngle) * -1;
            if(ball.xCenter > xBasketColl){
                xBasketColVector *= -1;
                yBasketColVector *= -1;
            }

            collisionAngle = (float) Math.acos((ball.xVelocity * xBasketColVector + ball.yVelocity * yBasketColVector) / (float) (Math.sqrt(Math.pow(ball.xVelocity, 2) + Math.pow(ball.yVelocity, 2)) * Math.sqrt(Math.pow(xBasketColVector, 2) + Math.pow(yBasketColVector, 2))));
        }

        if(Math.sqrt(Math.pow(xBasketColl - ball.xCenter, 2) + Math.pow(yBasketColl - ball.yCenter, 2)) < ball.RADIUS + basketCollRadius) {
            System.out.println("Col detected:");
            System.out.println("Angle: " + lastBasketColAngle + " new angle: " + basketColAngle);
            System.out.println("ball x: " + ball.xCenter);
            System.out.println("ball y: " + ball.yCenter);
            System.out.println("xBasketColl: " + xBasketColl);
            System.out.println("yBasketColl: " + yBasketColl);
            //lastBasketColAngle durch collisionAngle ersetzt
            //Idee: lastXCenter speichern, checken ob nun hinter basketColl, dann lastBasketColAngle benutzen. das gleiche mit Y
            if(ball.xCenter < xBasketColl && Math.sqrt(Math.pow(xBasketColl - ball.xCenter, 2) + Math.pow(yBasketColl - ball.yCenter, 2)) < (ball.RADIUS + basketCollRadius) * 4/6) {
                ball.xCenter = xBasketColl - (float) Math.cos(basketColAngle) * (ball.RADIUS + basketCollRadius);
                ball.yCenter = yBasketColl - (float) Math.sin(basketColAngle) * (ball.RADIUS + basketCollRadius);
            } else if (Math.sqrt(Math.pow(xBasketColl - ball.xCenter, 2) + Math.pow(yBasketColl - ball.yCenter, 2)) < (ball.RADIUS + basketCollRadius) * 4/6) {

                ball.xCenter = xBasketColl + (float) Math.cos(basketColAngle) * (ball.RADIUS + basketCollRadius);
                ball.yCenter = yBasketColl + (float) Math.sin(basketColAngle) * (ball.RADIUS + basketCollRadius);
            }

            System.out.println("ball x: " + ball.xCenter);
            System.out.println("ball y: " + ball.yCenter);
            System.out.println("ball velx: " + ball.xVelocity);
            System.out.println("ball vely: " + ball.yVelocity);

            ball.xVelocity = ((ball.xVelocity * (float) Math.cos(collisionAngle) + ball.yVelocity * (float) Math.sin(collisionAngle)) * velocity) / (float) Math.sqrt(Math.pow(ball.xVelocity, 2) + Math.pow(ball.yVelocity, 2)) * ball.FACTOR_BOUNCEBACK;
            ball.yVelocity = ((ball.yVelocity * (float) Math.cos(collisionAngle) + ball.xVelocity * (float) Math.sin(collisionAngle)) * velocity) / (float) Math.sqrt(Math.pow(ball.xVelocity, 2) + Math.pow(ball.yVelocity, 2)) * ball.FACTOR_BOUNCEBACK;
            System.out.println("ball velx: " + ball.xVelocity);
            System.out.println("ball vely: " + ball.yVelocity);

            /*if(ball.xCenter < xBasketColl) {
                //ball.xVelocity -= 2;
                ball.xCenter -= 1;
            }
            else {
                //ball.xVelocity += 2;
                ball.xCenter += 1;
            }
            if(ball.yCenter < yBasketColl) {
                //ball.yVelocity -= 2;
                ball.yCenter--;
            }
            else {
                //ball.yVelocity += 2;
                ball.yCenter++;
            }*/

        }
        lastBasketColAngle = basketColAngle;
        lastXCenter = ball.xCenter;
//        lastXBasketColVector = (float) Math.cos(lastBasketColAngle) * -1;
//        lastYBasketColVector = (float) Math.sin(lastBasketColAngle) * -1;
//        if(ball.xCenter > xBasketColl){
//            lastXBasketColVector *= -1;
//            lastYBasketColVector *= -1;
//        }

    }

    private void checkCollisionBBoard() {
        if(ball.xCenter + ball.RADIUS > hoop.xBackboardTop && ball.yCenter - ball.RADIUS < hoop.yBackboardBot)
        {
            ball.xCenter = hoop.xBackboardTop - ball.RADIUS;
            ball.xVelocity = -ball.xVelocity * ball.FACTOR_BOUNCEBACK;
        }
    }

    private void checkCollisionPole() {
        if(ball.xCenter + ball.RADIUS > hoop.xPoleCollTop && ball.yCenter - ball.RADIUS > hoop.yPoleCollTop)
        {
            ball.xCenter = hoop.xPoleCollTop - ball.RADIUS;
            ball.xVelocity = -ball.xVelocity * ball.FACTOR_BOUNCEBACK;
        }
    }

    private void checkCollisionNet() {
        if(ball.xCenter - ball.RADIUS < hoop.xBasketCollFront && scored && ball.yCenter > hoop.yBasketCollFront && ball.yCenter < hoop.yNetCollBot && ball.xCenter > hoop.xBasketCollFront) {
            ball.xVelocity += Math.pow(hoop.xBasketCollFront - ball.xCenter - ball.RADIUS, 2) / 50;
            ball.yVelocity += 2;
            if(ball.xVelocity > 10)
                ball.xVelocity = 10;
        }
        else if(ball.xCenter + ball.RADIUS > hoop.xBasketCollBack && scored && ball.yCenter > hoop.yBasketCollFront && ball.yCenter < hoop.yNetCollBot && ball.xCenter < hoop.xBasketCollBack) {
            ball.xVelocity -= Math.pow(ball.xCenter + ball.RADIUS - hoop.xBasketCollFront, 2) / 50;
            ball.yVelocity += 2;
            if(ball.xVelocity < -10)
                ball.xVelocity = -10;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        Paint p = new Paint();
        p.setColor(Color.BLUE);
        if(canvas != null){
            super.draw(canvas);
            canvas.drawColor(Color.WHITE);
            ball.myDraw(canvas);
            hoop.myDraw(canvas);

            if(drawPoint) {
                for (int i = 0; i < 25; i++) {
                    yV += ball.gravity * 0.5f;
                    xP += (int) (0.5f * (xV));
                    yP += (int) (0.5f * (yV + ball.gravity * 0.5f));

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


                    if(yP > ball.heightScreen - ball.RADIUS)
                    {
                        yP = ball.heightScreen - ball.RADIUS;
                        yV = -yV * 0.75f;
                        if(xV < 0){
                            xV+=1;
                        }else if(xV > 0){
                            xV-=1;
                        }
                    }

                    canvas.drawCircle(xP, yP, 10, p);
                }
                xP = ball.xCenter;yP = ball.yCenter;
            }
        }
    }
}
