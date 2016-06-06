package ulm.hochschule.project_hoops;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Johann on 03.06.2016.
 */
public class TippspielActivity extends AppCompatActivity {

    private ImageView team1;
    private ImageView team2;
    private SeekBar seekbar;
    private TextView prozent1;
    private TextView prozent2;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_tippspiel);
        init();
        seekbar.setProgress(50);
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            int progress = 0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                progress = progresValue;
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }

    private void init(){
        team1 = (ImageView) findViewById(R.id.iv_Team1);
        team2 = (ImageView) findViewById(R.id.iv_Team2);
        seekbar = (SeekBar) findViewById(R.id.sb_SeekBar);
        prozent1 = (TextView) findViewById(R.id.tv_Prozent1);
        prozent2 = (TextView) findViewById(R.id.tv_Prozent2);
    }
}
