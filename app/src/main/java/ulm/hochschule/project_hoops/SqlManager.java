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
 * Created by Johann on 14.05.2016.
 */
public class SqlManager {

    private Connection con;
    private Statement st;
    private ResultSet rs;

    //create an object of SingleObject
    private static SqlManager instance = new SqlManager();

    //make the constructor private so that this class cannot be
    //instantiated
    public SqlManager(){
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
            PreparedStatement preparedStmt = con.prepareStatement(query);

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

    public void remove(){
    }
                    /*
                    String query = "select * from users;";


                    rs = st.executeQuery(query);

                    while(rs.next()) {
                        String name = rs.getString("Username");
                        String fname = rs.getString("FirstName");
                        String nname = rs.getString("LastName");
                        System.out.println("User: " + name + "   Vorname: " + fname + "   Nachname: " + nname);
                    }*/


                    /*
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);

                    CookieHandler.setDefault( new CookieManager( null, CookiePolicy.ACCEPT_ALL ));

                    URL url = new URL("http://141.59.26.107/phpScripts/InsertValue");

                    URLConnection conn = url.openConnection();

                    conn.setDoOutput(true);
                    conn.setDoInput(true);

                    conn.connect();

                    BufferedOutputStream out = new BufferedOutputStream(conn.getOutputStream());


                    for(int i = 0;i<100;i++) {
                        out.write(i);
                    }
                    System.out.println("finished");
                    /*BufferedReader br = new BufferedReader(
                            new InputStreamReader(
                                    conn.getInputStream()));
                    String line;

                    String result = "";

                    while ((line = br.readLine()) != null)
                        result += line + "\n";
                    System.out.println(result);

                    br.close();*/

}
