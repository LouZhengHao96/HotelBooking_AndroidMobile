package com.example.testdb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.databasehelper.DataBaseHelper;

import java.text.DateFormat;
import java.util.ArrayList;

public class NotApprovedListAdapter extends RecyclerView.Adapter<NotApprovedListAdapter.NotApprovedHolder>{
    // private variables
    private Context context;
    private ArrayList NAfloor, NAroom, NAuser, NAdate, NAtimeslot, NAcapacity, NAprice, NApromocode, NAstatus,RoomiD;
    private RecyclerViewCLickListener RVclick;
    DataBaseHelper myDb;
    // constructor
    NotApprovedListAdapter(Context context,
                    ArrayList floor_id,
                    ArrayList room_id,
                    ArrayList user_id,
                    ArrayList date_id,
                    ArrayList timeslot_id,
                    ArrayList capacity_id,
                    ArrayList price_id,
                    ArrayList promo_code_id,
                    ArrayList status_id,
                    ArrayList Room_iD,
                    //new Recycler view
                    RecyclerViewCLickListener RVclick) {
        this.context = context;
        this.NAfloor = floor_id;
        this.NAroom = room_id;
        this.NAuser = user_id;
        this.NAdate = date_id;
        this.NAtimeslot = timeslot_id;
        this.NAcapacity = capacity_id;
        this.NAprice = price_id;
        this.NApromocode = promo_code_id;
        this.NAstatus = status_id;
        this.RoomiD=Room_iD;
        //click
        this.RVclick=RVclick;
        myDb = new DataBaseHelper(context);

    }


    // sets room_row layout as the row displayed in the roomList recyclerview
    @NonNull
    @Override
    public NotApprovedHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(com.example.supermain.R.layout.notapprovedroom, parent, false);
        return new NotApprovedHolder(view);
    }

    // sets the placeholder text of each row in the recyclerView onto the corresponding data
    @Override
    public void onBindViewHolder(@NonNull NotApprovedHolder holder, int position) {
        String formatDate = DateFormat.getDateInstance(DateFormat.MEDIUM).format(myDb.stringToCalendar(NAdate.get(position).toString()).getTime());
        holder.textview_date.setText(String.valueOf(formatDate));
        holder.textview_floorName.setText(String.valueOf(NAfloor.get(position)));
        holder.textview_roomName.setText(String.valueOf(NAroom.get(position)));
        holder.textview_time.setText(String.valueOf(NAtimeslot.get(position)));
        holder.textview_status.setText(String.valueOf(NAstatus.get(position)));

    }

    // counts the number of rows from database table
    @Override
    public int getItemCount() {
        return NAroom.size();
    }

    //
    public class NotApprovedHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView textview_date, textview_floorName, textview_roomName, textview_time, textview_status;
        CardView cardview_Card;

        public NotApprovedHolder(@NonNull View itemView) {
            super(itemView);
            textview_date = itemView.findViewById(com.example.supermain.R.id.text_NAdate);
            textview_floorName = itemView.findViewById(com.example.supermain.R.id.text_NAfloorName);
            textview_roomName = itemView.findViewById(com.example.supermain.R.id.text_NAroomName);
            textview_time = itemView.findViewById(com.example.supermain.R.id.text_NAtime);
            textview_status = itemView.findViewById(com.example.supermain.R.id.text_NAstatus);
            cardview_Card = itemView.findViewById(com.example.supermain.R.id.superApprovedCard);
            itemView.setOnClickListener(this);
        }
        //on click
        @Override
        public void onClick(View v) {
            RVclick.onClick(v,getAdapterPosition());
        }
    }

    //on click
    public interface RecyclerViewCLickListener{
        void onClick(View v,int position);
    }
}
