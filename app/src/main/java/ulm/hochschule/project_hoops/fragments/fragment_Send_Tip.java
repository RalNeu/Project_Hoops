package ulm.hochschule.project_hoops.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import ulm.hochschule.project_hoops.R;
import ulm.hochschule.project_hoops.interfaces.DataPassListener;
import ulm.hochschule.project_hoops.utilities.AchievementHandler;
import ulm.hochschule.project_hoops.utilities.SqlManager;
import ulm.hochschule.project_hoops.utilities.UserProfile;

/**
 * Fragment zum Versenden eines Tipps.
 */
public class fragment_Send_Tip extends Fragment {

    private View layout;

    private int chosenCoins = 0;

    private Button btn_Inc, btn_IncStrong, btn_Dec, btn_DecStrong, btn_VoteUlm, btn_VoteOther;
    private TextView tv_Coins;
    private UserProfile user;
    private DataPassListener mCallback;

    private int team = 0;

    public void setDataPassListener(DataPassListener dpl) {
        mCallback = dpl;
    }


    public fragment_Send_Tip() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layout = inflater.inflate(R.layout.fragment_send_tip, container, false);

        btn_Inc = (Button) layout.findViewById(R.id.btn_Inc);
        btn_IncStrong = (Button) layout.findViewById(R.id.btn_IncStrong);
        btn_Dec = (Button) layout.findViewById(R.id.btn_Dec);
        btn_DecStrong = (Button) layout.findViewById(R.id.btn_DecStrong);
        btn_VoteUlm = (Button) layout.findViewById(R.id.btn_VoteUlm);
        btn_VoteOther = (Button) layout.findViewById(R.id.btn_VoteOther);

        tv_Coins = (TextView) layout.findViewById(R.id.tv_Coins);
        tv_Coins.setText("0");
        chosenCoins = 0;

        user = UserProfile.getInstance();

        setOnCLickListeners();
        addToCoins(0);

        return layout;
    }

    /**
     * Setzt die Click-Listeners für die Addier- und Subtrahier-Buttons.
     */
    private void setOnCLickListeners() {
        btn_Inc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCoins(10);
            }
        });
        btn_IncStrong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCoins(100);
            }
        });
        btn_Dec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCoins(-10);
            }
        });
        btn_DecStrong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCoins(-100);
            }
        });

        btn_VoteUlm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                team = 0;
                vote();
            }
        });

        btn_VoteOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                team = 1;
                vote();
            }
        });

    }

    private void changeFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        ft.replace(R.id.view_TipGame, new TipTab()).commit();
    }

    /**
     * Sendet den Tipp an die Datenbank und wechselt anschließend auf TipTab um
     */
    public void vote() {

            final ProgressDialog progressDialog = new ProgressDialog(getActivity());
            progressDialog.setTitle(getResources().getString(R.string.wait));
            progressDialog.setMessage(getResources().getString(R.string.commitTipp));
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();

            final Activity a = getActivity();

            Thread t = new Thread(new Runnable() {

                @Override
                public void run() {
                    Looper.prepare();
                    if(chosenCoins > 0) {

                            try {
                                SqlManager.getInstance().sendTip(chosenCoins, team);
                                if(team == 0) {
                                    AchievementHandler.getInstance().performEvent(10, 1, getActivity());
                                } else {
                                    AchievementHandler.getInstance().performEvent(11, 1, getActivity());
                                }
                                AchievementHandler.getInstance().performEvent(13, chosenCoins, getActivity());
                                mCallback.passData("!" + getResources().getString(R.string.tippSucc));
                                //changeFragment();
                        } catch(Exception e) {
                                e.printStackTrace();
                                mCallback.passData("!" + getResources().getString(R.string.tippFail));
                        }
                    }
                    progressDialog.cancel();
                }
            });
        t.start();
    }

    /**
     * Addiert zum Zählerstand die Coins je nachdem ob man genug Coins hat
     * @param amount Anzahl an Coins die man hinzufügen will. Auch zum Abziehen
     */
    public void addToCoins(int amount) {
        int tmp = chosenCoins + amount;

        if(tmp >= 0 && tmp <= user.getCoins()) {
            chosenCoins = tmp;
        } else {
            if(tmp < 0) {
                chosenCoins = 0;
            }
            if(tmp > user.getCoins()) {
                chosenCoins = user.getCoins();
            }
        }

        tv_Coins.setText("" + chosenCoins);

        if(chosenCoins == 0) {
            btn_Dec.setEnabled(false);
            btn_DecStrong.setEnabled(false);
        } else {
            btn_Dec.setEnabled(true);
            btn_DecStrong.setEnabled(true);
        }
        if(chosenCoins == user.getCoins()) {
            btn_Inc.setEnabled(false);
            btn_IncStrong.setEnabled(false);
        } else {
            btn_Inc.setEnabled(true);
            btn_IncStrong.setEnabled(true);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}