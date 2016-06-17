package ulm.hochschule.project_hoops.tasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import ulm.hochschule.project_hoops.R;
import ulm.hochschule.project_hoops.utilities.ServerException;
import ulm.hochschule.project_hoops.utilities.UserProfile;

/**
 * Created by Ralph on 10.06.2016.
 */
public class ServerCommunicate{

    private Activity a;

    public ServerCommunicate(Activity a) {
        this.a = a;
    }

    public boolean sendTip(int coins, int team) throws ServerException {
        boolean retVal = true;

        try {
            Socket s = new Socket("141.59.26.107", 21395);
            ObjectOutputStream oos= new ObjectOutputStream(s.getOutputStream());
            InputStream is = s.getInputStream();

            oos.writeObject(UserProfile.getInstance().getUsername());
            int answer = is.read();
            if(answer != 0) {
                throw new ServerException("Sending of username went wrong. Errorcode: " + answer);
            }

            oos.writeObject(new Integer(team));
            answer = is.read();
            if(answer != 0) {
                throw new ServerException("Sending of team went wrong. Errorcode: " + answer);
            }

            oos.writeObject(new Integer(coins));
            answer = is.read();

            if(answer != 0) {
                throw new ServerException("Sending of coins went wrong. Errorcode: " + answer);
            }

            UserProfile.getInstance().changeCoins(coins*-1);

            s.close();

        } catch (IOException ex) {
            retVal = false;
        }

        return retVal;
    }

}
