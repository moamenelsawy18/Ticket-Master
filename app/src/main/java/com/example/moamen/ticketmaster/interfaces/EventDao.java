package com.example.moamen.ticketmaster.interfaces;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.moamen.ticketmaster.models.Event;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface EventDao {
    @Query("SELECT * FROM event")
    List<Event> getAll();

    @Query("SELECT * FROM event WHERE eventID LIKE :eventID")
    Event getByID(String eventID);

    @Query("SELECT * FROM event WHERE eventID LIKE :eventID")
    boolean ifThere(String eventID);

    @Query("SELECT * FROM event WHERE name LIKE :first AND "
           + "name LIKE :last LIMIT 1")
    Event findByName(String first, String last);

    @Insert
    void insertAll(Event... events);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertEvent(Event... event);

    @Delete
    void delete(Event event);

    @Query("DELETE FROM event")
    void deleteAll();
}
