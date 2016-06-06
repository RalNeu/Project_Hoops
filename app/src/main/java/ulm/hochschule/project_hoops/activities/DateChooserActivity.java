package ulm.hochschule.project_hoops.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.NumberPicker;

import ulm.hochschule.project_hoops.R;

public class DateChooserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_chooser);

        NumberPicker n = (NumberPicker) findViewById(R.id.np_Day);

    }
}
