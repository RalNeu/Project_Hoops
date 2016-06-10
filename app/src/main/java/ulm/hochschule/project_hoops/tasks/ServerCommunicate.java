package ulm.hochschule.project_hoops.tasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import ulm.hochschule.project_hoops.R;

/**
 * Created by Ralph on 10.06.2016.
 */
public class ServerCommunicate extends Thread {

    private Activity a;
    public void g(Activity activity) {
        a = activity;
    }

    @Override
    public void run() {
        System.out.println("test");
        final ProgressDialog progressDialog = new ProgressDialog(a,
                R.style.AppTheme_PopupOverlay);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Login...");
        progressDialog.getWindow().setLayout(600,400);
        progressDialog.show();

    }

}
