package ulm.hochschule.project_hoops.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import ulm.hochschule.project_hoops.R;
import ulm.hochschule.project_hoops.fragments.TipTab;
import ulm.hochschule.project_hoops.fragments.SendTipFragment;
import ulm.hochschule.project_hoops.interfaces.DataPassListener;

/**
 * Hostet das Tippspiel
 */
public class TippspielActivity extends AppCompatActivity implements DataPassListener{

    private TipTab tippTab;
    private SendTipFragment sendTipp;
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
        sendTipp = new SendTipFragment();
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