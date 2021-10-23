package com.craftofcode.amrita_event.adapter;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.craftofcode.amrita_event.R;

import java.util.LinkedList;

public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.ItemViewHolder> {
    private final LinkedList<String> EventTitle;
    private final LinkedList<Integer> EventImage;
    private final LinkedList<String> OrgClub;
    private final LinkedList<String> EventDate;
    private LayoutInflater ItemLayoutInflater;
    public Context context;


     class ItemViewHolder extends RecyclerView.ViewHolder {
        public ImageView ImageEvent;
        public TextView EventTitle;
        public TextView OrgClub;
        public TextView Date;

        //support for buttons
         public Button DeleteButton;
         public Button UpdateButton;
        final EventListAdapter EventsAdapter;

        public ItemViewHolder(View EventView, EventListAdapter EventsAdapter){
            super(EventView);
            ImageEvent = EventView.findViewById(R.id.image);
            EventTitle = EventView.findViewById(R.id.title);
            OrgClub = EventView.findViewById(R.id.club);
            Date = EventView.findViewById(R.id.date);
            DeleteButton = EventView.findViewById(R.id.delbutton);
            UpdateButton = EventView.findViewById(R.id.updbutton);
            this.EventsAdapter = EventsAdapter;
            //EventView.setOnClickListener(this);

            DeleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Make a Delete Request
                }
            });
            UpdateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("mess" , "Hello click");
                    Log.d("mess", String.valueOf(getAdapterPosition()));
                }
            });
        }

//        @Override
//        public void onClick(View v) {
//            Intent intent = new Intent(context, Expanded_Card_Admins.class);
//            //ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context, v, "ContainerTransform");
//            context.startActivity(intent);
//        }
    }

    public EventListAdapter(Context context, LinkedList<String> EventTitle, LinkedList<Integer> EventImage, LinkedList<String> OrgClub,LinkedList<String> EventDate){
        ItemLayoutInflater = LayoutInflater.from(context);
        this.EventTitle = EventTitle;
        this.EventImage = EventImage;
        this.OrgClub = OrgClub;
        this.EventDate = EventDate;
        this.context = context;
    }
    public EventListAdapter.ItemViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View ItemView = ItemLayoutInflater.inflate(R.layout.admin_card_view,parent,false);
        return new ItemViewHolder(ItemView, this);
    }

    @Override
    public void onBindViewHolder(EventListAdapter.ItemViewHolder holder, int position) {
        String CurrentEventName = EventTitle.get(position);
        int CurrentEventImage = EventImage.get(position);
        String CurrentOrgClub = OrgClub.get(position);
        String CurrentEventDate = EventDate.get(position);
        holder.EventTitle.setText(CurrentEventName);
        holder.ImageEvent.setImageResource(CurrentEventImage);
        holder.OrgClub.setText(CurrentOrgClub);
        holder.Date.setText(CurrentEventDate);

        //onclick listener here
    }

    @Override
    public int getItemCount() {
        return EventTitle.size();
    }
}