package sharora.mysubscription;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class View_All_Customers extends AppCompatActivity {

    DatabaseReference reference;
    RecyclerView recyclerView;
    ArrayList<Member> customers;
    RecyclerAdapter adapter;
    CardView cardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__all__customers);

        cardView = (CardView)findViewById(R.id.cardview);
        recyclerView = (RecyclerView)findViewById(R.id.recyler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
      /*  DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(View_All_Customers.this, R.drawable.divider));
        recyclerView.addItemDecoration(dividerItemDecoration);*/
        customers = new ArrayList<Member>();

        reference = FirebaseDatabase.getInstance().getReference("customers");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    Member mcustomer = ds.getValue(Member.class);
                    customers.add(mcustomer);
                }
                adapter = new RecyclerAdapter(View_All_Customers.this,customers);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(View_All_Customers.this, "Sorry.. We are facing a problem.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
