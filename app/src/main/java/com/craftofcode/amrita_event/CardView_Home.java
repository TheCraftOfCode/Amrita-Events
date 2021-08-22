package com.craftofcode.amrita_event;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CardView_Home extends AppCompatActivity {

    private RecyclerView recyclerView;

    private ArrayList<Event_Details> EventDetailsArrayList = new ArrayList<Event_Details>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_view_home);
        recyclerView = findViewById(R.id.cards_recycler);

        //dummy data while we wait for API to get ready
        EventDetailsArrayList.add(new Event_Details("Event one","This is an awesome event","12-08-2021","05:30 PM - 06:30 PM",1,"ASCII",false));
        EventDetailsArrayList.add(new Event_Details("Event two","This is an awesome event","12-08-2021","05:30 PM - 06:30 PM",2,"GDSC",true));
        EventDetailsArrayList.add(new Event_Details("Event three","This is an awesome event","12-08-2021","05:30 PM - 06:30 PM",3,"NSS",false));
        EventDetailsArrayList.add(new Event_Details("Event four","This is an awesome event","12-08-2021","05:30 PM - 06:30 PM",4,"Srishti",false));
        EventDetailsArrayList.add(new Event_Details("Event five","This is an awesome event","12-08-2021","05:30 PM - 06:30 PM",5,"Anantham",false));

        EventsAdapter eventDetails = new EventsAdapter(this,EventDetailsArrayList);

        for(int i = 0; i<EventDetailsArrayList.size();i++){
            Log.d("Printing", String.valueOf(EventDetailsArrayList.get(i)));
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(eventDetails);

    }
}