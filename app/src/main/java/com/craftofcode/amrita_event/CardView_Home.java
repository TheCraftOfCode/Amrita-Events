package com.craftofcode.amrita_event;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.craftofcode.amrita_event.apiModel.MySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CardView_Home extends AppCompatActivity {

    private RecyclerView recyclerView;

    private ArrayList<Event_Details> EventDetailsArrayList = new ArrayList<Event_Details>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_view_home);
        recyclerView = findViewById(R.id.cards_recycler);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        EventsAdapter eventDetails = new EventsAdapter(this,EventDetailsArrayList);
        //Opening the Get request


        // Api call is being made Here
        String AdminUsersEventsEndpoint = "https://amrita-events.herokuapp.com/api/admin-users-portal";

        JsonArrayRequest EventCardRequest = new JsonArrayRequest
                (Request.Method.GET, AdminUsersEventsEndpoint, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {

                        //Notifying that the dataset is changes when there is a new
                        eventDetails.notifyDataSetChanged();
                        //System.out.println(response);  //debugging purposes
                        for(int i = 0; i < response.length(); i++){
                            try {

                                //System.out.println(response.getJSONObject(i));   Debugging

                                JSONObject event = response.getJSONObject(i);
                                System.out.println("response" + event);
                                //EventDetailsArrayList.add(new Event_Details(event.get("Title").toString(),event.get("Description").toString(),event.get("Date").toString(),"05:30 PM - 06:30 PM",1 , event.get("OrganizingClub").toString(),false,1111111111);


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        System.out.println("This is in error..!");
                        System.out.println(error);
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                SharedPreferences TOKEN = getSharedPreferences("TOKEN", Context.MODE_PRIVATE);
                params.put("user-auth-token", TOKEN.getString("user-auth-token","Thief..!"));
                return params;
            }
        };

        //adding the request to Queue
        MySingleton.getInstance(this).addToRequestQueue(EventCardRequest);



        //dummy data while we wait for API to get ready
        EventDetailsArrayList.add(new Event_Details("Event one","This is an awesome event","12-08-2021","05:30 PM - 06:30 PM",1,"ASCII",false,1111111111));
        EventDetailsArrayList.add(new Event_Details("Event two","This is an awesome event","12-08-2021","05:30 PM - 06:30 PM",2,"GDSC",true,1111111111));
        EventDetailsArrayList.add(new Event_Details("Event three","This is an awesome event","12-08-2021","05:30 PM - 06:30 PM",3,"NSS",false,1111111111));
        EventDetailsArrayList.add(new Event_Details("Event four","This is an awesome event","12-08-2021","05:30 PM - 06:30 PM",4,"Srishti",false,1111111111));
        EventDetailsArrayList.add(new Event_Details("Event five","This is an awesome event","12-08-2021","05:30 PM - 06:30 PM",5,"Anantham",false,1111111111));


        for(int i = 0; i<EventDetailsArrayList.size();i++){
            Log.d("Printing", String.valueOf(EventDetailsArrayList.get(i)));
        }
        recyclerView.setAdapter(eventDetails);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));

    }

}