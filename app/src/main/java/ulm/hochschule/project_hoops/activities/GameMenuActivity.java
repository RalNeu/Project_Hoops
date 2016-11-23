package ulm.hochschule.project_hoops.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import ulm.hochschule.project_hoops.R;

public class GameMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_menu);

        final Button btnPlay = (Button) findViewById(R.id.btnPlay);
        final Button btnSettings = (Button) findViewById(R.id.btnSettings);
        Button btnExit = (Button) findViewById(R.id.btnExit);

        final Bundle b = new Bundle();

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnPlay.setText("10 Balls");
                btnPlay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        b.putString("key", "10Balls");
                        Intent intent = new Intent(GameMenuActivity.this, GameActivity.class);
                        intent.putExtras(b);
                        startActivity(intent);
                    }
                });
                btnSettings.setText("Time Trial");
                btnSettings.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        b.putString("key", "TimeTrial");
                        Intent intent = new Intent(GameMenuActivity.this, GameActivity.class);
                        intent.putExtras(b);
                        startActivity(intent);

                    }
                });

            }
        });

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }



}
