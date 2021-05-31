package com.littlejerk.xrecyclerviewdivider;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import static com.littlejerk.xrecyclerviewdivider.R.id.btn_StaggeredGridLayoutManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_LinearLayoutManager).setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, LinearActivity.class));
        });

        findViewById(R.id.btn_GridLayoutManager).setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, GridActivity.class));

        });

        findViewById(btn_StaggeredGridLayoutManager).setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, StaggeredGridActivity.class));

        });

    }
}