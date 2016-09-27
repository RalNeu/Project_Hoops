package ulm.hochschule.project_hoops.activities;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.VelocityTracker;
import android.view.View;

import ulm.hochschule.project_hoops.R;

public class GameActivity extends Activity{
    // animated view
    private ShapeView mShapeView;

    // screen size
    private int widthScreen;
    private int heightScreen;

    // motion parameters
    private final float FACTOR_FRICTION = 0.5f; // imaginary friction on the screen
    private final float GRAVITY = 9.8f; // acceleration of gravity
    private float mAx; // acceleration along x axis
    private float mAy; // acceleration along y axis
    private final float deltaT = 0.5f; // imaginary time interval between each acceleration updates

    private boolean ok = true;


    private BitmapDrawable ball;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // set the screen always portait
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        widthScreen = displaymetrics.widthPixels;
        heightScreen = displaymetrics.heightPixels;

        // initializing the view that renders the ball
        mShapeView = new ShapeView(this);
        mShapeView.setCenter((int)(widthScreen * 0.5), (int)(heightScreen * 0.5));
        setContentView(mShapeView);
    }

    // the view that renders the ball
    private class ShapeView extends SurfaceView implements SurfaceHolder.Callback{

        private int ballRadius = 88;
        private int rotatedBallHalfWidth = 96;
        private int rotatedBallHalfHeight = 96;
        private final float FACTOR_BOUNCEBACK = 0.75f;

        private int xCenter;
        private int yCenter;
        private ShapeThread thread;

        private float xVelocity;
        private float yVelocity;
        private float x, y;

        float angle = 0;

        Paint alpha = new Paint();

        private float xBasketCollFront = widthScreen - 500;
        private float xBasketColisBack = widthScreen;
        private float basketCollRadius = 25;
        private float yBasketCollTop = heightScreen / 2;
        private float yBasketColisBot = (heightScreen / 2) + 50;
        private float poleColisFront = widthScreen - 50;

        private VelocityTracker mVelocityTracker = null;

        public ShapeView(Context context) {
            super(context);

            getHolder().addCallback(this);
            thread = new ShapeThread(getHolder(), this);
            setFocusable(true);
            ball = (BitmapDrawable) context.getResources().getDrawable(R.drawable.basketball_small);
            mAx = 0;
            mAy = 0;
            setOnTouchListener(new OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if(!ok) {
                        int index = event.getActionIndex();
                        int pointerId = event.getPointerId(index);
                        switch (event.getAction()) {
                            case MotionEvent.ACTION_UP:
                                ok = true;
                                xVelocity = (event.getX() - x) / 5;
                                yVelocity = (event.getY() - y) / 5;

                                // xVelocity = VelocityTrackerCompat.getXVelocity(mVelocityTracker,
                                //        pointerId)/50;
                                // yVelocity = VelocityTrackerCompat.getYVelocity(mVelocityTracker,
                                //         pointerId)/50;

                                //mAy = GRAVITY;
                                //mAx = Math.signum(mAx) * Math.abs(mAx) * (1 - FACTOR_FRICTION * Math.abs(0) / GRAVITY);
                                //mAy = Math.signum(mAy) * Math.abs(mAy) * (1 - FACTOR_FRICTION * Math.abs(0) / GRAVITY);
                                break;
                            case MotionEvent.ACTION_MOVE:
                                //xCenter = (int)event.getX()-rotatedBallHalfWidth;
                                //yCenter = (int)event.getY()-rotatedBallHalfHeight;

                                mVelocityTracker.addMovement(event);
                                mVelocityTracker.computeCurrentVelocity(1000);
                                break;
                            case MotionEvent.ACTION_DOWN:
                                ok = false;
                                x = event.getX();
                                y = event.getY();
                                if (mVelocityTracker == null) {
                                    // Retrieve a new VelocityTracker object to watch the velocity of a motion.
                                    mVelocityTracker = VelocityTracker.obtain();
                                } else {
                                    // Reset the velocity tracker back to its initial state.
                                    mVelocityTracker.clear();
                                }
                                // Add a user's movement to the tracker.
                                mVelocityTracker.addMovement(event);
                                break;
                            case MotionEvent.ACTION_CANCEL:
                                // Return a VelocityTracker object back to be re-used by others.
                                mVelocityTracker.recycle();
                                break;
                        }
                    }
                    return true;
                }
            });
        }

        private float pX = xCenter, pY = yCenter;
        private float pVy = 0;
        private float pVx = 0;

        public void update(float mCX, float mCy) {
            pVy += GRAVITY * deltaT;

            pX += (int)(deltaT * (xVelocity));
            pY += (int)(deltaT * (yVelocity + 0.5f*GRAVITY* deltaT));

            if(pX < rotatedBallHalfWidth)
            {
                pX = rotatedBallHalfWidth;
                pVx = -pVx * FACTOR_BOUNCEBACK;
            }


            if(pX > widthScreen - rotatedBallHalfWidth)
            {
                pX = widthScreen - rotatedBallHalfWidth;
                pVx = -pVx * FACTOR_BOUNCEBACK;
            }

            if(pY > heightScreen - rotatedBallHalfHeight)
            {
                pY = heightScreen - rotatedBallHalfHeight;
                pVy = -pVy * FACTOR_BOUNCEBACK;
            }
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            //float mAz = event.values[2];

            // taking into account the frictions
            //mAx = Math.signum(mAx) * Math.abs(mAx) * (1 - FACTOR_FRICTION * Math.abs(mAz) / GRAVITY);
            //mAy = Math.signum(mAy) * Math.abs(mAy) * (1 - FACTOR_FRICTION * Math.abs(mAz) / GRAVITY);

            switch(event.getAction()){
                case MotionEvent.ACTION_UP:
                    break;
                case MotionEvent.ACTION_MOVE:
                    break;
            }
            return super.onTouchEvent(event);
        }

        // set the position of the ball
        public boolean setCenter(int x, int y)
        {
            xCenter = x;
            yCenter = y;
            return true;
        }

        // calculate and update the ball's position
        public boolean updateOvalCenter()
        {
            //xVelocity -= 5f * deltaT;
            yVelocity += GRAVITY * deltaT;

            xCenter += (int)(deltaT * (xVelocity));
            yCenter += (int)(deltaT * (yVelocity + GRAVITY * deltaT));

            if(xCenter < ballRadius)
            {
                xCenter = ballRadius;
                xVelocity = -xVelocity * FACTOR_BOUNCEBACK;
            }


            if(xCenter > widthScreen - ballRadius)
            {
                xCenter = widthScreen - ballRadius;
                xVelocity = -xVelocity * FACTOR_BOUNCEBACK;
            }


            if(yCenter > heightScreen - 2 * ballRadius)
            {
                angle += xVelocity/8;
                yCenter = heightScreen - 2 * ballRadius;
                yVelocity = -yVelocity * FACTOR_BOUNCEBACK;
                if(xVelocity < 0){
                    xVelocity+=1;
                }else if(xVelocity > 0){
                    xVelocity-=1;
                }
                if(xVelocity < 25 && xVelocity > 0){
                    alpha.setAlpha((int)xVelocity*4);
                }else if(xVelocity > -25 && xVelocity < 0){
                    alpha.setAlpha((int)xVelocity*4*(-1));
                }
            }

            if(xVelocity == 0 && yVelocity > -2.5f){
                ok = false;
                setCenter(500, 500);
                alpha.setAlpha(255);
            }else {
                angle += xVelocity / 8;
            }

            if(xVelocity < 2 && xVelocity > -2){
                xVelocity = 0;
            }

            //Kollision
            checkCollision();

            return true;
        }

        private void checkCollision() {

            float xBasketColVector;
            float yBasketColVector;
            float basketColAngle;
            float collisionAngle = 0;
            float velocity = (float) Math.sqrt(Math.pow(xVelocity, 2) + Math.pow(yVelocity, 2));

            if(xBasketCollFront + basketCollRadius - xCenter != 0) {
                basketColAngle = (float) Math.atan(((yBasketCollTop + basketCollRadius) - yCenter) / ((xBasketCollFront + basketCollRadius) - xCenter));
                xBasketColVector = (float) Math.cos(basketColAngle) * -1;
                yBasketColVector = (float) Math.sin(basketColAngle) * -1;
                if(xCenter > xBasketCollFront + basketCollRadius){
                    xBasketColVector *= -1;
                    yBasketColVector *= -1;
                }

                collisionAngle = (float) Math.acos((xVelocity * xBasketColVector + yVelocity * yBasketColVector) / (float) (Math.sqrt(Math.pow(xVelocity, 2) + Math.pow(yVelocity, 2)) * Math.sqrt(Math.pow(xBasketColVector, 2) + Math.pow(yBasketColVector, 2))));
            }

            if(Math.sqrt(Math.pow(xBasketCollFront + basketCollRadius - xCenter, 2) + Math.pow(yBasketCollTop + basketCollRadius - yCenter, 2)) < ballRadius + basketCollRadius) {
                xVelocity = ((xVelocity * (float) Math.cos(collisionAngle) + yVelocity * (float) Math.sin(collisionAngle)) * velocity) / (float) Math.sqrt(Math.pow(xVelocity, 2) + Math.pow(yVelocity, 2));
                yVelocity = ((yVelocity * (float) Math.cos(collisionAngle) + xVelocity * (float) Math.sin(collisionAngle)) * velocity) / (float) Math.sqrt(Math.pow(xVelocity, 2) + Math.pow(yVelocity, 2));
            }


        }

        // update the canvas
        protected void onDraww(Canvas canvas)
        {
            if(canvas != null){
                canvas.drawColor(getResources().getColor(android.R.color.white));
                Bitmap rotatedBitmap = RotateBitmap(ball.getBitmap(), angle);
                rotatedBallHalfWidth = rotatedBitmap.getWidth()/2;
                rotatedBallHalfHeight = rotatedBitmap.getHeight()/2;
                canvas.drawBitmap(rotatedBitmap, xCenter - rotatedBallHalfWidth, yCenter - rotatedBallHalfHeight, alpha);
                Paint p = new Paint();
                p.setColor(getResources().getColor(android.R.color.holo_red_dark));
                p.setAlpha(100);
                canvas.drawRect(widthScreen - 475, heightScreen /2, widthScreen, heightScreen /2 + 50, p);

                Paint p3 = new Paint();
                p3.setColor(getResources().getColor(android.R.color.holo_red_dark));
                canvas.drawCircle(widthScreen - 475, heightScreen / 2 + 25, 25, p3);

                Paint p2 = new Paint();
                p2.setColor(getResources().getColor(android.R.color.darker_gray));
                canvas.drawRect(widthScreen - 50, heightScreen /2 - 100, widthScreen, heightScreen, p2);
            }
        }

        public Bitmap RotateBitmap(Bitmap source, float angle)
        {
            Matrix matrix = new Matrix();
            matrix.postRotate(angle);

            return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width,
                                   int height) {
        }

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            if(!thread.mRun){
                thread.setRunning(true);
                thread.start();
            }
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            boolean retry = true;
            thread.setRunning(false);
            while(retry)
            {
                try{
                    thread.join();
                    retry = false;
                } catch (InterruptedException e){

                }
            }
        }
    }

    class ShapeThread extends Thread {
        private SurfaceHolder mSurfaceHolder;
        private ShapeView mShapeView;
        private boolean mRun = false;

        public ShapeThread(SurfaceHolder surfaceHolder, ShapeView shapeView) {
            mSurfaceHolder = surfaceHolder;
            mShapeView = shapeView;
        }

        public void setRunning(boolean run) {
            mRun = run;
        }

        public SurfaceHolder getSurfaceHolder() {
            return mSurfaceHolder;
        }

        @Override
        public void run() {
            Canvas c;
            while (mRun) {
                if(ok) {
                    mShapeView.updateOvalCenter();
                }
                c = null;
                try {
                    c = mSurfaceHolder.lockCanvas(null);
                    synchronized (mSurfaceHolder) {
                        mShapeView.onDraww(c);
                    }
                } finally {
                    if (c != null) {
                        mSurfaceHolder.unlockCanvasAndPost(c);
                    }
                }
            }
        }
    }
}
