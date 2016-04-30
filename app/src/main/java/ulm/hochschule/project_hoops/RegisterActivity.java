package ulm.hochschule.project_hoops;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText et_Firstname = (EditText) findViewById(R.id.et_Firstname);
        final EditText et_Secondname = (EditText) findViewById(R.id.et_SecondName);
        final EditText et_Email = (EditText) findViewById(R.id.et_Email);
        final EditText et_Passwort = (EditText) findViewById(R.id.et_Passwort);
        final EditText et_Passowortsecond = (EditText) findViewById(R.id.et_PasswortAgain);

        final Button bt_Accept = (Button) findViewById(R.id.bt_Accept);

        bt_Accept.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
         /*       Intent acceptIntent = new Intent(RegisterActivity.this, MainActivity.class);
                RegisterActivity.this.startActivity(acceptIntent);*/
                SQLiteDatabase mydatabase = openOrCreateDatabase("hoops", MODE_PRIVATE, null);

                System.out.println("INSERT INTO users(Username,Passwort,FirstName,LastName,Coins,ChatBan,EmailAdress,CreateDate,LastLogin)" +
                        " VALUES (TestUser," + et_Passwort.getText() + "," + et_Firstname.getText() + "," + et_Secondname.getText()+ "," + "0," + "0," + et_Email.getText()+ "," + "NOW(),NOW());");


            }
        });
    }
}
