package ulm.hochschule.project_hoops.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Observable;
import java.util.Observer;

import ulm.hochschule.project_hoops.R;
import ulm.hochschule.project_hoops.tasks.MailVerifierTask;
import ulm.hochschule.project_hoops.utilities.Notificator;
import ulm.hochschule.project_hoops.utilities.SqlManager;
import ulm.hochschule.project_hoops.utilities.UserProfile;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditProfileFragment extends Fragment {

    private EditText et_Forename, et_Surname, et_AboutMe, et_Code;
    private NumberPicker np_Day, np_Month, np_Year;
    private CheckBox cb_AllowForename, cb_AllowSurname, cb_AllowBirthdate, cb_AllowAboutMe;
    private Button btn_Save;
    private Button btn_reqCode;
    private Button btn_Send;
    private Button btn_deactivate;
    private UserProfile user;
    private SqlManager sm;
    private MailVerifierTask mailVerif;
    private AppCompatActivity context;
    private String oldForename, oldSurname, oldAboutMe;
    private Date oldGebDat;
    private int oldSettings;
    private Notificator notif;
    private View layout;


    public EditProfileFragment() {
        // Required empty public constructor
    }


    public void setObserver(Observer obs) {
        notif = new Notificator();
        notif.addObserver(obs);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        layout = inflater.inflate(R.layout.activity_edit_profile, container, false);

        return layout;
    }

    private void instantiateUiObjects() {
        et_Forename = (EditText) layout.findViewById(R.id.et_Forename);
        et_Surname = (EditText) layout.findViewById(R.id.et_Surname);
        et_AboutMe = (EditText) layout.findViewById(R.id.et_AboutMe);
        et_Code = (EditText) layout.findViewById(R.id.et_Code);
        context = new AppCompatActivity();

        np_Day = (NumberPicker) layout.findViewById(R.id.np_DayChooser);
        np_Day.setMinValue(1);
        np_Day.setMaxValue(31);
        np_Month = (NumberPicker) layout.findViewById(R.id.np_MonthChooser);
        np_Month.setMinValue(1);
        np_Month.setMaxValue(12);

        np_Month.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                if (newVal == 1 || newVal == 3 || newVal == 5 || newVal == 7 || newVal == 8 || newVal == 10 || newVal == 12) {
                    np_Day.setMaxValue(31);
                } else {
                    if (newVal == 2) {
                        if (isLeapYear(np_Year.getValue())) {
                            np_Day.setMaxValue(29);
                        } else {
                            np_Day.setMaxValue(28);
                        }
                    } else {
                        np_Day.setMaxValue(30);
                    }
                }
            }
        });

        np_Year = (NumberPicker) layout.findViewById(R.id.np_YearChooser);
        np_Year.setMinValue(1900);
        np_Year.setMaxValue(2016); //TODO
        np_Year.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                boolean schaltjahr = isLeapYear(newVal);

                if (np_Month.getValue() == 2) {
                    if (schaltjahr) {
                        np_Day.setMaxValue(29);
                    } else {
                        np_Day.setMaxValue(28);
                    }
                }
            }
        });
        btn_deactivate = (Button) layout.findViewById(R.id.btn_deactivate);
        btn_deactivate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View l = (View) layout.findViewById(R.id.lay_Verify);
                sm.setStatus(user.getUsername(), "inactive");
            }
        });

        btn_reqCode = (Button) layout.findViewById(R.id.btn_reqCode);
        btn_reqCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View l = (View) layout.findViewById(R.id.lay_Verify);

                mailVerif = new MailVerifierTask(context, user.getEmail(), user.getUsername());
                mailVerif.execute();
            }
        });
        btn_Send = (Button) layout.findViewById(R.id.btn_Send);
        btn_Send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkVerified();
            }
        });

        cb_AllowForename = (CheckBox) layout.findViewById(R.id.cb_AllowForename);
        cb_AllowSurname = (CheckBox) layout.findViewById(R.id.cb_AllowSurname);
        cb_AllowBirthdate = (CheckBox) layout.findViewById(R.id.cb_AllowBirthdate);
        cb_AllowAboutMe = (CheckBox) layout.findViewById(R.id.cb_AllowAboutMe);

        btn_Save = (Button) layout.findViewById(R.id.btn_Save);
        btn_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
                ProfilTab.getInstance().updateData();
                notif.notifObs();
            }
        });
    }

    private void checkVerified() {
        if (et_Code.getText().toString().equalsIgnoreCase(user.getVerifCode())) {
            sm.setVerif_Status(0, user.getPersonID());
            hideVerify();
            Toast toast = Toast.makeText(getActivity(), "Verifizierung erfolgreich", Toast.LENGTH_SHORT);
            toast.show();
        } else {
            et_Code.setError("Falscher Code!");
        }
    }

    private void hideVerify() {
        View l = (View) layout.findViewById(R.id.lay_Verify);
        l.setVisibility(View.GONE);
    }

    private boolean isLeapYear(int year) {
        boolean retVal = false;
        if (year % 4 == 0) {
            if (year % 100 == 0) {
                if (year % 400 == 0) {
                    retVal = true;
                }
            } else {
                retVal = true;
            }
        }
        return retVal;
    }

    private void save() {
        int personID = user.getPersonID();
        SqlManager manager = SqlManager.getInstance();

        if (!oldForename.equals(et_Forename.getText().toString())) {
            System.out.println(oldForename);
            oldForename = et_Forename.getText().toString();
            System.out.println(oldForename);
            manager.updatePerson(personID, "vorname", oldForename);
        }

        if (!oldSurname.equals(et_Surname.getText().toString())) {
            oldSurname = et_Surname.getText().toString();
            manager.updatePerson(personID, "nachname", oldSurname);
        }

        System.out.println(oldAboutMe);
        if (!oldAboutMe.equals(et_AboutMe.getText().toString())) {
            oldAboutMe = et_AboutMe.getText().toString();
            manager.updatePerson(personID, "hobbies", oldAboutMe);
        }

        if (oldSettings != calcSettings()) {
            manager.updateAccount(user.getUsername(), "einstellung", calcSettings());
        }

        Calendar c = Calendar.getInstance();
        c.set(np_Year.getValue(), np_Month.getValue(), np_Day.getValue());

        Date d = new Date(c.getTimeInMillis());

        if (!(oldGebDat.getDay() == d.getDay() && oldGebDat.getMonth() == d.getMonth() && oldGebDat.getYear() == d.getYear())) {
            oldGebDat = d;
            manager.updatePerson(personID, "geburtsdatum", oldGebDat);
        }

        user.update(oldForename, oldSurname, oldAboutMe, oldGebDat, calcSettings());
    }

    private int calcSettings() {
        int retVal = 0;

        boolean[] fields = {cb_AllowForename.isChecked(), cb_AllowSurname.isChecked(), cb_AllowBirthdate.isChecked(), cb_AllowAboutMe.isChecked()};

        for (int i = 0; i < 4; i++) {
            if (fields[i]) {
                retVal += Math.pow(2, i);
            }
        }

        return retVal;
    }

    private void mapUser() {
        if (UserProfile.getUserFound()) {
            oldForename = user.getForename();
            oldSurname = user.getSurname();
            oldAboutMe = user.getAboutMe();
            oldGebDat = user.getGebDat();
            oldSettings = user.getSettings();

            cb_AllowForename.setChecked((oldSettings & 1) > 0 ? true : false);
            cb_AllowSurname.setChecked((oldSettings & 2) > 0 ? true : false);
            cb_AllowBirthdate.setChecked((oldSettings & 4) > 0 ? true : false);
            cb_AllowAboutMe.setChecked((oldSettings & 8) > 0 ? true : false);

            et_Forename.setText(oldForename);
            et_Surname.setText(oldSurname);
            et_AboutMe.setText(oldAboutMe);

            if (oldGebDat != null) {
                Calendar c = Calendar.getInstance();
                c.setTime(oldGebDat);
                np_Day.setValue(c.get(Calendar.DAY_OF_MONTH));
                np_Month.setValue(c.get(Calendar.MONTH));
                np_Year.setValue(c.get(Calendar.YEAR));
            } else {
                np_Day.setValue(1);
                np_Month.setValue(1);
                np_Year.setValue(2000);
            }

            try {
                if (sm.isVerified(user.getUsername())) {
                    hideVerify();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}