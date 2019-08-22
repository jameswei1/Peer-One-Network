package sharora.mysubscription;

import android.content.Context;
import android.content.Intent;
import android.view.CollapsibleActionView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.myViewHolder> {

    Context context;
    ArrayList<Member> customers;

    public RecyclerAdapter(Context mContext, ArrayList<Member> mcustomers){
        context = mContext;
        customers = mcustomers;
    }


    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new myViewHolder(LayoutInflater.from(context).inflate(R.layout.cardview,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final myViewHolder holder, int position) {

        holder.name.setText(customers.get(position).Getname()+" " + customers.get(position).Getlastname());
        holder.start_sub.setText(customers.get(position).GetStartsub());
        holder.end_sub.setText(customers.get(position).GetEndsub());
        holder.paymentoptn.setText(customers.get(position).GetPaymentOption());
        holder.pkgtype.setText(customers.get(position).Getpkgtype());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               Intent intent = new Intent(context, DisplayUsers.class);
               intent.putExtra("NAME ID", holder.name.getText().toString().replaceAll("\\s+", "").toLowerCase());
               context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return customers.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder{
        TextView name,start_sub,end_sub,paymentoptn,pkgtype;
        CardView cardView;
        ImageView light;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            name =(TextView)itemView.findViewById(R.id.rname);
            start_sub =(TextView)itemView.findViewById(R.id.rstartsub);
            end_sub =(TextView)itemView.findViewById(R.id.rendsub);
            light =(ImageView) itemView.findViewById(R.id.imageView);
            paymentoptn =(TextView)itemView.findViewById(R.id.rtypeofpay);
            pkgtype =(TextView)itemView.findViewById(R.id.rpkgtype);
            cardView = (CardView)itemView.findViewById(R.id.cardview);
        }
    }


}
