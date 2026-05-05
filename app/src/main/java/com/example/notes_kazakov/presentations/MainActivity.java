package com.example.notes_kazakov.presentations;

import android.content.ClipData;
import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes_kazakov.R;
import com.example.notes_kazakov.datas.Basket;
import com.example.notes_kazakov.datas.ItemAdapter;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    public  MainActivity init;
    public ArrayList<Basket> BasketList = new ArrayList<>();
    public ArrayList<ClipData.Item> Items;
    public iOnClickInterface AddBasker = new iiOnClickInterface(){
        @Override
        public void setClick(View view, int position) {
            Basket Item = BasketList.stream().filter(item -> item.Id == position).findAny().orElse(null);
            Item FindItem = Items.stream().filter(item -> item.Id == position).findAny().orElse(null);
            if (Item == null){
                Item = new Basket(FindItem, 1);
                BasketList.add(Item);
            }
            else
                Item.Count++;

            Toast.makeText(Context, "Товар добавлен в корзину", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Context = this;
        init = this;
        ArrayList<Locale.Category> Categorys = CategoryContext.All();
        Items = ItemContext.All();

        RecyclerView CategoryList = findViewById(R.id.category_list);
        RecyclerView CardList = findViewById(R.id.card_list);

        CategoryAdapter CategoryAdapter = new CategoryAdapter(this, Categorys, Click);
        CategoryList.setAdapter(CategoryAdapter);

        ItemAdapter CardAdapter = new ItemAdapter(this, Items, AddBasker);
        CardList.setAdapter(CardAdapter);
    }
}