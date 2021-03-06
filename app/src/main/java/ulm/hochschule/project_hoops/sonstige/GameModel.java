package ulm.hochschule.project_hoops.sonstige;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.widget.TextView;

import ulm.hochschule.project_hoops.activities.GameEndActivity;
import ulm.hochschule.project_hoops.objects.Ball;
import ulm.hochschule.project_hoops.objects.Hoop;

/**
 * Created by Johann on 09.11.2016.
 */

//Spiellogik wird hier implementiert
public class GameModel {
    public Ball ball;
    public Hoop hoop;

    public int attempt = 1;

    private boolean inHoopZone = false;
    public int score = 0;
    private boolean scored = false;

    private float lastBasketColAngle = 90;
    private float width;
    private float height;
    public float distance = 1;

    private Context context;
    private Intent intent;

    public GameModel(Context context, float width, float height){
        this.width = width;
        this.height = height;
        this.context = context;
        ball = new Ball(width, height, context);
        hoop = new Hoop(width, height, context);
        this.intent = new Intent(context, GameEndActivity.class);
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
    }


    //setzt den Ball auf Anfangsposition zurück
    //wenn gepunktet wurde wird die Distanz erhöht
    //wenn man über den 10. Versuch hinaus ist, wird der Endscreen geöffnet
    public void restartGame() {
        if(scored)
            increaseDistance();
        else
            decreaseDistance();

        attempt++;


        if(attempt > 10) {

            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Bundle b = new Bundle();
            b.putInt("key", score); //score wird an GameEndActivity weitergegeben
            intent.putExtras(b);
            context.startActivity(intent);
        } else {

            ball.ok = false;
            ball.resetBall();
            scored = false;
            ball.alphaValue = 255;
        }

    }

    //erhöht Distanz bis maximal 0.3 Faktor, erstellt Objekte neu
    private void increaseDistance() {
        if(distance > 0.3)
            distance *= 0.9;
        //für Testzwecke
        //distance = 0.81f;
        ball = new Ball(width, height, context, distance);
        hoop = new Hoop(width, height, context, distance);
    }

    //verringert Distanz um gleiche Menge wie die Erhöhung, erstellt Objekte neu
    private void decreaseDistance() {
        distance += distance/9;
        if(distance > 1)
            distance = 1;
        //für Textzwecke
        //distance = 0.81f;
        ball = new Ball(width, height, context, distance);
        hoop = new Hoop(width, height, context, distance);
    }

    //wenn Ball in der grünen Zone ist, wird beim nächsten mal geprüft ob er unter der grünen Zone ist => Scored = true
    private void checkGoal() {
        if(inHoopZone && ball.yCenter > hoop.yBasketCollFront + 50 && !scored) {
            score += (1/(distance/20));
            scored = true;
            inHoopZone = false;
        }
        if(ball.xCenter > hoop.xBasketCollFront && ball.xCenter < hoop.xBasketCollBack && ball.yCenter >= hoop.yBasketCollFront - 50 && ball.yCenter <= hoop.yBasketCollFront + 50) {
            inHoopZone = true;
        }
        else {
            inHoopZone = false;
        }

    }

    //falls scored = true wird Ball langsam durchsichtiger
    private void fadeOut() {
        if(scored) {
            ball.alphaValue -= 3;
            if (ball.alphaValue <= 0) {
                restartGame();
            }
        }
    }

    //checkt die Kollision mit dem Korb, setzt die Ballgeschwindigkeit und Ballposition bei Kollision
    private void checkCollisionBasket(float xBasketColl, float yBasketColl, float basketCollRadius) {

        float xBasketColVector;
        float yBasketColVector;
        float basketColAngle = 90;
        float collisionAngle = 0;
        float velocity = (float) Math.sqrt(Math.pow(ball.xVelocity, 2) + Math.pow(ball.yVelocity, 2));

        if(ball.xCenter != xBasketColl) {
            basketColAngle = (float) Math.atan((yBasketColl - ball.yCenter) / (xBasketColl - ball.xCenter));
            if(lastBasketColAngle == 90)
                lastBasketColAngle = basketColAngle;
            xBasketColVector = (float) Math.sin(basketColAngle) * -1;
            yBasketColVector = (float) Math.cos(basketColAngle) * -1;
            if(ball.xCenter > xBasketColl){
                yBasketColVector *= -1;
            }

            collisionAngle = (float) Math.acos((ball.xVelocity * xBasketColVector + ball.yVelocity * yBasketColVector) / (float) (velocity * Math.sqrt(Math.pow(xBasketColVector, 2) + Math.pow(yBasketColVector, 2))));
        }

        if(Math.sqrt(Math.pow(xBasketColl - ball.xCenter, 2) + Math.pow(yBasketColl - ball.yCenter, 2)) < ball.RADIUS + basketCollRadius) {
            if(velocity > 20 || velocity < -20) {
                ball.xVelocity = ((ball.xVelocity * (float) Math.cos(collisionAngle) + ball.yVelocity * (float) Math.sin(collisionAngle)) * velocity) / (float) velocity * ball.FACTOR_BOUNCEBACK;
                ball.yVelocity = ((ball.yVelocity * (float) Math.cos(collisionAngle) + ball.xVelocity * (float) Math.sin(collisionAngle)) * velocity) / (float) velocity * ball.FACTOR_BOUNCEBACK;
            }

            if(ball.xCenter < xBasketColl && Math.sqrt(Math.pow(xBasketColl - ball.xCenter, 2) + Math.pow(yBasketColl - ball.yCenter, 2)) < (ball.RADIUS + basketCollRadius)) {
                if(velocity > 20 || velocity < -20)
                    ball.xCenter = xBasketColl - (float) Math.cos(basketColAngle) * (ball.RADIUS + basketCollRadius);
                ball.yCenter = yBasketColl - (float) Math.sin(basketColAngle) * (ball.RADIUS + basketCollRadius);
            } else if (Math.sqrt(Math.pow(xBasketColl - ball.xCenter, 2) + Math.pow(yBasketColl - ball.yCenter, 2)) < (ball.RADIUS + basketCollRadius)) {
                if(velocity > 20 || velocity < -20)
                    ball.xCenter = xBasketColl + (float) Math.cos(basketColAngle) * (ball.RADIUS + basketCollRadius);
                ball.yCenter = yBasketColl + (float) Math.sin(basketColAngle) * (ball.RADIUS + basketCollRadius);
            }

            if(velocity < 20 && velocity > -20) {
                if(ball.xCenter < xBasketColl)
                    ball.xVelocity -= 0.2f;
                else
                    ball.xVelocity += 0.2f;
            }

        }
        lastBasketColAngle = basketColAngle;
    }

    //checkt Kollision mit Backboard, setzt Ballgeschwindigkeit
    private void checkCollisionBBoard() {
        if(ball.xCenter + ball.RADIUS > hoop.xBackboardTop && ball.yCenter - ball.RADIUS < hoop.yBackboardBot)
        {
            ball.xCenter = hoop.xBackboardTop - ball.RADIUS;
            if(ball.xVelocity > 0)
                ball.xVelocity = -ball.xVelocity * ball.FACTOR_BOUNCEBACK;
        }
    }

    //checkt Kollision mit Stange, setzt Ballgeschwindigkeit
    private void checkCollisionPole() {
        if(ball.xCenter + ball.RADIUS > hoop.xPoleCollTop && ball.yCenter - ball.RADIUS > hoop.yPoleCollTop)
        {
            ball.xCenter = hoop.xPoleCollTop - ball.RADIUS;
            ball.xVelocity = -ball.xVelocity * ball.FACTOR_BOUNCEBACK;
        }
    }

    //checkt Kollision mit Netz, versucht Ball realistisch vom Netzrand wegzubewegen. Ändert Ballgeschwindigkeit
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
}
