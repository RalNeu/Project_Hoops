package ulm.hochschule.project_hoops.activities;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

import ulm.hochschule.project_hoops.fragments.EditProfileFragment;
import ulm.hochschule.project_hoops.fragments.ProfilTab;
import ulm.hochschule.project_hoops.tasks.MailVerifierTask;
import ulm.hochschule.project_hoops.R;
import ulm.hochschule.project_hoops.utilities.SqlManager;
import ulm.hochschule.project_hoops.utilities.UserProfile;

public class EditProfilActivity extends AppCompatActivity implements Observer{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_editprofile);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        EditProfileFragment epf = new EditProfileFragment();
        epf.setObserver(this);
        ft.replace(R.id.editprofilecontent, epf).commit();
    }


    @Override
    public void update(Observable observable, Object data) {
        finish();
    }
}
