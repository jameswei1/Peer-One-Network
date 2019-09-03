package sharora.mysubscription;

import android.content.Context;
import android.content.Intent;
import android.view.CollapsibleActionView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PackageAdapter extends RecyclerView.Adapter<PackageAdapter.myViewHolder> {

    Context context;
    ArrayList<Packg> packages;

    public PackageAdapter(Context mContext, ArrayList<Packg> mpackages){
        context = mContext;
        packages = mpackages;
    }


    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new myViewHolder(LayoutInflater.from(context).inflate(R.layout.packagesview,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final myViewHolder holder, int position) {
        String PackagetobeCap =packages.get(position).getName();
        String PackageAfterCap = PackagetobeCap.substring(0,1).toUpperCase()+PackagetobeCap.substring(1).toLowerCase();
        holder.Pakname.setText(PackageAfterCap);
        holder.Pakprice.setText("$"+packages.get(position).getPrice());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, DisplayPackages.class);
                intent.putExtra("NAME_PKG", holder.Pakname.getText().toString().toLowerCase());
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return packages.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder{
        TextView Pakname,Pakprice;
        LinearLayout cardView;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            Pakname = itemView.findViewById(R.id.pname);
            Pakprice = itemView.findViewById(R.id.pprice);
            cardView = itemView.findViewById(R.id.packagecardview);
        }
    }


}
