package ulm.hochschule.project_hoops.fragments;





import android.os.AsyncTask;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import ulm.hochschule.project_hoops.activities.ChatActivity;


public class ChatClient extends Thread {



    private String host;
    private int port;
    private String response = "";
    private TextView txtView;
    private EditText et_Text;
    private Button buttonSend;
    private DataOutputStream dout;
    private DataInputStream din;
    private Socket socket;
    private ChatActivity cA;


    public ChatClient(ChatActivity ca) {
        host =  "37.209.33.128";
        port = 4999;
       cA = ca;


        try {
            socket = new Socket(host, port);
            System.out.println("connected to" + socket);
            din = new DataInputStream(socket.getInputStream());
            dout = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void processMessage(String message){
        try{
            dout.writeUTF(message);
        } catch(IOException e){
            System.out.println(e);
        }

    }


public void recieveText(String msg){
    cA.recieveText(msg);
}




    @Override
    public void run() {
        super.run();
        try{

            while (true){
                String message = din.readUTF();
                recieveText(message);
            }
        }catch(IOException e){
            System.out.println(e);
        }

    }
}











