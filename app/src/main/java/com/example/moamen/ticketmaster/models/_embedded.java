package com.example.moamen.ticketmaster.models;

import java.util.ArrayList;

public class _embedded
{
    public ArrayList<Events> events;

    public ArrayList<Events> getEvents ()
    {
        return events;
    }

    public void setEvents (ArrayList<Events> events)
    {
        this.events = events;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [events = "+events+"]";
    }
}
