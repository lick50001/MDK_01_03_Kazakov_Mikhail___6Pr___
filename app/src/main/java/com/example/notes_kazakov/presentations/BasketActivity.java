package com.example.notes_kazakov.presentations;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes_kazakov.R;
import com.example.notes_kazakov.datas.Basket;
import com.example.notes_kazakov.datas.BasketAdapter;
import com.example.notes_kazakov.datas.iOnClickInterface;

public class BasketActivity extends AppCompatActivity {

    public iOnClickInterface Delete = new iOnClickInterface() {
        @Override
        public void setClick(View view, int position) {
            MainActivity.init.BasketList.remove(position);
            basketAdapter.notifyDataSetChanged(); // Лучше использовать notify, а не setAdapter заново
        }
    };

    public iOnClickInterface EventCost = new iOnClickInterface() {
        @Override
        public void setClick(View view, int position) {
            CostCalculation();
        }
    };

    public RecyclerView BasketRV;
    public TextView tvSum, tvAllSum;
    BasketAdapter basketAdapter;
    Context activityContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basket);

        activityContext = this;
        BasketRV = findViewById(R.id.basket_list);
        tvSum = findViewById(R.id.tv_sum);
        tvAllSum = findViewById(R.id.tv_all_sum);

        // Исправленный SimpleCallback без именованных аргументов
        ItemTouchHelper.SimpleCallback swipeCallback = new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                // Удаление элемента из списка и обновление адаптера
                int position = viewHolder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    MainActivity.init.BasketList.remove(position);
                    basketAdapter.notifyItemRemoved(position);
                    CostCalculation(); // Пересчёт после удаления
                }
            }

            // Переопределяем onChildDraw внутри Callback'а
            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                    float dX, float dY, int actionState, boolean isCurrentlyActive) {

                Resources r = getResources();
                float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 58, r.getDisplayMetrics());

                LinearLayout btnDelete = viewHolder.itemView.findViewById(R.id.ll_delete);
                LinearLayout btnCount = viewHolder.itemView.findViewById(R.id.ll_count);

                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    if (dX < -px) {
                        btnDelete.setVisibility(View.VISIBLE);
                        btnCount.setVisibility(View.GONE);
                    } else if (dX > px) {
                        btnDelete.setVisibility(View.GONE);
                        btnCount.setVisibility(View.VISIBLE);
                    } else {
                        btnDelete.setVisibility(View.GONE);
                        btnCount.setVisibility(View.GONE);
                    }
                }

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeCallback);
        itemTouchHelper.attachToRecyclerView(BasketRV);

        basketAdapter = new BasketAdapter(this, MainActivity.init.BasketList, Delete, EventCost);
        BasketRV.setAdapter(basketAdapter);

        CostCalculation();
    }

    public void CostCalculation() {
        float itemPrice = 0;
        for (Basket item : MainActivity.init.BasketList) {
            itemPrice += item.Item.Price * item.Count;
        }

        tvSum.setText("P" + String.format("%.2f", itemPrice));
        itemPrice += 60.20f;
        tvAllSum.setText("P" + String.format("%.2f", itemPrice));
    }

    public void ClosePopularActivity(View view) {
        finish();
    }
}