package com.example.notes_kazakov.presentations;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes_kazakov.R;
import com.example.notes_kazakov.datas.Basket;
import com.example.notes_kazakov.datas.Category;
import com.example.notes_kazakov.datas.Item;
import com.example.notes_kazakov.datas.ItemAdapter;
import com.example.notes_kazakov.datas.iOnClickInterface;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static MainActivity init;
    public ArrayList<Basket> BasketList = new ArrayList<>();
    public ArrayList<Item> Items;
    private Context context;

    public iOnClickInterface AddBasker = new iOnClickInterface() {
        @Override
        public void setClick(View view, int position) {
            // Находим товар по позиции (лучше по id, но пока так)
            Item findItem = Items.get(position);
            boolean found = false;
            for (Basket b : BasketList) {
                if (b.Item.id == findItem.id) {
                    b.Count++;
                    found = true;
                    break;
                }
            }
            if (!found) {
                BasketList.add(new Basket(findItem, 1));
            }
            Toast.makeText(context, "Товар добавлен в корзину", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;
        init = this;

        Items = createMockItems();
        ArrayList<Category> categories = createMockCategories();

        RecyclerView categoryList = findViewById(R.id.category_list);
        RecyclerView cardList = findViewById(R.id.card_list);

        ItemAdapter cardAdapter = new ItemAdapter(this, Items, AddBasker);
        cardList.setAdapter(cardAdapter);
    }

    private ArrayList<Item> createMockItems() {
        ArrayList<Item> list = new ArrayList<>();
        list.add(new Item(1, "Телефон", "Samsung", 25000));
        list.add(new Item(2, "Наушники", "Sony", 5000));
        return list;
    }

    private ArrayList<Category> createMockCategories() {
        ArrayList<Category> list = new ArrayList<>();
        list.add(new Category(1, "Электроника"));
        list.add(new Category(2, "Одежда"));
        return list;
    }

    public void openBasketView(View view) {
        Intent intent = new Intent(this, BasketActivity.class);
        startActivity(intent);
    }
}