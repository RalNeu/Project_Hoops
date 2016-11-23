package ulm.hochschule.project_hoops.fragments;


import android.content.Context;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import ulm.hochschule.project_hoops.R;
import ulm.hochschule.project_hoops.interfaces.DataPassListener;
import ulm.hochschule.project_hoops.utilities.AchievementHandler;
import ulm.hochschule.project_hoops.utilities.ServerCommunicate;
import ulm.hochschule.project_hoops.utilities.ServerException;

/**
 * A simple {@link Fragment} subclass.
 */
public class TipTab extends Fragment {

    private View layout;
    private ServerCommunicate sc;
    private TextView tv_Prozent_Other, tv_Prozent_Ulm;
    private SeekBar sb_SeekBar;
    private Button btn_Vote;
    private TextView tv_information_vote;

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


        Button btn_Refresh = (Button) layout.findViewById(R.id.btn_Refresh);

        sb_SeekBar.setEnabled(false);
        sc = ServerCommunicate.getInstance();

        btn_Refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();
            }
        });

        btn_Vote = (Button) layout.findViewById(R.id.btn_Vote);
        tv_information_vote = (TextView) layout.findViewById(R.id.tv_vote_information);

        update();



        final ImageView img_View = (ImageView) layout.findViewById(R.id.img_Team);

        img_View.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                // Ensure you call it only once :
                img_View.getViewTreeObserver().removeGlobalOnLayoutListener(this);

                img_View.getLayoutParams().height = img_View.getLayoutParams().width;
               // img_View.requestLayout();
            }
        });


        btn_Vote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.passData("");
            }
        });


        if(!message.equals("")) {
            AlertDialog.Builder dlgAlert = new AlertDialog.Builder(getActivity());
            dlgAlert.setMessage(message);
            dlgAlert.setTitle(getResources().getString(R.string.title_activity_tipp_spiel));
            dlgAlert.setPositiveButton("OK", null);
            dlgAlert.setCancelable(true);
            dlgAlert.create().show();
            message = "";
        }

        return layout;
    }

    public void setDataPassListener(DataPassListener dpl) {
        mCallback = dpl;
    }

    public void update() {
        try {
            int win = sc.getWin();
            boolean rdy = sc.getRdyToTipp() && win != -2;
            //btn_Vote.setEnabled(rdy);
            if(!rdy) {
                if(win == -2) {
                    tv_information_vote.setText(getResources().getString(R.string.tippedAlready));
                    sc.readQuote();
                    tv_Prozent_Ulm.setText("" + Math.round(sc.getQuoteUlm()));
                    tv_Prozent_Other.setText("" + (100 - Math.round(sc.getQuoteUlm())));
                    sb_SeekBar.setProgress((int) sc.getQuoteOther());
                } else {
                    tv_information_vote.setText(getResources().getString(R.string.noTipps));
                    tv_Prozent_Ulm.setText("0");
                    tv_Prozent_Other.setText("0");
                    sb_SeekBar.setProgress(50);
                }
            } else {
                sc.readQuote();
                tv_Prozent_Ulm.setText("" + Math.round(sc.getQuoteUlm()));
                tv_Prozent_Other.setText("" + (100 - Math.round(sc.getQuoteUlm())));
                sb_SeekBar.setProgress((int) sc.getQuoteOther());
                tv_information_vote.setText("");
            }



            if(win > 0) {
                Toast toast = Toast.makeText(getActivity().getApplicationContext(), getResources().getString(R.string.won1) + win + getResources().getString(R.string.won2), Toast.LENGTH_SHORT);
                try {
                    AchievementHandler.getInstance().performEvent(14, win, getActivity());
                } catch (ServerException e) {
                    e.printStackTrace();
                }
                toast.show();
            } else if(win == 0) {
                Toast toast = Toast.makeText(getActivity().getApplicationContext(), getResources().getString(R.string.won3), Toast.LENGTH_SHORT);
                toast.show();
            }


        } catch (ServerException e) {
            btn_Vote.setEnabled(false);
            tv_information_vote.setText(getResources().getString(R.string.noTipps));
            tv_Prozent_Ulm.setText("0");
            tv_Prozent_Other.setText("0");
            sb_SeekBar.setProgress(50);
        }
    }

}
