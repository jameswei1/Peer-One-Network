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

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import static androidx.core.content.ContextCompat.createDeviceProtectedStorageContext;
import static androidx.core.content.ContextCompat.getSystemService;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AlarmReceiver extends BroadcastReceiver {
    public static final String CHANNEL1_ID = "channel1";
    private NotificationManagerCompat notificationManager;

    private FirebaseDatabase base;
    private DatabaseReference ref;

    Date date1;

    @Override
    public void onReceive(final Context context, Intent intent) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR),
                     calendar.get(Calendar.MONTH),
                     calendar.get(Calendar.DAY_OF_MONTH));
        calendar.add(Calendar.DATE, 5);
        date1 = calendar.getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyy");
        String strDate = dateFormat.format(date1);

        Query query = FirebaseDatabase.getInstance().getReference("customers").orderByChild("Endsub").equalTo(strDate);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists() ){
                    for(DataSnapshot ds: dataSnapshot.getChildren()){
                        Toast.makeText(context, ds.getValue(Member.class).Getname(), Toast.LENGTH_SHORT).show();
                        Log.d("date", "found someone who expires");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        ref = FirebaseDatabase.getInstance().getReference("customers");


        Log.d("alarm", strDate);
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

//if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//        NotificationChannel channel1 = new NotificationChannel(CHANNEL1_ID, "Vibrate Notif", NotificationManager.IMPORTANCE_HIGH);
//        channel1.enableVibration(true);
//        channel1.setDescription("This should vibrate");
//
//        NotificationManager manager = getSystemService(context, NotificationManager.class);
//        manager.createNotificationChannel(channel1);
//        }
//        notificationManager = NotificationManagerCompat.from(context);
//        Toast.makeText(context, "Alarm", Toast.LENGTH_SHORT).show();
