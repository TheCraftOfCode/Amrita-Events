package com.craftofcode.amrita_event;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }


    public void LoginFunction(View view) {

        TextView usernameTV = findViewById(R.id.username);
        TextView passwordTV = findViewById(R.id.password);

        String uname = usernameTV.getText().toString();
        String pword = passwordTV.getText().toString();


        Intent firstIntent = new Intent(this, CardView_Home.class);
        startActivity(firstIntent);


        if (uname.equals("username") && (pword.equals("password"))) {
            Intent intent = new Intent(this, CardView_Home.class);
            startActivity(intent);
        } else if (uname.equals("username2") && pword.equals("password2")) {
            Intent intent = new Intent(this, club_view.class);
            startActivity(intent);
        }
        else if(uname.equals("username3")&&pword.equals("password3")){
            Intent intent = new Intent(this,Card_list_View_Admins.class);
            startActivity(intent);
        }
        else {
            Toast.makeText(getApplicationContext(), "Wrong credentials, try again", Toast.LENGTH_SHORT).show();
        }
    }

}