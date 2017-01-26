package ulm.hochschule.project_hoops.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import ulm.hochschule.project_hoops.R;
import ulm.hochschule.project_hoops.interfaces.DataPassListener;
import ulm.hochschule.project_hoops.utilities.AchievementHandler;
import ulm.hochschule.project_hoops.utilities.AlreadyBettedException;
import ulm.hochschule.project_hoops.utilities.NotBettableException;
import ulm.hochschule.project_hoops.utilities.ServerCommunicate;
import ulm.hochschule.project_hoops.utilities.ServerException;
import ulm.hochschule.project_hoops.utilities.SqlManager;

/**
 * Hier werden der aktuelle Stand und der aktuelle Gegner dargestellt
 */
public class TipTab extends Fragment {

    private View layout;
    private SqlManager sqlManager;
    private TextView tv_Prozent_Other, tv_Prozent_Ulm;
    private SeekBar sb_SeekBar;
    private Button btn_Vote;
    private TextView tv_information_vote, tv_gast;

    private String message = "";

    public void setMessage(String message) {
        this.message = message;
    }

    private DataPassListener mCallback;

    public TipTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        layout = inflater.inflate(R.layout.fragment_tip_game, container, false);

        tv_Prozent_Ulm = (TextView) layout.findViewById(R.id.tv_Prozent_Ulm);
        tv_Prozent_Other = (TextView) layout.findViewById(R.id.tv_Prozent_Other);
        sb_SeekBar = (SeekBar) layout.findViewById(R.id.sb_SeekBar);
        tv_gast = (TextView) layout.findViewById(R.id.gastView);


        Button btn_Refresh = (Button) layout.findViewById(R.id.btn_Refresh);

        sb_SeekBar.setEnabled(false);
        sqlManager = SqlManager.getInstance();

        btn_Refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();
            }
        });

        btn_Vote = (Button) layout.findViewById(R.id.btn_Vote);
        tv_information_vote = (TextView) layout.findViewById(R.id.tv_vote_information);

        update();

        btn_Vote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.passData("");
            }
        });

        if(!message.equals("")) {
            if(message.charAt(0) == '!') {
                System.out.println("lol " + message);
                message = message.substring(1);
                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(getActivity());
                dlgAlert.setMessage(message);
                dlgAlert.setTitle(getResources().getString(R.string.title_activity_tipp_spiel));
                dlgAlert.setPositiveButton("OK", null);
                dlgAlert.setCancelable(true);
                dlgAlert.create().show();
            }
            message = "";
        }
        return layout;
    }

    public void setDataPassListener(DataPassListener dpl) {
        mCallback = dpl;
    }

    /**
     * Holt die neuen Werte von der Datenbank
     */
    public void update() {
        try {
            int win = sqlManager.getWin();
            sqlManager.getRdyToTipp();

            tv_Prozent_Ulm.setText("" + Math.round(sqlManager.getQuoteUlm()));
            tv_Prozent_Other.setText("" + (Math.round(sqlManager.getQuoteOther())));
            tv_gast.setText(sqlManager.getOpponentName());
            sb_SeekBar.setProgress((int) sqlManager.getQuoteOther());
            tv_information_vote.setText("");

            AlertDialog.Builder dlgAlert = new AlertDialog.Builder(getActivity());

            if(win > 0) {
                dlgAlert.setMessage(getResources().getString(R.string.won1) + " " + win + " " + getResources().getString(R.string.won2));
                try {
                    AchievementHandler.getInstance().performEvent(14, win, getActivity());
                } catch (ServerException e) {
                    e.printStackTrace();
                }
            } else if(win == 0) {
                dlgAlert.setMessage(getResources().getString(R.string.won3));
            }

            dlgAlert.setTitle(getResources().getString(R.string.title_activity_tipp_spiel));
            dlgAlert.setPositiveButton("OK", null);
            dlgAlert.setCancelable(true);
            dlgAlert.create().show();
            btn_Vote.setEnabled(true);

        } catch (NotBettableException e) {
            btn_Vote.setEnabled(false);
            tv_information_vote.setText(getResources().getString(R.string.noTipps));
            tv_Prozent_Ulm.setText("0");
            tv_gast.setText("");
            tv_Prozent_Other.setText("0");
            sb_SeekBar.setProgress(50);
            btn_Vote.setEnabled(false);

        } catch (AlreadyBettedException e) {
            tv_information_vote.setText(getResources().getString(R.string.tippedAlready));
            tv_gast.setText(sqlManager.getOpponentName());
            tv_Prozent_Ulm.setText("" + Math.round(sqlManager.getQuoteUlm()));
            tv_Prozent_Other.setText("" + (Math.round(sqlManager.getQuoteOther())));
            sb_SeekBar.setProgress((int) sqlManager.getQuoteOther());
            btn_Vote.setEnabled(false);
        }
    }

}
