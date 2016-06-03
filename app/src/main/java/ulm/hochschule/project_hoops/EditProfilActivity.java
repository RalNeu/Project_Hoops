package ulm.hochschule.project_hoops;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.NumberPicker;

import java.sql.Date;
import java.util.Calendar;

public class EditProfilActivity extends AppCompatActivity {

    private EditText et_Forename, et_Surname, et_AboutMe, et_Code;
    private NumberPicker np_Day, np_Month, np_Year;
    private CheckBox cb_AllowForename, cb_AllowSurname, cb_AllowBirthdate, cb_AllowAboutMe;
    private Button btn_Save;
    private Button btn_reqCode;
    private Button btn_Send;
    private UserProfile user;
    private SqlManager sm;
    private MailVerifier mailVerif;

    private String oldForename, oldSurname, oldAboutMe;
    private Date oldGebDat;


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
        et_Code = (EditText) findViewById(R.id.et_Code);
        final AppCompatActivity context = new AppCompatActivity();

        np_Day = (NumberPicker) findViewById(R.id.np_DayChooser);
        np_Day.setMinValue(1);
        np_Day.setMaxValue(31);
        np_Month = (NumberPicker) findViewById(R.id.np_MonthChooser);
        np_Month.setMinValue(1);
        np_Month.setMaxValue(12);
        np_Year = (NumberPicker) findViewById(R.id.np_YearChooser);
        np_Year.setMinValue(1900);
        np_Year.setMaxValue(2016); //TODO

        btn_reqCode = (Button) findViewById(R.id.btn_reqCode);
        btn_reqCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View l = (View) findViewById(R.id.lay_Verify);
                l.setVisibility(View.GONE);
                mailVerif = new MailVerifier(context, user.getEmail(), user.getUsername());
                mailVerif.execute();
            }
        });
        btn_Send = (Button) findViewById(R.id.btn_Send);
        btn_Send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View l = (View) findViewById(R.id.lay_Verify);

                if (et_Code.getText().toString() == sm.getVerif_Code(user.getUsername())){
                    sm.setVerif_Status(true, user.getPersonID());
                    l.setVisibility(View.GONE);
                }else{
                    et_Code.setError("Falscher Code!!!");
                }
            }
        });

        cb_AllowForename = (CheckBox) findViewById(R.id.cb_AllowForename);
        cb_AllowSurname = (CheckBox) findViewById(R.id.cb_AllowSurname);
        cb_AllowBirthdate = (CheckBox) findViewById(R.id.cb_AllowBirthdate);
        cb_AllowAboutMe = (CheckBox) findViewById(R.id.cb_AllowAboutMe);

        btn_Save = (Button) findViewById(R.id.btn_Save);
        btn_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });
    }

    private void save() {
        int personID = user.getPersonID();
        SqlManager manager = SqlManager.getInstance();

        if(!oldForename.equals(et_Forename.getText().toString())) {
            System.out.println("Writing Forename");
            manager.updatePerson(personID, "vorname", et_Forename.getText().toString());
        }

        if(!oldSurname.equals(et_Surname.getText().toString())) {
            System.out.println("Writing Surname");
            manager.updatePerson(personID, "nachname", et_Surname.getText().toString());
        }

        if(!oldAboutMe.equals(et_AboutMe.getText().toString())) {
            System.out.println("Writing About me");
            manager.updatePerson(personID, "hobbies", et_AboutMe.getText().toString());
        }

        Calendar c = Calendar.getInstance();
        c.set(np_Year.getValue(), np_Month.getValue(), np_Day.getValue());

        if(!oldGebDat.equals(new Date(c.getTimeInMillis()))) {
            System.out.println("Writing Gebdat");
            manager.updatePerson(personID, "geburtsdatum", new Date(c.getTimeInMillis()));
        }

    }

    private void mapUser() {
        if(UserProfile.getUserFound()) {
            user = UserProfile.getInstance("");

            oldForename = user.getForename();
            oldSurname = user.getSurname();
            oldAboutMe = user.getAboutMe();
            oldGebDat = user.getGebDat();

            et_Forename.setText(oldForename);
            et_Surname.setText(oldSurname);
            if(oldGebDat != null) {
                Calendar c = Calendar.getInstance();
                c.setTime(oldGebDat);
                np_Day.setValue(c.get(Calendar.DAY_OF_MONTH));
                np_Month.setValue(c.get(Calendar.MONTH));
                np_Year.setValue(c.get(Calendar.YEAR));
            }


        }
    }
}
