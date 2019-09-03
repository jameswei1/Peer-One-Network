package sharora.mysubscription;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AddCustomer extends AppCompatActivity {
    Button send;
    EditText name ;
    EditText startsub;
    EditText endsub;
    EditText lastname;
    EditText etotalamt;
    EditText macid ;
    EditText phoneNumber;
    ConstraintLayout constraintLayout;

    RadioGroup paid ;
    RadioButton paidchoice ;
    RadioButton fully;

    RadioGroup modeofpay;
    RadioButton payoption;
    CalendarView subscriptiondate;
    Date date1, date2;

    Spinner packageT;
    ArrayList<String> Packages;

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
        constraintLayout = findViewById(R.id.contraint);

        subscriptiondate = findViewById(R.id.subcalendar);
        subscriptiondate.setVisibility(View.INVISIBLE);


        paid = findViewById(R.id.gpaid);
        modeofpay = findViewById(R.id.gmopay);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        reff = mFirebaseDatabase.getReference("customers");
        packagereff = mFirebaseDatabase.getReference("packages");

        Packages = new ArrayList<>();
        Query query = FirebaseDatabase.getInstance().getReference("packages").orderByChild("name");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Packg packages = ds.getValue(Packg.class);
                    String BeforeCapPackage = packages.getName();
                    String AfterCapPackage = BeforeCapPackage.substring(0,1).toUpperCase()+BeforeCapPackage.substring(1).toLowerCase();
                    Packages.add(AfterCapPackage);
                }

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(AddCustomer.this, R.layout.customer_spinner, Packages);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                packageT.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        packageT.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                final String clickedItem = (String) adapterView.getItemAtPosition(i);
                Query query2 = FirebaseDatabase.getInstance().getReference("packages").orderByChild("name").equalTo(clickedItem);
                query2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild(clickedItem)) {
                            for(DataSnapshot ds: dataSnapshot.getChildren()) {
                                etotalamt.setText(ds.getValue(Packg.class).getPrice());
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                etotalamt.setText("Select a package");
            }
        });

        startsub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeKey();
                subscriptiondate.setVisibility(View.VISIBLE);

                subscriptiondate.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                    @Override
                    public void onSelectedDayChange(CalendarView calendarView, int year, int month, int day) {
                        String stdate= day+"/"+(month+1)+"/"+year;
                        Date startdate = null;
                        try {
                            startdate = new SimpleDateFormat("dd/MM/yyyy").parse(stdate);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        startsub.setText(dateFormat.format(startdate));
                    }
                });
            }
        });
        endsub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeKey();
                subscriptiondate.setVisibility(View.VISIBLE);

                subscriptiondate.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                    @Override
                    public void onSelectedDayChange(CalendarView calendarView, int year, int month, int day) {
                        String endate= day+"/"+(month+1)+"/"+year;
                        Date enddate = null;
                        try {
                            enddate = new SimpleDateFormat("dd/MM/yyyy").parse(endate);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        endsub.setText(dateFormat.format(enddate));
                    }
                });
            }
        });


        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                subscriptiondate.setVisibility(View.INVISIBLE);
            }
        });

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
                        etotalamt.getText().toString().matches("") ||
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

                    packagetype= packageT.getSelectedItem().toString();

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
private void closeKey(){
        View view = this.getCurrentFocus();
        if(view!=null){
            InputMethodManager inputMethodManager=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
}
    private void goToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}