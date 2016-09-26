package ulm.hochschule.project_hoops.utilities;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
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
import ulm.hochschule.project_hoops.activities.EditProfilActivity;
import ulm.hochschule.project_hoops.fragments.ProfilTab;
import ulm.hochschule.project_hoops.fragments.ProfilTabChat;
import ulm.hochschule.project_hoops.objects.ChatMessage;


public class ChatAdapter extends ArrayAdapter<ChatMessage> {


    //TextMessage
    private TextView textView;
    private TextView time;
    private TextView name;
    private List<ChatMessage> messages = new ArrayList<ChatMessage>();

    //image Layout
    private ImageView image;
    private TextView text;
    private ListView listView;
    private FragmentManager fsm = null;
    private ProfilTabChat profile = new ProfilTabChat();

    public ChatAdapter(Context context, int r, ListView listView, FragmentManager fm){
        super(context, r);
        this.listView = listView;
        this.fsm = fm;
    }

    @Override
    public void add(ChatMessage obj){
        messages.add(obj);
        super.add(obj);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ChatMessage obj = getItem(position);
        View row = convertView;
        LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.string_message_left, parent, false);
            textView = (TextView) row.findViewById(R.id.msgr);
            name = (TextView) row.findViewById(R.id.name);
            if(obj.getUserName().length() > 10){
                name.setText(obj.getUserName().substring(0,10) + "...:");
            }else
                name.setText(obj.getUserName()+":");
            String mes;
            if(obj.getMessage().charAt(0) == '.'){
                mes = obj.getMessage().replaceFirst(".", "");
            }else
                mes = obj.getMessage();
            textView.setText(mes);

        final TextView tv = (TextView) row.findViewById(R.id.name);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fm = fsm;
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.contentPanel, profile).addToBackStack(profile.getTag()).commit();
                profile.name(tv.getText().toString());
            }
        });
        return row;
    }
}
