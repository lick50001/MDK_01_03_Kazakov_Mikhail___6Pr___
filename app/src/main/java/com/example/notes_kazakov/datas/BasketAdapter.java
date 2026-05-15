package com.example.notes_kazakov.datas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes_kazakov.R;

import java.util.ArrayList;

public class BasketAdapter extends RecyclerView.Adapter<BasketAdapter.ViewHolder> {

    private final iOnClickInterface delete;
    private final iOnClickInterface cost;
    private final LayoutInflater inflater;
    private final ArrayList<Basket> basketItems;

    public BasketAdapter(Context context, ArrayList<Basket> basketItems, iOnClickInterface delete, iOnClickInterface cost) {
        this.inflater = LayoutInflater.from(context);
        this.basketItems = basketItems;
        this.delete = delete;
        this.cost = cost;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_basket, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Basket item = basketItems.get(position);
        holder.tvName.setText(item.Item.Name);
        float price = item.Item.Price.floatValue();
        holder.tvPrice.setText("₽" + String.format("%.2f", price));
        holder.tvCount.setText(String.valueOf(item.Count));

        holder.bthPlus.setOnClickListener(v -> {
            item.Count++;
            holder.tvCount.setText(String.valueOf(item.Count));
            if (cost != null) {
                cost.setClick(v, position);
            }
        });

        holder.bthMinus.setOnClickListener(v -> {
            if (item.Count > 1) {
                item.Count--;
                holder.tvCount.setText(String.valueOf(item.Count));
                if (cost != null) {
                    cost.setClick(v, position);
                }
            }
        });

        holder.bthDelete.setOnClickListener(v -> {
            if (delete != null) {
                delete.setClick(v, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return basketItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvPrice, tvCount;
        ImageView bthPlus, bthMinus;
        LinearLayout bthDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvPrice = itemView.findViewById(R.id.tv_price);
            tvCount = itemView.findViewById(R.id.tv_count);
            bthPlus = itemView.findViewById(R.id.bthPlus);
            bthMinus = itemView.findViewById(R.id.bthMinus);
            bthDelete = itemView.findViewById(R.id.ll_delete);
        }
    }
}