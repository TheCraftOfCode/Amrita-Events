package com.craftofcode.amrita_event;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Network;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.craftofcode.amrita_event.adapter.EventListAdapter;
import com.craftofcode.amrita_event.apiModel.MySingleton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Card_list_View_Admins extends AppCompatActivity {

    Cache cache;
    Network network;
    RequestQueue requestQueue;

    public ProgressBar progressBar;
    public RecyclerView recyclerView;
    public EventListAdapter adapter;

    private LinkedList<String> _id;
    private LinkedList<Integer> EventImage;
    //Image URL variable for now
    private LinkedList<String> Url;
    private LinkedList<String> EventTitle;
    private LinkedList<String> Clubname;
    private LinkedList<String> DateEvent;

    private FloatingActionButton CreateEvents;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        _id = new LinkedList<>();

        Url = new LinkedList<>();
        EventTitle = new LinkedList<>();
        Clubname = new LinkedList<>();
        DateEvent = new LinkedList<>();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_list_view_admins);

        recyclerView = findViewById(R.id.recyclerView);
        CreateEvents = findViewById(R.id.createEvents);
        progressBar = findViewById(R.id.progress_bar);

        //setting up the adapter
        adapter = new EventListAdapter(getApplicationContext(), getLayoutInflater(),_id,EventTitle, Url, Clubname, DateEvent);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        //progress bar visibilty
        progressBarVisible();
        // setting up the Request Queue
        SettingUpRequestQueue();

        //Get Request to render all the events.
        GetRequestToTheAdminSideEventCardView();

        CreateEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View builderView = getLayoutInflater().inflate(R.layout.update_fields, null);
                final AlertDialog.Builder builder = new AlertDialog.Builder(CreateEvents.getContext());
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
                Button CreateEventButton = builderView.findViewById(R.id.CreateEventButton);

                alertDialog.show();
                ParentView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ImageUrl.clearFocus();EventTitle.clearFocus();Caption.clearFocus(); Description.clearFocus(); OrganisingClub.clearFocus(); Date.clearFocus(); Venue.clearFocus(); RegistrationLink.clearFocus();
                        Note.clearFocus(); ContactName1.clearFocus(); ContactName2.clearFocus(); ContactPhone1.clearFocus(); ContactPhone2.clearFocus();

                        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputMethodManager.hideSoftInputFromWindow(builderView.getWindowToken(), 0);

                    }
                });
                closeDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });

                //Creating the event
               CreateEventButton.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       //Preparing the Json Object
                       JSONObject CreateEventPostReqestBody = new JSONObject();
                       try {
                           CreateEventPostReqestBody.put("ImageUrl", ConvertEditTextContentToString(ImageUrl));
                           CreateEventPostReqestBody.put("Title", ConvertEditTextContentToString(EventTitle));
                           CreateEventPostReqestBody.put("Caption", ConvertEditTextContentToString(Caption));
                           CreateEventPostReqestBody.put("Description", ConvertEditTextContentToString(Description));
                           CreateEventPostReqestBody.put("OrganizingClub", ConvertEditTextContentToString(OrganisingClub));
                           CreateEventPostReqestBody.put("Date", ConvertEditTextContentToString(Date));
                           CreateEventPostReqestBody.put("Venue", ConvertEditTextContentToString(Venue));
                           CreateEventPostReqestBody.put("RegistrationLink", ConvertEditTextContentToString(RegistrationLink));
                           CreateEventPostReqestBody.put("Note", ConvertEditTextContentToString(Note));

                           //creating an other Json Object for contact details
                           JSONObject ContactDetails = new JSONObject();

                           ContactDetails.put(ContactName1.getText().toString(), ContactPhone1.getText().toString());
                           ContactDetails.put(ContactName2.getText().toString(), ContactPhone2.getText().toString());

                           //putting it back in the Json Body
                           CreateEventPostReqestBody.put("ContactDetails", ContactDetails);
                           System.out.println(CreateEventPostReqestBody);
                           //opening a post request
                           PostRequestToCreateANewEvents(CreateEventPostReqestBody);

                           //Json Body is Now ready

                       } catch (JSONException e) {
                           e.printStackTrace();
                       }

                   }
               });
            }
        });
    }

    public String ConvertEditTextContentToString(EditText somefield){
        return somefield.getText().toString();
    }

    private void PostRequestToCreateANewEvents( JSONObject RequestBody) {
        //Create a a new post request

        //preparing the body
        RequestQueue requestQueue2 = Volley.newRequestQueue(this);
        final String RequestBodyString = RequestBody.toString();

        String PostRequestUrl = "https://amrita-events.herokuapp.com/api/admin-users-portal";
        StringRequest CreateEventPostRequest = new StringRequest(Request.Method.POST, PostRequestUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("LOG_RESPONSE" + response);
                Toast.makeText(getApplicationContext(), "Event Successfully Created", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("LOG_RESPONSE" + error.toString());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                SharedPreferences Token = getSharedPreferences("Token",MODE_PRIVATE);
                params.put("user-auth-token", Token.getString("user-auth-token","Theif..!"));
                return params;
            }

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return RequestBodyString == null ? null : RequestBodyString.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", RequestBodyString, "utf-8");
                    return null;
                }
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                String responseString = "";
                if (response != null) {
                    responseString = String.valueOf(response.statusCode);
                }
                return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
            }
        };

        CreateEventPostRequest.setRetryPolicy(new DefaultRetryPolicy(500000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue2.add(CreateEventPostRequest);

    }
    private void SettingUpRequestQueue() {
        cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); //1Mb cap
        network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();
    }

    private void GetRequestToTheAdminSideEventCardView(){

        // Api call is being made Here
        String AdminUsersEventsEndpoint = "https://amrita-events.herokuapp.com/api/admin-users-portal";

        JsonArrayRequest EventCardRequest = new JsonArrayRequest
                (Request.Method.GET, AdminUsersEventsEndpoint, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {

                        _id.clear();

                        Url.clear();
                        EventTitle.clear();
                        Clubname.clear();
                        DateEvent.clear();

                        //Notifying that the dataset is changes when there is a new
                        adapter.notifyDataSetChanged();
                        //System.out.println(response);  //debugging purposes
                        for(int i = 0; i < response.length(); i++){
                            try {

                                //System.out.println(response.getJSONObject(i));   Debugging

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

                        progrssBarInVisible();
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
                SharedPreferences Token = getSharedPreferences("Token",MODE_PRIVATE);
                params.put("user-auth-token", Token.getString("user-auth-token","Theif..!"));
                return params;
            }
        };

        //adding the request to Queue
        MySingleton.getInstance(this).addToRequestQueue(EventCardRequest);

    }

    public void progressBarVisible(){
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);
        CreateEvents.setVisibility(View.INVISIBLE);
    }

    public void progrssBarInVisible(){
        progressBar.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
        CreateEvents.setVisibility(View.VISIBLE);
    }

}