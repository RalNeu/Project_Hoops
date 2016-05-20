package ulm.hochschule.project_hoops;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.NumberPicker;

public class EditProfilActivity extends AppCompatActivity {

    private EditText et_Forename, et_Surname, et_AboutMe;
    private NumberPicker np_Day, np_Month, np_Year;
    private CheckBox cb_AllowForename, cb_AllowSurname, cb_AllowBirthdate, cb_AllowAboutMe;
    private Button btn_Save;
    private UserProfile user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        instantiateUiObjects();
        mapUser();
    }

    private void instantiateUiObjects() {
        et_Forename = (EditText) findViewById(R.id.et_Forename);
        et_Surname = (EditText) findViewById(R.id.et_Surname);
        et_AboutMe = (EditText) findViewById(R.id.et_AboutMe);

        np_Day = (NumberPicker) findViewById(R.id.np_DayChooser);
        np_Month = (NumberPicker) findViewById(R.id.np_MonthChooser);
        np_Year = (NumberPicker) findViewById(R.id.np_YearChooser);

        cb_AllowForename = (CheckBox) findViewById(R.id.cb_AllowForename);
        cb_AllowSurname = (CheckBox) findViewById(R.id.cb_AllowSurname);
        cb_AllowBirthdate = (CheckBox) findViewById(R.id.cb_AllowBirthdate);
        cb_AllowAboutMe = (CheckBox) findViewById(R.id.cb_AllowAboutMe);

        btn_Save = (Button) findViewById(R.id.btn_Save);
    }

    private void mapUser() {
        if(UserProfile.getUserFound()) {
            user = UserProfile.getInstance("");


        }
    }
}
