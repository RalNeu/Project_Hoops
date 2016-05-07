package ulm.hochschule.project_hoops;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
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

        return layout;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        bregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    URL url = new URL("http://141.59.26.107/phpScripts/InsertValue");
                    String data = URLEncoder.encode("username", "UTF-8")
                            + "=" + URLEncoder.encode(username, "UTF-8");
                    data += "&" + URLEncoder.encode("password", "UTF-8")
                            + "=" + URLEncoder.encode(password, "UTF-8");
                    URLConnection conn = url.openConnection();
                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                    wr.write( data );
                    BufferedReader reader = new BufferedReader(new
                            InputStreamReader(conn.getInputStream()));
                }catch (Exception e){
                    System.out.print("Fehler");
                }


                if(password.equals(repeatpassword)){
                    //And if Username doesent exist,
                    //And email doesnt exist, then send data to Server
                    //new User(firstname,lastname,email,username,password);

                }
            }
        });


    }
}

