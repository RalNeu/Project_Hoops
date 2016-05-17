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

                SqlManager.getInstance().registerUser(firstname, lastname, email,username,password);
            }

        });
    }

}