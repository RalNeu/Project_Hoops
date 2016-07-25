package ulm.hochschule.project_hoops.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import ulm.hochschule.project_hoops.R;
import ulm.hochschule.project_hoops.fragments.TipTab;
import ulm.hochschule.project_hoops.objects.Card;
import ulm.hochschule.project_hoops.utilities.CardArrayAdapter;
import ulm.hochschule.project_hoops.utilities.ViewPagerAdapter;

/**
 * Created by Johann on 10.06.2016.
 */
public class TippspielActivity extends AppCompatActivity {


    private void changeFragment(Fragment f) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.view_TipGame, f).commit();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tippspiel);

        changeFragment(new TipTab());

    }
}
