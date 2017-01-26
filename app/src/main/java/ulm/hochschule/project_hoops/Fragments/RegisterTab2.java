package ulm.hochschule.project_hoops.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import ulm.hochschule.project_hoops.R;
import ulm.hochschule.project_hoops.activities.MainActivity;
import ulm.hochschule.project_hoops.tasks.MailVerifierTask;
import ulm.hochschule.project_hoops.utilities.AchievementHandler;
import ulm.hochschule.project_hoops.utilities.NotifyManager;
import ulm.hochschule.project_hoops.utilities.ServerException;
import ulm.hochschule.project_hoops.utilities.SqlManager;
import ulm.hochschule.project_hoops.utilities.UserProfile;

/**
 * Created by Johann on 17.05.2016.
 */
//Fragment, dass das Layout und die Funktion vom Registrierfenster beschreibt.
public class RegisterTab2 extends Fragment {

    private View layout;
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    private EditText et_Firstname;
    private EditText et_Lastname;
    private EditText et_Username;
    private EditText et_Email;
    private EditText et_Password;
    private EditText et_ConfirmPassword;
    private Button btn_Register;
    private TextView txt_Link;

    private SqlManager manager;
    private NotifyManager notifyManager;
    private MailVerifierTask mailVerifierTask;

    //Patrick für tastatur schließen
    private LinearLayout lLayout;

    //Attribute initialisieren
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        manager = SqlManager.getInstance();
        notifyManager = new NotifyManager();
    }

    //View wird erzeugt
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layout = inflater.inflate(R.layout.fragment_register2, container, false);
        et_Firstname = (EditText) layout.findViewById(R.id.et_Firstname);
        et_Lastname = (EditText) layout.findViewById(R.id.et_Lastname);
        et_Username = (EditText) layout.findViewById(R.id.et_Username);
        et_Email = (EditText) layout.findViewById(R.id.et_Email);
        et_Password = (EditText) layout.findViewById(R.id.et_Password);
        et_ConfirmPassword = (EditText) layout.findViewById(R.id.et_ConfirmPassword);
        btn_Register = (Button) layout.findViewById(R.id.btn_Register);
        txt_Link = (TextView) layout.findViewById(R.id.link_Login);
        lLayout = (LinearLayout) layout.findViewById(R.id.register2Layout);


        return layout;
    }

    //Activity wird gestartet
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        btn_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
                InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromInputMethod(lLayout.getWindowToken(),0);
            }
        });

        txt_Link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.contentPanel, new LoginTab()).commit();
            }
        });
    }

    //Versuch sich zu registrieren
    public void register() {
        if (!ok()) {
            onRegisterFailed();
            return;
        }
        btn_Register.setEnabled(false);


        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Bitte warten Sie einen Moment");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        progressDialog.setMessage("Anfrage wird verarbeitet...");
        progressDialog.show();

        // TODO: Implement your own authentication logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        onRegisterSuccess();
                        login(et_Username.getText().toString());
                        progressDialog.dismiss();
                    }
                }, 3000);
    }

    //Bei erfolgreicher Registrierung wird ein neuer User angelegt und demjenigen eine Email zur Verifizierung gesendet
    public void onRegisterSuccess() {
        btn_Register.setEnabled(true);

        mailVerifierTask = new MailVerifierTask(getContext(), et_Email.getText().toString(), et_Username.getText().toString());
        String valString = mailVerifierTask.createValString();
        manager.createUser(et_Firstname.getText().toString(), et_Lastname.getText().toString(), et_Email.getText().toString(), et_Username.getText().toString(), et_Password.getText().toString());
        manager.setVerif_Code(et_Username.getText().toString(), valString);

        mailVerifierTask.execute();
    }

    //User wird nach dem registrieren eingeloggt
    public void login(String username){
        UserProfile.logoffUser();
        UserProfile user = UserProfile.getInstance(username, getActivity());
        try {
            AchievementHandler.getInstance().performEvent(0, 1, getActivity());
        } catch (ServerException e) {
            e.printStackTrace();
        }
        MainActivity ma = (MainActivity) getActivity();
        ma.setProfileEnabled(true);
    }

    //Bei nicht erfolgreicher Registrierung
    private void onRegisterFailed() {
        Toast.makeText(getContext(), "Login failed", Toast.LENGTH_LONG).show();
        btn_Register.setEnabled(true);
    }

    //Überprüft ob alle Eingaben korrekt sind bzw. den erforderlichen Vorraussetzungen erfüllen
    private boolean ok(){
        boolean isok = true;

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
        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(et_Email.getText().toString()).matches()){
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
        if(et_Username.getText().toString().length() > 12){
            et_Username.setError(getString(R.string.username_too_long));
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
        if(!et_ConfirmPassword.getText().toString().equals(et_Password.getText().toString())){
            et_ConfirmPassword.setError(getString(R.string.passwords_arent_equal));
            isok = false;
        }
        return isok;
    }
}
