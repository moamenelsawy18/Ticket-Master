package com.example.moamen.ticketmaster.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
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
import com.example.moamen.ticketmaster.models.Events;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.MyViewHolder> {


    private ArrayList<Events> events;
    private Context context;
    private EventSelected eventSelected;
    private SharedPreferences preferences;

    public EventsAdapter(Context context, ArrayList<Events> events , EventSelected eventSelected)
    {
        preferences = context.getSharedPreferences("Show",Context.MODE_PRIVATE);
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
        String url = null;
        holder.title.setText("Event Name : " + events.get(position).getName());
        holder.date.setText("Event Date : " + events.get(position).getDates().getStart().getLocalDate());

        for (int i=0 ; i < events.get(position).getImages().size() ;i++){
            if (preferences.getBoolean("TowPane" , false)) {
                if (events.get(position).getImages().get(i).getUrl().contains("RETINA_PORTRAIT_16_9"))
                    url = events.get(position).getImages().get(i).getUrl();
            }else {
                if (events.get(position).getImages().get(i).getUrl().contains("TABLET_LANDSCAPE_16_9"))
                    url = events.get(position).getImages().get(i).getUrl();
            }
        }

        Picasso.with(context).load(Uri.parse(url)).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return events.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        Button sectionTitle;
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
                    eventSelected.selectEvent(events.get(getPosition()).getId());
                }
            });

        }
    }

    public void clear() {
        events.clear();
        notifyDataSetChanged();
    }

    public void addAll(ArrayList<Events> events) {
        this.events = events;
        notifyDataSetChanged();
    }

}
