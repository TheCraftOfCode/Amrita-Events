package com.craftofcode.amrita_event;

public class Event_Details {

    private String event_name;
    private String event_description;
    private String event_id;
    private String event_date;
    private String event_time;
    private String event_club;
    private long event_phone;
    private String image_url;
    private String contactNameOne;
    private String contactPhoneOne;
    private String ContactName2;
    private String ContactPhone2;

    public Event_Details(String name, String description, String date, String time, String id,String club, String url, long phone){
        event_id = id;
        event_date = date;
        event_description = description;
        event_name = name;
        event_club = club;
        event_time = time;
        image_url = url;
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

    public String getEvent_id(){
        return event_id;
    }

    public String getEvent_club(){
        return event_club;
    }

    public String getEvent_image() {
        return image_url;
    }

    public long getEvent_phone(){
        return event_phone;
    }
}
