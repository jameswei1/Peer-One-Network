package sharora.mysubscription;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton AddCust = (ImageButton)findViewById(R.id.addcust);
        ImageButton ViewCust = (ImageButton)findViewById(R.id.viewcust);

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
}