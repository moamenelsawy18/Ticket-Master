package com.example.moamen.ticketmaster.utils;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import com.example.moamen.ticketmaster.interfaces.EventDao;
import com.example.moamen.ticketmaster.models.Event;

@Database(entities = {Event.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract EventDao eventDao();
}
