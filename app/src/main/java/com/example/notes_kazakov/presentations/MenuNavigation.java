package com.example.notes_kazakov.presentations;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.notes_kazakov.R;

public class MenuNavigation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_menu_navigation);

        AppCompatButton btnHome = findViewById(R.id.btnHome);
        AppCompatButton btnLike = findViewById(R.id.btnLike);
        AppCompatButton btnUser = findViewById(R.id.btnUser);
        AppCompatButton btnNotif = findViewById(R.id.btnNotif);

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onHomeClick();
            }
        });

        btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLikeClick();
            }
        });

        btnUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onUserClick();
            }
        });

        btnNotif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNotificationClick();
            }
        });
    }

    public void openBasketView(View view) {
        Toast.makeText(this, "Корзина открыта", Toast.LENGTH_SHORT).show();
    }

    private void onHomeClick() {
        Toast.makeText(this, "Домой", Toast.LENGTH_SHORT).show();
    }

    private void onLikeClick() {
        Toast.makeText(this, "Избранное", Toast.LENGTH_SHORT).show();
    }

    private void onUserClick() {
        Toast.makeText(this, "Профиль пользователя", Toast.LENGTH_SHORT).show();
    }

    private void onNotificationClick() {
        Toast.makeText(this, "Уведомления", Toast.LENGTH_SHORT).show();
    }
}