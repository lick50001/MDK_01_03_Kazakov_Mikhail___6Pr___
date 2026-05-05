package com.example.notes_kazakov.datas;

import android.content.ClipData;
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

    public iOnClickInterface AddBasket;
    public LayoutInflater Inflater;
    public ArrayList<ClipData.Item> Item;

    public ItemAdapter(Context con, ArrayList<ClipData.Item> item, iOnClickListioner addBasket){
        this.Inflater = LayoutInflater.from(con);
        this.Item = item;
        this.AddBasket = addBasket;
    }
    @NonNull
    @Override
    public ItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = Inflater.inflate(R.layout.item_card, parent, false);
        return new RecyclerView.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemAdapter.ViewHolder holder, int position) {
        Item item = Item.get(position);
        holder.tvName.setText(Item.Name);
        holder.tvModell.setText(Item.Modell);
        holder.tvPrice.setText("₽" + String.valueOf(Item.Price));
        holder.bthAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                AddBasket.setClick(view, Item.id);
            }
        });
    }

    @Override
    public int getItemCount() {return Item.size();}

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tvName, tvModell, tvPrice;

        public LinearLayout bthAdd;

        RecyclerView.ViewHolder(View view){
            super(view);
            tvName = view.findViewById(R.id.tv_name);
            tvModell = view.findViewById(R.id.tv_modell);
            tvPrice = view.findViewById(R.id.tv_price);

            bthAdd = view.findViewById(R.id.bthAdd);
        }
    }
}
