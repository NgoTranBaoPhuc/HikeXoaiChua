package com.example.hikexoaichua;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.SearchView;
import android.widget.Toast;
import android.app.SearchManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    BottomNavigationView bottom_menu;

    MyDatabaseHelper myDB;
    ArrayList<HikeModel> hike;
    SearchView search_sv;

    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        changeActivity();
        fetchHikeData();

    }
    public void changeActivity() {
        bottom_menu = findViewById(R.id.bottom_navigation);
        bottom_menu.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.mainActivity) {
                changeActivity(MainActivity.class);

            } else if (item.getItemId() == R.id.addFragment) {
                changeActivity(AddHike.class);

            } else if(item.getItemId() == R.id.search_sv){
                changeActivity(SearchHike.class);
            }
            return false;
        });
    }
    private void changeActivity(Class<?> targetActivity) {
        Intent intent = new Intent(this, targetActivity);
        startActivity(intent);
    }
    public void fetchHikeData() {
        recyclerView = findViewById(R.id.recyclerView);
        MyDatabaseHelper db = new MyDatabaseHelper(this);
        hike = db.fetchHikeData();
        customAdapter = new CustomAdapter(this, hike, hike);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}