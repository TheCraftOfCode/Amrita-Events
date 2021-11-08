package com.craftofcode.amrita_event;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
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

public class Filtered_club_Events extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EventsAdapter eventDetails;
    private ArrayList<Event_Details> EventDetailsArrayList = new ArrayList<Event_Details>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtered_club_events);

        Bundle b = getIntent().getExtras();
        String ClubName = b.getString("ClubName");

        recyclerView = findViewById(R.id.cards_recycler);
        eventDetails = new EventsAdapter(this,EventDetailsArrayList);
        recyclerView.setAdapter(eventDetails);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        GetFilteredEventsByClubName(ClubName);
    }

    private void GetFilteredEventsByClubName(String ClubName) {

        // Api call is being made Here
        String AdminUsersEventsEndpoint = "https://amrita-events.herokuapp.com/api/club-events/fetchEvents/" + ClubName;

        JsonArrayRequest EventCardRequest = new JsonArrayRequest
                (Request.Method.GET, AdminUsersEventsEndpoint, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        eventDetails.notifyDataSetChanged();
                        if(response.length() == 0){
                            Toast.makeText(getApplicationContext(), "No Events at the moment..!", Toast.LENGTH_SHORT).show();
                        }
                        for(int i = 0; i < response.length(); i++){
                            try {
                                //System.out.println(response.getJSONObject(i));   Debugging
                                JSONObject Clubevent = response.getJSONObject(i);
                                EventDetailsArrayList.add(new Event_Details(Clubevent.get("Title").toString(),Clubevent.get("Description").toString(),Clubevent.get("Date").toString(),"05:30 PM - 06:30 PM",Clubevent.get("_id").toString() , Clubevent.get("OrganizingClub").toString(),Clubevent.get("ImageUrl").toString(),1111111111));
                                eventDetails.notifyItemInserted(i);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Toast.makeText(getApplicationContext(), "Sorry...! Something Went Wrong..!", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                SharedPreferences Token = getSharedPreferences("Token", Context.MODE_PRIVATE);
                params.put("user-auth-token", Token.getString("user-auth-token","Theif..!"));
                return params;
            }
        };

        //adding the request to Queue
        MySingleton.getInstance(this).addToRequestQueue(EventCardRequest);

    }
}