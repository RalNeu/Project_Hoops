package ulm.hochschule.project_hoops.fragments;

import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import ulm.hochschule.project_hoops.R;
import ulm.hochschule.project_hoops.objects.ChatClient;
import ulm.hochschule.project_hoops.utilities.ChatAdapter;
import ulm.hochschule.project_hoops.objects.ChatMessage;
import ulm.hochschule.project_hoops.utilities.UserProfile;

public class ChatFragment extends Fragment {

    private FloatingActionButton button;
    private EditText et_Text;

    private ChatClient myClient;
    private ChatAdapter adapter;
    private ListView listView;
    private UserProfile user;

    private String username;

    private View layout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layout = inflater.inflate(R.layout.activity_chat, container, false);
        button = (FloatingActionButton) layout.findViewById(R.id.btn_send);
        et_Text  = (EditText)layout.findViewById(R.id.tv_message);
        listView = (ListView) layout.findViewById(R.id.listView);
        listView.setClipToPadding(false);


        adapter = new ChatAdapter(getContext(), R.layout.string_message_right,listView, getActivity().getSupportFragmentManager(),this);
        Window window = getActivity().getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        Toolbar toolbar = (Toolbar) layout.findViewById(R.id.toolbar);
        listView.setAdapter(adapter);
        return layout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        user = UserProfile.getInstance();
        username = user.getUsername();

        myClient = new ChatClient(this);
        myClient.start();

        if(user.getVerifStatus() == true) {

            et_Text.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        if (textSize() && textSize0()) {
                            myClient.processMessage(et_Text.getText().toString(), username);
                            et_Text.setText("");
                        } else {
                            et_Text.setError(getResources().getString(R.string.one80Chars));
                        }
                    }
                    return false;
                }
            });

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (textSize() && textSize0()) {
                        myClient.processMessage(et_Text.getText().toString(), username);
                        et_Text.setText("");
                    } else {
                        et_Text.setError(getResources().getString(R.string.one80Chars));
                    }
                }
            });
        }else{
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), getResources().getString(R.string.HasToBeVerified),Toast.LENGTH_SHORT ).show();

                    button.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View v) {
                            if (textSize() && textSize0()) {
                                myClient.processMessage(et_Text.getText().toString(), username);
                                et_Text.setText("");
                            } else {
                                et_Text.setError(getResources().getString(R.string.one80Chars));
                            }
                        }
                    });

                }
            });
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        closeCon();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        closeCon();
    }

    public boolean textSize(){
        if(et_Text.length()<=80){
            return true;
        }else{
            return false;
        }
    }
    public boolean textSize0(){
        if(et_Text.length()!=0){
            return true;
        }else{
            return false;
        }
    }

    public void recieveText(final String text , final String username){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                sendMessage(new ChatMessage(text, username));
            }
        });
    }

    public void sendMessage(ChatMessage obj) {
        adapter.add(obj);
        this.et_Text.setText("");
    }

    public void closeCon(){
        myClient.closeCon();
    }
}
