package sharora.mysubscription;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddPackage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_package);

        final DatabaseReference reff;

        reff = FirebaseDatabase.getInstance().getReference("packages");

        Button addButton = findViewById(R.id.addButton);
        EditText pkg = findViewById(R.id.pkgName);
        final String pkgStr = pkg.getText().toString();

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reff.child(pkgStr).setValue();
                Toast.makeText(AddPackage.this, "Package added successfully", Toast.LENGTH_SHORT).show();
                goToMain();
            }
        });
    }

    public void goToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
