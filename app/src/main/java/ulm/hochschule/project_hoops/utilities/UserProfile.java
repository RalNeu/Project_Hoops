package ulm.hochschule.project_hoops.utilities;

import java.sql.Date;
import java.sql.SQLException;

import ulm.hochschule.project_hoops.objects.Coins;

/**
 * Created by Ralph on 18.05.2016.
 */
public class UserProfile {

    private static UserProfile user;

    private String username, forename, surname, email, password, aboutMe, verifCode, achievements;
    private Coins coins;
    private int ranking, highscore, userID, personID, settings;
    private Date gebDat;

    private static boolean userFound = false;

    private UserProfile(String sqlUSER) {

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
            System.out.println(achievements);

            userFound = true;
        } catch (SQLException e) {
            System.err.println("User not found.");
            e.printStackTrace();
        }
    }

    public int getSettings() {
        return settings;
    }

    public static UserProfile getInstance(String userName) {

        if(user == null) {
            user = new UserProfile(userName);
        } else {
            if(user.username.equals(userName)) {
                user = new UserProfile(userName);
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
        SqlManager.getInstance().updateCoins(coins, username);
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

}
