package com.example.moamen.ticketmaster.models;

import java.util.ArrayList;

public class Dates
{
    private Start start;


    public Start getStart ()
    {
        return start;
    }

    public void setStart (Start start)
    {
        this.start = start;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [start = "+start+"]";
    }
}
