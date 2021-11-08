package com.craftofcode.amrita_event;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Club_List extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList <Club_Details> ClubDetailsArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_list);
        recyclerView= findViewById(R.id.club_recycler);

        Club_Adapter clubDetails = new Club_Adapter(this, ClubDetailsArrayList);

        ClubDetailsArrayList.add(new Club_Details("ASCII", "CLUB DESCRIPTION", "@ASCII","https://www.tutorialspoint.com/images/tp-logo-diamond.png"));
        ClubDetailsArrayList.add(new Club_Details("ASCII", "CLUB DESCRIPTION", "@ASCII","https://www.tutorialspoint.com/images/tp-logo-diamond.png"));
        ClubDetailsArrayList.add(new Club_Details("ASCII", "CLUB DESCRIPTION", "@ASCII","https://www.tutorialspoint.com/images/tp-logo-diamond.png"));
        ClubDetailsArrayList.add(new Club_Details("ASCII", "CLUB DESCRIPTION", "@ASCII","https://www.tutorialspoint.com/images/tp-logo-diamond.png"));
        ClubDetailsArrayList.add(new Club_Details("ASCII", "CLUB DESCRIPTION", "@ASCII","https://www.tutorialspoint.com/images/tp-logo-diamond.png"));


        recyclerView.setAdapter(clubDetails);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));



    }
}