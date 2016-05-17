package ulm.hochschule.project_hoops;

import android.util.Log;
import android.widget.Toast;

/**
 * Created by zoll on 17.05.2016.
 */
public class MailVerifier {

    Mail m = new Mail("USER EMAIL", "PASSWORD");
    String[] toArr = {"EMAIL-1", "EMAIL-2"};

    m.setTo(toArr);
    m.setFrom("USER EMAIL");
    m.setSubject("This is an email sent using my Mail JavaMail wrapper from an Android device.");
    m.setBody("Email body");

    try {
        // benötige ich nicht...
        // m.addAttachment("/sdcard/bday.jpg");
        if(m.send()) {
            Toast.makeText(this, "Email was sent successfully.", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Email was not sent.", Toast.LENGTH_LONG).show();
        }
} catch(Exception e) ()
        Log.e("MailApp", "Could not send email", e);
    )

}
