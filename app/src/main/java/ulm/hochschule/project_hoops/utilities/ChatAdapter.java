package ulm.hochschule.project_hoops.utilities;

import android.content.Context;
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
import ulm.hochschule.project_hoops.objects.ChatMessage;

/**
 * Created by Johann on 27.07.2016.
 */
public class ChatAdapter extends ArrayAdapter<ChatMessage> {

    //TextMessage
    private TextView textView;
    private TextView time;
    private List<ChatMessage> messages = new ArrayList<ChatMessage>();

    //image Layout
    private ImageView image;
    private TextView text;
    private ListView listView;

    public ChatAdapter(Context context, int r, ListView listView){
        super(context, r);
        this.listView = listView;
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
        if(obj.getKind().equals("String")){
            if(obj.getMessage().charAt(0) == '.'){
                row = inflater.inflate(R.layout.string_message_left, parent, false);
            }else
                row = inflater.inflate(R.layout.string_message_right, parent, false);
            textView = (TextView) row.findViewById(R.id.msgr);
            time = (TextView) row.findViewById(R.id.time);
            time.setText(obj.getTime());
            String mes;
            if(obj.getMessage().charAt(0) == '.'){
                mes = obj.getMessage().replaceFirst(".", "");
            }else
                mes = obj.getMessage();
            textView.setText(mes);
        }
        return row;
    }
}
