package ulm.hochschule.project_hoops.activities;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import ulm.hochschule.project_hoops.R;
import ulm.hochschule.project_hoops.fragments.ChatClient;

public class ChatActivity extends AppCompatActivity {

    private Button button;
    public TextView txt_View;
    private EditText et_Text;
    private ChatClient myClient;
    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        button = (Button)findViewById(R.id.buttonSend);
        txt_View = (TextView) findViewById(R.id.txt_chat);
        et_Text  = (EditText) findViewById(R.id.et_ChatMessage);
        final ChatClient myClient = new ChatClient(this);
        myClient.start();

        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                myClient.processMessage(et_Text.getText().toString()+"\n");
            }
        });
    }


    public void recieveText(String text ){
        final String text1 = text;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                txt_View.append(text1+"\n");
            }
        });
    }


}
