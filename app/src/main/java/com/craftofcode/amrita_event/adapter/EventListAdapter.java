package com.craftofcode.amrita_event.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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

import org.json.JSONException;
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
    private LayoutInflater MainactivityInflater;


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
                    deleteAlert.setMessage("Are you sure you want to delete this ?");
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
                    Context updateButton = UpdateButton.getContext();
                    final View builderView = MainactivityInflater.inflate(R.layout.update_events, null);
                    final AlertDialog.Builder builder = new AlertDialog.Builder(UpdateButton.getContext());
                    builder.setView(builderView);

                    final AlertDialog alertDialog = builder.create();
                    RelativeLayout ParentView = builderView.findViewById(R.id.parentView);
                    EditText ImageUrl = builderView.findViewById(R.id.ImageUrl);
                    EditText EventTitle = builderView.findViewById(R.id.Title);
                    EditText Caption = builderView.findViewById(R.id.Caption);
                    EditText Description = builderView.findViewById(R.id.Description);
                    EditText OrganisingClub = builderView.findViewById(R.id.OrganisingClub);
                    EditText Date = builderView.findViewById(R.id.Date);
                    EditText Venue = builderView.findViewById(R.id.Venue);
                    EditText RegistrationLink = builderView.findViewById(R.id.RegistrationLink);
                    EditText Note = builderView.findViewById(R.id.Note);
                    EditText ContactName1 = builderView.findViewById(R.id.ContactDetailName1);
                    EditText ContactPhone1 = builderView.findViewById(R.id.ContactPhone1);
                    EditText ContactName2 = builderView.findViewById(R.id.ContactDetailName2);
                    EditText ContactPhone2 = builderView.findViewById(R.id.ContactPhone2);

                    ImageButton closeDialog = builderView.findViewById(R.id.cancel);
                    Button UpdateEventButton = builderView.findViewById(R.id.UpdateEventButton);

                    alertDialog.show();
                    //Keyboard patch
                    ParentView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ImageUrl.clearFocus();EventTitle.clearFocus();Caption.clearFocus(); Description.clearFocus(); OrganisingClub.clearFocus(); Date.clearFocus(); Venue.clearFocus(); RegistrationLink.clearFocus();
                            Note.clearFocus(); ContactName1.clearFocus(); ContactName2.clearFocus(); ContactPhone1.clearFocus(); ContactPhone2.clearFocus();

                            InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                            inputMethodManager.hideSoftInputFromWindow(builderView.getWindowToken(), 0);

                        }
                    });
                    closeDialog.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.dismiss();
                        }
                    });

                    UpdateEventButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Map<String, String> updatedBody = new HashMap<String, String>();
                            JSONObject UpdateRequestBody = new JSONObject();

                            if(ImageUrl.getText().toString().length() != 0){
                                updatedBody.put("ImageUrl", ImageUrl.getText().toString());
                            }
                            if(EventTitle.getText().toString().length() != 0){
                                updatedBody.put("EventTitle", EventTitle.getText().toString());
                            }
                            if(Caption.getText().toString().length() != 0){
                                updatedBody.put("Caption", Caption.getText().toString());
                            }
                            if(Description.getText().toString().length() != 0){
                                updatedBody.put("Description", Description.getText().toString());
                            }
                            if(OrganisingClub.getText().toString().length() != 0){
                                updatedBody.put("OrganisingClub", OrganisingClub.getText().toString());
                            }
                            if(Date.getText().toString().length() != 0){
                                updatedBody.put("Date", Date.getText().toString());
                            }
                            if(Venue.getText().toString().length() != 0){
                                updatedBody.put("Venue", Venue.getText().toString());
                            }
                            if(RegistrationLink.getText().toString().length() != 0){
                                updatedBody.put("RegistrationLink", RegistrationLink.getText().toString());
                            }
                            if(Note.getText().toString().length() != 0){
                                updatedBody.put("Note", Note.getText().toString());
                            }

//                    JSONObject contactDetailsModel = new JSONObject();
//                    JSONArray ContactDetails
//                    if(ContactName1.getText().toString().length() != 0){
//                        if(ContactPhone1.getText().toString().length() != 0){
//                            try {
//                                contactDetailsModel.put("Name", ContactName1.getText().toString());
//                                contactDetailsModel.put("Phone", ContactPhone1.getText().toString());
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//
//                        }
//                    }
//
//                    if(ContactName2.getText().toString().length() != 0){
//                        if(!ContactPhone2.getText().toString().equals("")){
//                            try {
//                                UpdateRequestBody.put(ContactName2.getText().toString(), ContactPhone2.getText().toString());
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//
//                        }
//                    }


                            for (Map.Entry<String, String> set : updatedBody.entrySet()){
                                try {
                                    UpdateRequestBody.put(set.getKey(), set.getValue());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            MakingTheUpdateRequest(UpdateRequestBody, getAdapterPosition());
                            alertDialog.dismiss();
                        }
                    });

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
                     SharedPreferences Token = context.getSharedPreferences("Token", Context.MODE_PRIVATE);
                     Map<String, String> params = new HashMap<String, String>();
                     params.put("user-auth-token",Token.getString("user-auth-token","Theif...!"));
                     return params;
                 }
             };

             requestQueue.add(DeleteEventRequest);
         }
     }

    private void MakingTheUpdateRequest(JSONObject updateRequestBody, int AdapterPosition) {

        Cache cache = new DiskBasedCache(context.getCacheDir(), 1024 * 1024); //1Mb cap
        Network network = new BasicNetwork(new HurlStack());
        RequestQueue requestQueue = new RequestQueue(cache, network);
        requestQueue.start();


        String UpdateUrl = "https://amrita-events.herokuapp.com/api/admin-users-portal/" + _id.get(AdapterPosition);

        //Delete request
        JsonObjectRequest UpdateEventRequest = new JsonObjectRequest(Request.Method.PUT, UpdateUrl, updateRequestBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //Deleting that event from the view
                System.out.println(response);
                Toast.makeText(context, "Updated Successfully", Toast.LENGTH_SHORT).show();
                notifyDataSetChanged();
                notifyItemChanged(AdapterPosition);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("wrong..!");
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                SharedPreferences Token = context.getSharedPreferences("Token", Context.MODE_PRIVATE);
                Map<String, String> params = new HashMap<String, String>();
                params.put("user-auth-token", Token.getString("user-auth-token", "Thief...!"));
                return params;
            }
        };

        requestQueue.add(UpdateEventRequest);
    }

    public EventListAdapter(Context context, LayoutInflater mainactivityinflater,LinkedList<String> _id, LinkedList<String> EventTitle, LinkedList<String> EventImage, LinkedList<String> OrgClub,LinkedList<String> EventDate){
        this._id = _id;
        this.EventTitleLinked = EventTitle;
        this.EventImage = EventImage;
        this.OrgClubLinked = OrgClub;
        this.EventDate = EventDate;
        this.context = context;
        this.MainactivityInflater = mainactivityinflater;
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

    public boolean handleKeyEvent(View view, int KeyCode){
        if(KeyCode == KeyEvent.KEYCODE_ENTER){
            InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
            return true;
        }
        return false;
    }
}