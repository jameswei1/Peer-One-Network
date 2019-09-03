package sharora.mysubscription;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DisplayUsers extends AppCompatActivity {
    String customer_name,recycler_customer_name,fName,lName;
    TextView custname,custstartsub,custendsub,custtotalamt,custpayoptn,custmodeofpay,custmacid,custpkgtype,pNum;
    Button edit, delete;

    private FirebaseDatabase myref   ;
    private DatabaseReference getdata;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_users);

        custname = findViewById(R.id.nameview);
        custstartsub = findViewById(R.id.startsubdet);
        custendsub = findViewById(R.id.endsubdet);
        custtotalamt = findViewById(R.id.totalamountdet);
        custpayoptn = findViewById(R.id.payoptiondet);
        custmodeofpay = findViewById(R.id.mopaydet);
        custmacid = findViewById(R.id.maciddet);
        custpkgtype = findViewById(R.id.pkgdet);
        myref = FirebaseDatabase.getInstance();
        getdata = myref.getReference();
        edit = findViewById(R.id.editInfo);
        delete = findViewById(R.id.deleteButton);
        pNum = findViewById(R.id.Mobile);


        getdata.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                showvalues(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(DisplayUsers.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editValues();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteValue();
                Toast.makeText(DisplayUsers.this, "Deleted Successfully", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void showvalues(DataSnapshot dataSnapshot){
        customer_name = getIntent().getStringExtra("NAME ID");
        for(DataSnapshot ds : dataSnapshot.getChildren()) {
            if (ds.hasChild(customer_name)) {

                String Fnametobecap =ds.child(customer_name).getValue(Member.class).Getname();
                String Fnameaftercap = Fnametobecap.substring(0,1).toUpperCase()+Fnametobecap.substring(1).toLowerCase();

                String Lnametobecap =ds.child(customer_name).getValue(Member.class).Getlastname();
                String Lnameaftercap = Lnametobecap.substring(0,1).toUpperCase()+Lnametobecap.substring(1).toLowerCase();

                custname.setText( Fnameaftercap+ " " + Lnameaftercap);
                custstartsub.setText(ds.child(customer_name).getValue(Member.class).GetStartsub());
                custendsub.setText(ds.child(customer_name).getValue(Member.class).GetEndsub());
                custtotalamt.setText(ds.child(customer_name).getValue(Member.class).GetTotalamt());
                custmacid.setText(ds.child(customer_name).getValue(Member.class).GetMACid());
                custpkgtype.setText(ds.child(customer_name).getValue(Member.class).Getpkgtype());
                custpayoptn.setText(ds.child(customer_name).getValue(Member.class).GetPaymentOption());
                custmodeofpay.setText(ds.child(customer_name).getValue(Member.class).GetPaymentMethod());
                pNum.setText(ds.child(customer_name).getValue(Member.class).getPhoneNumber());
                fName = ds.child(customer_name).getValue(Member.class).Getname();
                lName = ds.child(customer_name).getValue(Member.class).Getlastname();
            }
        }
    }

    private void editValues() {
        Intent intent = new Intent(this, EditCustomer.class);

        intent.putExtra("firstName", fName);
        intent.putExtra("lastName", lName);
        intent.putExtra("startSub", custstartsub.getText().toString());
        intent.putExtra("endSub", custendsub.getText().toString());
        intent.putExtra("totalAmount", custtotalamt.getText().toString());
        intent.putExtra("MACID", custmacid.getText().toString());
        intent.putExtra("packageType", custpkgtype.getText().toString());
        intent.putExtra("payOption", custpayoptn.getText().toString());
        intent.putExtra("paymentMethod", custmodeofpay.getText().toString());
        intent.putExtra("number", pNum.getText().toString());

        startActivity(intent);
    }

    private void deleteValue() {
        String id = fName + lName;
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("customers").child(id.toLowerCase());
        databaseReference.removeValue();
        Intent intent = new Intent(this, View_All_Customers.class);
        startActivity(intent);
    }
}