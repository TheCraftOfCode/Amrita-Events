package com.craftofcode.amrita_event;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
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
import com.android.volley.toolbox.JsonArrayRequest;
import com.craftofcode.amrita_event.adapter.EventListAdapter;
import com.craftofcode.amrita_event.apiModel.MySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Card_list_View_Admins extends AppCompatActivity {

    Cache cache;
    Network network;
    RequestQueue requestQueue;


//    public String[] Title = {"Peppy paneer Pizza", "Paneer Makhni Pizza", "Cheese Burst Pizza", "Corn Pizza","papperoni pizza","farm house pizza", "vegie deilight pizza","chicken pizza", "tandoori pizza","custom pizza"};
//    public String[] Club = {"Peppy paneer Pizza", "Paneer Makhni Pizza", "Cheese Burst Pizza", "Corn Pizza","papperoni pizza","farm house pizza", "vegie deilight pizza","chicken pizza", "tandoori pizza","custom pizza"};
//    public String[] Date = {"250","260","240.5","350","312","250","260","240.5","350","312"};
    int[] EventImages = {
            R.drawable.p1,
            R.drawable.p2,
    };

    public RecyclerView recyclerView;
    public EventListAdapter adapter;

    private LinkedList<String> _id;
    private LinkedList<Integer> EventImage;
    //Image URL variable for now
    private LinkedList<String> Url;
    private LinkedList<String> EventTitle;
    private LinkedList<String> Clubname;
    private LinkedList<String> DateEvent;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        _id = new LinkedList<>();
        EventImage = new LinkedList<>();
        Url = new LinkedList<>();
        EventTitle = new LinkedList<>();
        Clubname = new LinkedList<>();
        DateEvent = new LinkedList<>();


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_list_view_admins);

        recyclerView = findViewById(R.id.recyclerView);

        adapter = new EventListAdapter(getApplicationContext(), EventTitle, EventImage, Clubname, DateEvent);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        // Image Url String for now

        // setting up the Request Queue
        SettingUpRequestQueue();

        SharedPreferences TOKEN = getSharedPreferences("TOKEN", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = TOKEN.edit();

        //pushing token to shared preference
        edit.putString("user-auth-token", "");
        edit.commit();

        // Api call is being made Here
        String AdminUsersEventsEndpoint = "https://amrita-events.herokuapp.com/api/admin-users-portal";

        JsonArrayRequest EventCardRequest = new JsonArrayRequest
                (Request.Method.GET, AdminUsersEventsEndpoint, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {

                        _id.clear();
                        EventImage.clear();
                        Url.clear();
                        EventTitle.clear();
                        Clubname.clear();
                        DateEvent.clear();

                        adapter.notifyDataSetChanged();
                        //System.out.println(response);
                        for(int i = 0; i < response.length(); i++){
                            try {
                                EventImage.addLast(EventImages[i]);
                                System.out.println(response.getJSONObject(i));
                                JSONObject event = response.getJSONObject(i);
                                _id.addLast(event.get("_id").toString());
                                Url.addLast(event.get("ImageUrl").toString());
                                EventTitle.addLast(event.get("Title").toString());
                                Clubname.addLast(event.get("OrganizingClub").toString());
                                DateEvent.addLast(event.get("Date").toString());

                                adapter.notifyItemInserted(i);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error

                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                SharedPreferences TOKEN = getSharedPreferences("TOKEN", Context.MODE_PRIVATE);
                params.put("user-auth-token", TOKEN.getString("user-auth-token","Theif..!"));
                return params;
            }
        };

        //adding the request to Queue
        MySingleton.getInstance(this).addToRequestQueue(EventCardRequest);


    }

    private void SettingUpRequestQueue() {
        cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); //1Mb cap
        network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();
    }

}