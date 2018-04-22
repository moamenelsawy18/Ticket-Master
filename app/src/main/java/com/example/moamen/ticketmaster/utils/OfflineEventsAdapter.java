package com.example.moamen.ticketmaster.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.moamen.ticketmaster.R;
import com.example.moamen.ticketmaster.interfaces.EventSelected;
import com.example.moamen.ticketmaster.models.Event;
import com.example.moamen.ticketmaster.models.Events;
import com.squareup.picasso.Picasso;

import java.io.FileInputStream;
import java.util.ArrayList;


public class OfflineEventsAdapter extends RecyclerView.Adapter<OfflineEventsAdapter.MyViewHolder> {


    private ArrayList<Event> events;
    private Context context;
    private EventSelected eventSelected;

    public OfflineEventsAdapter(Context context, ArrayList<Event> events , EventSelected eventSelected)
    {
        this.eventSelected = eventSelected;
        this.context = context;
        this.events = events;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_item, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.title.setText("Event Name : " + events.get(position).getEventName());
        holder.date.setText("Event Date : " + events.get(position).getEventDate());
        holder.image.setImageBitmap(loadImageBitmap(context.getApplicationContext() , events.get(position).getImage()));

    }

    public Bitmap loadImageBitmap(Context context, String imageName) {
        Bitmap bitmap = null;
        FileInputStream fiStream;
        try {
            fiStream    = context.openFileInput(imageName);
            bitmap      = BitmapFactory.decodeStream(fiStream);
            fiStream.close();
        } catch (Exception e) {
            Log.d("saveImage", "Exception 3, Something went wrong!");
            e.printStackTrace();
        }
        return bitmap;
    }


    @Override
    public int getItemCount() {
        return events.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        ImageView image;
        TextView date , title;

        public MyViewHolder(final View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.name);
            date = (TextView) itemView.findViewById(R.id.eventDate);
            image = (ImageView) itemView.findViewById(R.id.eventImage);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    eventSelected.selectEvent(events.get(getPosition()).getEventID());
                }
            });

        }
    }

    public void clear() {
        events.clear();
        notifyDataSetChanged();
    }

    public void addAll(ArrayList<Event> events) {
        this.events = events;
        notifyDataSetChanged();
    }
}
