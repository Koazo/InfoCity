package com.infocity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.infocity.InfoCity.R;
import com.infocity.Model.Articles;
import com.infocity.Model.Headines;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BusinessActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    final  String API_KEY = "ef9e5b7ab14c4ea2b57f092f60e28d40";
    Adapter adapter;
    List<Articles> articles = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_business );


        swipeRefreshLayout = findViewById(R.id.swipeRefresh);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final String country ="ru";


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                retrieveJson("бизнес",country,API_KEY);
            }
        });
        retrieveJson("бизнес",country,API_KEY);

        //Нижнее меню
        BottomNavigationView bottomNavigationView;
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_search);
        bottomNavigationView.setOnNavigationItemSelectedListener( new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.nav_home:
                        startActivity( new Intent( getApplicationContext(),HomeActivity.class ) );
                        overridePendingTransition(  0,0);

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
        call = ApiClient.getInstance().getApi().getSpecificData(query,apiKey);




        call.enqueue(new Callback<Headines>() {
            @Override
            public void onResponse(Call<Headines> call, Response<Headines> response) {
                if(response.isSuccessful() && response.body().getArticles() != null){
                    swipeRefreshLayout.setRefreshing(false);
                    articles.clear();
                    articles = response.body().getArticles();
                    adapter = new Adapter(BusinessActivity.this, articles);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<Headines> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(BusinessActivity.this,t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent( BusinessActivity.this, SearchActivity.class );
        startActivity( intent );
    }

}

