
package sharora.mysubscription;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static sharora.mysubscription.AlarmReceiver.CHANNEL1_ID;

public class Splashscreen extends AppCompatActivity {
    TextView name;
    Date date1;
    private NotificationManagerCompat notificationManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        name = findViewById(R.id.name_val);

        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        calendar.add(Calendar.DATE, 5);
        date1 = calendar.getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyy");
        String strDate = dateFormat.format(date1);
        notificationManager = NotificationManagerCompat.from(this);

        Query query = FirebaseDatabase.getInstance().getReference("customers").orderByChild("Endsub").equalTo(strDate);

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    for(DataSnapshot ds: dataSnapshot.getChildren()) {
                        Log.d("id", "name");
                        Toast.makeText(Splashscreen.this, "text", Toast.LENGTH_SHORT).show();
                        //Getting intent and PendingIntent instance
                        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                        PendingIntent pi= PendingIntent.getActivity(getApplicationContext(), 0, intent,0);

                        //Get the SmsManager instance and call the sendTextMessage method to send message
                        SmsManager sms=SmsManager.getDefault();
                        sms.sendTextMessage("6478305885", null, "hello javatpoint", pi,null);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Splashscreen.this, "Error occurred", Toast.LENGTH_SHORT).show();
            }
        };
        query.addListenerForSingleValueEvent(valueEventListener);
    }
}