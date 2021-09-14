package com.infocity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.infocity.InfoCity.R;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener{
    Button btnElBusiness,btnElEntertainment, btnElHealth, btnElScience, btnElSports, btnElTechnology;

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_search );

        btnElBusiness = findViewById( R.id.btnElBusiness );
        btnElEntertainment = findViewById( R.id.btnElEntertainment );
        btnElHealth = findViewById( R.id.btnElHealth );
        btnElScience = findViewById( R.id.btnElScience );
        btnElSports = findViewById( R.id.btnElSports );
        btnElTechnology = findViewById( R.id.btnElTechnology );

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_search);

        bottomNavigationView.setOnNavigationItemSelectedListener( new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.nav_home:
                        startActivity( new Intent( getApplicationContext(),HomeActivity.class ) );
                        overridePendingTransition(  0,0);
                        finish();
                    case R.id.nav_search:
                        return true;
                    case R.id.nav_notifications:

                }
                return false;
            }
        } ) ;
    }


    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btnElBusiness) {
            Intent intent = new Intent(this, BusinessActivity.class);
            startActivity(intent);
        }else if(view.getId() == R.id.btnElEntertainment){
            Intent intent = new Intent(this, EntertainmentActivity.class);
            startActivity(intent);
        }
        else if(view.getId()==R.id.btnElHealth){
            Intent intent = new Intent(this, HealthActivity.class);
            startActivity(intent);
        }
        else if (view.getId()==R.id.btnElScience){
            Intent intent = new Intent(this, ScienceActivity.class);
            startActivity(intent);
        }
        else if (view.getId()==R.id.btnElSports){
            Intent intent = new Intent(this, SportsActivity.class);
            startActivity(intent);
        }
        else if (view.getId()==R.id.btnElTechnology){
            Intent intent = new Intent(this, TechnologyActivity.class);
            startActivity(intent);
        }



    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent( SearchActivity.this, HomeActivity.class );
        startActivity( intent );
        finish();
    }
}
