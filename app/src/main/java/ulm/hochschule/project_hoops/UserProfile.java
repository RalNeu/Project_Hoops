package ulm.hochschule.project_hoops;

/**
 * Created by Ralph on 18.05.2016.
 */
public class UserProfile {

    private static UserProfile user;

    private String username, forename, surname, email, password;
    private Coins coins;
    private int ranking, highscore, userID;

    private UserProfile(String sqlUSER) {

        Object[] userInfo = SqlManager.getInstance().getUser(sqlUSER);

        username = sqlUSER;
        forename = (String) userInfo[0];
        surname = (String) userInfo[1];
        email = (String) userInfo[2];
        password = (String) userInfo[3];

        coins = (Coins) userInfo[4];

        userID = (int) userInfo[5];
    }

    public static UserProfile getInstance() {

        if(user == null) {
            user = new UserProfile("");
    }

        return user;
    }

    public static void logoffUser() {

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

}
