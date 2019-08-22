package sharora.mysubscription;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AddCustomer extends AppCompatActivity {
    Button send;
    EditText name ;
    EditText startsub;
    EditText endsub;
    EditText lastname;
    TextView remmo ;
    TextView remda ;
    EditText etotalamt ;
    EditText packageT ;
    EditText macid ;
    EditText phoneNumber;


    RadioGroup paid ;
    RadioButton paidchoice ;
    RadioButton fully;

    RadioGroup modeofpay;
    RadioButton payoption;

    Date date1, date2;

    String cname ;
    String clastname;
    String totalamt ;
    String mac_id;
    String packagetype ;
    String storePaidoptn;
    String typeofpay;
    String name_key;
    String Fire_name_key;
    String PhoneNumber;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference reff,packagereff;
    Member member;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_customer);
        send = findViewById(R.id.send);
        name = findViewById(R.id.ename);
        startsub = findViewById(R.id.estsub);
        endsub = findViewById(R.id.eesub);
        lastname = findViewById(R.id.lastname);
        etotalamt = findViewById(R.id.etotamt);
        packageT = findViewById(R.id.evpack);
        macid = findViewById(R.id.emacid);
        phoneNumber = findViewById(R.id.number);

        paid = findViewById(R.id.gpaid);
        modeofpay = findViewById(R.id.gmopay);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        reff = mFirebaseDatabase.getReference("customers");
        packagereff = mFirebaseDatabase.getReference("packages");

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    date1= new SimpleDateFormat("dd/MM/yyyy").parse(startsub.getText().toString());
                    date2= new SimpleDateFormat("dd/MM/yyyy").parse(endsub.getText().toString());
                }
                catch (Exception e) {}

                if (name.getText().toString().matches("") || lastname.getText().toString().matches("") ||
                        startsub.getText().toString().matches("") || endsub.getText().toString().matches("") ||
                        etotalamt.getText().toString().matches("") || packageT.getText().toString().matches("") ||
                        macid.getText().toString().matches("") || phoneNumber.getText().toString().matches("")) {
                    Toast.makeText(AddCustomer.this, "Field missing", Toast.LENGTH_SHORT).show();
                }

                else if (date1 == null || date2 == null) {
                    Toast.makeText(AddCustomer.this, "Improper date format", Toast.LENGTH_SHORT).show();
                }
                else if (!(phoneNumber.getText().toString().matches("\\d+")) || phoneNumber.getText().toString().length() != 10) {
                    Toast.makeText(AddCustomer.this, "Improper phone number format", Toast.LENGTH_SHORT).show();
                }

                else {
                    int paidid = paid.getCheckedRadioButtonId();
                    paidchoice = findViewById(paidid);
                    storePaidoptn = paidchoice.getText().toString();
                    int mopayid = modeofpay.getCheckedRadioButtonId();
                    payoption = findViewById(mopayid);
                    typeofpay = payoption.getText().toString();

                    cname = name.getText().toString();
                    PhoneNumber = phoneNumber.getText().toString();
                    clastname = lastname.getText().toString();
                    totalamt = etotalamt.getText().toString();
                    mac_id = macid.getText().toString();
                    packagetype = packageT.getText().toString();

                    name_key = cname+clastname;
                    Fire_name_key = name_key.toLowerCase();

                    try {
                        member = new Member(cname,clastname, startsub.getText().toString(), endsub.getText().toString(), totalamt, packagetype, mac_id, typeofpay, storePaidoptn,name_key, PhoneNumber);
                        reff.child(Fire_name_key).setValue(member);
                        Toast.makeText(AddCustomer.this, "Customer info successfully saved !", Toast.LENGTH_SHORT).show();
                        goToMain();
                    }
                    catch (Exception e) {}
                }
            }
        });
    }

    public ArrayList<String> retrieve(){
        final ArrayList<String> packages = new ArrayList<>();
        packagereff.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                fetchdata(dataSnapshot,packages);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                fetchdata(dataSnapshot,packages);

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return packages;
    }

    private void fetchdata(DataSnapshot dataSnapshot,ArrayList<String> Packages){
        Packages.clear();
        for(DataSnapshot ds : dataSnapshot.getChildren()){
            String packages = ds.getValue(Packg.class).getName();
            Packages.add(packages);

        }
    }
    private void goToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}