package ulm.hochschule.project_hoops.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Ralph on 29.09.2016.
 */
public class ChargingButton extends Button {

    private int distance = 0;
    int alpha = 20;
    private Runnable task;
    private Timer charger;

    public ChargingButton(Context context) {
        super(context);
        init();
    }

    public ChargingButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();

    }

    public ChargingButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setTask(Runnable r) {
        task = r;
    }

    private void init() {
        charger = new Timer();

        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {


                System.out.println(event.getAction());

                if(event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    System.out.println("test");
                }
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    charger = new Timer();
                    charger.schedule(new TimerTask() {
                        @Override
                        public void run() {

                          if(charge()) {
                              if(task != null)
                                post(task);
                              charger.cancel();
                              distance = 0;
                              post(new Runnable() {
                                  @Override
                                  public void run() {
                                      ChargingButton.this.invalidate();
                                  }
                              });
                          }


                        }
                    }, 17,17);
                } else if (event.getAction() == 3 || event.getAction() == 1) {//MotionEvent.ACTION_UP) {

                    charger.cancel();
                    distance = 0;
                    alpha = 20;
                    post(new Runnable() {
                        @Override
                        public void run() {
                            ChargingButton.this.invalidate();
                        }
                    });
                }
                return true;
            }
        });
    }

    private void reqLay() {
        requestLayout();
    }
    private boolean charge() {
        if(distance <= getWidth()/2 ) {
            distance += 1;
            if (distance > 16) {
                distance++;
            }
            if (distance > 32) {
                distance++;
            }
            if (distance > 40) {
                distance++;
            }
            alpha += 2;

            post(new Runnable() {
                @Override
                public void run() {
                    ChargingButton.this.invalidate();
                }
            });
            return false;
        }

        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setAlpha(alpha);
        int start = (getWidth()/2) - distance;
        start = start < 12 ? 12 : start;
        int end = (getWidth()/2) + distance;
        end = end > getWidth() - 12 ? getWidth() - 12 : end;
        canvas.drawRect(new Rect(start, 17, end, getHeight() - 17), paint);
        super.onDraw(canvas);
    }

}
