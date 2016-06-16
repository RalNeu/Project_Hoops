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
import ulm.hochschule.project_hoops.utilities.UserProfile;

/**
 * Created by Ralph on 10.06.2016.
 */
public class ServerCommunicate extends Thread {

    private Activity a;
    private ProgressDialog p;

    public ServerCommunicate(Activity a) {
        this.a = a;
        ProgressDialog progress = new ProgressDialog(a);
        progress.setMessage("Registering Tip");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        //progress.show();
        p = ProgressDialog.show(a, "Sending Tip", "Please hold on...", true);

    }

    @Override
    public void run() {
        try {
            Socket s = new Socket("141.59.26.107", 21395);
            ObjectOutputStream oos= new ObjectOutputStream(s.getOutputStream());
            InputStream is = s.getInputStream();

            System.out.println(UserProfile.getInstance("Teddy").getUsername());
            oos.writeObject(UserProfile.getInstance("").getUsername());
            int answer = is.read();
            oos.writeObject(new Integer(0));
            answer = is.read();
            oos.writeObject(new Integer(0));
            answer = is.read();
            p.dismiss();
            System.out.println("feddich");
            s.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
