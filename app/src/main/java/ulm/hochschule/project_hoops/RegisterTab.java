package ulm.hochschule.project_hoops;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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

        firstname = layout.findViewById(R.id.etFirstname).toString();
        lastname = layout.findViewById(R.id.etLastname).toString();
        email = layout.findViewById(R.id.etEmail).toString();
        username = layout.findViewById(R.id.etUsername).toString();
        password = layout.findViewById(R.id.etPassword).toString();
        repeatpassword = layout.findViewById(R.id.etRepeatPassword).toString();
        bregister = (Button) layout.findViewById(R.id.bContentRegister);

        return layout;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        bregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(password.equals(repeatpassword)){
                    //And if Username doesent exist,
                    //And email doesnt exist, then send data to Server
                    //new User(firstname,lastname,email,username,password);
                }
            }
        });
    }
}

