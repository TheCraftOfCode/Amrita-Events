package com.craftofcode.amrita_event;

import android.widget.ImageView;

public class Club_Details {

    private String club_name;
    private String club_description;
    private String club_insta;
    private String club_img_url;
    private ImageView club_image;

    public Club_Details(String club_name, String club_description, String club_insta, String club_img_url) {
        this.club_name = club_name;
        this.club_description = club_description;
        this.club_insta = club_insta;
        this.club_img_url = club_img_url;
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

    public String getClub_img_url() {
        return club_img_url;
    }
}
