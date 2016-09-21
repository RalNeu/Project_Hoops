package ulm.hochschule.project_hoops.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import ulm.hochschule.project_hoops.R;
import ulm.hochschule.project_hoops.utilities.ChatAdapter;
import ulm.hochschule.project_hoops.objects.ChatMessage;
import ulm.hochschule.project_hoops.fragments.ChatClient;
import ulm.hochschule.project_hoops.utilities.UserProfile;

public class ChatActivity extends AppCompatActivity {

    private Button button;
    private EditText et_Text;

    private ChatClient myClient;
    private ChatAdapter adapter;
    private ListView listView;
    private UserProfile user;

    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        button = (Button)findViewById(R.id.btn_send);
        et_Text  = (EditText) findViewById(R.id.tv_message);
        listView = (ListView) findViewById(R.id.listView);
        listView.setClipToPadding(false);


        adapter = new ChatAdapter(getApplicationContext(), R.layout.string_message_right,listView);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        listView.setAdapter(adapter);

        user = UserProfile.getInstance();
        userName = user.getUsername();

        final ChatClient myClient = new ChatClient(this);
        myClient.start();

        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(textSize()){
                myClient.processMessage(userName+": "+et_Text.getText().toString());
                    et_Text.setText("");
            }else{
                    et_Text.setError("Es können nur 80 Zeichen verwendet werden");
                }
            }

        });
    }

    public boolean textSize(){
        if(et_Text.length()<=80){
            return true;
        }else{
            return false;
        }
    }

    public void recieveText(String text ){
        final String text1 = text;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                sendMessage(new ChatMessage(text1,"","String"));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    public void sendMessage(ChatMessage obj) {
        adapter.add(obj);
        this.et_Text.setText("");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
