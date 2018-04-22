package com.example.moamen.ticketmaster.utils;


import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.moamen.ticketmaster.interfaces.ApiInterface;
import com.example.moamen.ticketmaster.interfaces.RequestStatus;
import com.example.moamen.ticketmaster.models.Events;
import com.example.moamen.ticketmaster.models.TicketsMaster;

import org.json.JSONException;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by PC on 7/10/2017.
 */

public class ApiImp {

    private Context context;
    private ApiInterface apiInterface;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    RequestStatus requestStatus;

    public ApiImp(RequestStatus requestStatus , Context context){
        this.requestStatus = requestStatus;
        this.context = context;
    }

    public void search(int page , String searchWords){

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<TicketsMaster> call = apiInterface.search(page, searchWords , ApiClient.ApiKey);
        Log.w("@@##" , call.request().url().toString());

        call.enqueue(new Callback<TicketsMaster>() {
            @Override
            public void onResponse(Call<TicketsMaster> call, Response<TicketsMaster> response) {
                switch (response.code()) {
                    case 200:
                        requestStatus.onSuccess(response);
                        break;
                    case 401:
                        requestStatus.onFailed("Failed to access");
                        break;
                    default:
                        requestStatus.onFailed("" + response.message());
                }
            }

            @Override
            public void onFailure(Call<TicketsMaster> call, Throwable t) {
                Log.w("@@## ONFailur" , "HERE !!!!!");
            }
        });
    }

    public void getEventDetails(String id){
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<Events> call = apiInterface.getEventDetails(id , ApiClient.ApiKey);
        Log.w("@@##" , call.request().url().toString());

        call.enqueue(new Callback<Events>() {
            @Override
            public void onResponse(Call<Events> call, Response<Events> response) {
                switch (response.code()) {
                    case 200:
                        requestStatus.onSuccess(response);
                        break;
                    case 401:
                        requestStatus.onFailed("Failed to access");
                        break;
                    default:
                        requestStatus.onFailed("" + response.message());
                }
            }

            @Override
            public void onFailure(Call<Events> call, Throwable t) {

            }
        });
    }
}
