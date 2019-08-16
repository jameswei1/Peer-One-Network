
package sharora.mysubscription;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Splashscreen extends AppCompatActivity {
    TextView name;
    Date date1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        name = findViewById(R.id.name_val);

        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        calendar.add(Calendar.DATE, 5);
        date1 = calendar.getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyy");
        String strDate = dateFormat.format(date1);
//        Log.d("date", strDate);

        Query query = FirebaseDatabase.getInstance().getReference("customers").orderByChild("Endsub").equalTo(strDate);


        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists() ){
                    for(DataSnapshot ds: dataSnapshot.getChildren())
                        name.setText( ds.getValue(Member.class).Getname());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        query.addListenerForSingleValueEvent(valueEventListener);
    }
}