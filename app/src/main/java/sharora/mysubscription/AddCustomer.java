package sharora.mysubscription;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.Telephony;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.time.LocalDate;
import java.time.Period;
import java.util.Date;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddCustomer extends AppCompatActivity {
    ImageButton send;
    EditText name ;
    EditText startsub;
    EditText endsub;
    EditText lastname;
    TextView remmo ;
    TextView remda ;
    EditText etotalamt ;
    EditText packageT ;
    EditText macid ;


    RadioGroup paid ;
    RadioButton paidchoice ;
    RadioButton fully;

    RadioGroup modeofpay;
    RadioButton payoption;

    Date date1, date2;

    String cname ;
    String clastname;
    String startofsub;
    int Days,Months;
    String endofsub, DaysLeft,MonthsLeft;
    String totalamt ;
    String mac_id;
    String packagetype ;
    String storePaidoptn;
    String typeofpay;
    String name_key;
    String Fire_name_key;
    String phnum = "6478305885";
    String msg = "Yo this app works!!!";

    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference reff;
    Member member;
    final int RequestCode = 1;

    private static final String TAG = "MyActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_customer);
        send = findViewById(R.id.send);
        name = findViewById(R.id.ename);
        startsub = findViewById(R.id.estsub);
        endsub = findViewById(R.id.eesub);
        remmo = findViewById(R.id.vrmo);
        lastname = findViewById(R.id.lastname);
        remda = findViewById(R.id.vrda);
        etotalamt = findViewById(R.id.etotamt);
        packageT = findViewById(R.id.evpack);
        macid = findViewById(R.id.emacid);

        paid = findViewById(R.id.gpaid);
        modeofpay = findViewById(R.id.gmopay);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        reff = mFirebaseDatabase.getReference("customers");

      //  Toast.makeText(this, "Firebase connection Success !! Your good to go !!", Toast.LENGTH_SHORT).show();
      if(checkPermission(Manifest.permission.SEND_SMS)){
          Calendar c = Calendar.getInstance();
          int timeOfDay = c.get(Calendar.MINUTE);
          Date time = c.getTime();
          DateFormat dateFormat = new SimpleDateFormat("hh:mm");
          String strDate = dateFormat.format(time);
          // if(timeOfDay == 01){
      //    if(strDate.equals("10:44")){
          try {
              SmsManager smsManager = SmsManager.getDefault();
              smsManager.sendTextMessage(phnum, null, msg, null, null);
              Toast.makeText(getApplicationContext(), "SMS Sent!",
                      Toast.LENGTH_LONG).show();
          } catch (Exception e) {
              Toast.makeText(getApplicationContext(),
                      "SMS faild, please try again later!",
                      Toast.LENGTH_LONG).show();
              e.printStackTrace();
          }

         // }
      }
      else{
          ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, RequestCode);
      }


       // }
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int paidid = paid.getCheckedRadioButtonId();
                paidchoice = findViewById(paidid);
                storePaidoptn = paidchoice.getText().toString();
                int mopayid = modeofpay.getCheckedRadioButtonId();
                payoption = findViewById(mopayid);
                typeofpay = payoption.getText().toString();
                cname = name.getText().toString();

                clastname = lastname.getText().toString();
                totalamt = etotalamt.getText().toString();
                mac_id= macid.getText().toString();
                packagetype = packageT.getText().toString();
                name_key = cname+clastname;
                Fire_name_key = name_key.toLowerCase();
                String id = reff.push().getKey();
                member = new Member(cname,clastname, date1.toString(), date2.toString(), totalamt, packagetype, mac_id, typeofpay, storePaidoptn,name_key);

                reff.child(Fire_name_key).setValue(member);
                Toast.makeText(AddCustomer.this, "Customer info successfully saved !", Toast.LENGTH_SHORT).show();
                goToMain();
            }
        });
    }
    public boolean checkPermission(String permission){
        int check = ContextCompat.checkSelfPermission(this,permission);
        return (check== PackageManager.PERMISSION_GRANTED);
    }
   /* public void TransferValues(){

            send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int paidid = paid.getCheckedRadioButtonId();
                    paidchoice = (RadioButton) findViewById(paidid);
                    storePaidoptn = paidchoice.getText().toString();
                    int mopayid = modeofpay.getCheckedRadioButtonId();
                    payoption = (RadioButton) findViewById(mopayid);
                    typeofpay = payoption.getText().toString();
                    cname = name.getText().toString();
                    startofsub = startsub.getText().toString();
                    endofsub = endsub.getText().toString();
                    totalamt = etotalamt.getText().toString();
                    mac_id= macid.getText().toString();
                    packagetype = packageT.getText().toString();

                    member.Setname(cname);
                    member.SetStarsub(startofsub);
                    member.SetEndsub(endofsub);
                    member.Setpkgtype(packagetype);
                    member.SetMACid(mac_id);
                    member.SetToalamt(totalamt);

                    reff.push().setValue(member);
                     // Toast.makeText(AddCustomer.this,storePaidoptn, Toast.LENGTH_SHORT).show();

                    //Toast.makeText(AddCustomer.this,"Payment Option: "+ storePaidoptn+" Type of Payment: "+typeofpay, Toast.LENGTH_SHORT).show();*/


    private void goToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
