package ulm.hochschule.project_hoops.activities;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.DisplayMetrics;

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
        heightScreen = displaymetrics.heightPixels;
        setContentView(new GamePanel(getApplicationContext(), widthScreen, heightScreen));
    }
}
