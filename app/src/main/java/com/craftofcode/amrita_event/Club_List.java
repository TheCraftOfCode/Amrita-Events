package com.craftofcode.amrita_event;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class Club_List extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    private RecyclerView recyclerView;
    private ArrayList <Club_Details> ClubDetailsArrayList = new ArrayList<>();
    private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_list);
        recyclerView= findViewById(R.id.club_recycler);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener((BottomNavigationView.OnNavigationItemSelectedListener) this);
        bottomNavigationView.setSelectedItemId(R.id.clubs);

        Club_Adapter clubDetails = new Club_Adapter(this, ClubDetailsArrayList);

        ClubDetailsArrayList.add(new Club_Details("ASCII", "CLUB DESCRIPTION", "@ASCII","https://www.tutorialspoint.com/images/tp-logo-diamond.png"));
        ClubDetailsArrayList.add(new Club_Details("ASCII", "CLUB DESCRIPTION", "@ASCII","https://www.tutorialspoint.com/images/tp-logo-diamond.png"));
        ClubDetailsArrayList.add(new Club_Details("ASCII", "CLUB DESCRIPTION", "@ASCII","https://www.tutorialspoint.com/images/tp-logo-diamond.png"));
        ClubDetailsArrayList.add(new Club_Details("ASCII", "CLUB DESCRIPTION", "@ASCII","https://www.tutorialspoint.com/images/tp-logo-diamond.png"));
        ClubDetailsArrayList.add(new Club_Details("ASCII", "CLUB DESCRIPTION", "@ASCII","https://www.tutorialspoint.com/images/tp-logo-diamond.png"));


        recyclerView.setAdapter(clubDetails);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));



    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.clubs:

                return true;

            case R.id.events:

                Intent intent = new Intent(getApplicationContext(),CardView_Home.class);
                startActivity(intent);
                return true;


        }
        return false;
    }

}