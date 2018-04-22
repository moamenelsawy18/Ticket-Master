package com.example.moamen.ticketmaster.interfaces;


import com.example.moamen.ticketmaster.models.Events;
import com.example.moamen.ticketmaster.models.TicketsMaster;
import com.example.moamen.ticketmaster.utils.ApiClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface ApiInterface {

    @GET("events.json?size=10")
    Call<TicketsMaster> search(@Query("page") int page , @Query("keyword") String searchWords , @Query("apikey") String apiKey);

    @GET("events/{id}" + ".json?")
    Call<Events> getEventDetails(@Path("id") String id , @Query("apikey") String apiKey);

}
