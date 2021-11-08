package com.craftofcode.amrita_event;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;





    public class Club_Adapter extends RecyclerView.Adapter<Club_Adapter.Viewholder> {
        private Context context;
        private final ArrayList<Club_Details> clubDetailsArrayList;


        public Club_Adapter(Context context, ArrayList<Club_Details> clubDetailsArrayList) {
            this.context = context;
            this.clubDetailsArrayList = clubDetailsArrayList;
        }
        @NonNull
        @Override
        public Club_Adapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.club_card, parent, false);
            return new Viewholder(view, this);

        }

        @Override
        public void onBindViewHolder(Club_Adapter.Viewholder holder, int position){
            Club_Details club = clubDetailsArrayList.get(position);
            holder.clubName.setText(club.getClub_name());
            holder.clubDes.setText(club.getClub_description());

            Glide.with(context).load(club.getClub_img_url()).override(100, 100).into(holder.club_img);
        }


        @Override
        public int getItemCount(){
            return clubDetailsArrayList.size();
        }

        public class Viewholder extends RecyclerView.ViewHolder {

            public View v;
            private TextView clubName, clubDes;
            final Club_Adapter adapter;
            private Button check_events;
            private String club_img_url;
            private ImageView club_img;

            public Viewholder(@NonNull View itemView,   Club_Adapter adapter) {
                super(itemView);
                this.v = v;
                this.clubName =itemView.findViewById(R.id.club_name);

                this.clubDes = itemView.findViewById(R.id.club_des);
                this.adapter = adapter;
                this.check_events = itemView.findViewById(R.id.check_events);
                this.club_img = itemView.findViewById(R.id.club_image);

                check_events.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("onClick: ","Item has been clicked");
                        int position = getAdapterPosition();
                        Intent intent = new Intent(context,CardView_Home.class);
                        context.startActivity(intent);
                    }
                });
            }
        }
    }



