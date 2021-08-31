package com.craftofcode.amrita_event;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Clubs_View extends AppCompatActivity {
    Button ascii, shrishti;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_view);
        setupHyperlink();
        ascii=findViewById(R.id.ASCIIbt1);
        shrishti = findViewById(R.id.shrishtibt1);
        ascii.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EventView_Home.class);
                startActivity(intent);
            }
        });
    }
    private void setupHyperlink() {
        TextView ASCIIlinkTextView = findViewById(R.id.ASCIItv3);
        TextView srishtilinkTextView = findViewById(R.id.shrishtitv3);
        ASCIIlinkTextView.setMovementMethod(LinkMovementMethod.getInstance());
        srishtilinkTextView.setMovementMethod(LinkMovementMethod.getInstance());
        ASCIIlinkTextView.setLinkTextColor(Color.BLUE);
        srishtilinkTextView.setLinkTextColor(Color.BLUE);

    }
}