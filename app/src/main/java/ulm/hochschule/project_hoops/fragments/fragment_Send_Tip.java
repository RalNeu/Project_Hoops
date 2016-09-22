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
import android.widget.Toast;

import ulm.hochschule.project_hoops.R;
import ulm.hochschule.project_hoops.utilities.AchievementHandler;
import ulm.hochschule.project_hoops.utilities.ServerCommunicate;
import ulm.hochschule.project_hoops.utilities.ServerException;
import ulm.hochschule.project_hoops.utilities.TimeoutException;
import ulm.hochschule.project_hoops.utilities.UserProfile;


public class fragment_Send_Tip extends Fragment {

    private View layout;

    private int chosenCoins = 0;

    private Button btn_Inc, btn_IncStrong, btn_Dec, btn_DecStrong, btn_VoteUlm, btn_VoteOther;
    private TextView tv_Coins;
    private UserProfile user;

    private int team = 0;


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

        user = UserProfile.getInstance();

        setOnCLickListeners();
        addToCoins(0);

        return layout;
    }

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

    public void vote() {

            final ProgressDialog progressDialog = new ProgressDialog(getActivity());
            progressDialog.setTitle("Bitte warten Sie einen Moment");
            progressDialog.setMessage("Tipp abgeben");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();

            final Activity a = getActivity();

            Thread t = new Thread(new Runnable() {

                @Override
                public void run() {
                   String s = "";
                    Looper.prepare();
                    if(chosenCoins > 0) {
                        try {
                            ServerCommunicate sc = ServerCommunicate.getInstance();
                            try {
                                sc.sendTip(chosenCoins, team);
                                s = "Tipp gesendet!";
                                if(team == 0) {
                                    AchievementHandler.getInstance().performEvent(10, 1, getActivity());
                                } else {
                                    AchievementHandler.getInstance().performEvent(11, 1, getActivity());
                                }
                                AchievementHandler.getInstance().performEvent(13, chosenCoins, getActivity());
                                changeFragment();
                            } catch (TimeoutException e) {
                                s = "Es können momentan keine Tipps entgegen genommen werden. Versuchen Sie es bitte später erneut.";
                            }
                        } catch(ServerException e) {
                            e.printStackTrace();
                            s = "Es ist ein Fehler aufgetreten! Bitte versuchen Sie es erneut.";
                        }
                    } else {
                        s = "Bitte Geben Sie einen Tipp über 0 an!";
                    }
                    progressDialog.cancel();
                    showToast(s);
                }
            });
        t.start();
    }

    public void showToast(String s) {
        Toast toast = Toast.makeText(getActivity().getApplicationContext(), s, Toast.LENGTH_SHORT);
        toast.show();
    }

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