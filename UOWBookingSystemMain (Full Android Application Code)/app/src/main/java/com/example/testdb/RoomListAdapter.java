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

public class RoomListAdapter extends RecyclerView.Adapter<RoomListAdapter.RoomViewHolder> {

    // private variables
    private Context context;
    private ArrayList room_id_array, floor_num_array, room_num_array, user_array, date_array, timeslot_array, capacity_array, price_array, promo_code_array, status_array;
    private RecyclerViewCLickListener RVclick;
    DataBaseHelper myDb;

    // constructor
    RoomListAdapter(Context context,
                    ArrayList room_id_array,
                    ArrayList floor_num_array,
                    ArrayList room_num_array,
                    ArrayList user_array,
                    ArrayList date_array,
                    ArrayList timeslot_array,
                    ArrayList capacity_array,
                    ArrayList price_array,
                    ArrayList promo_code_array,
                    ArrayList status_array,
                    //new Recycler view
                    RecyclerViewCLickListener RVclick) {
        this.context = context;
        this.room_id_array = room_id_array;
        this.floor_num_array = floor_num_array;
        this.room_num_array = room_num_array;
        this.user_array = user_array;
        this.date_array = date_array;
        this.timeslot_array = timeslot_array;
        this.capacity_array = capacity_array;
        this.price_array = price_array;
        this.promo_code_array = promo_code_array;
        this.status_array = status_array;
        //click
        this.RVclick=RVclick;
        myDb = new DataBaseHelper(context);

    }


    // sets room_row layout as the row displayed in the roomList recyclerview
    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(com.example.staffmainmenuv2.R.layout.room_row, parent, false);
        return new RoomViewHolder(view);
    }

    // sets the placeholder text of each row in the recyclerView onto the corresponding data
    @Override
    public void onBindViewHolder(@NonNull RoomViewHolder holder, int position) {
        holder.textview_roomID.setText(String.valueOf(room_id_array.get(position)));
//      get formatted date
        System.out.println(date_array.get(position).toString());
        String formatDate = DateFormat.getDateInstance(DateFormat.MEDIUM).format(myDb.stringToCalendar(date_array.get(position).toString()).getTime());
        holder.textview_date.setText(String.valueOf(formatDate));
        holder.textview_floorName.setText(String.valueOf(floor_num_array.get(position)));
        holder.textview_roomName.setText(String.valueOf(room_num_array.get(position)));
        holder.textview_time.setText(String.valueOf(timeslot_array.get(position)));
        holder.textview_status.setText(String.valueOf(status_array.get(position)));
        String tempStatus = String.valueOf(status_array.get(position));
        switch (tempStatus){
            case "BOOKED": holder.cardview_Card.setCardBackgroundColor(context.getResources().getColor(com.example.staffmainmenuv2.R.color.mainBlue));
            break;
            case "NOT BOOKED": holder.cardview_Card.setCardBackgroundColor(context.getResources().getColor(com.example.staffmainmenuv2.R.color.mainYellow));
            break;
            case "APPROVED": holder.cardview_Card.setCardBackgroundColor(context.getResources().getColor(com.example.staffmainmenuv2.R.color.mainGreen));
            break;
            case "NOT APPROVED": holder.cardview_Card.setCardBackgroundColor(context.getResources().getColor(com.example.staffmainmenuv2.R.color.mainRed));
            break;
        }
//        holder.textview_capacity.setText(String.valueOf(capacity_array.get(position)));
//        holder.textview_price.setText(String.valueOf(price_array.get(position)));
//        holder.textview_promocode.setText(String.valueOf(promo_code_array.get(position)));
    }

    // counts the number of rows from database table
    @Override
    public int getItemCount() {
        return room_num_array.size();
    }

    // gets the roomview row variables
    public class RoomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView textview_roomID, textview_date, textview_floorName, textview_roomName, textview_time, textview_status, textview_capacity, textview_price, textview_promocode;
        CardView cardview_Card;
        public RoomViewHolder(@NonNull View itemView) {
            super(itemView);
            textview_roomID = itemView.findViewById(com.example.staffmainmenuv2.R.id.text_id);
            textview_date = itemView.findViewById(com.example.staffmainmenuv2.R.id.text_date);
            textview_floorName = itemView.findViewById(com.example.staffmainmenuv2.R.id.text_floorName);
            textview_roomName = itemView.findViewById(com.example.staffmainmenuv2.R.id.text_roomName);
            textview_time = itemView.findViewById(com.example.staffmainmenuv2.R.id.text_time);
            textview_status = itemView.findViewById(com.example.staffmainmenuv2.R.id.text_status);
            cardview_Card = itemView.findViewById(com.example.staffmainmenuv2.R.id.staffRoomCard);
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
