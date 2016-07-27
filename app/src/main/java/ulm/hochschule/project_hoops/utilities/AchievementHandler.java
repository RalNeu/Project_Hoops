package ulm.hochschule.project_hoops.utilities;

import java.util.HashMap;

import ulm.hochschule.project_hoops.interfaces.AchievementReceiver;

/**
 * Created by Ralph on 26.07.2016.
 */
public class AchievementHandler {

    private static AchievementHandler instance;

    private final int N = 16;

    private HashMap<Integer, Integer> achievements;
    private int[] achievementreference;
    private int[] achiementStatus;

    private String[] descriptions;
    private String[] titles;

    private AchievementHandler () {
        achievements = new HashMap<Integer, Integer>();
        achiementStatus = new int[N];
        for(int i = 0; i< N;i++) {
            achiementStatus[i] = 0;
        }
        instantiateVariables();
    }


    private void instantiateVariables() {
        String reference =
                "1/1/1/" +
                "1/1/1/" +
                "10/30/75/" +
                "3/7/15/" +
                "5/20/50/" +
                "3/8/15/" +
                "3/3/3/" +
                "10/30/100/" +
                "5/15/30/" +
                "5/15/30/" +
                "1/10/30/" +
                "1/10/30/" +
                "5/20/50/" +
                "500/3000/10000/" +
                "2000/8000/20000/" +
                "1000/5000/15000";

        String[] referenceString = reference.split("/");
        achievementreference = new int[referenceString.length];
        for(int i = 0;i < referenceString.length;i++) {
            achievementreference[i] = Integer.parseInt(referenceString[i]);
        }

        descriptions = new String[N];
        descriptions[0] = "Sie haben sich erfolgreich registriert!";
        descriptions[1] = "Sie haben Ihr Profil verifiziert!";
        descriptions[2] = "Sie haben sich öfter als ? mal eingeloggt!";
        descriptions[3] = "Sie haben sich öfter als ? mal hintereinander eingeloggt!";
        descriptions[4] = "Sie haben Ihr Profil öfter als ? mal bearbeitet!";
        descriptions[5] = "Sie haben öfter als ? Ihren Namen geändert!";
        descriptions[6] = "Sie haben öfter als 3 mal Ihr Geburtsdatum geändert!";
        descriptions[7] = "Sie haben öfter als ? mal die Information \"Über mich\" geändert!";
        descriptions[8] = "Sie haben mindestens ? Accessoires gekauft!";
        descriptions[9] = "Sie haben öfter als ? Ihren Avatar geändert!";
        descriptions[10] = "Sie haben mindestens ? mal auf Ulm getippt!";
        descriptions[11] = "Sie haben mindestens ? mal auf den Gegner getippt!";
        descriptions[12] = "Sie haben öfter als ? mal richtig getippt!";
        descriptions[13] = "Sie haben in einem Tipp mehr als ? Coins gesetzt!";
        descriptions[14] = "Sie haben mehr als ? Coins über das Tippspiel verdient!";
        descriptions[15] = "Sie haben einen Highscore von ? übertroffen!";

        titles = new String[N];

        titles[0] = "Ein Teil der Community";
        titles[1] = "Ich kann es beweisen!";
        titles[2] = "Wiederkehr macht Freude";
        titles[3] = "Junkie";
        titles[4] = "Gefällt mir nicht mehr";
        titles[5] = "Who am I?";
        titles[6] = "Jünger als man aussieht";
        titles[7] = "Wer rastet der rostet";
        titles[8] = "Von außen Hui";
        titles[9] = "Das Geheimnis der Promis";
        titles[10] = "Ich steh zu meinem Team!";
        titles[11] = "Fremdgehen";
        titles[12] = "Ich kenn mich halt aus";
        titles[13] = "All in";
        titles[14] = "Ein lukratives Geschäft";
        titles[15] = "Ich will der Allerbeste sein!";
    }

    public static AchievementHandler getInstance() {
        if (instance == null) {
            instance = new AchievementHandler();
        }
        return instance;
    }

    public static void addEvent(int event, AchievementReceiver ar) {

    }

    public void mapAchievements(String achievements) {
        String[] s = achievements.split("/");

        for (int i = 0; i<s.length; i++) {
            this.achievements.put(i, Integer.parseInt(s[i]));
        }
        checkForAchievement();
    }

    private void checkForAchievement() {
        for(int i = 0;i<16;i++) {
            checkForAchievement(i);
        }
    }

    private void checkForAchievement(int i) {
        int cur = achievements.get(i);
        achiementStatus[i] = 0;
        for (int j = 0;j < 3;j++) {
            if(achievementreference[i*3+j] <= cur) {
                achiementStatus[i]++;
            } else {
                j = 3;
            }
        }
    }

    public AchievementObject getAchievement(int i) {
        checkForAchievement(i);
        int e = achiementStatus[i];
        String d, t;

        if (e == 0) {
            d = "???";
            t = "???";
        } else {
            d = descriptions[i];
            d.replace("?", "" + achievementreference[i*3+e]);
            t = titles[i];
        }

        return new AchievementObject(e,d,t);

    }


}
