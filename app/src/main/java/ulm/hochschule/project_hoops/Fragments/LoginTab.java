package ulm.hochschule.project_hoops.fragments;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.SQLException;

import ulm.hochschule.project_hoops.R;
import ulm.hochschule.project_hoops.utilities.SqlManager;
import ulm.hochschule.project_hoops.utilities.UserProfile;

/**
 * Created by Johann on 20.05.2016.
 */
public class LoginTab extends Fragment {

    private View layout;

    private EditText et_Username;
    private EditText et_Password;
    private Button btn_Login;
    private AppCompatActivity context;
    private SqlManager manager;
    private UserProfile user;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        manager = SqlManager.getInstance();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layout = inflater.inflate(R.layout.fragment_login, container, false);
        et_Username = (EditText) layout.findViewById(R.id.et_Username);
        et_Password = (EditText) layout.findViewById(R.id.et_Password);
        btn_Login = (Button) layout.findViewById(R.id.btn_Login);
        context = new AppCompatActivity();
        return layout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(check()){
                    login();
                }
            }
        });
    }

    public void onLoginSuccess()  {
        btn_Login.setEnabled(true);
        user = UserProfile.getInstance(et_Username.getText().toString());
        if (!manager.getVerifStatus(et_Username.getText().toString())) {
            verifReminder();
        }
    }
    public void verifReminder(){
        Toast.makeText(context, "Vergessen Sie Ihre Verifikation nicht, Ihnen bleiben noch: ", Toast.LENGTH_SHORT).show();//TODO //verbleibende tage einfügen
    }
    private void onLoginFailed() {
        Toast.makeText(getContext(), "Login failed", Toast.LENGTH_LONG).show();

        btn_Login.setEnabled(true);
    }

    public void login() {
        if (!check()) {
            onLoginFailed();
            return;
        }
        btn_Login.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(getActivity(),
                R.style.AppTheme_PopupOverlay);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Login...");
        progressDialog.getWindow().setLayout(600,400);
        progressDialog.show();

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        onLoginSuccess();
                        progressDialog.dismiss();
                    }
                }, 3000);

    }

    private boolean check(){
        boolean ok = true;
        try {
            if(manager.userExist(et_Username.getText().toString())){
                if (!et_Password.getText().toString().equals(manager.getUser(et_Username.getText().toString())[1])){
                    //TODO

                    ok = false;
                }
            }else{
                //TODO
                ok = false;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return ok;
    }
}
