package com.craftofcode.amrita_event.adapter;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.craftofcode.amrita_event.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.ItemViewHolder> {
    private LinkedList<String> EventTitleLinked;
    private LinkedList<String> EventImage;
    private LinkedList<String> OrgClubLinked;
    private LinkedList<String> EventDate;

    //Linked list of id's of all the events
    private LinkedList<String> _id;
    private LayoutInflater ItemLayoutInflater;
    public Context context;


    class ItemViewHolder extends RecyclerView.ViewHolder{
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
//            EventView.setOnClickListener(this);

            DeleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //getting a delete request by getting the id of the event
                    //extracting the id of the event
                    System.out.println("Position : " + getAdapterPosition()); //for debugging purposes

                    //poping an alert when clicked on delete button
                    AlertDialog.Builder deleteAlert = new AlertDialog.Builder(DeleteButton.getContext());
                    deleteAlert.setTitle("Are you sure..!");
                    deleteAlert.setTitle("Are you sure you want to delete this ?");
                    //deleteAlert.setIcon(R.drawable.ic_baseline_delete_24);

                    deleteAlert.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //if user clicks yes then Making a delete request
                            DeleteRequestToDeleteEvent(getAdapterPosition());
                        }
                    });
                    deleteAlert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            return;
                        }
                    });

                    deleteAlert.show();
                    //System.out.println("Done..!");
                }
            });
            UpdateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("mess", String.valueOf(getAdapterPosition()));
                }
            });
        }

        // function to delete an event
         private void DeleteRequestToDeleteEvent(int adapterPositon) {

             Cache cache = new DiskBasedCache(context.getCacheDir(), 1024 * 1024); //1Mb cap
             Network network = new BasicNetwork(new HurlStack());
             RequestQueue requestQueue = new RequestQueue(cache, network);
             requestQueue.start();

             //extracting the id of the event that is to be deleted
//             System.out.println(_id.get(adapterPositon));
             int DeletedEventIndex = getAdapterPosition();
             String DeleteUrl = "https://amrita-events.herokuapp.com/api/admin-users-portal/" + _id.get(getAdapterPosition());
//             System.out.println(DeleteUrl);  // for debugging purposes

             System.out.println("Hello");
             //Delete request
             JsonObjectRequest DeleteEventRequest = new JsonObjectRequest(Request.Method.DELETE, DeleteUrl, null, new Response.Listener<JSONObject>() {
                 @Override
                 public void onResponse(JSONObject response) {
                     //Deleting that event from the view
                     _id.remove(DeletedEventIndex);
                     EventTitleLinked.remove(DeletedEventIndex);
                     EventImage.remove(DeletedEventIndex);
                     OrgClubLinked.remove(DeletedEventIndex);
                     EventDate.remove(DeletedEventIndex);

                     //notifying the changes to the adapter
                     notifyItemRemoved(DeletedEventIndex);
                     notifyItemRangeChanged(DeletedEventIndex, _id.size());
                     notifyDataSetChanged();
                 }
             }, new Response.ErrorListener() {
                 @Override
                 public void onErrorResponse(VolleyError error) {
                     System.out.println("wrong..!");
                 }
             }) {
                 @Override
                 public Map<String, String> getHeaders() throws AuthFailureError {
                     SharedPreferences Token = context.getSharedPreferences("TOKEN", Context.MODE_PRIVATE);
                     Map<String, String> params = new HashMap<String, String>();
                     params.put("user-auth-token","");
                     return params;
                 }
             };

             requestQueue.add(DeleteEventRequest);
         }
     }

    public EventListAdapter(Context context,LinkedList<String> _id, LinkedList<String> EventTitle, LinkedList<String> EventImage, LinkedList<String> OrgClub,LinkedList<String> EventDate){
        this._id = _id;
        this.EventTitleLinked = EventTitle;
        this.EventImage = EventImage;
        this.OrgClubLinked = OrgClub;
        this.EventDate = EventDate;
        this.context = context;
    }
    @NonNull
    public EventListAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemLayoutInflater = LayoutInflater.from(parent.getContext());
        View ItemView = ItemLayoutInflater.inflate(R.layout.admin_card_view, parent,false);
        return new ItemViewHolder(ItemView, this);
    }

    @Override
    public void onBindViewHolder(EventListAdapter.ItemViewHolder holder, int position) {
        String CurrentEventName = EventTitleLinked.get(position);
        String CurrentEventImageUrl = EventImage.get(position);

        //Using Glide to Download Image
        Glide.with(context).load(CurrentEventImageUrl).into(holder.ImageEvent);
        String CurrentOrgClub = OrgClubLinked.get(position);
        String CurrentEventDate = EventDate.get(position);
        holder.EventTitle.setText(CurrentEventName);
        holder.OrgClub.setText(CurrentOrgClub);
        holder.Date.setText(CurrentEventDate);
        //onclick listener here
    }

    @Override
    public int getItemCount() {
        return EventTitleLinked.size();
    }
}