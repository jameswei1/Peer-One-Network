package sharora.mysubscription;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Enter_Firstname_Lastname extends AppCompatActivity {

    EditText firstname,lastname;
    ImageButton snd;
    String id;

    private FirebaseDatabase myref   ;
    private DatabaseReference getdata;

    String First_name,Last_name,Fire_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter__firstname__lastname);
        firstname = findViewById(R.id.firstname);
        snd = findViewById(R.id.send);
        lastname = findViewById(R.id.lastname);
        getdata = FirebaseDatabase.getInstance().getReference("customers");
        snd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (firstname.getText().toString().matches("") || lastname.getText().toString().matches("")){
                    Toast.makeText(Enter_Firstname_Lastname.this, "Field missing", Toast.LENGTH_SHORT).show();
                }
                else {
                    id = firstname.getText().toString() + lastname.getText().toString();
                    getdata.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.hasChild(id.toLowerCase())) {
                                GoToDisplayCustomer();
                            } else {
                                Toast.makeText(Enter_Firstname_Lastname.this, id+" Customer not found", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(Enter_Firstname_Lastname.this, databaseError.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
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