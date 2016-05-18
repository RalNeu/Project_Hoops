package ulm.hochschule.project_hoops;

/**
 * Created by Ralph on 18.05.2016.
 */
public class UserProfile {

    private static UserProfile user;

    private String username;
    private Coins coins;
    private int ranking;
    private int highscore;
    private String surname;
    private String name;



    private UserProfile(String sqlUSER) {

        username = "TestUser";
        coins = new Coins(10313);
        ranking = 1;
        highscore = 10000;
        surname = "Neumann";
        name = "Ralph";

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
