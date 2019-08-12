package sharora.mysubscription;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditCustomer extends AppCompatActivity {

    TextView startSubField, firstNameField, lastNameField, endSubField, totalAmountField, packageTypeField, MACIDField;
    Button update;

    RadioGroup modeofpay;
    RadioButton payoption;

    RadioGroup paid ;
    RadioButton paidchoice ;
    RadioButton fully;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_customer);

        update = findViewById(R.id.updateButton);

        final String  startSub = getIntent().getStringExtra("startSub");
        startSubField =  findViewById(R.id.startSubField);
        startSubField.setText(startSub);

        final String  endSub = getIntent().getStringExtra("endSub");
        endSubField = findViewById(R.id.endSubField);
        endSubField.setText(endSub);

        final String  totalAmount = getIntent().getStringExtra("totalAmount");
        totalAmountField = findViewById(R.id.totalAmountField);
        totalAmountField.setText(totalAmount);

        final String  fName = getIntent().getStringExtra("firstName");
        firstNameField = findViewById(R.id.firstNameField);
        firstNameField.setText(fName);

        final String  lName = getIntent().getStringExtra("lastName");
        lastNameField = findViewById(R.id.lastNameField);
        lastNameField.setText(lName);

        final String  pkgType = getIntent().getStringExtra("packageType");
        packageTypeField = findViewById(R.id.packageTypeField);
        packageTypeField.setText(pkgType);

        final String  MACID = getIntent().getStringExtra("MACID");
        MACIDField = findViewById(R.id.MACIDField);
        MACIDField.setText(MACID);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteValue(fName, lName);

                final String fName = firstNameField.getText().toString();
                final String lName = lastNameField.getText().toString();
                final String startSub = startSubField.getText().toString();
                final String endSub = endSubField.getText().toString();
                final String totalAmount = totalAmountField.getText().toString();
                final String pkgType = packageTypeField.getText().toString();
                final String MACID = MACIDField.getText().toString();

//                RadioGroup payoption = findViewById(R.id.gmopay);
//                int typeOfPay = payoption.getCheckedRadioButtonId();
//
//                RadioGroup paidOrNa = findViewById(R.id.gpaid);
//                int howMuchPaid = paidOrNa.getCheckedRadioButtonId();

                updateValue(fName, lName, startSub, endSub, totalAmount, pkgType, MACID, 3, 2);
            }
        });
    }

    private void deleteValue(String fName, String lName) {
        String id = fName + lName;
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("customers").child(id.toLowerCase());
        databaseReference.removeValue();
    }

    private void updateValue(String fName, String lName, String startSub, String endSub, String totalamt, String pkgtype, String macid, int paymentmethod, int howMuchPaid) {
        String id = fName + lName;
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("customers").child(id.toLowerCase());
        Member member = new Member(fName, lName, startSub, endSub, totalamt, pkgtype, macid, "visa", "Totally Paid", id);
        databaseReference.setValue(member);
//        Toast.makeText(this, String.valueOf(paymentmethod), Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, String.valueOf(howMuchPaid), Toast.LENGTH_SHORT).show();
    }
}