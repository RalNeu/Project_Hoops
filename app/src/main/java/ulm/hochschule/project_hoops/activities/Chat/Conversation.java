package ulm.hochschule.project_hoops.activities.Chat;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Johann on 26.07.2016.
 */
public class Conversation {

    private String name, lastMessage, time;
    private ArrayList<String> messages;

    public Conversation(String name, String time){
        this.name = name;
        this.time = time;
        messages = new ArrayList<String>();
    }

    public String getName(){return name;}

    public String getTime(){return time;}

    public String getLastMessage(){return messages.get(messages.size() - 1);}

    public void setLastMessage(String lastMessage){this.lastMessage = lastMessage;}

    public ArrayList<String> getConversation(){return messages;}

    public  void addMessage(String message){
        messages.add(message);
        time = Calendar.getInstance().getTime().getHours() + ":" + Calendar.getInstance().getTime().getMinutes();
    }

}
