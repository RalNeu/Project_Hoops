package ulm.hochschule.project_hoops.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import ulm.hochschule.project_hoops.R;

public class GameActivity extends Activity {


    private ViewGroup mainLayout;
    private ImageView image;
    private Point start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Turn title off
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_basketball_game);
        image = (ImageView) findViewById(R.id.basketball);
        start = new Point((int)image.getTranslationX(), (int)image.getTranslationY());
        mainLayout = (RelativeLayout) findViewById(R.id.layout);
        image.setOnTouchListener(onTouchListener());
    }

    private View.OnTouchListener onTouchListener() {
        return new View.OnTouchListener() {

            float x,y = 0;

            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getAction() & MotionEvent.ACTION_MASK) {

                    case MotionEvent.ACTION_DOWN:
                        x = event.getRawX();
                        y = event.getRawY();
                        break;

                    case MotionEvent.ACTION_UP:
                        if(!running){
                            startThrowAnimation(x, y, event.getRawX(), event.getRawY());
                        }
                        System.out.println(running);
                        break;

                    case MotionEvent.ACTION_MOVE:
                        break;
                }
                mainLayout.invalidate();
                return true;
            }
        };
    }

    public void startThrowAnimation(final float fromX, float fromY, float toX, float toY){
        running = true;
        final float max = image.getTranslationY()-2000;
        final float min = image.getTranslationY();
        final float landPoint = getLandPoint(fromX, fromY, toX, toY);


        AnimationSet animationSet = new AnimationSet(true);

        //Scale
        ScaleAnimation scaleAnimation = new ScaleAnimation(1f, 0.8f, 1, 0.8f);
        scaleAnimation.setFillAfter(true);
        scaleAnimation.setStartOffset(200);
        scaleAnimation.setDuration(500);

        //throw Up
        TranslateAnimation throwUp = new TranslateAnimation(image.getTranslationX(), landPoint, min, max);
        throwUp.setDuration(700);
        throwUp.setFillAfter(true);


        //add all Animation to one
        animationSet.addAnimation(scaleAnimation);
        animationSet.addAnimation(throwUp);
        animationSet.setFillEnabled(true);
        animationSet.setFillAfter(true);
        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                fallAnimation(landPoint);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        prepare(max);
        image.startAnimation(animationSet);
    }

    private boolean running = false;

    private Thread t;

    public void prepare(final float y){
        t = new Thread(new Runnable() {
            float dy = y;
            @Override
            public void run() {
                while(dy < 3000){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(dy > 200){
                                image.setTranslationX(image.getTranslationX()+1);
                            }
                            dy += 4;
                            image.setTranslationY(dy);
                        }
                    });
                    try{
                        Thread.sleep(1);
                    }catch(Exception e){}
                }
                running = false;
            }
        });
    }

    private void fallAnimation(float x){
        t.start();
    }

    private float getLandPoint(float x, float y, float x2, float y2){
        float m = (y-y2)/(x-x2);
        float b = y-m*x;
        float max = image.getTranslationY()-1800;
        float landPoint = (max-b)/m -x;
        return landPoint;
    }
}
