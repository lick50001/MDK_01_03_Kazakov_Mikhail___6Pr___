package com.example.notes_kazakov.presentations;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes_kazakov.R;
import com.example.notes_kazakov.datas.Basket;
import com.example.notes_kazakov.datas.Category;
import com.example.notes_kazakov.datas.CategoryAdapter;
import com.example.notes_kazakov.datas.Item;
import com.example.notes_kazakov.datas.ItemAdapter;
import com.example.notes_kazakov.datas.iOnClickInterface;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static MainActivity init;
    public ArrayList<Basket> BasketList = new ArrayList<>();
    public ArrayList<Item> Items;
    public ArrayList<Category> Categories;
    private Context context;
    private CategoryAdapter categoryAdapter;

    public iOnClickInterface AddBasker = new iOnClickInterface() {
        @Override
        public void setClick(View view, int position) {
            Item findItem = null;
            for (Item item : Items) {
                if (item.id == position) {
                    findItem = item;
                    break;
                }
            }

            if (findItem != null) {
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
        }
    };

    // Обработчик клика по категории
    private final iOnClickInterface categoryClickListener = new iOnClickInterface() {
        @Override
        public void setClick(View view, int position) {
            // Сброс активной категории
            for (Category category : Categories) {
                category.setActive(false);
            }

            // Установка новой активной категории
            Category selectCategory = Categories.get(position);
            selectCategory.setActive(true);

            // Обновление адаптера категорий
            categoryAdapter.notifyDataSetChanged();

            // Фильтрация товаров по выбранной категории
            filterItemsByCategory(position);
        }
    };

    private void filterItemsByCategory(int categoryPosition) {
        ArrayList<Item> filteredItems = new ArrayList<>();

        if (categoryPosition == 0) {
            // Все товары
            filteredItems.addAll(createMockItems());
        } else {
            // Товары по категории
            filteredItems.addAll(getItemsByCategory(categoryPosition));
        }

        // Обновление адаптера товаров
        ItemAdapter cardAdapter = new ItemAdapter(this, filteredItems, AddBasker);
        RecyclerView cardList = findViewById(R.id.card_list);
        cardList.setAdapter(cardAdapter);
    }

    private ArrayList<Item> getItemsByCategory(int categoryPosition) {
        ArrayList<Item> list = new ArrayList<>();

        switch (categoryPosition) {
            case 1: // Электроника
                list.add(new Item(1, "Смартфон", "Samsung Galaxy S21", 59990));
                list.add(new Item(2, "Наушники", "Sony WH-1000XM4", 19990));
                list.add(new Item(3, "Часы", "Apple Watch Series 7", 39990));
                break;
            case 2: // Одежда
                list.add(new Item(4, "Футболка", "Adidas Originals", 2990));
                list.add(new Item(5, "Джинсы", "Levi's 501", 4990));
                list.add(new Item(6, "Куртка", "The North Face", 8990));
                break;
            case 3: // Обувь
                list.add(new Item(7, "Кроссовки", "Nike Air Max", 7990));
                list.add(new Item(8, "Ботинки", "Timberland", 12990));
                break;
            case 4: // Аксессуары
                list.add(new Item(9, "Рюкзак", "Herschel", 3990));
                list.add(new Item(10, "Часы", "Casio", 4990));
                break;
        }

        return list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;
        init = this;

        // Создание данных
        Categories = createMockCategories();
        Items = createMockItems();

        // Настройка категорий
        RecyclerView categoryList = findViewById(R.id.category_list);
        categoryList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        categoryAdapter = new CategoryAdapter(this, Categories, categoryClickListener);
        categoryList.setAdapter(categoryAdapter);

        // Настройка товаров
        RecyclerView cardList = findViewById(R.id.card_list);
        cardList.setLayoutManager(new androidx.recyclerview.widget.GridLayoutManager(this, 2));
        ItemAdapter cardAdapter = new ItemAdapter(this, Items, AddBasker);
        cardList.setAdapter(cardAdapter);
    }

    private ArrayList<Item> createMockItems() {
        ArrayList<Item> list = new ArrayList<>();
        // Все товары для главной страницы
        list.add(new Item(1, "Смартфон", "Samsung Galaxy S21", 59990));
        list.add(new Item(2, "Наушники", "Sony WH-1000XM4", 19990));
        list.add(new Item(3, "Часы", "Apple Watch Series 7", 39990));
        list.add(new Item(4, "Футболка", "Adidas Originals", 2990));
        list.add(new Item(5, "Джинсы", "Levi's 501", 4990));
        list.add(new Item(6, "Куртка", "The North Face", 8990));
        list.add(new Item(7, "Кроссовки", "Nike Air Max", 7990));
        list.add(new Item(8, "Ботинки", "Timberland", 12990));
        list.add(new Item(9, "Рюкзак", "Herschel", 3990));
        list.add(new Item(10, "Часы", "Casio", 4990));
        return list;
    }

    private ArrayList<Category> createMockCategories() {
        ArrayList<Category> list = new ArrayList<>();
        // Добавляем категорию "Все" для показа всех товаров
        Category allCategory = new Category(0, "Все");
        allCategory.setActive(true); // По умолчанию активна категория "Все"
        list.add(allCategory);
        list.add(new Category(1, "Электроника"));
        list.add(new Category(2, "Одежда"));
        list.add(new Category(3, "Обувь"));
        list.add(new Category(4, "Аксессуары"));
        return list;
    }

    public void openBasketView(View view) {
        Intent intent = new Intent(this, BasketActivity.class);
        startActivity(intent);
    }
}