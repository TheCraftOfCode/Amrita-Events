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

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Card_list_View_Admins extends AppCompatActivity {

    Cache cache;
    Network network;
    RequestQueue requestQueue;

    public EventListAdapter adapter;
    public String[] Title = {"Peppy paneer Pizza", "Paneer Makhni Pizza", "Cheese Burst Pizza", "Corn Pizza","papperoni pizza","farm house pizza", "vegie deilight pizza","chicken pizza", "tandoori pizza","custom pizza"};
    public String[] Club = {"Peppy paneer Pizza", "Paneer Makhni Pizza", "Cheese Burst Pizza", "Corn Pizza","papperoni pizza","farm house pizza", "vegie deilight pizza","chicken pizza", "tandoori pizza","custom pizza"};
    public String[] Date = {"250","260","240.5","350","312","250","260","240.5","350","312"};
    int[] EventImages = {
            R.drawable.p1,
            R.drawable.p2,
            R.drawable.p3,
            R.drawable.p4,
            R.drawable.p5,
            R.drawable.p6,
            R.drawable.p7,
            R.drawable.p8,
            R.drawable.p9,
            R.drawable.p10
    };

    private LinkedList<String> EventTitle = new LinkedList<>();
    private LinkedList<Integer> EventImage = new LinkedList<>();
    private LinkedList<String> Clubname = new LinkedList<>();
    private LinkedList<String> DateEvent = new LinkedList<>();


    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_list_view_admins);

        // setting up the Request Queue
        SettingUpRequestQueue();

        SharedPreferences TOKEN = getSharedPreferences("TOKEN", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = TOKEN.edit();

        //pushing token to shared preference
        edit.putString("user-auth-token", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2MTIxM2YyNmUzNmZhMjAwMDRlZjM0MDUiLCJ1c2VybmFtZSI6IkNCLkVOLlU0Q1NFMTkwNjMiLCJpYXQiOjE2MzUwODczODV9.EIfz22nKX7B6cCMgJ8eVbWDK2SPmRr38JJdFjPzgwg0");
        edit.commit();

        // Api call is being made Here
        String AdminUsersEventsEndpoint = "https://amrita-events.herokuapp.com/api/admin-users-portal";

        JsonArrayRequest EventCardRequest = new JsonArrayRequest
                (Request.Method.GET, AdminUsersEventsEndpoint, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        System.out.println(response);
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

        recyclerView = findViewById(R.id.recyclerView);
        for(int i = 0; i< Title.length; i++){
            EventTitle.addLast(Title[i]);
            EventImage.addLast(EventImages[i]);
            Clubname.addLast(Club[i]);
            DateEvent.addLast(Date[i]);
        }
        adapter = new EventListAdapter(this, EventTitle, EventImage, Clubname, DateEvent);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void SettingUpRequestQueue() {
        cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); //1Mb cap
        network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();
    }

}