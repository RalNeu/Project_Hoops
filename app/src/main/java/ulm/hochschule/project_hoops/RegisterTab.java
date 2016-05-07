package ulm.hochschule.project_hoops;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.sql.*;
import java.util.Calendar;

/**
 * Created by Johann on 06.05.2016.
 */
public class RegisterTab extends Fragment {

    private View layout;

    private String firstname;
    private String lastname;
    private String email;
    private String username;
    private String password;
    private String repeatpassword;
    private Button bregister;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        layout = inflater.inflate(R.layout.fragment_register, container, false);

        firstname = layout.findViewById(R.id.et_Firstname).toString();
        lastname = layout.findViewById(R.id.et_Lastname).toString();
        email = layout.findViewById(R.id.et_Email).toString();
        username = layout.findViewById(R.id.et_Username).toString();
        password = layout.findViewById(R.id.et_Password).toString();
        repeatpassword = layout.findViewById(R.id.et_ConfirmPassword).toString();
        bregister = (Button) layout.findViewById(R.id.btn_ContentRegister);
        return layout;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        bregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);

                    CookieHandler.setDefault( new CookieManager( null, CookiePolicy.ACCEPT_ALL ));
                    Connection con;
                    Statement st;
                    ResultSet rs;

                    Class.forName("com.mysql.jdbc.Driver");

                    con = DriverManager.getConnection("jdbc:mysql://141.59.26.107:3306/hoops", "SuperUser", "root");
                    st = con.createStatement();

                    Calendar calendar = Calendar.getInstance();
                    java.sql.Date startDate = new java.sql.Date(calendar.getTime().getTime());

                    // the mysql insert statement
                    String query = " insert into users (Username, Password, EmailAdress, FirstName, LastName, Coins, ChatBan, CreateDate, LastLogin)"
                            + " values (?, ?, ?, ?, ?, ?, ?, ?, ?)";

                    // create the mysql insert preparedstatement
                    PreparedStatement preparedStmt = con.prepareStatement(query);

                    preparedStmt.setString (1, "xXx_hansJo_xXx");
                    preparedStmt.setString (2, "passwort123");
                    preparedStmt.setString   (3, "h1@vmail.de");
                    preparedStmt.setString(4, "Hans");
                    preparedStmt.setString    (5, "Jorgenssen");
                    preparedStmt.setLong(6, 1361);
                    preparedStmt.setBoolean(7, true);
                    preparedStmt.setDate(8, startDate);
                    preparedStmt.setDate(9, startDate);

                    // execute the preparedstatement
                    preparedStmt.execute();

                    /*
                    String query = "select * from users;";


                    rs = st.executeQuery(query);

                    while(rs.next()) {
                        String name = rs.getString("Username");
                        String fname = rs.getString("FirstName");
                        String nname = rs.getString("LastName");
                        System.out.println("User: " + name + "   Vorname: " + fname + "   Nachname: " + nname);
                    }*/

                    System.out.println("test");

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

                }catch (Exception e){
                    System.out.print("Fehler");
                    e.printStackTrace();
                }
            }
        });

    }
}