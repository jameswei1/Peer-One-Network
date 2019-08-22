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

        holder.Pakname.setText(packages.get(position).getName());
        holder.Pakprice.setText(packages.get(position).getPrice());


    }

    @Override
    public int getItemCount() {
        return packages.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder{
        TextView Pakname,Pakprice;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            Pakname =(TextView)itemView.findViewById(R.id.pname);
            Pakprice=itemView.findViewById(R.id.pprice);
        }
    }


}
