package sharora.mysubscription;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DisplayPackages extends AppCompatActivity {

    TextView name, price;
    String pkgName;
    Button delete;

    private FirebaseDatabase myref;
    private DatabaseReference getdata;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_packages);

        name = findViewById(R.id.namedet);
        price = findViewById(R.id.price);
        delete = findViewById(R.id.deleteButton);

        myref = FirebaseDatabase.getInstance();
        getdata = myref.getReference();

        getdata.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                showValues(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(DisplayPackages.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteValue();
                Toast.makeText(DisplayPackages.this, "Deleted Successfully", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void showValues(DataSnapshot dataSnapshot) {
        pkgName = getIntent().getStringExtra("NAME ID");
        for(DataSnapshot ds : dataSnapshot.getChildren()) {
            if (ds.hasChild(pkgName)) {
                name.setText(ds.child(pkgName).getValue(Packg.class).getName());
                price.setText(ds.child(pkgName).getValue(Packg.class).getPrice());
            }
        }
    }

    public void deleteValue() {
        String id = pkgName;
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("packages").child(id.toLowerCase());
        databaseReference.removeValue();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
