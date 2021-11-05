package com.craftofcode.amrita_event;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.craftofcode.amrita_event.apiModel.MySingleton;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

public class MainActivity extends AppCompatActivity {

    EditText username, password;
    Button LoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        LoginButton = findViewById(R.id.login_button);

        //Making a post request to check username and password

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(username.getText().toString().length() != 0 && password.getText().toString().length() != 0){
                    String LoginUrl = "https://amrita-events.herokuapp.com/api/login";
                    JSONObject UserCredentials = new JSONObject();
                    try {
                        UserCredentials.put("username", username.getText().toString());
                        UserCredentials.put("password", password.getText().toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String UserCredentailsString = UserCredentials.toString();

                    //opening a post request to Volley
                    StringRequest LoginUserRequest = new StringRequest(Request.Method.POST, LoginUrl, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
//                            System.out.println(response);
                            Toast.makeText(getApplicationContext(), "Logged in", Toast.LENGTH_SHORT).show();
                            SharedPreferences TOKEN = getSharedPreferences("Token", Context.MODE_PRIVATE);
                            SharedPreferences.Editor edit = TOKEN.edit();

                            edit.putString("user-auth-token", response);
                            edit.commit();

                            //decoding the json web token
                            String[] chunks = response.split("\\.");
                            Base64.Decoder decoder = Base64.getDecoder();
                            String payload = new String(decoder.decode(chunks[1]));

                            JSONObject TokenBody = new JSONObject();
                            JsonObject jsonObject = (JsonObject) new JsonParser().parseString(payload);
//                            System.out.println(jsonObject.get("isAdmin"));
                            if(jsonObject.get("isAdmin").toString().equals("true")){
                                Intent intent = new Intent(getApplicationContext(), Card_list_View_Admins.class);
                                startActivity(intent);
                            }else if(jsonObject.get("isAdmin").toString().equals("false")){
                                Intent intent = new Intent(getApplicationContext(), CardView_Home.class);
                                startActivity(intent);
                            }else{
                                return;
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            System.out.println("LOG_RESPONSE" + error.toString());
                        }
                    }) {

                        @Override
                        public String getBodyContentType() {
                            return "application/json; charset=utf-8";
                        }

                        @Override
                        public byte[] getBody() throws AuthFailureError {
                            try {
                                return UserCredentials == null ? null : UserCredentailsString.getBytes("utf-8");
                            } catch (UnsupportedEncodingException uee) {
                                VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", UserCredentials, "utf-8");
                                return null;
                            }
                        }

                        @Override
                        protected Response<String> parseNetworkResponse(NetworkResponse response) {
                            String responseString = "";
                            if (response != null) {
                                responseString = new String(response.data);
                            }
                            return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                        }
                    };

                    MySingleton.getInstance(getApplicationContext()).addToRequestQueue(LoginUserRequest);

                }
            }
        });
    }
}