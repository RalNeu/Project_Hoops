package ulm.hochschule.project_hoops;

import java.sql.SQLInvalidAuthorizationSpecException;

/**
 * Created by Johann on 14.05.2016.
 */
public class SqlManager {

    //create an object of SingleObject
    private static SqlManager instance = new SqlManager();

    //make the constructor private so that this class cannot be
    //instantiated
    private SqlManager(){}

    //Get the only object available
    public static SqlManager getInstance(){
        return instance;
    }

    public void add(User user){
    }

    public void remove(){
    }
}
