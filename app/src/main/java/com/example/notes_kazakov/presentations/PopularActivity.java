package com.example.notes_kazakov.presentations;

import android.content.ClipData;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes_kazakov.R;
import com.example.notes_kazakov.datas.ItemAdapter;

import java.util.ArrayList;
import java.util.Locale;

public class PopularActivity extends AppCompatActivity {

    RecyclerView CategoryRV, CardRV;
    TextView TvNamePage;
    CategoryAdapter CaregoryAdapter;
    ArrayList<Locale.Category> Categorys = new ArrayList<>();
    ArrayList<ClipData.Item> items = new ArrayList<>();
    Context Context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular);

        Context = this;
        Categorys = CategoryContext.All();

        Bundle arguments = getIntent().getExtras();
        Integer IdCategory = arguments.getInt("Category");

        CategoryRV = findViewById(R.id.category_list);
        CardRV = findViewById(R.id.card_list);
        TvNamePage = findViewById(R.id.tv_name_page);

        if (IdCategory != -1){
            Locale.Category SelectCategory = Categorys.get(IdCategory);
            SelectCategory.Active = true;
            TvNamePage.setText(SelectCategory.Name);

            CaregoryAdapter = new CategoryAdapter(this, Categorys, Click);
            CategoryRV.setAdapter(CaregoryAdapter);
        }else{
            CategoryRV.setVisibility(View.GONE);

            TextView TvNameCategory = findViewById(R.id.tv_name_category);
            TvNameCategory.setVisibility(View.GONE);
        }

        Items = IdCategory == -1 ? ItemContext.All() : ItemContext.GetByCategory(IdCategory);
        CardRV.setLayoutManager(new GridLayoutManager(this, 2));
        ItemAdapter CardAdapter = new ItemAdapter(this, Items, MainActivity.init.AddBasker);
        CardRV.setAdapter(CardAdapter);
    }

    iOnClickInterface Click = new iOnClickInterface(){
        @Override
        public void setClick(View view, int position){
            for (Locale.Category itm : Categorys)
                itm.Active = false;

            Locale.Category SelectCategory = Categorys.get(position);
            SelectCategory.Active = true;
            CategoryRV.setAdapter(CategoryAdapter);

            TvNamePage.setText(SelectCategory.Name);

            ClipData.Item = ItemContext.GetByCategory(position);
            ItemAdapter CardAdapter = new ItemAdapter(Context, Items, MainActivity.init.AddBasker);
            CardRV.setAdapter(CardAdapter);
        }
    };

    public void ClosePopularActivity(View view){finish();}
}