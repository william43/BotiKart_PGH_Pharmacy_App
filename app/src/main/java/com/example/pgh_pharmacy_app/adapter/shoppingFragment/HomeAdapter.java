package com.example.pgh_pharmacy_app.adapter.shoppingFragment;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pgh_pharmacy_app.R;
import com.example.pgh_pharmacy_app.activity.VoucherActivity;
import com.example.pgh_pharmacy_app.model.HomeNewsModel;

import java.util.ArrayList;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    Context context;
    ArrayList<HomeNewsModel> homeNewsModels;

    public HomeAdapter(Context context, ArrayList<HomeNewsModel> homeNewsModels) {
        this.context = context;
        this.homeNewsModels = homeNewsModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_vouchers, parent, false);

        // Return a new holder instance
        HomeAdapter.ViewHolder viewHolder  = new HomeAdapter.ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tv.setText(homeNewsModels.get(position).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, VoucherActivity.class);
                i.putExtra("position", position);
                context.startActivity(i);
            }
        });
        if(position == 0){
            holder.r.setBackgroundResource(R.drawable.pgh_expansion);
        }
        else if(position == 1){
            holder.r.setBackgroundResource(R.drawable.pgh_state_of_the_art);
        }
        else if(position == 2){
            holder.r.setBackgroundResource(R.drawable.pgh_world_class);
        }
    }

    @Override
    public int getItemCount() {
        return homeNewsModels.size();
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
