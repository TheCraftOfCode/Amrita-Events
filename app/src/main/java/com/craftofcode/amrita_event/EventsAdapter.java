package com.craftofcode.amrita_event;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.Viewholder> {

    private Context context;
    private ArrayList<Event_Details> eventDetailsArrayList;


    public EventsAdapter(Context context, ArrayList<Event_Details> eventDetailsArrayList) {
        this.context = context;
        this.eventDetailsArrayList = eventDetailsArrayList;
    }

    @NonNull
    @Override
    public EventsAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // to inflate the layout for each item of recycler view.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cards_list, parent, false);
        return new Viewholder(view,this);
    }

    @Override
    public void onBindViewHolder(@NonNull EventsAdapter.Viewholder holder, int position) {
        // to set data to textview and imageview of each card layout
        Event_Details event = eventDetailsArrayList.get(position);
        holder.eventName.setText(event.getEvent_name());
        holder.eventClub.setText(event.getEvent_club());
        holder.eventDate.setText(event.getEvent_date());
        holder.eventTime.setText(event.getEvent_time());
    }

    @Override
    public int getItemCount() {
        // this method is used for showing number of card items in recycler view.
        return eventDetailsArrayList.size();
    }

    // View holder class for initializing of your views such as TextView and Imageview.
    public class Viewholder extends RecyclerView.ViewHolder {
        public View v;
        private TextView eventName, eventClub, eventDate, eventTime;
        final EventsAdapter adapter;
        private Button view_more;


        public Viewholder(View itemView,EventsAdapter adapter) {
            super(itemView);
            eventName = itemView.findViewById(R.id.card_title);
            eventClub = itemView.findViewById(R.id.card_club);
            eventDate = itemView.findViewById(R.id.card_date);
            eventTime = itemView.findViewById(R.id.card_time);
            view_more = itemView.findViewById(R.id.moreButton);
            this.adapter = adapter;
            this.v = itemView;

            view_more.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    Log.d("onClick: ","Item has been clicked");
                    int position = getAdapterPosition();
                    Intent intent = new Intent(context,EventView_Home.class);
                    intent.putExtra("position", position);
                    context.startActivity(intent);
                }
            });
        }
    }
}
