package sharora.mysubscription;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class Enter_Firstname_Lastname extends AppCompatActivity {

    EditText firstname,lastname;
    ImageButton snd;

    String First_name,Last_name,Fire_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter__firstname__lastname);
        firstname = (EditText)findViewById(R.id.firstname);
        snd = (ImageButton)findViewById(R.id.send);
        lastname = (EditText)findViewById(R.id.lastname);
        snd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoToDisplayCustomer();
            }
        });
    }

    public void GoToDisplayCustomer(){

        First_name = firstname.getText().toString();
        Last_name = lastname.getText().toString();
        Fire_name = First_name+Last_name;
        Intent intent = new Intent(this, DisplayUsers.class);
        intent.putExtra("NAME ID",Fire_name.toLowerCase());
        startActivity(intent);
    }
}
