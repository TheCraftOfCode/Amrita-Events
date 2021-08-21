package com.craftofcode.amrita_event;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CardView_Home extends AppCompatActivity {

    private RecyclerView recyclerView;

    private ArrayList<Event_Details> eventDetailsArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_view_home);
        recyclerView = findViewById(R.id.cards_recycler);

        //dummy data while we wait for API to get ready
        eventDetailsArrayList.add(new Event_Details("Event one","This is an awesome event","12-08-2021","05:30 PM - 06:30 PM",1,"ASCII"));
        eventDetailsArrayList.add(new Event_Details("Event two","This is an awesome event","12-08-2021","05:30 PM - 06:30 PM",2,"GDSC"));
        eventDetailsArrayList.add(new Event_Details("Event three","This is an awesome event","12-08-2021","05:30 PM - 06:30 PM",3,"NSS"));
        eventDetailsArrayList.add(new Event_Details("Event four","This is an awesome event","12-08-2021","05:30 PM - 06:30 PM",4,"Srishti"));
        eventDetailsArrayList.add(new Event_Details("Event five","This is an awesome event","12-08-2021","05:30 PM - 06:30 PM",5,"Anantham"));

        EventsAdapter eventDetails = new EventsAdapter(this,eventDetailsArrayList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(eventDetails);

    }
}