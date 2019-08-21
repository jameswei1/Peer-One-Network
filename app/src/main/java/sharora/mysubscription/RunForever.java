package sharora.mysubscription;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.Log;
import android.util.TimeUtils;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static sharora.mysubscription.notifications.CHANNEL_1_ID;

public class RunForever extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
//        if (!checkPermission(Manifest.permission.SEND_SMS)) {
//            ActivityCompat.requestPermissions(RunForever.this, new String[]{Manifest.permission.SEND_SMS}, 1);
//        }
        startTimer();
        startForeground();

        return super.onStartCommand(intent, flags, startId);
    }
    private void startForeground() {
        Intent notificationIntent = new Intent(this, Fingerprint.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, 0);

        startForeground(1, new NotificationCompat.Builder(this,
                CHANNEL_1_ID) // don't forget create a notification channel first
                .setOngoing(true)
                .setContentTitle("My Subscription")
                .setContentText("Service is running in background")
                .setContentIntent(pendingIntent)
                .build());
    }

    public void startTimer() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),
                10,
                16,
                0);
        setAlarm(calendar.getTimeInMillis());
        Log.d("ran", "runs forever");
    }

    public void setAlarm(long timeInMillis) {
        Log.d("reached", "should send sms");
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent broadcast = PendingIntent.getBroadcast(this, 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, timeInMillis, broadcast);

    }
    public boolean checkPermission(String Permission) {
        int check = ContextCompat.checkSelfPermission(this, Permission);
        return (check== PackageManager.PERMISSION_GRANTED);
    }
}
