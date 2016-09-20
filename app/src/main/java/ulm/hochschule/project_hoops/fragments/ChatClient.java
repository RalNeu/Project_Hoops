package ulm.hochschule.project_hoops.fragments;





import android.os.AsyncTask;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


public class ChatClient extends AsyncTask<Void,Void,Void> {



    private String host;
    private int port;
    private String response = "";
    private TextView txtView;
    private EditText et_Text;
    private Button buttonSend;
    private DataOutputStream dout;
    private DataInputStream din;
    private Socket socket;
    private ChatClientView chatcv;


    public ChatClient(ChatClientView ccv) {
        host =  "37.209.33.128";
        port = 4999;
        chatcv = ccv;


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



    protected void onProgressUpdate(String... values) {

        chatcv.text(values[0]);
    }

    @Override
    protected Void doInBackground(Void... params) {
        try{

            while (!this.isCancelled()){
                String message = din.readUTF();
                onProgressUpdate(message);
            }
        }catch(IOException e){
            System.out.println(e);
        }
        return null;
    }

    }











