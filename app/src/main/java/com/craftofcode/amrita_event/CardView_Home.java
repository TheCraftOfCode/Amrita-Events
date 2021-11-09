package com.craftofcode.amrita_event;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CardView_Home extends AppCompatActivity {

    private RecyclerView recyclerView;
    private BottomNavigationView bottomNavigationView;

    private ArrayList<Event_Details> EventDetailsArrayList = new ArrayList<Event_Details>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_view_home);
        recyclerView = findViewById(R.id.cards_recycler);
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
                                String MongoDate = event.get("Date").toString();
                                JSONArray contactDetails = event.getJSONArray("ContactDetails");
                                System.out.println(contactDetails);

                                for(int j=0;j<contactDetails.length();j++){
                                    JSONObject contactDetail = contactDetails.getJSONObject(j);
                                    String nameOne = contactDetail.get("Name").toString();
                                    String contactOne = contactDetail.get("Phone").toString();

                                }

                                EventDetailsArrayList.add(new Event_Details(event.get("Title").toString(),event.get("Description").toString(),ConvertMongoDateFormat(MongoDate),"05:30 PM - 06:30 PM",event.get("_id").toString() , event.get("OrganizingClub").toString(),event.get("ImageUrl").toString(),1111111111));


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
                SharedPreferences TOKEN = getSharedPreferences("Token", Context.MODE_PRIVATE);
                params.put("user-auth-token", TOKEN.getString("user-auth-token","Theif..!"));
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

    private String ConvertMongoDateFormat(String MongoDate){
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        SimpleDateFormat OutputDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        String FinalDate = "";
        try {
            FinalDate = OutputDateFormat.format(inputFormat.parse(MongoDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(FinalDate);
        return FinalDate;
    }

}