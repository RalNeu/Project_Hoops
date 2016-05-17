package ulm.hochschule.project_hoops;

import android.os.StrictMode;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLInvalidAuthorizationSpecException;
import java.sql.Statement;
import java.util.Calendar;

/**
 * Created by Johann /PatrickW on 14.05.2016.
 */
public class SqlManager {

    private StrictMode.ThreadPolicy policy;
    private Connection con;
    private Statement st;
    private ResultSet rs;
    private java.sql.Date startDate;
    private String query;


    //create an object of SingleObject
    private static SqlManager instance ;

    //make the constructor private so that this class cannot be
    //instantiated
    private SqlManager(){
        try {
            policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            CookieHandler.setDefault(new CookieManager(null, CookiePolicy.ACCEPT_ALL));
            Connection con;

            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://141.59.26.107:3306/hoops", "SuperUser", "root");
            st = con.createStatement();





        }catch (Exception e){
            System.out.print("Fehler");
            e.printStackTrace();
        }

    }

    //Get the only object available
    public static SqlManager getInstance()
    {
        if(instance == null)
        {
            instance = new SqlManager();
        }
        return instance;
    }


    public void registerUser(String firstName,String lastName,String eMail, String userName, String password)
    {
        try {
            Calendar calendar = Calendar.getInstance();
            startDate = new java.sql.Date(calendar.getTime().getTime());
            // the mysql insert statement
            query = " insert into users (Username, Password, EmailAdress, FirstName, LastName, Coins, ChatBan, CreateDate, LastLogin)"
                    + " values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
// create the mysql insert preparedstatement
            PreparedStatement preparedStmt = con.prepareStatement(query);

            preparedStmt.setString(1, userName);
            preparedStmt.setString(2, password);
            preparedStmt.setString(3, eMail);
            preparedStmt.setString(4, firstName);
            preparedStmt.setString(5, lastName);
            preparedStmt.setLong(6, 1361);
            preparedStmt.setBoolean(7, true);
            preparedStmt.setDate(8, startDate);
            preparedStmt.setDate(9, startDate);
        }catch (Exception e){
            System.out.print("Fehler");
            e.printStackTrace();
        }
    }

    public void add(User user){
    }

    public void remove(){
    }
}
