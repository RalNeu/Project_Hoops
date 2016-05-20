package ulm.hochschule.project_hoops;

import java.sql.SQLException;

/**
 * Created by Ralph on 18.05.2016.
 */
public class UserProfile {

    private static UserProfile user;

    private String username, forename, surname, email, password;
    private Coins coins;
    private int ranking, highscore, userID;

    private static boolean userFound = false;

    private UserProfile(String sqlUSER) {

        Object[] userInfo = new Object[0];
        try {
            userInfo = SqlManager.getInstance().getUser(sqlUSER);

            username    =           sqlUSER;
            forename    = (String)  userInfo[3];
            surname     = (String)  userInfo[4];
            email       = (String)  userInfo[5];
            password    = (String)  userInfo[1];
            coins       = (Coins)   userInfo[2];

            userFound = true;
        } catch (SQLException e) {
            System.err.println("User not found.");
            e.printStackTrace();
        }
    }

    public static UserProfile getInstance(String userName) {

        if(user == null) {
            user = new UserProfile(userName);
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

    public String getUsername() {

        return username;
    }

    public int getCoins() {

        return coins.getCoins();
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

}
