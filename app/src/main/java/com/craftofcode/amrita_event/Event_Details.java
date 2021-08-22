package com.craftofcode.amrita_event;

public class Event_Details {

    private String event_name;
    private String event_description;
    private int event_id;
    private String event_date;
    private String event_time;
    private String event_club;
    private boolean event_status;
    private long event_phone;

    public Event_Details(String name, String description, String date, String time, int id,String club, boolean status, long phone){
        event_id = id;
        event_date = date;
        event_description = description;
        event_name = name;
        event_club = club;
        event_time = time;
        event_status = status;
        event_phone = phone;
    }

    public String getEvent_name(){
        return event_name;
    }

    public  String getEvent_description(){
        return event_description;
    }

    public String getEvent_date(){
        return event_date;
    }

    public String getEvent_time(){
        return event_time;
    }

    public int getEvent_id(){
        return event_id;
    }

    public String getEvent_club(){
        return event_club;
    }

    public boolean getEvent_status() {
        return event_status;
    }

    public long getEvent_phone(){
        return event_phone;
    }
}
