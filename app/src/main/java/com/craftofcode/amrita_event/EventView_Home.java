package com.craftofcode.amrita_event;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class EventView_Home extends AppCompatActivity {

    public EventView_Home(){}

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        String shareString = "This is a test string";

        int id = item.getItemId();
        if(id==R.id.share_button){
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("application/vnd.android.package-archive");
            intent.putExtra(Intent.EXTRA_TEXT,shareString);
            startActivity(intent.createChooser(intent,"Share via"));
        }



        return true;
    }

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
        String eventID = importFromCards.getString("id");
        boolean eventStatus = Boolean.parseBoolean(importFromCards.getString("status"));


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

//Event Name
//Tagline
//Cub name
//Description
//Time
//Contact
