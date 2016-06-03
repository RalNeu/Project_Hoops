package ulm.hochschule.project_hoops;

        import android.app.ProgressDialog;
        import android.content.Context;
        import android.graphics.Bitmap;
        import android.os.AsyncTask;
        import android.util.Config;
        import android.widget.Toast;

        import java.util.Properties;
        import java.util.Random;

        import javax.mail.Message;
        import javax.mail.MessagingException;
        import javax.mail.PasswordAuthentication;
        import javax.mail.Session;
        import javax.mail.Transport;
        import javax.mail.internet.InternetAddress;
        import javax.mail.internet.MimeMessage;
/**
 * Created by zoll on 17.05.2016.
 */
public class MailVerifier extends AsyncTask<Void,Void,Void> {



    /**
     * Created by Belal on 10/30/2015.
     */

//Class is extending AsyncTask because this class is going to perform a networking operation

        //Declaring Variables
        private Context context;
        private Session session;

        //Information to send email
        private String valString; // Random String der zur Verifikation benötigt wird
        private String email;
        private String subject = "Validation Code";
        private String message = "Here is your Code: ";
        private final static String bungamail = "projecthoops69@gmail.com";
        private boolean emailSent = false;
        private SqlManager sm;
        private UserProfile userProfile;
        private String username;
        //Progressdialog to show while sending email
        private ProgressDialog progressDialog;

        //Class Constructor
        public MailVerifier(Context context, String email, String username){
            //Initializing variables
            this.context = context;
            this.email = email;
            this.username = username;
        }



        @Override
        protected Void doInBackground(Void... params) {
            //creating valString
            Random generator = new Random();

            StringBuilder randomStringBuilder = new StringBuilder();

            char tempChar;
            for (int i = 0; i < 5; i++){
                tempChar = (char) (generator.nextInt(96) + 32);
                randomStringBuilder.append(tempChar);
            }
            this.valString = randomStringBuilder.toString();
            message += valString;


            //Creating properties
            Properties props = new Properties();

            //Configuring properties for gmail
            //If you are not using gmail you may need to change the values
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.socketFactory.port", "465");
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.port", "465");

            //Creating a new session
            session = Session.getDefaultInstance(props,
                    new javax.mail.Authenticator() {
                        //Authenticating the password
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(bungamail, "JZ_Sslru");
                        }
                    });

            try {
                //Creating MimeMessage object

                MimeMessage mm = new MimeMessage(session);

                //Setting sender address
                mm.setFrom(new InternetAddress(bungamail));
                //Adding receiver
                mm.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
                //Adding subject
                mm.setSubject(subject);
                //Adding message
                mm.setText(message);

                //Sending email
                Transport.send(mm);
                emailSent = true;
                sm.setVerif_Code(username, valString);

            } catch (MessagingException e) {
                e.printStackTrace();
            }
            return null;
        }
    }