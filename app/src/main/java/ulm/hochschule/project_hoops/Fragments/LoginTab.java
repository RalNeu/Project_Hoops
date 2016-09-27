package ulm.hochschule.project_hoops.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import ulm.hochschule.project_hoops.R;
import ulm.hochschule.project_hoops.activities.MainActivity;
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
    private TextView register;
    private SqlManager manager;

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
        register = (TextView) layout.findViewById(R.id.link_register);
        return layout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    login();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.contentPanel, new RegisterTab2());
            }
        });
    }

    public void onLoginSuccess()  {
        btn_Login.setEnabled(true);
        UserProfile.logoffUser();
        UserProfile.getInstance(et_Username.getText().toString(), getActivity());
        MainActivity ma = (MainActivity) getActivity();
        ma.setProfileEnabled(true);
        if (!manager.getVerifStatus(et_Username.getText().toString())) {
            verifReminder();
        }
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.contentPanel, new NewsTab()).commit();
        Toast.makeText(getContext(), "Login erfolgreich", Toast.LENGTH_SHORT).show();
    }

    public void verifReminder(){
        Toast.makeText(getContext(), "Vergessen Sie Ihre Verifikation nicht, Ihnen bleiben noch: ", Toast.LENGTH_SHORT).show();//TODO //verbleibende tage einfügen
    }

    private void onLoginFailed() {
        MainActivity ma = (MainActivity) getActivity();
        ma.setProfileEnabled(false);
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

        Runnable progressRunnable = new Runnable() {

            @Override
            public void run() {
                onLoginSuccess();
                progressDialog.cancel();
            }
        };
        progressRunnable.run();

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
