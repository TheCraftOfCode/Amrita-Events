package com.craftofcode.amrita_event;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.craftofcode.amrita_event.apiModel.MySingleton;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CardView_Home extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private RecyclerView recyclerView;

    private ArrayList<Event_Details> EventDetailsArrayList = new ArrayList<Event_Details>();
private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_view_home);
        recyclerView = findViewById(R.id.cards_recycler);
        bottomNavigationView = findViewById(R.id.event_bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener((BottomNavigationView.OnNavigationItemSelectedListener) this);
        bottomNavigationView.setSelectedItemId(R.id.events);
        EventsAdapter eventDetails = new EventsAdapter(this,EventDetailsArrayList);
        //Opening the Get request


        // Api call is being made Here
        String EventsEndpoint = "https://amrita-events.herokuapp.com/api/all-events";

        JsonArrayRequest EventCardRequest = new JsonArrayRequest
                (Request.Method.GET, EventsEndpoint, null, new Response.Listener<JSONArray>() {

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
                                EventDetailsArrayList.add(new Event_Details(event.get("Title").toString(),event.get("Description").toString(),event.get("Date").toString(),"05:30 PM - 06:30 PM",event.get("_id").toString() , event.get("OrganizingClub").toString(),event.get("ImageUrl").toString(),1111111111));


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        System.out.println("There has been an error.");
                        System.out.println(error);
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                SharedPreferences TOKEN = getSharedPreferences("TOKEN", Context.MODE_PRIVATE);
                params.put("user-auth-token", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2MTg1NTM2MGI5MWI0YjAwMDQxNTcxZjAiLCJ1c2VybmFtZSI6IkNCLkVOLlU0Q1NFMTkwMzgiLCJpYXQiOjE2MzYxMjc2MTF9.yCho08MtlUfaDj5BTYcE1UJc6X4fWmT6nOd_G_7rPbk");
                return params;
            }
        };

        //adding the request to Queue
        MySingleton.getInstance(this).addToRequestQueue(EventCardRequest);


        for(int i = 0; i<EventDetailsArrayList.size();i++){
            Log.d("Printing", String.valueOf(EventDetailsArrayList.get(i)));
        }
        recyclerView.setAdapter(eventDetails);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.clubs:
                Intent intent = new Intent(getApplicationContext(),Club_List.class);
                startActivity(intent);
                return true;

            case R.id.events:


                return true;


        }
        return false;
    }

}