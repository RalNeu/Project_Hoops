package ulm.hochschule.project_hoops.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.sql.SQLException;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

import ulm.hochschule.project_hoops.activities.EditProfilActivity;
import ulm.hochschule.project_hoops.objects.AvatarItems;
import ulm.hochschule.project_hoops.utilities.Notificator;
import ulm.hochschule.project_hoops.R;
import ulm.hochschule.project_hoops.utilities.SqlManager;
import ulm.hochschule.project_hoops.utilities.UserProfile;

/**
 * Created by Johann on 06.05.2016.
 */
public class ProfilTab extends Fragment {

    private static ProfilTab tab;

    private UserProfile user;
    private AvatarItems aItems;

    private Button btn_EditProfile;
    private TextView lbl_Coins, lbl_Ranking, lbl_Highscore, lbl_Username, lbl_Name, lbl_GebDat, lbl_AboutMe;
    private Notificator notif;
    private View layout, view_Name, view_GebDat, view_AboutMe;
    private ImageView imgHat, imgHead, imgHair, imgEyes;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layout = inflater.inflate(R.layout.fragment_profile, container, false);

        instatiateUiObjects();

        btn_EditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openEditProfile();
            }
        });
        user = UserProfile.getInstance();
        mapUser();

        try {
            updateAvatar();
        } catch(SQLException e) {
            e.printStackTrace();
        }

        return layout;
    }

    public static ProfilTab getInstance() {
        if (tab == null) {
            tab = new ProfilTab();
        }
        return tab;
    }

    public void updateData() {

        String name = "", gebDat = "";

        int settings = user.getSettings();

        if((settings&3) > 0) {
            if ((settings & 1) > 0) {
                name += user.getForename();
            }
            if ((settings & 2) > 0) {
                if(name.length() != 0) {
                    name += " ";
                }
                name += user.getSurname();
            }

            lbl_Name.setText(name);
            view_Name.setVisibility(View.VISIBLE);

        } else {
            view_Name.setVisibility(View.GONE);
        }

        if((settings&4) > 0) {
            Date d = user.getGebDat();
            String[] s = d.toString().split("-");

            gebDat = s[2] + "." + d.getMonth() + "." + s[0];

            lbl_GebDat.setText(gebDat);
            view_GebDat.setVisibility(View.VISIBLE);
        } else {
            view_GebDat.setVisibility(View.GONE);
        }

        if((settings&8) > 0) {
            lbl_AboutMe.setText(user.getAboutMe());
            view_AboutMe.setVisibility(View.VISIBLE);
        } else {
            view_AboutMe.setVisibility(View.GONE);
        }

    }

    public void updateAvatar() throws SQLException{
        aItems = AvatarItems.getInstance();
        /*imgHat.setBackgroundDrawable(aItems.getAccountItem("hat"));
        imgEyes.setBackgroundDrawable(aItems.getAccountItem("eyes"));
        imgHair.setBackgroundDrawable(aItems.getAccountItem("hair"));*/
        imgHat.setBackgroundResource(aItems.getAccountItemByID("hat"));
        imgEyes.setBackgroundResource(aItems.getAccountItemByID("eyes"));
        imgHair.setBackgroundResource(aItems.getAccountItemByID("hair"));

    }

    private void openEditProfile() {
        Intent i = new Intent(getActivity().getApplicationContext(), EditProfilActivity.class);
        startActivity(i);
    }

    private void instatiateUiObjects() {
        lbl_Coins = (TextView) layout.findViewById(R.id.lbl_Coins);
        lbl_Highscore = (TextView) layout.findViewById(R.id.lbl_Highscore);
        lbl_Ranking = (TextView) layout.findViewById(R.id.lbl_Ranking);
        lbl_Username = (TextView) layout.findViewById(R.id.lbl_Username);
        lbl_Name = (TextView) layout.findViewById(R.id.lbl_Name);
        lbl_GebDat = (TextView) layout.findViewById(R.id.lbl_GebDat);
        lbl_AboutMe = (TextView) layout.findViewById(R.id.lbl_AboutMe);

        imgHat = (ImageView) layout.findViewById(R.id.imgHat);
        imgHead = (ImageView) layout.findViewById(R.id.imgHead);
        imgHair = (ImageView) layout.findViewById(R.id.imgHair);
        imgEyes = (ImageView) layout.findViewById(R.id.imgEyes);

        btn_EditProfile = (Button) layout.findViewById(R.id.btn_ConfigureProfile);

        view_Name = (View) layout.findViewById(R.id.view_Name);
        view_GebDat = (View) layout.findViewById(R.id.view_GebDat);
        view_AboutMe = (View) layout.findViewById(R.id.view_AboutMe);

        final ImageView iViewCoins = (ImageView) layout.findViewById(R.id.img_Coins);
        final ImageView iViewRanking = (ImageView) layout.findViewById(R.id.img_Ranking);
        final ImageView iViewHighscore = (ImageView) layout.findViewById(R.id.img_Highscore);
        final FrameLayout fL = (FrameLayout) layout.findViewById(R.id.frLay);

        iViewCoins.post(new Runnable() {
            @Override
            public void run() {
                iViewCoins.getLayoutParams().width = iViewCoins.getHeight();
                iViewCoins.requestLayout();
            }
        });

        iViewRanking.post(new Runnable() {
            @Override
            public void run() {
                iViewRanking.getLayoutParams().width = iViewRanking.getHeight();
                iViewRanking.requestLayout();
            }
        });

        iViewHighscore.post(new Runnable() {
            @Override
            public void run() {
                iViewHighscore.getLayoutParams().width = iViewHighscore.getHeight();
                iViewHighscore.requestLayout();
            }
        });
    }


    private void mapUser() {
        lbl_Coins.setText("" + user.getCoins());
        lbl_Ranking.setText("" + user.getRanking());
        lbl_Highscore.setText("" + user.getHighscore());
        lbl_Username.setText(user.getUsername());
        updateData();
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
