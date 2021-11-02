package com.craftofcode.amrita_event;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class Club_list_view extends AppCompatActivity {
    //Button ascii, shrishti,ted, se;
    private RecyclerView rv;
    private ArrayList<club_view> ClubDetailsArrayList = new ArrayList<club_view>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_list_view);
    rv= findViewById(R.id.rv);

    ClubDetailsArrayList.add(new club_view("ASSOCIATION OF STUDENTS OF COMPUTER SCIENCE FOR INFORMATION INTERCHANGE (ASCII)",
            "The Association of Students of Computer Science for Information Interchange (ASCII) is the technical association of the Department of Computer Science and Engineering, Amrita Vishwa Vidyapeetham. It was established in the year 1997. The association hosts various technical and social events planned and executed by students.",
            getResources().getString(R.string.ASCII),R.drawable.cse_club));

        ClubDetailsArrayList.add(new club_view("SRISHTI",
                "Srishti inspired the students to break stereotypes and nurture their talents. It allowed them to escape from the regular college humdrum and motivated them to be explosively creative.",
                getResources().getString(R.string.srishti),R.drawable.srishti));


        ClubDetailsArrayList.add(new club_view("TEDxAmritaViswaVidhyapeetham",
                 "In the spirit of ideas worth spreading," +
                         "TED has created a program called TEDx. TEDx is a program local, " +
                         "self-organized events that bring people together TED-like experience. Our event is called TEDxAmritaVishwaVidyapeetham, where x = independently organized TED event. At our TEDxAmritaVishwaVidyapeetham event, TED Talks video and live speakers will combine to spark deep discussion and connection in a small group. The TED Conference provides general guidance for the TEDx program, but individual TEDx events, including ours, are self-organized.",
                 getResources().getString(R.string.TED),R.drawable.ted));

        ClubDetailsArrayList.add(new club_view("Student Energy ",
                "The club supports a plethora of opportunities for young people to " +
                        "develop their skills and take the required action.Our events range from skill-building programmes " +
                        "to conference delegations to new digital learning platforms.",
                getResources().getString(R.string.studentenergy),R.drawable.se));




ClubAdapter clubAdapter = new ClubAdapter(this, ClubDetailsArrayList);

for (int i =0;i < ClubDetailsArrayList.size();i++){
    Log.d("Printing", String.valueOf(ClubDetailsArrayList.get(i)));


}
rv.setAdapter(clubAdapter);
rv.setLayoutManager(new LinearLayoutManager(this));



    }


}
