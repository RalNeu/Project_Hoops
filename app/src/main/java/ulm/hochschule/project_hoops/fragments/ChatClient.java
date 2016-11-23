package ulm.hochschule.project_hoops.fragments;





import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


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
    private ChatFragment cA;


    public ChatClient(ChatFragment ca) {
        host =  "141.59.26.107";
        port = 21401;
       cA = ca;


        try {
            socket = new Socket(host, port);
            din = new DataInputStream(socket.getInputStream());
            dout = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void processMessage(String message,String username){
        try{
            dout.writeUTF(message);
            dout.writeUTF(username);
        } catch(IOException e){
            System.out.println(e);
        }

    }

public void recieveText(String msg,String username){
    cA.recieveText(msg,username);
}

    @Override
    public void run() {
        super.run();
        try{

            while (true){
                String message = din.readUTF();
                String username = din.readUTF();
                recieveText(message,username);

            }
        }catch(IOException e){
            System.out.println(e);
        }

    }

    public void closeCon(){
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}











