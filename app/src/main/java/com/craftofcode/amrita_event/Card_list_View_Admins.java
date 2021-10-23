package com.craftofcode.amrita_event;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interceptors.HttpLoggingInterceptor;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.craftofcode.amrita_event.adapter.EventListAdapter;
import com.craftofcode.amrita_event.apiModel.AdminCardModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jacksonandroidnetworking.JacksonParserFactory;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.LinkedList;

import okhttp3.OkHttpClient;

public class Card_list_View_Admins extends AppCompatActivity {
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

        // Adding an Network Interceptor for Debugging purpose :
        OkHttpClient okHttpClient = new OkHttpClient() .newBuilder()
                .addNetworkInterceptor(new HttpLoggingInterceptor())
                .build();


        AndroidNetworking.initialize(getApplicationContext(),okHttpClient);

        // setting the JacksonParserFactory below
        AndroidNetworking.setParserFactory(new JacksonParserFactory());

        GetRequestToAdminCardView();

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

    private LinkedList<AdminCardModel> GetRequestToAdminCardView() {
        final LinkedList<AdminCardModel>[] Events = new LinkedList[1];
        AndroidNetworking.get("https://amrita-events.herokuapp.com/api/admin-users-portal")
                .addHeaders("user-auth-token", "")
                .setPriority(Priority.HIGH)
                .setTag("CardRequest")
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Gson gson = new Gson();
                        String JsonResponse = response.toString();
                        Type listType = new TypeToken<LinkedList<AdminCardModel>>(){}.getType();
                        Events[0] = gson.fromJson(JsonResponse, listType);
                        System.out.println(Events[0]);
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Log.d("message", "Error as usual");
                    }
                });
        return Events[0];
    }


}