package ulm.hochschule.project_hoops.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;

import ulm.hochschule.project_hoops.R;
import ulm.hochschule.project_hoops.fragments.ChatClient;
import ulm.hochschule.project_hoops.fragments.TipTab;


/**
 * Created by Patri on 19.09.2016.
 */
public class ChatClientView extends Fragment {

    private View layout;
    private Button button;
    public TextView txt_View;
    private EditText et_Text;
    //Attribut für den Username und der muss dann abgesendet werden mit der nachricht




   public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layout = inflater.inflate(R.layout.fragment_chat, container, false);
        button = (Button) layout.findViewById(R.id.buttonSend);
        txt_View = (TextView) layout.findViewById(R.id.txt_chat);
        et_Text  = (EditText) layout.findViewById(R.id.et_ChatMessage);
       final ChatClient myClient = new ChatClient(this);
        myClient.execute();

        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                myClient.processMessage(et_Text.getText().toString()+"\n");
            }
        });

        return layout;
    }

   public void text(String text ){
        txt_View.append(text);
    }

}
