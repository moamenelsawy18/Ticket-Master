package com.example.moamen.ticketmaster.interfaces;

import com.example.moamen.ticketmaster.models.Event;
import com.example.moamen.ticketmaster.models.Events;

import java.util.ArrayList;


public interface DetailsEvents extends BaseEvents {
    void getEventDetailsResponse(Events event);
    void getEventDB(Event event);
}
