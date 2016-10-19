package ulm.hochschule.project_hoops.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import ulm.hochschule.project_hoops.R;
import ulm.hochschule.project_hoops.views.GamePanel;

public class GameActivity extends Activity implements View.OnClickListener {
    // screen size
    private int widthScreen;
    private int heightScreen;
    private GamePanel gamePanel;
    private int score = 0;
    private int attempt = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // set the screen always portait
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        widthScreen = displaymetrics.widthPixels;
        heightScreen = displaymetrics.heightPixels - getStatusBarHeight();


        final TextView scoreText = new TextView(this);
        scoreText.setText("Score: " + score);
        final TextView attemptText = new TextView(this);
        attemptText.setText("    attempt: " + attempt + "/10");


        FrameLayout game = new FrameLayout(this);
        this.gamePanel = new GamePanel(getApplicationContext(), widthScreen, heightScreen, scoreText, attemptText);
        LinearLayout gameWidgets = new LinearLayout (this);


        Button endGameButton = new Button(this);
        endGameButton.setWidth(300);
        endGameButton.setText("Restart");


        gameWidgets.addView(scoreText);
        gameWidgets.addView(attemptText);
        gameWidgets.addView(endGameButton);

        game.addView(gamePanel);
        game.addView(gameWidgets);

        setContentView(game);
        endGameButton.setOnClickListener(this);


    }

    public void onClick(View v) {
        //Intent intent = new Intent(this, MainActivity.class);
        //startActivity(intent);
        gamePanel.restartGame();
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
