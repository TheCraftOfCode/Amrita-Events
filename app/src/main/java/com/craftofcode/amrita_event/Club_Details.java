package com.craftofcode.amrita_event;

public class Club_Details {

private String club_name;
private String club_description;
private String club_insta;

    public Club_Details(String club_name, String club_description, String club_insta) {
        this.club_name = club_name;
        this.club_description = club_description;
        this.club_insta = club_insta;
    }

    public String getClub_name() {
        return club_name;
    }

    public String getClub_description() {
        return club_description;
    }

    public String getClub_insta() {
        return club_insta;
    }
}
