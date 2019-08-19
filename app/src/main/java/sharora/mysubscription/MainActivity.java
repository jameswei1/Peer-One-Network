package sharora.mysubscription;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton AddCust = findViewById(R.id.addcust);
        ImageButton ViewCust =(ImageButton) findViewById(R.id.viewcust2);
        ImageButton ViewAllCust =(ImageButton) findViewById(R.id.viewallcust);
        Button logout = findViewById(R.id.logoutButton);

        AddCust.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GotoAddCustomer();
            }
        });
        ViewCust.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GotoMACID();
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToFingerPrint();
            }
        });
        ViewAllCust.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GotoViewAllCustomers();
            }
        });


        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR),
                     calendar.get(Calendar.MONTH),
                     calendar.get(Calendar.DAY_OF_MONTH),
                    4,
                    23,
                    0);
        setAlarm(calendar.getTimeInMillis());
    }

    //Sends to next Activity
    public void GotoAddCustomer(){
        Intent intent = new Intent(this, AddCustomer.class);
        startActivity(intent);
    }

    public void GotoMACID(){
        Intent intent = new Intent(this,Enter_Firstname_Lastname.class);
        startActivity(intent);
    }
    public void GotoViewAllCustomers(){
        Intent intent = new Intent(this,View_All_Customers.class);
        startActivity(intent);
    }

    public void goToFingerPrint() {
        Intent intent = new Intent(this, Fingerprint.class);
        startActivity(intent);
    }

    public void setAlarm(long timeInMillis) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent broadcast = PendingIntent.getBroadcast(this, 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, timeInMillis, broadcast);

    }
}