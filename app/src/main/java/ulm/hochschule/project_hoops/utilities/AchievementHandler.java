package ulm.hochschule.project_hoops.utilities;

import java.util.Deque;
import java.util.LinkedList;

import ulm.hochschule.project_hoops.Interfaces.AchievementReceiver;
import ulm.hochschule.project_hoops.Views.Achievement;

/**
 * Created by Ralph on 26.07.2016.
 */
public class AchievementHandler {

    private AchievementReceiver ar;
    private static AchievementHandler instance;

    private LinkedList<Integer> eventQueue;

    private AchievementHandler () {
       eventQueue = new LinkedList<Integer>();
    }

    public static AchievementHandler getInstance() {
        if (instance == null) {
            instance = new AchievementHandler();
        }
        return instance;
    }

    public void setAchievementReceiver(AchievementReceiver ar) {
        this.ar = ar;
    }

    public static void addEvent(int event) {
        instance.eventQueue.add(event);
    }
}
