package ulm.hochschule.project_hoops;

import android.nfc.Tag;
import android.os.StrictMode;
import android.util.Log;
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
public class SqlManager {

    private Connection con;
    private Statement st;
    private ResultSet rs;
    private PreparedStatement preparedStmt;




    //create an object of SingleObject
    private static SqlManager instance = new SqlManager();

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

        int personID=0, spielerID=0, accountID=0,erstellDatumID=0;
        String query;

        try {
            //create Lieblingsspieler
            query = "insert into lieblingsspieler()" + "values ()";
            preparedStmt = con.prepareStatement(query);
            preparedStmt.execute();
            //get the primeKey from the Lieblingsspieler
            query ="select lID from lieblingsspieler where lID = (Select Max(lID)from lieblingsspieler)";
            preparedStmt = con.prepareStatement(query);
            rs = preparedStmt.executeQuery();
            rs.beforeFirst();
            while(rs.next()) {
                spielerID = rs.getInt("lID");
                rs.next();
            }
            preparedStmt.execute();


            //Create
            query = "insert into erstelldatum(eDatum)" + "value(Now())";
            preparedStmt = con.prepareStatement(query);
            preparedStmt.execute();
            //get Erstelldatum ID
            query = "select eID from erstelldatum where eID = (Select MAX(eID) from erstelldatum)";
            preparedStmt = con.prepareStatement(query);
            rs= preparedStmt.executeQuery();
            while(rs.next()) {
                erstellDatumID = rs.getInt("eID");
                rs.next();
            }


            // create the mysql insert for person
            query = " insert into person ( email, vorname, nachname,lID)"
                    + " values (?, ?, ?,?)";
            preparedStmt = con.prepareStatement(query);
            preparedStmt.setString(1, email);
            preparedStmt.setString(2, firstName);
            preparedStmt.setString(3, lastName);
            preparedStmt.setInt(4,spielerID);
            preparedStmt.execute();

            //get the primeKey from the person
            query ="select pID from person where pID = (Select MAX(pID) from person)";
            preparedStmt = con.prepareStatement(query);
            rs= preparedStmt.executeQuery();
            while(rs.next()) {
                personID = rs.getInt("pID");
                rs.next();
            }

            //create Account
            query ="insert into account(username,password, pID,eID,lastlogin)"+"value(?,?,?,?,NOW())";
            preparedStmt = con.prepareStatement(query);
            preparedStmt.setString(1, userName);
            preparedStmt.setString(2,password);
            preparedStmt.setInt(3,personID);
            preparedStmt.setInt(4,erstellDatumID);
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

    public void setVerif_Status(boolean bool, int id) {
        try {
            String query ="UPDATE users SET Verif_Status=? WHERE UserID=? ";
            preparedStmt = con.prepareStatement(query);
            preparedStmt.setBoolean(1, bool);
            preparedStmt.setInt(2,id);
            preparedStmt.executeUpdate();
        }
        catch(Exception e) {
            e.getStackTrace();
        }

    }

    public boolean userExist(String name){
        boolean response = false;
        try {
            String query = "select case WHEN (select count(*) from users where Username='"+name+"') > 0  THEN 1 ELSE 0 END AS Abfrage;";
            preparedStmt = con.prepareStatement(query);
            rs = preparedStmt.executeQuery();
            rs.next();
            response = rs.getBoolean("Abfrage");
        }catch(Exception e){
            e.printStackTrace();
        }
        return response;
    }

    public Object[] getUser(String userName) {
        int  personID=0;

        Object[] retArray = new Object[5];
        try {
            String query="select * from account where Username = ?";
            preparedStmt = con.prepareStatement(query);
            preparedStmt.setString(1,userName);
            rs = preparedStmt.executeQuery();
            rs.beforeFirst();
            rs.next();

            retArray[0] = rs.getString("username");
            retArray[1] = rs.getString("Password");
            retArray[2] = new Coins(rs.getInt("Coins"));
            personID = rs.getInt("pID");



            query = "select * from person where aID = ?";
            preparedStmt = con.prepareStatement(query);
            preparedStmt.setInt(1,personID);
            rs = preparedStmt.executeQuery();
            rs.beforeFirst();
            rs.next();

            retArray[3] = rs.getString("vorname");
            retArray[4] = rs.getString("nachname");
            retArray[5] = rs.getString("email");

        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        return retArray;
    }

    public void writeUser(UserProfile user) {
        //TODO
    }

}
