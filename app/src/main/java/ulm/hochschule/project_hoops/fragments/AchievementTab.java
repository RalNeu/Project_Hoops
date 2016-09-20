package ulm.hochschule.project_hoops.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.HashMap;

import ulm.hochschule.project_hoops.R;
import ulm.hochschule.project_hoops.utilities.AchievementHandler;
import ulm.hochschule.project_hoops.utilities.AchievementObject;
import ulm.hochschule.project_hoops.utilities.ServerException;
import ulm.hochschule.project_hoops.views.Achievement;

public class AchievementTab extends Fragment {

    private View layout;

    private LinearLayout lay_Achievement;

    private HashMap<Integer, Achievement> achievements;

    public AchievementTab() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void addAchievement(int idx, int emblem, String description, String date) {
        Achievement a;

        if(achievements.containsKey(idx)) {
            a = (Achievement) lay_Achievement.findViewById(idx);
            a.setAchievement(emblem, description, date);
        } else {
            a = new Achievement(getContext());
            a.setId(idx);
            lay_Achievement.addView(a);
            a.setAchievement(emblem, description, date);
            achievements.put(idx, a);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        layout = inflater.inflate(R.layout.fragment_achievement, container, false);

        lay_Achievement = (LinearLayout) layout.findViewById(R.id.lay_AchievementContainer);

        achievements = new HashMap<Integer, Achievement>();


        for(int i = 0;i< 16;i++) {
            AchievementObject ao = null;
            try {
                ao = AchievementHandler.getInstance().getAchievement(i);
                addAchievement(i, ao.getEmblem(), ao.getDescription(), ao.getTitle());
            } catch (ServerException e) {
                e.printStackTrace();
            }
        }

        return layout;
    }

}
