package sharora.mysubscription;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class Enter_Firstname_Lastname extends AppCompatActivity {

    EditText macidentered;
    ImageButton snd;

    String custmacid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter__firstname__lastname);
        macidentered = (EditText)findViewById(R.id.macidenter);
        snd = (ImageButton)findViewById(R.id.send);

        snd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoToDisplayCustomer();
            }
        });
    }

    public void GoToDisplayCustomer(){

        custmacid = macidentered.getText().toString();
        Intent intent = new Intent(this, DisplayUsers.class);
        intent.putExtra("MAC ID",custmacid);
        startActivity(intent);
    }
}
