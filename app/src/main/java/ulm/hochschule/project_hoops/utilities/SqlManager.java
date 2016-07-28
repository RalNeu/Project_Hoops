package ulm.hochschule.project_hoops.utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.StrictMode;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;

import ulm.hochschule.project_hoops.objects.Coins;

/**
 * Created by Johann on 14.05.2016.
 */
public class SqlManager {

    private Connection con;
    private Statement st;
    private ResultSet rs;
    private PreparedStatement preparedStmt;

    //create an object of SingleObject
    private static SqlManager instance;

    //make the constructor private so that this class cannot be
    //instantiated

    private SqlManager() {

        try {
             StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            CookieHandler.setDefault(new CookieManager(null, CookiePolicy.ACCEPT_ALL));

            Class.forName("com.mysql.jdbc.Driver");

            con = DriverManager.getConnection("jdbc:mysql://141.59.26.107:3306/hoops", "SuperUser", "root");
            st = con.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private SqlManager(boolean f) {
        try {
            Class.forName("com.mysql.jdbc.Driver");

            con = DriverManager.getConnection("jdbc:mysql://141.59.26.107:3306/hoops", "SuperUser", "root");
            st = con.createStatement();
        } catch (Exception e) {
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

    public static SqlManager getInstanceWithoutStrictMode() {
        if(instance == null){
            instance = new SqlManager(true);
        }
        return instance;
    }

    public void createUser(String firstName, String lastName, String email, String userName, String password) {
        createUser(firstName, lastName, email, userName, password, "1/0/1/0/0/0/0/0/0/0/0/0/0/0/0/0");
    }

    public void createUser(String firstName, String lastName, String email, String userName, String password, String achievements, int mod){
        createUser(firstName, lastName, email, userName, password, achievements);
    }

    private void createUser(String firstName, String lastName, String email, String userName, String password, String achievements){
        Calendar calendar = Calendar.getInstance();

        Calendar c = Calendar.getInstance();
        c.set(2000, 1, 1);


        int personID=0, spielerID=0, erstellDatumID=0;
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
            query = "insert into person ( email, vorname, nachname,lID, geburtsdatum, hobbies)"
                    + " values (?, ?, ?, ?, ?, ?)";
            preparedStmt = con.prepareStatement(query);
            preparedStmt.setString(1, email);
            preparedStmt.setString(2, firstName);
            preparedStmt.setString(3, lastName);
            preparedStmt.setInt(4,spielerID);
            preparedStmt.setDate(5, new Date(100,1,1)); //TODO
            preparedStmt.setString(6, "");
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
            query ="insert into account(username, password, pID, eID, lastlogin, achievements) values (?, ?, ?, ?, NOW(), ?)";
            preparedStmt = con.prepareStatement(query);
            preparedStmt.setString(1, userName);
            preparedStmt.setString(2,password);
            preparedStmt.setInt(3,personID);

            preparedStmt.setInt(4,erstellDatumID);
            preparedStmt.setString(5, achievements);
            preparedStmt.execute();

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void updateAchievements(int aID, String achievements) {
        try {
            String query = "update account set achievements = '" + achievements + "' where aID = '" + aID + "'";
            preparedStmt = con.prepareStatement(query);
            preparedStmt.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public void remove(String accnametodelete){
        try {
            int pID = 0;
            int erstellID = 0;
            int lieblingsID = 0;
            //Gette die pID des zu löschendes Datensatzes um zu wissen, zu welcher Person dieser
            //gehört. Danach kann man die pID verwenden um die Person zu löschen!
            String hilfsquery = "SELECT pID,eID from account where username=?";
            preparedStmt = con.prepareStatement(hilfsquery);
            preparedStmt.setString(1, accnametodelete);
            rs=preparedStmt.executeQuery();
            rs.next();
            pID=rs.getInt("pID");
            erstellID = rs.getInt("eID");


            String query = "delete from account where username = ?";
            preparedStmt = con.prepareStatement(query);
            preparedStmt.setString(1,accnametodelete);
            preparedStmt.execute();

            hilfsquery = "select lID from person where pID=?";
            preparedStmt = con.prepareStatement(hilfsquery);
            preparedStmt.setInt(1,pID);
            rs = preparedStmt.executeQuery();
            rs.next();
            lieblingsID = rs.getInt("lID");


            query = "delete from person where pID=?";
            preparedStmt = con.prepareStatement(query);
            preparedStmt.setInt(1,pID);
            preparedStmt.execute();

            query = "delete from erstelldatum where eID=?";
            preparedStmt = con.prepareStatement(query);
            preparedStmt.setInt(1,erstellID);
            preparedStmt.execute();

            query = "delete from lieblingsspieler where lID=?";
            preparedStmt = con.prepareStatement(query);
            preparedStmt.setInt(1,lieblingsID);
            preparedStmt.execute();

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void setVerif_Status(int intwert, int id) {
        try {
            String query ="UPDATE account SET verified=? WHERE aID=? ";
            preparedStmt = con.prepareStatement(query);
            preparedStmt.setInt(1, intwert);
            preparedStmt.setInt(2,id);
            preparedStmt.executeUpdate();
        }
        catch(Exception e) {
            e.getStackTrace();
        }

    }

    public void setVerif_Code(String username, String code){
        try{
            String query = "UPDATE account SET verified_string=? WHERE username=?";
            preparedStmt = con.prepareStatement(query);
            preparedStmt.setString(1,code);
            preparedStmt.setString(2,username);
            preparedStmt.execute();
        }catch (Exception e){
            e.getStackTrace();
        }
    }

    public void setStatus(String username, String status){
        try {
            String query = "UPDATE account SET status=? WHERE username=?";
            preparedStmt = con.prepareStatement(query);
            preparedStmt.setString(1,status);
            preparedStmt.setString(2,username);
            preparedStmt.execute();
        }catch (Exception e){
            e.getStackTrace();
        }
    }

    public boolean userExist(String name){
        boolean response = false;
        try {
            String query = "select case WHEN (select count(*) from account where username='"+name+"') > 0  THEN 1 ELSE 0 END AS Abfrage;";
            preparedStmt = con.prepareStatement(query);
            rs = preparedStmt.executeQuery();
            rs.next();
            response = rs.getBoolean("Abfrage");
        }catch(Exception e){
            e.printStackTrace();
        }
        return response;
    }

    public boolean emailExist(String email){ //TODO : testen obs geht
        boolean response = false;
        try {
            String query = "select case WHEN (select count(*) from person where email='"+email+"') > 0  THEN 1 ELSE 0 END AS Abfrage;";
            preparedStmt = con.prepareStatement(query);
            rs = preparedStmt.executeQuery();
            rs.next();
            response = rs.getBoolean("Abfrage");
        }catch(Exception e){
            e.printStackTrace();
        }
        return response;
    }

    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public Object[] getUser(String userName) throws java.sql.SQLException{
        int  personID=0;

        Object[] retArray = new Object[13];

        String query="select * from account where username = ?";
        preparedStmt = con.prepareStatement(query);
        preparedStmt.setString(1,userName);
        rs = preparedStmt.executeQuery();
        rs.beforeFirst();
        rs.next();

        retArray[0] = rs.getString("username");
        retArray[1] = rs.getString("password");
        retArray[2] = new Coins(rs.getInt("coins"));

        retArray[9] = rs.getInt("einstellung");
        retArray[10] = rs.getString("verified_string");
        retArray[11] = rs.getInt("aID");
        retArray[12] = rs.getString("achievements");
        personID = rs.getInt("pID");



        query = "select * from person where pID = ?";
        preparedStmt = con.prepareStatement(query);
        preparedStmt.setInt(1,personID);
        rs = preparedStmt.executeQuery();
        rs.beforeFirst();
        rs.next();

        retArray[3] = rs.getString("vorname");
        retArray[4] = rs.getString("nachname");
        retArray[5] = rs.getString("email");
        retArray[6] = rs.getDate("geburtsdatum");
        retArray[7] = personID;
        retArray[8] = rs.getString("hobbies");

        return retArray;
    }

    public void writeUser(UserProfile user) {
        //TODO
    }

    public void updatePerson(int personID, String col, String val) {
        try {
            String query = "update person set " + col + " = '" + val + "' where pID = '" + personID + "'";
            preparedStmt = con.prepareStatement(query);
            preparedStmt.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public void updatePerson(int personID, String col, Date val) {
        try {
            String query = "update person set " + col + " = '" + val +"' where pID = '" + personID + "'";
            preparedStmt = con.prepareStatement(query);
            preparedStmt.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateAccount(String username, String col, int val) {
        try {
            String query = "update account set " + col + " = '" + val +"' where username = '" + username + "'";
            preparedStmt = con.prepareStatement(query);
            preparedStmt.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public String getPassword(String username) throws java.sql.SQLException{//TODO: testen obs geht

        String result = "";

        String query="select password from account where username=?";
        preparedStmt = con.prepareStatement(query);
        preparedStmt.setString(1,username);
        rs = preparedStmt.executeQuery();
        rs.beforeFirst();

        rs.next();

        result = rs.getString("password");

        return result;
    }

    public boolean isVerified(String username) throws SQLException {
        boolean retVal = false;

        String query = "select verified, username from account where username=?";

        preparedStmt = con.prepareStatement(query);

        preparedStmt.setString(1,username);
        rs = preparedStmt.executeQuery();
        rs.next();

        retVal = ((int) rs.getInt("verified")) == 0;

        return retVal;
    }

    public boolean getVerifStatus(String username) {
        boolean retVal = false;

        String query = "select verified, username from account where username=?";

        try {
            preparedStmt = con.prepareStatement(query);

        preparedStmt.setString(1,username);
        rs = preparedStmt.executeQuery();
        rs.next();

        retVal = ((int) rs.getInt("verified")) == 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return retVal;
    }

    public void updateCoins(Coins c, String username) {
        String query = "update account set coins = '" + c.getCoins() + "' where username = '" + username + "'";

        try {
            preparedStmt = con.prepareStatement(query);
            preparedStmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getVerif_Code(String username){
        String result = "";

        try{
            String query= "SELECT verified_string from account WHERE username=?";
            preparedStmt = con.prepareStatement(query);
            preparedStmt.setString(1,username);
            rs = preparedStmt.executeQuery();
            rs.beforeFirst();
            rs.next();
            result = rs.getString("verified_string");
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }
}
