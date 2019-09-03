package sharora.mysubscription;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import android.view.View;
import android.widget.Toast;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class EditCustomer extends AppCompatActivity {

    EditText startSubField, firstNameField, lastNameField, endSubField, totalAmountField, MACIDField, pNum;
    ConstraintLayout mconstraint;
    Spinner packageTypeField;
    Button update;
    CalendarView mcalendarView;
    ArrayList<String> Packages;
    Date date1, date2;

    RadioGroup modeofpay;
    RadioGroup paid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_customer);
        mcalendarView = findViewById(R.id.subs_calendar);
        mcalendarView.setVisibility(View.INVISIBLE);
        mconstraint = findViewById(R.id.editconstraint);
        update = findViewById(R.id.updateButton);

        final String startSub = getIntent().getStringExtra("startSub");
        startSubField = findViewById(R.id.startSubField);
        startSubField.setText(startSub);
        startSubField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeKey();
                mcalendarView.setVisibility(View.VISIBLE);

                mcalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
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
                        startSubField.setText(dateFormat.format(startdate));
                    }
                });
            }
        });


        final String endSub = getIntent().getStringExtra("endSub");
        endSubField = findViewById(R.id.endSubField);
        endSubField.setText(endSub);

        endSubField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeKey();
                mcalendarView.setVisibility(View.VISIBLE);

                mcalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
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
                        endSubField.setText(dateFormat.format(enddate));
                    }
                });
            }
        });

        mconstraint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mcalendarView.setVisibility(View.INVISIBLE);
            }
        });

        packageTypeField = findViewById(R.id.packagetypefield);
      //  final String totalAmount = getIntent().getStringExtra("totalAmount");
        totalAmountField = findViewById(R.id.totalAmountField);
      //  totalAmountField.setText(totalAmount);

        final String fName = getIntent().getStringExtra("firstName");
        final String AfteCapFname = fName.substring(0,1).toUpperCase()+fName.substring(1).toLowerCase();
        firstNameField = findViewById(R.id.firstNameField);
        firstNameField.setText(AfteCapFname);

        final String lName = getIntent().getStringExtra("lastName");
        final String AfteCapLname = lName.substring(0,1).toUpperCase()+lName.substring(1).toLowerCase();
        lastNameField = findViewById(R.id.lastNameField);
        lastNameField.setText(AfteCapLname);

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

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(EditCustomer.this, R.layout.customer_spinner, Packages);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                packageTypeField.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        packageTypeField.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                final String clickedItem = (String) adapterView.getItemAtPosition(i);
                Query query2 = FirebaseDatabase.getInstance().getReference("packages").orderByChild("name").equalTo(clickedItem);
                query2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild(clickedItem)) {
                            for(DataSnapshot ds: dataSnapshot.getChildren()) {
                                totalAmountField.setText(ds.getValue(Packg.class).getPrice());
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
                totalAmountField.setText("Select a package");
            }
        });


        final String MACID = getIntent().getStringExtra("MACID");
        MACIDField = findViewById(R.id.MACIDField);
        MACIDField.setText(MACID);

        final String pNumber = getIntent().getStringExtra("number");
        pNum = findViewById(R.id.phoneNumber);
        pNum.setText(pNumber);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paid = findViewById(R.id.gpaid);
                modeofpay = findViewById(R.id.gmopay);
                try {
                    date1= new SimpleDateFormat("dd/MM/yyyy").parse(startSubField.getText().toString());
                    date2= new SimpleDateFormat("dd/MM/yyyy").parse(endSubField.getText().toString());
                }
                catch (Exception e) {}

                if (firstNameField.getText().toString().matches("") || lastNameField.getText().toString().matches("") ||
                        startSubField.getText().toString().matches("") || endSubField.getText().toString().matches("") ||
                        totalAmountField.getText().toString().matches("") || packageTypeField.getSelectedItem().toString().matches("") ||
                        MACIDField.getText().toString().matches("") || pNum.getText().toString().matches("") ||
                        paid.getCheckedRadioButtonId() == -1 || modeofpay.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(EditCustomer.this, "Something's missing", Toast.LENGTH_SHORT).show();
                }
                else if (date2 == null || date1 == null) {
                    Toast.makeText(EditCustomer.this, "Improper date format", Toast.LENGTH_SHORT).show();
                }
                else {
                    deleteValue(fName, lName);
                    final String fName = firstNameField.getText().toString();
                    final String lName = lastNameField.getText().toString();
                    final String startSub = startSubField.getText().toString();
                    final String endSub = endSubField.getText().toString();
                    final String totalAmount = totalAmountField.getText().toString();
                    final String pkgType = packageTypeField.getSelectedItem().toString();
                    final String MACID = MACIDField.getText().toString();
                    final String pNumber = pNum.getText().toString();
                    updateValue(fName, lName, startSub, endSub, totalAmount, pkgType, MACID, pNumber);
                    Toast.makeText(EditCustomer.this, "Update Successful", Toast.LENGTH_SHORT).show();
                    goToMain();
                }
            }
        });
    }

    private void deleteValue(String fName, String lName) {
        String id = fName + lName;
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("customers").child(id.toLowerCase());
        databaseReference.removeValue();
    }

    private void updateValue(String fName, String lName, String startSub, String endSub, String totalamt, String pkgtype, String macid, String Phone) {
        RadioButton paymentPlan;
        RadioButton paymentMethod;

        String id = fName + lName;
        String HowMuchPaid;
        String PaymentMethod;


        paymentPlan = findViewById(paid.getCheckedRadioButtonId());
        HowMuchPaid = paymentPlan.getText().toString();


        paymentMethod = findViewById(modeofpay.getCheckedRadioButtonId());
        PaymentMethod = paymentMethod.getText().toString();


        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("customers").child(id.toLowerCase());

        Member member = new Member(fName, lName, startSub, endSub, totalamt, pkgtype, macid, PaymentMethod, HowMuchPaid, id, Phone);
        databaseReference.setValue(member);
    }

    private void goToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    private void closeKey(){
        View view = this.getCurrentFocus();
        if(view!=null){
            InputMethodManager inputMethodManager=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
    }
}