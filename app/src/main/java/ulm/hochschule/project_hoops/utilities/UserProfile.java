package ulm.hochschule.project_hoops.utilities;

import android.app.Activity;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import ulm.hochschule.project_hoops.objects.Coins;

/**
 * Repräsentiert einen eingeloggten User
 * Created by Ralph on 18.05.2016.
 */
public class UserProfile {

    private static UserProfile user;

    private String username, forename, surname, email, password, aboutMe, verifCode, achievements;
    private Coins coins;
    private int ranking, highscore, userID, personID, settings;
    private Date gebDat, lastLogin;
    private boolean isVerified;

    private static boolean userFound = false;

    private Activity a;

    private char[][] boughtAvatarItems;

    /**
     *
     * @param sqlUSER
     * @param a
     */
    public UserProfile(String sqlUSER, Activity a) {
        Object[] userInfo;
        try {
            userInfo = SqlManager.getInstance().getUser(sqlUSER);

            username    =           sqlUSER;
            forename    = (String)  userInfo[3];
            surname     = (String)  userInfo[4];
            email       = (String)  userInfo[5];
            password    = (String)  userInfo[1];
            coins       = (Coins)   userInfo[2];
            gebDat      = (Date)    userInfo[6];
            personID    = (int)     userInfo[7];
            aboutMe     = (String)  userInfo[8];
            settings    = (int)     userInfo[9];
            verifCode   = (String)  userInfo[10];
            userID      = (int)     userInfo[11];
            achievements = (String) userInfo[12];
            lastLogin =     (Date)  userInfo[13];
            isVerified = SqlManager.getInstance().getVerifStatus(sqlUSER);
            AchievementHandler ah = null;
            this.a = a;
            mapBoughtAvatarItems();

            try {
                ah = AchievementHandler.getInstance();
                ah.mapAchievements(userID, achievements);
                ah.performEvent(2, 1, a);
                checkDateYesterday(a, ah);
            } catch (ServerException e) {
            }
            userFound = true;
        } catch (SQLException e) {
            System.err.println("User not found.");
            e.printStackTrace();
        }
    }

    public String getAchievements() {
        return achievements;
    }

    private void mapBoughtAvatarItems() {
        ArrayList<String> items = SqlManager.getInstance().readBoughtAvatarItems(userID);

        boughtAvatarItems = new char[6][20];

        for(int i = 0;i<6;i++) {
            String cur = items.get(i);
            for(int j = 0; j< cur.length();j++) {
                boughtAvatarItems[i][j] = cur.charAt(j);
            }
        }

    }

    public char getItemAt(int item, int idx) {
        return boughtAvatarItems[item][idx];
    }

    public int getMaxItems(int item) {
        return boughtAvatarItems[item].length;
    }

    public int getHighestItemAvailable(int item) {
        int ret = 0;
        for(int i = boughtAvatarItems[item].length-1;i>= 0; i--) {
            if(boughtAvatarItems[item][i] == 'y') {
                ret = i;
                i = -1;
            }
        }
        return ret;
    }

    public void buyItem(int id, int idx, int price){
        if(coins.getCoins() >= price) {

            String[] columnNames = {"hats", "eyes", "backgrounds", "mouths", "skins", "bodies"};

            try {
                SqlManager.getInstance().buyItem(userID, coins.getCoins()-price, columnNames[id], getNewAvatarString(id,idx));
                boughtAvatarItems[id][idx] = 'y';
                coins.updateCoins(price * -1);
                AchievementHandler ah = AchievementHandler.getInstance();
                ah.performEvent(8, 1, a);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ServerException e) {
                e.printStackTrace();
            }
        }
    }

    private String getNewAvatarString(int id, int idx) {
        String ret = "";
        for(int i = 0; i < boughtAvatarItems[id].length;i++) {
            if(i == idx) {
                ret += 'y';
            } else
                ret += boughtAvatarItems[id][i];
        }

        return ret;
    }

    public int getLowestItemAvailable(int item) {
        int ret = boughtAvatarItems[item].length-1;
        for(int i = 0; i< boughtAvatarItems[item].length;i++) {
            if(boughtAvatarItems[item][i] == 'y') {
                ret = i;
                i = boughtAvatarItems[item].length;
            }
        }
        return ret;
    }

    private void checkDateYesterday(Activity a, AchievementHandler ah) {
        Date now = SqlManager.getInstance().getNow();
        long l = now.getTime() - lastLogin.getTime();
        int tagevergangen = (int) l/(1000*60*60*24);

        if(tagevergangen == 0 || tagevergangen == 1) {
            ah.performEvent(3, ah.getTageHintereinander() + tagevergangen, a);
        } else {
            ah.performEvent(3,0,a);
        }
        SqlManager.getInstance().updateLastLogin(userID);
    }

    public int getSettings() {
        return settings;
    }

    public static UserProfile getInstance(String userName, Activity a) {

        if(user == null) {
            user = new UserProfile(userName, a);
        } else {
            if(user.username.equals(userName)) {
                user = new UserProfile(userName, a);
            }
        }
        return user;
    }

    public static UserProfile getInstance() {

        if(user == null) {
            throw new RuntimeException("Please login a user in the first place.");
        }
        return user;
    }

    public static boolean isLoggedIn() {
        if(user == null)
            return false;
        return true;
    }

    public static boolean getUserFound() {
        return userFound;
    }

    public static void logoffUser() {
        //TODO
        if(user != null)
            SqlManager.getInstance().writeUser(user);
        user = null;
    }

    public Date getGebDat() {
        return gebDat;
    }

    public String getEmail(){
        return email;
    }

    public String getVerifCode(){
        return verifCode;
    }

    public String getUsername() {

        return username;
    }

    public int getCoins() {

        return coins.getCoins();
    }

    public void updateCoins(int c) {
        coins.updateCoins(c);
    }

    public int getRanking() {

        return ranking;
    }

    public int getHighscore() {

        return highscore;
    }

    public String getForename() {
        return forename;
    }

    public String getSurname() {
        return surname;
    }

    public int getPersonID() {
        return  personID;
    }

    public String getAboutMe() {
        return aboutMe; //test
    }

    public void update(String forename, String surname, String aboutMe, Date gebDat, int settings) {
        this.forename = forename;
        this.surname = surname;
        this.gebDat = gebDat;
        this.aboutMe = aboutMe;
        this.settings = settings;
    }

    public int getUserID() {
        return userID;
    }
    public boolean getVerifStatus(){
        return this.isVerified;
    }
}

