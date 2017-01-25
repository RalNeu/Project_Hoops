package ulm.hochschule.project_hoops.utilities;

/**
 * Repräsentiert ein Achievement
 * Created by Ralph on 27.07.2016.
 */
public class AchievementObject {

    private int emblem;
    private String description, title;

    public AchievementObject(int e, String d, String t) {
        emblem = e;
        description = d;
        title = t;
    }

    public int getEmblem() {
        return emblem;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }
}
