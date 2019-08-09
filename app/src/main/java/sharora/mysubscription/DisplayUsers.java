package sharora.mysubscription;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DisplayUsers extends AppCompatActivity {
    String macidcustomer,cname,cstsub,cesub,ctotamt,cpayoptn,cmopay,cmacid;
    TextView custname,custstartsub,custendsub,custtotalamt,custpayoptn,custmodeofpay,custmacid,custpkgtype;
    private FirebaseDatabase myref;
    private DatabaseReference getdata;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_users);
        macidcustomer = getIntent().getStringExtra("MAC ID");
        // Toast.makeText(this, "MAC ID:"+macidcustomer, Toast.LENGTH_SHORT).show();
        custname = (TextView)findViewById(R.id.namedet);
        custstartsub = (TextView)findViewById(R.id.startsubdet);
        custendsub = (TextView)findViewById(R.id.endsubdet);
        custtotalamt = (TextView)findViewById(R.id.totalamountdet);
        custpayoptn = (TextView)findViewById(R.id.payoptiondet);
        custmodeofpay = (TextView)findViewById(R.id.mopaydet);
        custmacid = (TextView)findViewById(R.id.maciddet);
        custpkgtype = (TextView)findViewById(R.id.pkgdet);
        myref = FirebaseDatabase.getInstance();
        getdata = myref.getReference();
        //  Log.d("TAG","Ehello");

        getdata.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                showvalues(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void showvalues(DataSnapshot dataSnapshot){
        for(DataSnapshot ds : dataSnapshot.getChildren()) {
            Member viewMember = new Member();
            //  Log.d("TAG","Ehello");
            // Toast.makeText(this,ds.child(macidcustomer).getValue(Member.class).Getname() , Toast.LENGTH_SHORT).show();
            // viewMember.setName(ds.child(macidcustomer).getValue(ViewMember.class).getName());
           /* viewMember.setStartsub(ds.child(macidcustomer).getValue(ViewMember.class).getStartsub());
            viewMember.setEndsub(ds.child(macidcustomer).getValue(ViewMember.class).getEndsub());
            viewMember.setTotalamt(ds.child(macidcustomer).getValue(ViewMember.class).getTotalamt());
            viewMember.setMACid(ds.child(macidcustomer).getValue(ViewMember.class).getMACid());
            viewMember.setPkgtype(ds.child(macidcustomer).getValue(ViewMember.class).getPkgtype());
            viewMember.setPaymentmethod(ds.child(macidcustomer).getValue(ViewMember.class).getPaymentmethod());
            viewMember.setPaymentoption(ds.child(macidcustomer).getValue(ViewMember.class).getPaymentoption());*/

            custname.setText(ds.child(macidcustomer).getValue(Member.class).Getname());
            custstartsub.setText(ds.child(macidcustomer).getValue(Member.class).GetStartsub());
            custendsub.setText(ds.child(macidcustomer).getValue(Member.class).GetEndsub());
            custtotalamt.setText(ds.child(macidcustomer).getValue(Member.class).GetTotalamt());
            custmacid.setText(ds.child(macidcustomer).getValue(Member.class).GetMACid());
            custpkgtype.setText(ds.child(macidcustomer).getValue(Member.class).Getpkgtype());
            custpayoptn.setText(ds.child(macidcustomer).getValue(Member.class).GetPaymentOption());
            custmodeofpay.setText(ds.child(macidcustomer).getValue(Member.class).GetPaymentMethod());
        }


    }
}
