package com.example.notes_kazakov.presentations;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes_kazakov.R;
import com.example.notes_kazakov.datas.Category;
import com.example.notes_kazakov.datas.CategoryAdapter;
import com.example.notes_kazakov.datas.Item;
import com.example.notes_kazakov.datas.ItemAdapter;
import com.example.notes_kazakov.datas.iOnClickInterface;

import java.util.ArrayList;

public class PopularActivity extends AppCompatActivity {

    private RecyclerView categoryRV, cardRV;
    private TextView tvNamePage;
    private CategoryAdapter categoryAdapter;
    private ArrayList<Category> categories = new ArrayList<>();
    private ArrayList<Item> items = new ArrayList<>();
    private int idCategory = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular);

        // Инициализация компонентов
        categoryRV = findViewById(R.id.category_list);
        cardRV = findViewById(R.id.card_list);
        tvNamePage = findViewById(R.id.tv_name_page);

        // Получаем параметры из Intent
        Bundle arguments = getIntent().getExtras();
        if (arguments != null) {
            idCategory = arguments.getInt("Category", -1);
        }

        // Загружаем категории
        categories = createMockCategories();

        // Обработка категории
        if (idCategory != -1 && idCategory < categories.size()) {
            Category selectCategory = categories.get(idCategory);
            selectCategory.setActive(true);
            tvNamePage.setText(selectCategory.getName());

            categoryAdapter = new CategoryAdapter(this, categories, clickListener);
            categoryRV.setAdapter(categoryAdapter);
        } else {
            categoryRV.setVisibility(View.GONE);
            TextView tvNameCategory = findViewById(R.id.tv_name_category);
            if (tvNameCategory != null) {
                tvNameCategory.setVisibility(View.GONE);
            }
        }

        // Загружаем товары
        items = createMockItems(idCategory);
        cardRV.setLayoutManager(new GridLayoutManager(this, 2));
        ItemAdapter cardAdapter = new ItemAdapter(this, items, MainActivity.init.AddBasker);
        cardRV.setAdapter(cardAdapter);
    }

    private ArrayList<Category> createMockCategories() {
        ArrayList<Category> list = new ArrayList<>();
        list.add(new Category(1, "Электроника"));
        list.add(new Category(2, "Одежда"));
        list.add(new Category(3, "Обувь"));
        list.add(new Category(4, "Аксессуары"));
        return list;
    }

    private ArrayList<Item> createMockItems(int categoryId) {
        ArrayList<Item> list = new ArrayList<>();

        if (categoryId == -1 || categoryId == 1) {
            // Электроника
            list.add(new Item(1, "Смартфон", "Samsung Galaxy S21", 59990));
            list.add(new Item(2, "Наушники", "Sony WH-1000XM4", 19990));
            list.add(new Item(3, "Часы", "Apple Watch Series 7", 39990));
        }

        if (categoryId == -1 || categoryId == 2) {
            // Одежда
            list.add(new Item(4, "Футболка", "Adidas Originals", 2990));
            list.add(new Item(5, "Джинсы", "Levi's 501", 4990));
        }

        return list;
    }

    private iOnClickInterface clickListener = new iOnClickInterface() {
        @Override
        public void setClick(View view, int position) {
            // Сброс активной категории
            for (Category category : categories) {
                category.setActive(false);
            }

            // Установка новой активной категории
            Category selectCategory = categories.get(position);
            selectCategory.setActive(true);
            tvNamePage.setText(selectCategory.getName());

            // Обновление адаптера
            categoryAdapter.notifyDataSetChanged();

            // Обновление товаров
            items = createMockItems(position);
            ItemAdapter cardAdapter = new ItemAdapter(PopularActivity.this, items, MainActivity.init.AddBasker);
            cardRV.setAdapter(cardAdapter);
        }
    };

    public void ClosePopularActivity(View view) {
        finish();
    }
}