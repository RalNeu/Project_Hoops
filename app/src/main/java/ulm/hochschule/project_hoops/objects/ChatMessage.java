package ulm.hochschule.project_hoops.objects;

import android.graphics.Bitmap;
import android.widget.ImageView;


public class ChatMessage {

    private String message;
    private String userName;

    public ChatMessage(String message,String userName){
        this.message = message;

        this.userName = userName;
    }

    public String getUserName(){
        return userName;
    }


    public String getMessage(){
        return message;
    }

}
