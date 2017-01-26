package ulm.hochschule.project_hoops.utilities;

import android.content.Context;
import android.graphics.Paint;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ulm.hochschule.project_hoops.R;
import ulm.hochschule.project_hoops.fragments.ChatFragment;
import ulm.hochschule.project_hoops.fragments.ProfilTabChat;
import ulm.hochschule.project_hoops.objects.ChatMessage;

//Der Chatadapter is für die Anzeige einer Chatnachricht unter dem berücksichtigten Layout zuständig.
public class ChatAdapter extends ArrayAdapter<ChatMessage> {

    //Layout
    private TextView textView;
    private TextView name;
    private List<ChatMessage> messages = new ArrayList<ChatMessage>();

    private FragmentManager fsm = null;
    private ChatFragment cA = new ChatFragment();

    public ChatAdapter(Context context, int r, ListView listView, FragmentManager fm, ChatFragment cA){
        super(context, r);
        this.fsm = fm;
        this.cA = cA;
    }

    //Fügt eine Nachricht hinzu
    @Override
    public void add(ChatMessage obj){
        messages.add(obj);
        super.add(obj);
    }

    //Erstellt das Layout
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ChatMessage obj = getItem(position);
        View row = convertView;
        LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.string_message_left, parent, false);
            textView = (TextView) row.findViewById(R.id.msgr);
            name = (TextView) row.findViewById(R.id.name);
            name.setText(obj.getUserName()+":");
            String mes;
            mes = obj.getMessage();
            textView.setText(mes);

        final TextView tv = (TextView) row.findViewById(R.id.name);
        tv.setPaintFlags(tv.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cA.closeCon();
                ProfilTabChat profile = new ProfilTabChat();
                FragmentManager fm = fsm;
                FragmentTransaction ft = fm.beginTransaction();
                profile.setUsername(tv.getText().toString());
                ft.replace(R.id.contentPanel, profile).addToBackStack(profile.getTag()).commit();


            }
        });
        return row;
    }
}
