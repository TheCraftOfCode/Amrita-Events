package com.craftofcode.amrita_event;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.craftofcode.amrita_event.apiModel.MySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class Register_user extends AppCompatActivity {

    EditText Name, Email, Username, Password;
    Button RegisterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        Name = findViewById(R.id.Name);
        Email = findViewById(R.id.Email);
        Username = findViewById(R.id.Username);
        Password = findViewById(R.id.password);
        RegisterButton = findViewById(R.id.RegisterButton);

        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Name.getText().toString().length() != 0 && Email.getText().toString().length() != 0 && Username.getText().toString().length() != 0 && Password.getText().toString().length() != 0) {
                    JSONObject requestBody = new JSONObject();
                    try {
                        requestBody.put("Name", Name.getText().toString());
                        requestBody.put("email", Email.getText().toString());
                        requestBody.put("username", Username.getText().toString());
                        requestBody.put("password", Password.getText().toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    //opening a post request
                    String RegisterUserEndpoint = "https://amrita-events.herokuapp.com/api/register";

                    String requestBodyString = requestBody.toString();

                    StringRequest LoginUserRequest = new StringRequest(Request.Method.POST, RegisterUserEndpoint, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
//                            System.out.println(response);
                            Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                            startActivity(intent);
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
                                return requestBody == null ? null : requestBodyString.getBytes("utf-8");
                            } catch (UnsupportedEncodingException uee) {
                                VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
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