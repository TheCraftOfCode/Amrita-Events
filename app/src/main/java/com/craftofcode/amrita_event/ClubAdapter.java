package com.craftofcode.amrita_event;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ClubAdapter extends RecyclerView.Adapter<ClubAdapter.Viewholder>
{
private Context context;

private final ArrayList<club_view> clubDetailsArrayList;

    public ClubAdapter(Context context, ArrayList<club_view> clubDetailsArrayList) {
        this.context = context;
        this.clubDetailsArrayList = clubDetailsArrayList;

    }
    @NonNull
    @Override


    public ClubAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.club_card,parent,false);

        return new Viewholder(view, this);
    }



    @Override
    public void onBindViewHolder(@NonNull ClubAdapter.Viewholder holder, int position) {

        club_view club = clubDetailsArrayList.get(position);
        holder.clubName.setText(club.getClub_name());
        holder.clubDescription.setText(club.getClub_Description());
        holder.clubInsta.setText(club.getClub_insta_id());



    }

    @Override
    public int getItemCount() {
        return 0;
    }

public class Viewholder extends RecyclerView.ViewHolder{
        public View v;
        private TextView clubName, clubDescription, clubInsta;
        final ClubAdapter adapter;
        private Button checkEvents;

       public Viewholder(View itemView ,ClubAdapter adapter) {
           super((itemView));
           clubName = itemView.findViewById(R.id.club_name);
           clubDescription = itemView.findViewById(R.id.club_description);
           clubInsta = itemView.findViewById(R.id.club_insta_id);
           checkEvents = itemView.findViewById(R.id.club_button);
           this.adapter = adapter;
           this.v = itemView;

           checkEvents.setOnClickListener(new View.OnClickListener() {
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
