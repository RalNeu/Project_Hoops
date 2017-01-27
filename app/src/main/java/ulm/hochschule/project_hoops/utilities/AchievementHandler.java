package ulm.hochschule.project_hoops.utilities;

import android.app.Activity;

import java.util.HashMap;

import ulm.hochschule.project_hoops.R;

/**
 * Überwacht die Achievement-Events und übernimmt dann den Rest, wie die Datenbank zu aktualisieren und eine Pushnachricht zu versenden.
 * Created by Ralph on 26.07.2016.
 */
public class AchievementHandler {

    private static AchievementHandler instance;

    private final int N = 16;

    private HashMap<Integer, Integer> achievements;
    private int[] achievementreference;
    private int[] achiementStatus;
    private AchievementStrategyHandler ash = new AchievementStrategyHandler();

    private String[] descriptions;
    private String[] titles;
    private int aID;
    private int tageHintereinander = 0;
    private boolean achievementsMapped = false;

    private AchievementHandler () throws ServerException{
        achievements = new HashMap<Integer, Integer>();
        achiementStatus = new int[N];
        for(int i = 0; i< N;i++) {
            achiementStatus[i] = 0;
        }
        instantiateVariables();
    }

    /**
     * Holt zuerst vom Server die Achievement-Kriterien, also welche Bedingungen erfüllt sein müssen, damit ein Achievement erreicht wird
     * und dann setzt er die Beschreibungen und Titel der Achievements.
     * @throws ServerException
     */
    private void instantiateVariables() throws ServerException{

        String[] referenceString = ServerCommunicate.getInstance().getAch().split("/");
        achievementreference = new int[referenceString.length];
        for(int i = 0;i < referenceString.length;i++) {
            achievementreference[i] = Integer.parseInt(referenceString[i]);
        }

        descriptions = new String[N];
        descriptions[0] = "Sie haben sich erfolgreich registriert!";
        descriptions[1] = "Sie haben Ihr Profil verifiziert!";
        descriptions[2] = "Sie haben sich öfter als / mal eingeloggt!";
        descriptions[3] = "Sie haben sich öfter als / mal hintereinander eingeloggt!";
        descriptions[4] = "Sie haben Ihr Profil öfter als / mal bearbeitet!";
        descriptions[5] = "Sie haben öfter als / mal Ihren Namen geändert!";
        descriptions[6] = "Sie haben öfter als 3 mal Ihr Geburtsdatum geändert!";
        descriptions[7] = "Sie haben öfter als / mal die Information \"Über mich\" geändert!";
        descriptions[8] = "Sie haben mindestens / Accessoires gekauft!";
        descriptions[9] = "Sie haben öfter als / mal Ihren Avatar geändert!";
        descriptions[10] = "Sie haben mindestens / mal auf Ulm getippt!";
        descriptions[11] = "Sie haben mindestens / mal auf den Gegner getippt!";
        descriptions[12] = "Sie haben öfter als / mal richtig getippt!";
        descriptions[13] = "Sie haben in einem Tipp mehr als / Coins gesetzt!";
        descriptions[14] = "Sie haben mehr als / Coins über das Tippspiel verdient!";
        descriptions[15] = "Sie haben einen Highscore von / übertroffen!";

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

    public static AchievementHandler getInstance() throws ServerException{
        if (instance == null) {
            instance = new AchievementHandler();
        }
        return instance;
    }

    public int getTageHintereinander() {
        return tageHintereinander;
    }


    /**
     * Da AchievementHandler ein Singleton ist, kann jede Klasse jederzeit ein Event auslösen. Der Handler kümmert sich dann darum.
     * @param event Die ID des Achievements, das ausgelöst werden soll
     * @param val Den Wert um den sich das Achievement ändern soll. Hier gibt es unterschiedliche Strategien. Bei manchen Achievements bedeutet val,
     *            dass der Wert aufaddiert werden soll, bei manchen wird der alte Wert durch val ersetzt. Dazu gibt es ein Strategy-Pattern,
     *            das anhand der ID die richtige Strategie ausführt.
     * @param context Nötig um eine Pushnachricht zu versenden
     */
    public synchronized void performEvent(int event, int val, Activity context) {

        if(!achievementsMapped) {
            mapAchievements(UserProfile.getInstance().getUserID(), UserProfile.getInstance().getAchievements());
        }

        if(achiementStatus[event] != 3) {
            //int newVal = achievements.get(event) + val;
            int newVal = ash.getStrategy(event).changeValue(achievements.get(event), val, achievementreference[event*3+2]);

            if(event == 3)
                tageHintereinander = val;
            
            achievements.put(event, newVal);
            if(checkForAchievement(event)) {
                new NotifyManager().sendNotify(event, context.getResources().getString(R.string.newAch), titles[event], context, R.drawable.achievement_icon);
            }
            saveAchievements();
        }
    }

    /**
     * Beim Einloggen werden die Achievements aus der Datenbank geladen und hier eingefügt.
     * @param aID Speicherung der User-ID
     * @param achievements Die Achievements
     */
    public void mapAchievements(int aID, String achievements) {
        this.aID = aID;
        String[] s = achievements.split("/");

        for (int i = 0; i<s.length; i++) {
            if(i == 3) {
                String[] t = s[i].split("-");
                this.achievements.put(i, Integer.parseInt(t[0]));
                tageHintereinander = Integer.parseInt(t[1]);
            } else
                this.achievements.put(i, Integer.parseInt(s[i]));

        }
        checkForAchievement();
        achievementsMapped = true;
    }

    /**
     * prüft beim mappen, ob die Achievements bereits erreicht wurden oder nicht
     */
    private void checkForAchievement() {
        for(int i = 0;i<16;i++) {
            checkForAchievement(i);
        }
    }

    /**
     * Prüft für ein Achievement ob es bereits erreicht ist und in welchem Grad(Bronze, Silber, Gold)
     * @param i Die Position des Achievements
     * @return ob das Achievement erreicht wurde.
     */
    private boolean checkForAchievement(int i) {
        int cur = achievements.get(i);
        int temp = achiementStatus[i];
        achiementStatus[i] = 0;
        for (int j = 0;j < 3;j++) {
            if(achievementreference[i*3+j] <= cur) {
                achiementStatus[i]++;
            } else {
                j = 3;
            }
        }
        return temp < achiementStatus[i];
    }

    /**
     * Speichert die Achievements ab
     */
    private void saveAchievements() {
        SqlManager.getInstance().updateAchievements(aID, formAchievementString());
    }

    /**
     * Gibt das gwünschte Achievements zurück
     * @param i Nummer des Achievements
     * @return Ein Objekt dass das Achievement repräsentiert
     */
    public AchievementObject getAchievement(int i) {
        checkForAchievement(i);
        int e = achiementStatus[i];
        String d, t;

        if (e == 0) {
            d = "???";
            t = "???";
        } else {
            if(i != 0 && i != 1 && i != 6) {
                String[] s = descriptions[i].split("/");
                d = s[0] + achievementreference[i * 3 + e - 1] + s[1];
            } else {
                d = descriptions[i];
            }
            t = titles[i];
        }

        return new AchievementObject(e,d,t);
    }

    /**
     * Bildet anhand der Achievements einen Konfiurationsstring, der in der Datenbank abgespeichert wird.
     * @return Der Achievement-String
     */
    private String formAchievementString() {
        String ret = "";

        for (int i = 0; i< N; i++) {
            ret += achievements.get(i);
            if(i == 3) {
                ret += "-" + tageHintereinander;
            }
            if(i != 15) {
                ret += "/";
            }
        }

        return ret;
    }
}
