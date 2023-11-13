package com.example.hikexoaichua;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.SearchView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;
import java.util.ArrayList;

public class SearchHike extends AppCompatActivity {

    BottomNavigationView bottom_navigation_search;
    CustomAdapter customAdapter;
    SearchView search_sv;
    ArrayList<HikeModel> hike;
    RecyclerView search_lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_hike);
        changeActivity();
        fetchHikeData();
        searchHike();

    }

    public void changeActivity() {
        bottom_navigation_search = findViewById(R.id.bottom_search);
        bottom_navigation_search.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.MainActivity) {
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
        search_lv = findViewById(R.id.search_lv);
        MyDatabaseHelper db = new MyDatabaseHelper(this);
        hike = db.fetchHikeData();
        customAdapter = new CustomAdapter(this, hike, hike);
        search_lv.setAdapter(customAdapter);
        search_lv.setLayoutManager(new LinearLayoutManager(SearchHike.this));
    }


    public void searchHike() {
        search_sv = findViewById(R.id.searchView);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        search_sv.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        search_sv.setMaxWidth(Integer.MAX_VALUE);

        search_sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                customAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                customAdapter.getFilter().filter(newText);
                return false;
            }
        });

    }
}