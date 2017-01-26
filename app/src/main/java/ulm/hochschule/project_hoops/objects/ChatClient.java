package ulm.hochschule.project_hoops.objects;





import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import ulm.hochschule.project_hoops.fragments.ChatFragment;


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

//Verbindung zum Chat Server
    public ChatClient(ChatFragment ca) {
        host =  "141.59.26.107";
        port = 21401;
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

    //Sendet Nachricht und Benutzername zum Server.
    public void processMessage(String message,String username){
        try{
            dout.writeUTF(message);
            dout.writeUTF(username);
        } catch(IOException e){
            System.out.println(e);
        }

    }

    //Daten werden an Chat Fragment weiter geleitet
public void recieveText(String msg,String username){
    cA.recieveText(msg,username);
}


    //Thread der die Inputs vom Server entgegen nimmt und im recieveText weiterverarbeitet
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

    //Beendet die Verbindung zum Server
    public void closeCon(){
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}











