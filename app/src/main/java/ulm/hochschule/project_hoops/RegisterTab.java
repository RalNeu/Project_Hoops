package ulm.hochschule.project_hoops;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;



import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

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
        bregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("testing");



                try {

                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);

                    CookieHandler.setDefault( new CookieManager( null, CookiePolicy.ACCEPT_ALL ));

                    URL url = new URL("http://141.59.26.107/phpScripts/InsertValue");

                    URLConnection conn = url.openConnection();

                    conn.setDoOutput(true);

                    InputStreamReader is = new InputStreamReader(conn.getInputStream());
                    char[] buffer = new char[1];


                }catch (Exception e){
                    System.out.print("Fehler");
                    e.printStackTrace();
                }
            }
        });
        return layout;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



    }
}

