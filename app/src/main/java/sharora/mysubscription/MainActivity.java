package sharora.mysubscription;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button AddCust = findViewById(R.id.addcust);
        Button ViewCust = findViewById(R.id.viewcust2);
        Button ViewAllCust = findViewById(R.id.viewallcust);
        Button logout = findViewById(R.id.logoutButton);
        Button viewDelBtn = findViewById(R.id.viewDeleteBtn);
        Button addBtn = findViewById(R.id.addBtn);

        if (!checkPermission(Manifest.permission.SEND_SMS)) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 1);
        }

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
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToAddPkg();
            }
        });
    }

    //Sends to next Activity
    public void GotoAddCustomer(){
        Intent intent = new Intent(this, AddCustomer.class);
        startActivity(intent);
    }

    public void GotoMACID(){
        Intent intent = new Intent(this, Enter_Firstname_Lastname.class);
        startActivity(intent);
    }
    public void GotoViewAllCustomers(){
        Intent intent = new Intent(this, View_All_Customers.class);
        startActivity(intent);
    }

    public void goToFingerPrint() {
        Intent intent = new Intent(this, Fingerprint.class);
        startActivity(intent);
    }

    public boolean checkPermission(String Permission) {
        int check = ContextCompat.checkSelfPermission(this, Permission);
        return (check== PackageManager.PERMISSION_GRANTED);
    }

    public void goToAddPkg() {
        Intent intent = new Intent(this, AddPackage.class);
        startActivity(intent);
    }
}