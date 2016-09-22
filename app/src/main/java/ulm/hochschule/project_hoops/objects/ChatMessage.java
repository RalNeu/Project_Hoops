package ulm.hochschule.project_hoops.objects;

import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * Created by Johann on 28.07.2016.
 */
public class ChatMessage {

    private String message;
    private String time;
    private String kind;
    private String userName;

    public ChatMessage(String message, String time, String kind, String userName){
        this.message = message;
        this.time = time;
        this.userName = userName;
        this.kind = kind;
    }

    public String getUserName(){
        return userName;
    }

    public String getKind(){
        return kind;
    }

    public String getMessage(){
        return message;
    }

    public String getTime(){
        return time;
    }
}
