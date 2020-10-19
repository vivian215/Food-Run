package com.example.foodrun;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

public class FoodCatalogueActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        setContentView(R.layout.food_catalogue);

        findViewById(R.id.donutbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FoodCatalogueActivity.this, DonutInfoActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.backbutton2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FoodCatalogueActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
