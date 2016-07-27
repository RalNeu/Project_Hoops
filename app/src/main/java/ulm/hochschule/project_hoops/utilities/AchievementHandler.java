package ulm.hochschule.project_hoops.utilities;

import ulm.hochschule.project_hoops.interfaces.AchievementReceiver;

/**
 * Created by Ralph on 26.07.2016.
 */
public class AchievementHandler {

    private static AchievementHandler instance;


    private AchievementHandler () {
    }

    public static AchievementHandler getInstance() {
        if (instance == null) {
            instance = new AchievementHandler();
        }
        return instance;
    }

    public static void addEvent(int event, AchievementReceiver ar) {

    }
}
