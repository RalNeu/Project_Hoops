package ulm.hochschule.project_hoops;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class EditProfilActivity extends AppCompatActivity {

    Button btn_ChooseDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        btn_ChooseDate = (Button) findViewById(R.id.btn_ChooseDate);
        btn_ChooseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), DateChooserActivity.class);
                startActivity(i);
            }
        });
    }
}
