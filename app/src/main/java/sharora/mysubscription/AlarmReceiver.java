package sharora.mysubscription;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import android.telephony.SmsManager;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
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
                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage("6478305885",null, "Expires in 5 days: "+ds.getValue(Member.class).Getname()+" "+ds.getValue(Member.class).Getlastname(), null, null);
                        smsManager.sendTextMessage(ds.getValue(Member.class).getPhoneNumber(), null, "Your subscription expires in 5 days", null, null);
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
                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage("6478305885",null, "Expires tomorrow: "+ds.getValue(Member.class).Getname()+" "+ds.getValue(Member.class).Getlastname(), null, null);
                        smsManager.sendTextMessage(ds.getValue(Member.class).getPhoneNumber(), null, "Your subscription expires tomorrow", null, null);
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