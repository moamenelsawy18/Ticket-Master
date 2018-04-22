package com.example.moamen.ticketmaster.viewmodel;

import android.annotation.SuppressLint;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.moamen.ticketmaster.interfaces.DetailsEvents;
import com.example.moamen.ticketmaster.interfaces.RequestStatus;
import com.example.moamen.ticketmaster.interfaces.SearchEvents;
import com.example.moamen.ticketmaster.models.Event;
import com.example.moamen.ticketmaster.models.Events;
import com.example.moamen.ticketmaster.models.TicketsMaster;
import com.example.moamen.ticketmaster.utils.ApiImp;
import com.example.moamen.ticketmaster.utils.AppDatabase;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import retrofit2.Response;

public class DetailsViewModel implements RequestStatus{
    public Events events;
    private ApiImp apiImp;
    private Context context;
    private DetailsEvents detailsEvents;
    private AppDatabase db;
    private Event event;

    public DetailsViewModel(DetailsEvents detailsEvents, Context context) {
        apiImp = new ApiImp(this , context);
        this.detailsEvents = detailsEvents;
        this.context = context;
    }

    public void getEventDetails(String id){
        if (detailsEvents != null){
            detailsEvents.showProgress();
        }
        apiImp.getEventDetails(id);
    }

    public void insertEvent(final Event event){
        db = Room.databaseBuilder(context.getApplicationContext(),
                AppDatabase.class, "Events-Database").allowMainThreadQueries().build();

        db.eventDao().insertEvent(event);
        Toast.makeText(context, "Added To Marked Events", Toast.LENGTH_SHORT).show();
    }

    public void getEvent(final String id){
        db = Room.databaseBuilder(context.getApplicationContext(),
                AppDatabase.class, "Events-Database").allowMainThreadQueries().fallbackToDestructiveMigration().build();

        event = db.eventDao().getByID(id);
        detailsEvents.getEventDB(event);
    }

    public void deleteEvent(Event event){
        db = Room.databaseBuilder(context.getApplicationContext(),
                AppDatabase.class, "Events-Database").allowMainThreadQueries().build();

        db.eventDao().delete(event);
        Toast.makeText(context, "Deleted To Marked Events", Toast.LENGTH_SHORT).show();
    }

    public String getImagePath(String url , String name) {
        InputStream inputStream = null;
        FileOutputStream foStream;
        Bitmap bitmap;
        try {
            inputStream = new URL(url).openStream();
            bitmap = BitmapFactory.decodeStream(inputStream);
            foStream = context.openFileOutput(name+".png", Context.MODE_PRIVATE);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, foStream);
            foStream.close();
            } catch (IOException e) {
            e.printStackTrace();
        }
        return name+".png";
    }

    @Override
    public void onSuccess(Response response) {
        detailsEvents.hideProgress();
        if(response.body() instanceof Events) {
            events = (Events) response.body();
            if (events != null)
                detailsEvents.getEventDetailsResponse(events);
            else
                detailsEvents.showErrorMessage("Error , please try again");
        } else {
            detailsEvents.showErrorMessage("Error , please try again");
        }
    }

    @Override
    public void onFailed(String failurReason) {
        detailsEvents.showErrorMessage("Error , please try again");
    }
}
