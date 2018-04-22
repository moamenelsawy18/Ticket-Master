package com.example.moamen.ticketmaster.interfaces;

import com.example.moamen.ticketmaster.models.Events;

import java.util.ArrayList;
import java.util.EventListener;

import retrofit2.Response;


public interface SearchEvents extends BaseEvents {
    void getSearchResultResponse(ArrayList<Events> events);
}
