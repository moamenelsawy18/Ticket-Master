package com.example.moamen.ticketmaster.interfaces;

import com.example.moamen.ticketmaster.models.Event;

import java.util.ArrayList;


public interface MarkedEvents extends BaseEvents {
    void getMarkedEvents(ArrayList<Event> events);
}
