package com.craftofcode.amrita_event;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.craftofcode.amrita_event.adapter.EventListAdapter;

import java.util.LinkedList;

public class Card_list_View_Admins extends AppCompatActivity {
    public EventListAdapter adapter;
    public String[] Title = {"Peppy paneer Pizza", "Paneer Makhni Pizza", "Cheese Burst Pizza", "Corn Pizza","papperoni pizza","farm house pizza", "vegie deilight pizza","chicken pizza", "tandoori pizza","custom pizza"};
    public String[] Club = {"Peppy paneer Pizza", "Paneer Makhni Pizza", "Cheese Burst Pizza", "Corn Pizza","papperoni pizza","farm house pizza", "vegie deilight pizza","chicken pizza", "tandoori pizza","custom pizza"};
    public String[] Date = {"250","260","240.5","350","312","250","260","240.5","350","312"};
    int[] EventImages = {
            R.drawable.p1,
            R.drawable.p2,
            R.drawable.p3,
            R.drawable.p4,
            R.drawable.p5,
            R.drawable.p6,
            R.drawable.p7,
            R.drawable.p8,
            R.drawable.p9,
            R.drawable.p10
    };
    private LinkedList<String> EventTitle = new LinkedList<>();
    private LinkedList<Integer> EventImage = new LinkedList<>();
    private LinkedList<String> Clubname = new LinkedList<>();
    private LinkedList<String> DateEvent = new LinkedList<>();


    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_list_view_admins);
        recyclerView = findViewById(R.id.recyclerView);

        for(int i = 0; i< Title.length; i++){
            EventTitle.addLast(Title[i]);
            EventImage.addLast(EventImages[i]);
            Clubname.addLast(Club[i]);
            DateEvent.addLast(Date[i]);
        }
        adapter = new EventListAdapter(this, EventTitle, EventImage, Clubname, DateEvent);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


}