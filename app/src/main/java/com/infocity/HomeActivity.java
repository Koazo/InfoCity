package com.infocity;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;

import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.infocity.Model.Articles;
import com.infocity.Model.Headines;
import com.infocity.InfoCity.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {



    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    EditText etQuery;
    Button btnSearch;
    final  String API_KEY = "ef9e5b7ab14c4ea2b57f092f60e28d40";
    Adapter adapter;
    List<Articles> articles = new ArrayList<>();
    BottomNavigationView bottomNavigationView, bottomNavigationUpper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        swipeRefreshLayout = findViewById(R.id.swipeRefresh);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final String country ="ru";

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                retrieveJson("",country,API_KEY);
            }
        });
        retrieveJson("",country,API_KEY);


        //Верхнее меню
        bottomNavigationUpper = findViewById( R.id.bottom_navigationUpper );
        bottomNavigationUpper.setSelectedItemId(R.id.nav_country);

        bottomNavigationUpper.setOnNavigationItemSelectedListener( new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.nav_country:
                        return true;
                    case R.id.nav_world:
                        startActivity( new Intent( getApplicationContext(),WorldActivity.class ) );
                        overridePendingTransition(  0,0);
                        finish();
                }
                return false;
            }
        } );

        //Нижнее меню
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_home);

        bottomNavigationView.setOnNavigationItemSelectedListener( new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.nav_home:
                        return true;
                    case R.id.nav_search:
                        startActivity( new Intent( getApplicationContext(),SearchActivity.class ) );
                        overridePendingTransition(  0,0);
                    case R.id.nav_notifications:

                }
                return false;
            }
        } ) ;
    }


    public void retrieveJson(String query, String country, String apiKey){
        swipeRefreshLayout.setRefreshing(true);
        Call<Headines> call;
            call = ApiClient.getInstance().getApi().getSpecificData(country, apiKey);



        call.enqueue(new Callback<Headines>() {
            @Override
            public void onResponse(Call<Headines> call, Response<Headines> response) {
                if(response.isSuccessful() && response.body().getArticles() != null){
                    swipeRefreshLayout.setRefreshing(false);
                    articles.clear();
                    articles = response.body().getArticles();
                    adapter = new Adapter(HomeActivity.this, articles);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<Headines> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(HomeActivity.this,t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public String getCountry(){
        Locale locale = Locale.getDefault();
        String country = locale.getCountry();
        return country.toLowerCase();
    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent startMain = new Intent(Intent.ACTION_MAIN);
            startMain.addCategory(Intent.CATEGORY_HOME);
            startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(startMain);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
