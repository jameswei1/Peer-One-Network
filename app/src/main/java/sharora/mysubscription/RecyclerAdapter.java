package sharora.mysubscription;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
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
        String FnameBeforeCap =customers.get(position).Getname();
        String FnameAfterCap = FnameBeforeCap.substring(0,1).toUpperCase()+FnameBeforeCap.substring(1).toLowerCase();

        String LnameBeforeCap =customers.get(position).Getlastname();
        String LnameAfterCap = LnameBeforeCap.substring(0,1).toUpperCase()+LnameBeforeCap.substring(1).toLowerCase();

        holder.name.setText(FnameAfterCap+" " + LnameAfterCap);
        holder.start_sub.setText(customers.get(position).GetStartsub());
        holder.end_sub.setText(customers.get(position).GetEndsub());
        holder.paymentoptn.setText(customers.get(position).GetPaymentOption());
        holder.pkgtype.setText(customers.get(position).Getpkgtype());

        holder.cardLinearLayout.setOnClickListener(new View.OnClickListener() {
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
        LinearLayout cardLinearLayout;
        ImageView light;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            name =(TextView)itemView.findViewById(R.id.rname);
            start_sub =(TextView)itemView.findViewById(R.id.rstartsub);
            end_sub =(TextView)itemView.findViewById(R.id.rendsub);
            light =(ImageView) itemView.findViewById(R.id.imageView);
            paymentoptn =(TextView)itemView.findViewById(R.id.rtypeofpay);
            pkgtype =(TextView)itemView.findViewById(R.id.rpkgtype);
            cardLinearLayout = itemView.findViewById(R.id.cardview);
        }
    }


}
