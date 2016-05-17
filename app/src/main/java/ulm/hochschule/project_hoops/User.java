package ulm.hochschule.project_hoops;

/**
 * Created by Johann on 03.05.2016.
 */
public class User {

    private int id, score;
    private final String firstname, lastname,email,username,password;
    private Coins coins;

    public User(int id, String firstname, String lastname, String email, String username, String password, int coins){
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.username = username;
        this.password = password;
        this.coins = new Coins(coins);
        score = 0;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getLastname() { return lastname; }

    public String getFirstname() { return firstname; }

    public int getid(){ return id; }

    public Coins getCoinsObject(){ return coins; }
}
