package ulm.hochschule.project_hoops;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.support.v4.app.FragmentManager;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Observable;
import java.util.Observer;

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
        user = UserProfile.getInstance("Teddy");
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
        String coinString = "" + user.getCoins();
        String rankingString = "" + user.getRanking();
        String highscoreString = "" + user.getHighscore();

        lbl_Coins.setText(coinString.toCharArray(), 0, coinString.length());
        lbl_Ranking.setText(rankingString.toCharArray(), 0, rankingString.length());
        lbl_Highscore.setText(highscoreString.toCharArray(), 0, highscoreString.length());
        lbl_Username.setText(user.getUsername().toCharArray(), 0, user.getUsername().length());
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
