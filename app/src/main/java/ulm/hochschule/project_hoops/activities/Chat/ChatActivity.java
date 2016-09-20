package ulm.hochschule.project_hoops.activities.Chat;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import ulm.hochschule.project_hoops.R;
import ulm.hochschule.project_hoops.fragments.ChatClient;

public class ChatActivity extends AppCompatActivity {

    private Button button;
    public TextView txt_View;
    private EditText et_Text;
    private ChatClient myClient;
    private ChatAdapter adapter;
    private ListView listView;
    private Conversation conversation;

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
        toolbar.setTitle("ChatTeilnehmer");
        setSupportActionBar(toolbar);
        listView.setAdapter(adapter);
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

    public void loadMessages(Conversation conversation) {
        conversation.getConversation();
    }
}
