package ulm.hochschule.project_hoops.activities;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
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

import ulm.hochschule.project_hoops.fragments.EditAvatarTab;
import ulm.hochschule.project_hoops.fragments.EditProfileFragment;
import ulm.hochschule.project_hoops.fragments.ProfilTab;
import ulm.hochschule.project_hoops.tasks.MailVerifierTask;
import ulm.hochschule.project_hoops.R;
import ulm.hochschule.project_hoops.utilities.Notificator;
import ulm.hochschule.project_hoops.utilities.SqlManager;
import ulm.hochschule.project_hoops.utilities.UserProfile;
import ulm.hochschule.project_hoops.utilities.ViewPagerAdapter;

public class EditProfilActivity extends AppCompatActivity {
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
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_tabs);
        init();
        user = UserProfile.getInstance();
        sm = SqlManager.getInstance();
    }

    private void init(){

        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabsProfile);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void checkVerified() {
        if (et_Code.getText().toString().equalsIgnoreCase(user.getVerifCode())) {
            sm.setVerif_Status(0, user.getPersonID());
            hideVerify();
            Toast toast = Toast.makeText(this, "Verifizierung erfolgreich", Toast.LENGTH_SHORT);
            toast.show();
        } else {
            et_Code.setError("Falscher Code!");
        }
    }

    private void hideVerify() {
        View l = (View) findViewById(R.id.lay_Verify);
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
            oldForename = et_Forename.getText().toString();
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

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        EditProfileFragment epf = new EditProfileFragment();
        EditAvatarTab eat = new EditAvatarTab();
        adapter.addFrag(epf, "Profilinformationen");
        adapter.addFrag(eat , "Avatar");
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrolled(int position, float offset, int offsetPixels) {
                final InputMethodManager imm = (InputMethodManager)getSystemService(
                        Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(tabLayout.getWindowToken(), 0);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }
}
