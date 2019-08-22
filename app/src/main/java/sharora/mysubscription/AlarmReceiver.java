package sharora.mysubscription;

import android.Manifest;
import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

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

    Date date1, date2;

    @Override
    public void onReceive(final Context context, Intent intent) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        calendar.add(Calendar.DATE, 5);
        date1 = calendar.getTime();
        calendar.add(Calendar.DATE, -4);
        date2 = calendar.getTime();

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyy");
        String strDate = dateFormat.format(date1);
        String strDate2 = dateFormat.format(date2);

        Query query = FirebaseDatabase.getInstance().getReference("customers").orderByChild("Endsub").equalTo(strDate);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists() ){
                    for(DataSnapshot ds: dataSnapshot.getChildren()){
//                        SmsManager smsManager = SmsManager.getDefault();
//                        smsManager.sendTextMessage("6478305885",null, "Expires in 5 days: "+ds.getValue(Member.class).Getname()+" "+ds.getValue(Member.class).Getlastname(), null, null);
//                        smsManager.sendTextMessage(ds.getValue(Member.class).getPhoneNumber(), null, "Your subscription expires in 5 days", null, null);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(context, "Something's wrong. Contact developers", Toast.LENGTH_SHORT).show();
            }
        });

        Query query1 = FirebaseDatabase.getInstance().getReference("customers").orderByChild("Endsub").equalTo(strDate2);
        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists() ){
                    for(DataSnapshot ds: dataSnapshot.getChildren()){
//                        SmsManager smsManager = SmsManager.getDefault();
//                        smsManager.sendTextMessage("6478305885",null, "Expires tomorrow: "+ds.getValue(Member.class).Getname()+" "+ds.getValue(Member.class).Getlastname(), null, null);
//                        smsManager.sendTextMessage(ds.getValue(Member.class).getPhoneNumber(), null, "Your subscription expires tomorrow", null, null);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(context, "Something's wrong. Contact developers", Toast.LENGTH_SHORT).show();
            }
        });
    }
}