package com.craftofcode.amrita_event;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Club_list extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList <Club_Details> ClubDetailsArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_list);
        recyclerView= findViewById(R.id.club_recycler);

        Club_Adapter clubDetails = new Club_Adapter(this, ClubDetailsArrayList);

        //ClubDetailsArrayList.add(new Club_Details());



    }
}