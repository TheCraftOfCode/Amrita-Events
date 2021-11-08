package com.craftofcode.amrita_event;

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

public class Club_List extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList <Club_Details> ClubDetailsArrayList = new ArrayList<>();
    public Club_Adapter clubDetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_list);
        recyclerView= findViewById(R.id.club_recycler);

        clubDetails = new Club_Adapter(this, ClubDetailsArrayList);

        recyclerView.setAdapter(clubDetails);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));

        GetRequestClubCardView();

    }

    //Get request Function
    private void GetRequestClubCardView(){

        // Api call is being made Here
        String AdminUsersEventsEndpoint = "https://amrita-events.herokuapp.com/api/club-events";

        JsonArrayRequest EventCardRequest = new JsonArrayRequest
                (Request.Method.GET, AdminUsersEventsEndpoint, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {


                        //Notifying that the dataset is changes when there is a new
                        clubDetails.notifyDataSetChanged();
                        //System.out.println(response);  //debugging purposes
                        for(int i = 0; i < response.length(); i++){
                            try {

                                //System.out.println(response.getJSONObject(i));   Debugging
                                JSONObject club = response.getJSONObject(i);
                                ClubDetailsArrayList.add(new Club_Details(club.get("ClubName").toString(), club.get("ClubDescription").toString(), club.get("ClubName").toString(),club.get("ClubImageUrl").toString()));
                                clubDetails.notifyItemInserted(i);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Toast.makeText(getApplicationContext(), "Sorry..! Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                SharedPreferences Token = getSharedPreferences("Token",MODE_PRIVATE);
                params.put("user-auth-token", Token.getString("user-auth-token","Theif..!"));
                return params;
            }
        };

        //adding the request to Queue
        MySingleton.getInstance(this).addToRequestQueue(EventCardRequest);

    }

}
