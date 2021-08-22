package com.craftofcode.amrita_event;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class EventView_Home extends AppCompatActivity {

    private Context context;
    private ArrayList<Event_Details> eventDetailsArrayList;


    public EventView_Home(Context context, ArrayList<Event_Details> eventDetailsArrayList) {
        this.context = context;
        this.eventDetailsArrayList = eventDetailsArrayList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_view_home);

        Bundle importFromCards = getIntent().getExtras();
        int position = importFromCards.getInt("position");

        TextView EventTitle, EventDescription,EventDate,EventTime,EventPhone;




    }
}