package ulm.hochschule.project_hoops;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Johann on 06.05.2016.
 */
public class RegisterTab extends Fragment {

    private View layout;
    private MailVerifier mailVerif;
    private AppCompatActivity context;
    private EditText et_Firstname;
    private EditText et_Lastname;
    private EditText et_Email;
    private EditText et_Username;
    private EditText et_Password;
    private EditText et_Repeatpassword;
    private Button btn_Register;

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

        et_Firstname = (EditText) layout.findViewById(R.id.et_Firstname);
        et_Lastname = (EditText) layout.findViewById(R.id.et_Lastname);
        et_Email = (EditText)layout.findViewById(R.id.et_Email);
        et_Username = (EditText)layout.findViewById(R.id.et_Username);
        et_Password = (EditText)layout.findViewById(R.id.et_Password);
        et_Repeatpassword = (EditText)layout.findViewById(R.id.et_ConfirmPassword);
        btn_Register = (Button) layout.findViewById(R.id.btn_ContentRegister);
        return layout;
    }

    @Override
     public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        btn_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(manager.isNetworkAvailable(getContext())) {
                    if(ok()) {
                        manager.createUser(et_Firstname.getText().toString(), et_Lastname.getText().toString(), et_Email.getText().toString()
                                , et_Username.getText().toString(), et_Password.getText().toString());
                        mailVerif = new MailVerifier(context, "r.zoll995@gmail.com");
                        mailVerif.execute();

                        FragmentManager fm = getFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        ft.replace(R.id.contentPanel, new NewsTab()).commit();
                    }
                }else Toast.makeText(getActivity(), "You are not connected to the internet",
                        Toast.LENGTH_LONG).show();
            }
        });

    }

    private boolean ok(){
        boolean isok = true;
        System.out.print(et_Email.getText().toString());
        if(et_Firstname.getText().toString().trim().equals("")){
            et_Firstname.setError(getString(R.string.enter_your_firstname));
            isok = false;
        }
        if(et_Lastname.getText().toString().trim().equals("")){
            et_Lastname.setError(getString(R.string.enter_your_lastname));
            isok = false;
        }
        if(et_Email.getText().toString().trim().equals("")){
            et_Email.setError(getString(R.string.enter_your_email));
            isok = false;
        }
        if(!et_Email.getText().toString().contains("@") || !et_Email.getText().toString().contains(".")){
            et_Email.setError(getString((R.string.check_email_standard)));
            isok = false;
        }
        if(et_Username.getText().toString().trim().equals("") || et_Username.getText().toString().contains(" ")) {
            et_Username.setError(getString(R.string.no_whitespaces_allowed));
            isok = false;
        }
        if(manager.userExist(et_Username.getText().toString())){
            et_Username.setError(getString(R.string.username_already_exists));
            isok = false;
        }
        if(et_Password.getText().toString().trim().equals("") || et_Password.getText().toString().contains(" ")){
            et_Password.setError(getString(R.string.no_whitespaces_allowed));
            isok = false;
        }
        if(manager.emailExist(et_Email.getText().toString())){
            et_Email.setError("email exists");
            isok = false;
        }
        if(et_Password.getText().toString().length() < 7){
            et_Password.setError(getString(R.string.password_too_short));
            isok = false;
        }
        if(!et_Repeatpassword.getText().toString().equals(et_Password.getText().toString())){
            et_Repeatpassword.setError(getString(R.string.passwords_arent_equal));
            isok = false;
        }
        return isok;
    }
}