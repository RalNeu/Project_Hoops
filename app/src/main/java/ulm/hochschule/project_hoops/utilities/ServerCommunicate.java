package ulm.hochschule.project_hoops.utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.InterfaceAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Ralph on 10.06.2016.
 */
public class ServerCommunicate {

    private static ServerCommunicate instance;

    private int timeout = 2000;

    String ipAdress = "141.59.26.107";

    private ServerCommunicate() {

    }

    public static ServerCommunicate getInstance() {
        if(instance == null) {
            instance = new ServerCommunicate();
        }
        return instance;
    }

    public String getAch() throws ServerException {

        String returnString = "";

        try {
            Socket connect = new Socket("141.59.26.107", 80);
            PrintWriter pw = new PrintWriter(connect.getOutputStream());
            pw.println("GET /achievements.txt HTTP/1.1\nHost: 141.59.26.107");
            pw.println("");
            pw.flush();
            BufferedReader br = new BufferedReader(new InputStreamReader(connect.getInputStream()));
            String t;
            boolean run = true;
            returnString = "";
            while (run && (t = br.readLine()) != null) {
                returnString = t;
                if(!br.ready())
                    run = false;
            }
            br.close();
        } catch (IOException e) {
        }
        System.out.println(returnString);
        return returnString;

    }
}
