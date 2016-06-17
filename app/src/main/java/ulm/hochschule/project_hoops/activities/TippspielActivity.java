package ulm.hochschule.project_hoops.activities;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;

import ulm.hochschule.project_hoops.R;
import ulm.hochschule.project_hoops.fragments.MyBetTab;
import ulm.hochschule.project_hoops.fragments.TipTab;
import ulm.hochschule.project_hoops.fragments.fragment_Send_Tip;

/**
 * Created by Johann on 10.06.2016.
 */
public class TippspielActivity extends AppCompatActivity {

    ViewPager Tab;
    TabPagerAdapter TabAdapter;
    ActionBar actionBar;


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
/*
        TabAdapter = new TabPagerAdapter(getSupportFragmentManager());

        Tab = (ViewPager)findViewById(R.id.pager);
        Tab.setOnPageChangeListener(
                new ViewPager.SimpleOnPageChangeListener() {
                    @Override
                    public void onPageSelected(int position) {

                        actionBar = getActionBar();
                        actionBar.setSelectedNavigationItem(position);
                    }
                });
       // Tab.setAdapter(TabAdapter);

        actionBar = getActionBar();
        //Enable Tabs on Action Bar
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        ActionBar.TabListener tabListener = new ActionBar.TabListener(){
            @Override
            public void onTabSelected(ActionBar.Tab tab, android.app.FragmentTransaction ft) {
                Tab.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(ActionBar.Tab tab, android.app.FragmentTransaction ft) {
            }

            @Override
            public void onTabReselected(ActionBar.Tab tab, android.app.FragmentTransaction ft) {
            }
        };
        //Add New Tab
        actionBar.addTab(actionBar.newTab().setText("Android").setTabListener(tabListener));
        actionBar.addTab(actionBar.newTab().setText("iOS").setTabListener(tabListener));
        actionBar.addTab(actionBar.newTab().setText("Windows").setTabListener(tabListener));
*/



    }

    private class TabPagerAdapter extends FragmentStatePagerAdapter {
        public TabPagerAdapter(FragmentManager fm) {
            super(fm);
            // TODO Auto-generated constructor stub
        }

        @Override
        public Fragment getItem(int i) {
            switch (i) {
                case 0:
                    //Fragement for Android Tab
                    return new MyBetTab();
            }
            return null;

        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return 3; //No of Tabs
        }

    }
}
