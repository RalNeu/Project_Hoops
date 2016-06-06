package ulm.hochschule.project_hoops.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Observer;

import ulm.hochschule.project_hoops.utilities.Notificator;
import ulm.hochschule.project_hoops.R;
import ulm.hochschule.project_hoops.utilities.UserProfile;

/**
 * Created by Johann on 06.05.2016.
 */
public class ProfilTab extends Fragment {

    private UserProfile user;

    private Button btn_EditProfile;
    private TextView lbl_Coins, lbl_Ranking, lbl_Highscore, lbl_Username;
    private Notificator notif;
    private View layout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = UserProfile.getInstance("RalNeu");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layout = inflater.inflate(R.layout.fragment_profile, container, false);

        instatiateUiObjects();
        mapUser();

        btn_EditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openEditProfile();
            }
        });

        return layout;
    }

    private void openEditProfile() {

        notif.notifObs();
    }

    private void instatiateUiObjects() {
        lbl_Coins = (TextView) layout.findViewById(R.id.lbl_Coins);
        lbl_Highscore = (TextView) layout.findViewById(R.id.lbl_Highscore);
        lbl_Ranking = (TextView) layout.findViewById(R.id.lbl_Ranking);
        lbl_Username = (TextView) layout.findViewById(R.id.lbl_Username);
        btn_EditProfile = (Button) layout.findViewById(R.id.btn_ConfigureProfile);
    }

    private void mapUser() {
        lbl_Coins.setText("" + user.getCoins());
        lbl_Ranking.setText("" + user.getRanking());
        lbl_Highscore.setText("" + user.getHighscore());
        lbl_Username.setText(user.getUsername());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void addObserver(Observer obs) {
        notif = new Notificator();
        notif.addObserver(obs);
    }

}
