package sharora.mysubscription;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import static androidx.core.content.ContextCompat.createDeviceProtectedStorageContext;
import static androidx.core.content.ContextCompat.getSystemService;

public class AlarmReceiver extends BroadcastReceiver {
    public static final String CHANNEL1_ID = "channel1";
    private NotificationManagerCompat notificationManager;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel1 = new NotificationChannel(CHANNEL1_ID, "Vibrate Notif", NotificationManager.IMPORTANCE_HIGH);
            channel1.enableVibration(true);
            channel1.setDescription("This should vibrate");

            NotificationManager manager = getSystemService(context, NotificationManager.class);
            manager.createNotificationChannel(channel1);
        }
        notificationManager = NotificationManagerCompat.from(context);
        Toast.makeText(context, "Alarm", Toast.LENGTH_SHORT).show();
        Log.d("alarm", "onReceive: alarm");
    }

    public void sendOnChannel1(View view) {
        Notification notification = new NotificationCompat.Builder(view.getContext(), CHANNEL1_ID)
                .setContentTitle("Hi")
                .setContentText("James")
                .setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 })
                .build();
        notificationManager.notify(1, notification);
    }
}