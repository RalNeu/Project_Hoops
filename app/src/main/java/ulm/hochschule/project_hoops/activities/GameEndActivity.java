package ulm.hochschule.project_hoops.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import ulm.hochschule.project_hoops.R;

public class GameEndActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_end);

        TextView scoreText = (TextView) findViewById(R.id.scoreText);
        Button btnTryAgain = (Button) findViewById(R.id.btnTryAgain);
        Button btnMode = (Button) findViewById(R.id.btnMode);


        Bundle b = getIntent().getExtras();
        int score = -1; // or other values
        if(b != null) {
            score = b.getInt("key");
            scoreText.setText("Score: " + score);
        }

        btnTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GameEndActivity.this, GameActivity.class));
            }
        });

        btnMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GameEndActivity.this, GameMenuActivity.class));
            }
        });

    }
}
