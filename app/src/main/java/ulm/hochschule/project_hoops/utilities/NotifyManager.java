package ulm.hochschule.project_hoops.utilities;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;

/**
 * Created by Johann on 26.07.2016.
 */
//Klasse zum Senden vom Push-Up Benachrichtigungen
public class NotifyManager {

    public void sendNotify(int id, String title, String message, Context context, int icon){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(icon)
                .setContentTitle(title)
                .setContentText(message)
                .setDefaults(Notification.DEFAULT_ALL)
                .setColor(Color.WHITE);
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(id,builder.build());
    }
}
