package ulm.hochschule.project_hoops;

import android.os.AsyncTask;
import android.os.StrictMode;
import android.widget.Toast;

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
 * Created by Johann on 14.05.2016.
 */
public class SqlManager extends AsyncTask<String, String, String>{

    private Connection con;
    private Statement st;
    private ResultSet rs;
    private PreparedStatement preparedStmt;

    //create an object of SingleObject
    private static SqlManager instance = new SqlManager();

    @Override
    protected String doInBackground(String... s) {
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }

    //make the constructor private so that this class cannot be
    //instantiated
    private  SqlManager(){
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            CookieHandler.setDefault(new CookieManager(null, CookiePolicy.ACCEPT_ALL));

            Class.forName("com.mysql.jdbc.Driver");

            con = DriverManager.getConnection("jdbc:mysql://141.59.26.107:3306/hoops", "SuperUser", "root");
            st = con.createStatement();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //Get the only object available
    public static SqlManager getInstance(){
        if(instance == null){
            instance = new SqlManager();
        }
        return instance;
    }

    public void createUser(String firstName, String lastName, String email, String userName, String password){
        Calendar calendar = Calendar.getInstance();
        java.sql.Date startDate = new java.sql.Date(calendar.getTime().getTime());

        // the mysql insert statement
        String query = " insert into users (Username, Password, EmailAdress, FirstName, LastName, Coins, ChatBan, CreateDate, LastLogin)"
                + " values (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            // create the mysql insert preparedstatement
            preparedStmt = con.prepareStatement(query);

            preparedStmt.setString(1, userName);
            preparedStmt.setString(2, password);
            preparedStmt.setString(3, email);
            preparedStmt.setString(4, firstName);
            preparedStmt.setString(5, lastName);
            //Coins
            preparedStmt.setLong(6, 0);

            preparedStmt.setBoolean(7, false);
            preparedStmt.setDate(8, startDate);
            preparedStmt.setDate(9, startDate);

            // execute the preparedstatement
            preparedStmt.execute();

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void remove(int i){
        try {
            String query = "delete from users where UserID = ?";
            preparedStmt = con.prepareStatement(query);
            preparedStmt.setInt(1, i);
            preparedStmt.execute();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void setVerif_Status(boolean bool, int id)
    {
        try {
            String query ="UPDATE users SET Verif_Status=? WHERE UserID=? ";
            preparedStmt = con.prepareStatement(query);
            preparedStmt.setBoolean(1, bool);
            preparedStmt.setInt(2,id);
            preparedStmt.executeUpdate();
        }
        catch(Exception e)
        {
            e.getStackTrace();
        }

    }

    public boolean userExist(String name){
        try {
            String query = "select case WHEN (select count(*) from users where Username='name') > 0  THEN 1 ELSE 0 END;";
            preparedStmt = con.prepareStatement(query);
            rs = preparedStmt.executeQuery();
            System.out.print(rs.toString());
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean passwordExists(String username){
        return false;
    }
}
