package ulm.hochschule.project_hoops.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import ulm.hochschule.project_hoops.R;
import ulm.hochschule.project_hoops.Views.Achievement;

public class AchievementTab extends Fragment {

    private View layout;

    private LinearLayout lay_Achievement;

    public AchievementTab() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        layout = inflater.inflate(R.layout.fragment_achievement, container, false);

        lay_Achievement = (LinearLayout) layout.findViewById(R.id.lay_AchievementContainer);

        final Achievement[] ach = new Achievement[6];

        ach[0] = new Achievement(getContext());
        lay_Achievement.addView(ach[0]);
        ach[0].setAchievement(0,"???","-");

        ach[1] = new Achievement(getContext());
        lay_Achievement.addView(ach[1]);
        ach[1].setAchievement(0,"???","-");

        ach[2] = new Achievement(getContext());
        lay_Achievement.addView(ach[2]);
        ach[2].setAchievement(3, "Sie haben 20 Spiele richtig getippt!", "25.10.2016");

        ach[3] = new Achievement(getContext());
        lay_Achievement.addView(ach[3]);
        ach[3].setAchievement(1,"Sie haben sich an drei Tagen hintereinander eingeloggt!","26.07.2016");

        ach[4] = new Achievement(getContext());
        lay_Achievement.addView(ach[4]);
        ach[4].setAchievement(0,"???","-");

        ach[5] = new Achievement(getContext());
        lay_Achievement.addView(ach[5]);
        ach[5].setAchievement(2, "Sie haben einen Highscore von 5000 Punkten erreicht!", "16.11.2016");

        return layout;
    }

}
