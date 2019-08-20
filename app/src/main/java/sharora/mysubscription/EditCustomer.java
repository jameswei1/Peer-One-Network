package sharora.mysubscription;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import android.view.View;
import android.widget.Toast;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EditCustomer extends AppCompatActivity {

    TextView startSubField, firstNameField, lastNameField, endSubField, totalAmountField, packageTypeField, MACIDField, pNum;
    Button update;
    Date date1, date2;

    RadioGroup modeofpay;
    RadioGroup paid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_customer);

        update = findViewById(R.id.updateButton);

        final String startSub = getIntent().getStringExtra("startSub");
        startSubField = findViewById(R.id.startSubField);
        startSubField.setText(startSub);

        final String endSub = getIntent().getStringExtra("endSub");
        endSubField = findViewById(R.id.endSubField);
        endSubField.setText(endSub);

        final String totalAmount = getIntent().getStringExtra("totalAmount");
        totalAmountField = findViewById(R.id.totalAmountField);
        totalAmountField.setText(totalAmount);

        final String fName = getIntent().getStringExtra("firstName");
        firstNameField = findViewById(R.id.firstNameField);
        firstNameField.setText(fName);

        final String lName = getIntent().getStringExtra("lastName");
        lastNameField = findViewById(R.id.lastNameField);
        lastNameField.setText(lName);

        final String pkgType = getIntent().getStringExtra("packageType");
        packageTypeField = findViewById(R.id.packageTypeField);
        packageTypeField.setText(pkgType);

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
                        totalAmountField.getText().toString().matches("") || packageTypeField.getText().toString().matches("") ||
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
                    final String pkgType = packageTypeField.getText().toString();
                    final String MACID = MACIDField.getText().toString();
                    final String pNumber = pNum.getText().toString();
                    updateValue(fName, lName, startSub, endSub, totalAmount, pkgType, MACID, pNumber);
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
}