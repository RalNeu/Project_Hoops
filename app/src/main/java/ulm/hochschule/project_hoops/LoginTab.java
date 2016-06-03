package ulm.hochschule.project_hoops;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Johann on 20.05.2016.
 */
public class LoginTab extends Fragment {

    private View layout;

    private EditText et_Username;
    private EditText et_Password;
    private Button btn_Login;

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
        return layout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(check()){
                    Toast.makeText(getActivity(), "eingeloggt", Toast.LENGTH_LONG).show();
                }else
                    Toast.makeText(getActivity(), "nicht eingeloggt", Toast.LENGTH_LONG).show();
            }
        });
    }

    private boolean check(){
        boolean ok = true;
        try {
            if(manager.userExist(et_Username.getText().toString())){
                if (!et_Password.getText().toString().equals(manager.getUser(et_Username.getText().toString())[3])){
                    et_Password.setError("Passwort falsch");
                    et_Username.setError("Benutzer existiert nicht");
                    ok = false;
                }
            }else if(manager.emailExist(et_Username.getText().toString())){
                if(!et_Password.getText().toString().equals(manager.getPassword(et_Username.getText().toString()))){
                    et_Password.setError("Passwort falsch");
                    et_Username.setError("Benutzer existiert nicht");
                    ok = false;
                }
            }else{
                et_Password.setError("Passwort falsch");
                et_Username.setError("Benutzer existiert nicht");
                ok = false;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        try{
            System.out.println((manager.getPassword(et_Username.getText().toString())));
        }catch (Exception e){

        }
        return ok;
    }
}
