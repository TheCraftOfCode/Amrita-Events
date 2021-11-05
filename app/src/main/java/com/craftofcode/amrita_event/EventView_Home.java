package com.craftofcode.amrita_event;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class EventView_Home extends AppCompatActivity {

    public EventView_Home(){}

    String name,description;
    Date dateDate;
    SimpleDateFormat formatter=new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
    String datetimeString = "31-Nov-2021 23:37:50";

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

        if(id==R.id.set_reminder){
            addReminderInCalendar();
        }

        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_view_home);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        Bundle importFromCards = getIntent().getExtras();
        String name = importFromCards.getString("name");
        String club = importFromCards.getString("club");
        description = importFromCards.getString("description");
        String date = importFromCards.getString("date");
        String time = importFromCards.getString("time");
        String phone = importFromCards.getString("phone");
        int eventID = importFromCards.getInt("id");
        boolean eventStatus = Boolean.parseBoolean(importFromCards.getString("status"));
        datetimeString =date+" "+time;
        try {
            dateDate = formatter.parse(datetimeString);
        } catch (ParseException e) {
            e.printStackTrace();
        }


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

    /** Adds Events and Reminders in Calendar. */
    private void addReminderInCalendar() {
        Calendar cal = Calendar.getInstance();
        Uri EVENTS_URI = Uri.parse(getCalendarUriBase(true) + "events");
        ContentResolver cr = getContentResolver();
        TimeZone timeZone = TimeZone.getDefault();

        /** Inserting an event in calendar. */
        ContentValues values = new ContentValues();
        values.put(CalendarContract.Events.CALENDAR_ID, 1);
        values.put(CalendarContract.Events.TITLE,name);
        values.put(CalendarContract.Events.DESCRIPTION, description);
        values.put(CalendarContract.Events.ALL_DAY, 0);
        //start time
        values.put(CalendarContract.Events.DTSTART, String.valueOf(dateDate));
        // end time
        values.put(CalendarContract.Events.DTEND, String.valueOf(dateDate) + 60 * 60 * 1000);
        values.put(CalendarContract.Events.EVENT_TIMEZONE, timeZone.getID());
        values.put(CalendarContract.Events.HAS_ALARM, 1);
        Uri event = cr.insert(EVENTS_URI, values);

        // Display event id
        Toast.makeText(getApplicationContext(), "Event added :: ID :: " + event.getLastPathSegment(), Toast.LENGTH_SHORT).show();

        /** Adding reminder for event added. */
        Uri REMINDERS_URI = Uri.parse(getCalendarUriBase(true) + "reminders");
        values = new ContentValues();
        values.put(CalendarContract.Reminders.EVENT_ID, Long.parseLong(event.getLastPathSegment()));
        values.put(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALERT);
        values.put(CalendarContract.Reminders.MINUTES, 10);
        ((ContentResolver) cr).insert(REMINDERS_URI, values);
    }


    private String getCalendarUriBase(boolean eventUri) {
        Uri calendarURI = null;
        try {
            calendarURI = (eventUri) ? Uri.parse("content://com.android.calendar/") : Uri
                    .parse("content://com.android.calendar/calendars");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return calendarURI.toString();
    }
}

//Event Name
//Tagline
//Cub name
//Description
//Time
//Contact
