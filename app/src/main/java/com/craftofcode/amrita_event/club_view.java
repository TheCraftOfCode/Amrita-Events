package com.craftofcode.amrita_event;

public class club_view {
    private String Club_name;
    private String Club_Description;
    private String Club_insta_id;
    private int Club_image;

    public club_view(String club_name, String club_Description, String club_insta_id, int club_image) {
        Club_name = club_name;
        Club_Description = club_Description;
        Club_insta_id = club_insta_id;
        Club_image = club_image;
    }

    public club_view(){}

    public String getClub_name() {
        return Club_name;
    }

    public String getClub_Description() {
        return Club_Description;
    }

    public String getClub_insta_id() {
        return Club_insta_id;
    }

    public int getClub_image() {
        return Club_image;
    }
}