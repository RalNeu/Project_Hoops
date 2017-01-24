package ulm.hochschule.project_hoops.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import ulm.hochschule.project_hoops.R;
import ulm.hochschule.project_hoops.fragments.TipTab;
import ulm.hochschule.project_hoops.fragments.fragment_Send_Tip;
import ulm.hochschule.project_hoops.interfaces.DataPassListener;

/**
 * Created by Johann on 10.06.2016.
 */
public class TippspielActivity extends AppCompatActivity implements DataPassListener{

    private TipTab tippTab;
    private fragment_Send_Tip sendTipp;
    private boolean frag = true;


    private void changeFragment(Fragment f) {
        getSupportFragmentManager().beginTransaction().replace(R.id.view_TipGame, f).commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tippspiel);

        tippTab = new TipTab();
        tippTab.setDataPassListener(this);
        sendTipp = new fragment_Send_Tip();
        sendTipp.setDataPassListener(this);

        changeFragment( tippTab);

    }

    @Override
    public void onBackPressed() {
        if(frag == false) {
            frag = !frag;
            changeFragment(tippTab);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void passData(String data) {
        frag = !frag;
        if(frag) {
            tippTab.setMessage(data);
            System.out.println("In TippspielActivity: frag = true");
            changeFragment(tippTab);
        }
        else {
            System.out.println("In TippspielActivity: frag = false");
            changeFragment(sendTipp);
        }
    }
}