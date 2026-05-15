// com.example.notes_kazakov.datas.CategoryAdapter
package com.example.notes_kazakov.datas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes_kazakov.R;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private final Context context;
    private final ArrayList<Category> categories;
    private final iOnClickInterface onClickListener;

    public CategoryAdapter(Context context, ArrayList<Category> categories, iOnClickInterface onClickListener) {
        this.context = context;
        this.categories = categories;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Category category = categories.get(position);
        holder.tvName.setText(category.Name);
        holder.itemView.setActivated(category.Active);

        holder.itemView.setOnClickListener(v -> {
            if (onClickListener != null) {
                onClickListener.setClick(v, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_category_name);
        }
    }
}