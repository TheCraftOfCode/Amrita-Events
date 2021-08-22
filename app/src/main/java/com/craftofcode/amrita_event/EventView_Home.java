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

        TextView eventTitle, eventDescription, eventDate, eventTime, eventPhone, eventClub;

        eventTitle = findViewById(R.id.event_heading);
        eventDescription = findViewById(R.id.event_description);
        eventDate = findViewById(R.id.event_date);
        eventTime = findViewById(R.id.event_time);
        eventPhone = findViewById(R.id.event_phone);
        eventClub = findViewById(R.id.event_club);

        Event_Details event = eventDetailsArrayList.get(position);

        eventTitle.setText(event.getEvent_name());
        eventDescription.setText(event.getEvent_description());
        eventClub.setText(event.getEvent_club());
        eventDate.setText(event.getEvent_date());
        eventTime.setText(event.getEvent_time());
        eventPhone.setText(Long.toString(event.getEvent_phone()));

    }
}