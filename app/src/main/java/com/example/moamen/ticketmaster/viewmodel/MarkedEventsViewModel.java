package com.example.moamen.ticketmaster.viewmodel;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.widget.Toast;

import com.example.moamen.ticketmaster.interfaces.MarkedEvents;
import com.example.moamen.ticketmaster.interfaces.RequestStatus;
import com.example.moamen.ticketmaster.interfaces.SearchEvents;
import com.example.moamen.ticketmaster.models.Event;
import com.example.moamen.ticketmaster.models.TicketsMaster;
import com.example.moamen.ticketmaster.utils.ApiImp;
import com.example.moamen.ticketmaster.utils.AppDatabase;

import java.util.ArrayList;

import retrofit2.Response;

public class MarkedEventsViewModel{
    public TicketsMaster ticketsMaster;
    private Context context;
    private MarkedEvents markedEvents;
    private AppDatabase db;
    private ArrayList<Event> events;


    public MarkedEventsViewModel(MarkedEvents markedEvents, Context context) {
        this.markedEvents = markedEvents;
        this.context = context;
    }

    public void getEventsDB(){
        if (markedEvents != null){
            markedEvents.showProgress();
        }

        db = Room.databaseBuilder(context.getApplicationContext(),
                AppDatabase.class, "Events-Database").allowMainThreadQueries().build();

        events = (ArrayList<Event>) db.eventDao().getAll();
        markedEvents.getMarkedEvents(events);

    }

    public void insertEvent(final Event event){
        db = Room.databaseBuilder(context.getApplicationContext(),
                AppDatabase.class, "Events-Database").allowMainThreadQueries().build();

        db.eventDao().insertEvent(event);
        Toast.makeText(context, "Added To Marked Events", Toast.LENGTH_SHORT).show();
    }

    public void getEvent(final String id){
        db = Room.databaseBuilder(context.getApplicationContext(),
                AppDatabase.class, "Events-Database").allowMainThreadQueries().build();

//        event = db.eventDao().getByID(id);
//        detailsEvents.getEventDB(event);
    }

    public void deleteEvent(Event event){
        db = Room.databaseBuilder(context.getApplicationContext(),
                AppDatabase.class, "Events-Database").allowMainThreadQueries().build();

        db.eventDao().delete(event);
        Toast.makeText(context, "Deleted To Marked Events", Toast.LENGTH_SHORT).show();
    }
}
