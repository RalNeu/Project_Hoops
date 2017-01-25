package ulm.hochschule.project_hoops.interfaces;

/**
 * Ein Interface um der Acievement-View neue Achievements hinzuzufügen
 * Created by Ralph on 26.07.2016.
 */
public interface AchievementReceiver {

    public void addAchievement(int idx, int emblem, String description, String date);

}