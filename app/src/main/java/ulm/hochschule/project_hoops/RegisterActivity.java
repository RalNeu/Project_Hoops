package ulm.hochschule.project_hoops;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText etFirstname = (EditText) findViewById(R.id.etFirstname);
        final EditText etSecondname = (EditText) findViewById(R.id.etSecondName);
        final EditText etEmail = (EditText) findViewById(R.id.etEmail);
        final EditText etPasswort = (EditText) findViewById(R.id.etPasswort);
        final EditText etPassowortsecond = (EditText) findViewById(R.id.etPasswortAgain);

        final Button btAccept = (Button) findViewById(R.id.btAccept);

        btAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent acceptIntent = new Intent(RegisterActivity.this, MainActivity.class);
                RegisterActivity.this.startActivity(acceptIntent);

            }
        });
    }
}
