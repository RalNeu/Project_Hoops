package ulm.hochschule.project_hoops;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.sql.*;
import java.util.Calendar;

/**
 * Created by Johann on 06.05.2016.
 */
public class RegisterTab extends Fragment {
    private AppCompatActivity appcomp = new AppCompatActivity();
    private View layout;
    private MailVerifier mailVerifier;
    private EditText firstname;
    private EditText lastname;
    private EditText email;
    private EditText username;
    private EditText password;
    private EditText repeatpassword;
    private Button bregister;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        layout = inflater.inflate(R.layout.fragment_register, container, false);

        firstname = (EditText) layout.findViewById(R.id.et_Firstname);
        lastname = (EditText) layout.findViewById(R.id.et_Lastname);
        email = (EditText)layout.findViewById(R.id.et_Email);
        username = (EditText)layout.findViewById(R.id.et_Username);
        password = (EditText)layout.findViewById(R.id.et_Password);
        repeatpassword = (EditText)layout.findViewById(R.id.et_ConfirmPassword);
        bregister = (Button) layout.findViewById(R.id.btn_ContentRegister);
        return layout;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        bregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){

                SqlManager m = SqlManager.getInstance();
                m.createUser(firstname.getText().toString(),lastname.getText().toString(),email.getText().toString()
                        ,username.getText().toString(),password.getText().toString());

                mailVerifier = new MailVerifier(appcomp,"r.zoll995@gmail.com");

            }
        });

    }
}