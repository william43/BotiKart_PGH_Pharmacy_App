package com.example.pgh_pharmacy_app.adapter.shoppingFragment;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pgh_pharmacy_app.R;
import com.example.pgh_pharmacy_app.model.MedicineModel;
import com.example.pgh_pharmacy_app.model.PrescribedMedicineModel;

import java.util.ArrayList;

public class OverTheCounterAdapter extends RecyclerView.Adapter<OverTheCounterAdapter.ViewHolder> {

    private Context context;
    private ArrayList<MedicineModel> stringList = new ArrayList<>();
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(MedicineModel item);
    }

    public OverTheCounterAdapter(Context context, ArrayList<MedicineModel> stringList,  OnItemClickListener listener){
        this.context = context;
        this.stringList = stringList;
        this.listener = listener;
    }

    public OverTheCounterAdapter(ArrayList<MedicineModel> stringList, OnItemClickListener listener){
        this.stringList = stringList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public OverTheCounterAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_shopping_over_the_counter, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.checkBox.setText(stringList.get(position).getGenericName());
        holder.bind(stringList.get(position), listener);
        if(stringList.get(position) instanceof PrescribedMedicineModel){
            holder.star.setVisibility(View.VISIBLE);
        }
        holder.price.setText(String.valueOf(stringList.get(position).getPrice()));
        holder.dosage.setText(stringList.get(position).getDosage());

    }

    @Override
    public int getItemCount() {
        return stringList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView checkBox;
        private ImageView star;
        private TextView dosage;
        private TextView price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            checkBox = itemView.findViewById(R.id.overthecounterCB);
            star = itemView.findViewById(R.id.prescribed_star_notice_over);
            dosage = itemView.findViewById(R.id.overthecounter_dosage);
            price = itemView.findViewById(R.id.overthecounter_price);
            star.setVisibility(View.INVISIBLE);
        }
        public void bind(final MedicineModel item, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }
}
