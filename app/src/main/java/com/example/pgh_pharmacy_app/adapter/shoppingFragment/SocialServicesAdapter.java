package com.example.pgh_pharmacy_app.adapter.shoppingFragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pgh_pharmacy_app.R;
import com.example.pgh_pharmacy_app.model.Vouchers;

import java.util.ArrayList;

public class SocialServicesAdapter extends RecyclerView.Adapter<SocialServicesAdapter.ViewHolder> {

    ArrayList<Vouchers> voucherslist;
    Context context;

    public SocialServicesAdapter(ArrayList<Vouchers> voucherslist, Context context) {
        this.voucherslist = voucherslist;
        this.context = context;
    }

    @NonNull
    @Override
    public SocialServicesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_vouchers, parent, false);

        // Return a new holder instance
        SocialServicesAdapter.ViewHolder viewHolder  = new SocialServicesAdapter.ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SocialServicesAdapter.ViewHolder holder, int position) {

        holder.tv.setText(voucherslist.get(position).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        if(position % 4 == 1){
            holder.r.setBackgroundResource(R.drawable.health_care_bohol);
        }
        else if(position % 4 == 2){
            holder.r.setBackgroundResource(R.drawable.health_care_child);
        }
        else if(position % 4 == 3){
            holder.r.setBackgroundResource(R.drawable.health_care_programs);
        }
    }

    @Override
    public int getItemCount() {
        return voucherslist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView tv;
        RelativeLayout r;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.vouchers_name);
            img = itemView.findViewById(R.id.vouchers_img);
            r = itemView.findViewById(R.id.item_background);
        }
    }
}
