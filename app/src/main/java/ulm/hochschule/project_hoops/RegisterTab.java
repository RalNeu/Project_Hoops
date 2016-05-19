package ulm.hochschule.project_hoops;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

    private View layout;
    private MailVerifier mailVerif;
    private AppCompatActivity context;
    private EditText firstname;
    private EditText lastname;
    private EditText email;
    private EditText username;
    private EditText password;
    private EditText repeatpassword;
    private Button bregister;

    private SqlManager manager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        manager = SqlManager.getInstance();
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
            public void onClick(View view) {
                if(ok()) {

                    manager.createUser(firstname.getText().toString(), lastname.getText().toString(), email.getText().toString()
                            , username.getText().toString(), password.getText().toString());
                    mailVerif = new MailVerifier(context, "r.zoll995@gmail.com");
                    mailVerif.execute();

                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.contentPanel, new NewsTab()).commit();
                }
            }
        });

    }

    private boolean ok(){
        boolean isok = true;

        if(firstname.getText().toString().trim().equals("")){
            firstname.setError("Enter your name");
            isok = false;
        }
        if(lastname.getText().toString().trim().equals("")){
            lastname.setError("Enter your name");
            isok = false;
        }
        if(email.getText().toString().trim().equals("")){
            email.setError("Enter your email");
            isok = false;
        }
        if(!email.getText().toString().contains("@") || !email.getText().toString().contains(".")){
            email.setError("Not email standard");
            isok = false;
        }
        if(username.getText().toString().trim().equals("") || username.getText().toString().contains(" ")) {
            username.setError("No white spaces");
            isok = false;
        }
        if(manager.userExist(username.getText().toString())){
            username.setError("Username exists");
            isok = false;
        }
        if(password.getText().toString().trim().equals("") || password.getText().toString().contains(" ")){
            password.setError("No white spaces");
            isok = false;
        }
        if(password.getText().toString().length() < 7){
            password.setError("password to short. Must have a length of min. 8");
            isok = false;
        }
        if(!repeatpassword.getText().toString().equals(password.getText().toString())){
            repeatpassword.setError("Repeat your password");
            isok = false;
        }
        return isok;
    }
}