package com.craftofcode.amrita_event;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class EventView_Home extends AppCompatActivity {

    private Context context;
    private ArrayList<Event_Details> eventDetailsArrayList;


    public EventView_Home(Context context, ArrayList<Event_Details> eventDetailsArrayListExternal) {
        this.context = context;
        this.eventDetailsArrayList = eventDetailsArrayListExternal;
    }

    public EventView_Home(){}


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_view_home);

        Bundle importFromCards = getIntent().getExtras();
        String name = importFromCards.getString("name");
        String club = importFromCards.getString("club");
        String description = importFromCards.getString("description");
        String date = importFromCards.getString("date");
        String time = importFromCards.getString("time");
        String phone = importFromCards.getString("phone");


        TextView eventTitle, eventDescription, eventDate, eventTime, eventPhone, eventClub;

        eventTitle = findViewById(R.id.event_heading);
        eventDescription = findViewById(R.id.event_description);
        eventDate = findViewById(R.id.event_date);
        eventTime = findViewById(R.id.event_time);
        eventPhone = findViewById(R.id.event_phone);
        eventClub = findViewById(R.id.event_club);

        Log.d("message", "Intent called");

        eventTitle.setText(name);
        eventDescription.setText(description);
        eventClub.setText(club);
        eventDate.setText(date);
        eventTime.setText(time);
        eventPhone.setText(phone);

    }
}