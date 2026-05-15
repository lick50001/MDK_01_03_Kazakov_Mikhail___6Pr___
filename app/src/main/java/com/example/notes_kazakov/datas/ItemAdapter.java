package com.example.notes_kazakov.datas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes_kazakov.R;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private final iOnClickInterface addBasket;
    private final LayoutInflater inflater;
    private final ArrayList<Item> items;
    public ItemAdapter(Context context, ArrayList<Item> items, iOnClickInterface addBasket) {
        this.inflater = LayoutInflater.from(context);
        this.items = items;
        this.addBasket = addBasket;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item currentItem = items.get(position);
        holder.tvName.setText(currentItem.Name);
        holder.tvModell.setText(currentItem.Modell);
        holder.tvPrice.setText("₽" + currentItem.Price);
        holder.bthAdd.setOnClickListener(v -> {
            addBasket.setClick(v, currentItem.id);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName, tvModell, tvPrice;
        public LinearLayout bthAdd;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvModell = itemView.findViewById(R.id.tv_modell);
            tvPrice = itemView.findViewById(R.id.tv_price);
            bthAdd = itemView.findViewById(R.id.bthAdd);
        }
    }
}