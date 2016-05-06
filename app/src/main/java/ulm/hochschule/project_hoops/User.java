package ulm.hochschule.project_hoops;

/**
 * Created by Johann on 03.05.2016.
 */
public class User {

    private final String firstname, lastname,email,username,password;

    public User(String firstname, String lastname, String email, String username, String password){
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.username = username;
        this.password = password;
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

    public String getLastname() {
        return lastname;
    }

    public String getFirstname() {

        return firstname;
    }
}
