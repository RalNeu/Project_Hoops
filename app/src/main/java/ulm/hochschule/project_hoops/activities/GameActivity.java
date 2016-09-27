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
import ulm.hochschule.project_hoops.views.GamePanel;

public class GameActivity extends Activity{
    // screen size
    private int widthScreen;
    private int heightScreen;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // set the screen always portait
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        widthScreen = displaymetrics.widthPixels;
        heightScreen = displaymetrics.heightPixels - getStatusBarHeight();
        setContentView(new GamePanel(getApplicationContext(), widthScreen, heightScreen));
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
