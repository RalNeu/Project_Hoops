package ulm.hochschule.project_hoops.activities;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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

import ulm.hochschule.project_hoops.R;
import ulm.hochschule.project_hoops.fragments.EditAvatarTab;
import ulm.hochschule.project_hoops.fragments.EditProfileFragment;
import ulm.hochschule.project_hoops.fragments.ProfilTab;
import ulm.hochschule.project_hoops.utilities.Notificator;
import ulm.hochschule.project_hoops.utilities.SqlManager;
import ulm.hochschule.project_hoops.utilities.UserProfile;
import ulm.hochschule.project_hoops.utilities.ViewPagerAdapter;

public class EditProfilActivity extends AppCompatActivity {
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_tabs);
        init();
    }

    private void init(){

        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabsProfile);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        final EditProfileFragment epf = new EditProfileFragment();
        final EditAvatarTab eat = new EditAvatarTab();
        adapter.addFrag(epf, "Profilinformationen");
        adapter.addFrag(eat , "Avatar");
        
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                epf.save();
                eat.save();
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
