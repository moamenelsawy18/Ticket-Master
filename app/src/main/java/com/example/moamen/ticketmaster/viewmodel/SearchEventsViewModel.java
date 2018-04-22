package com.example.moamen.ticketmaster.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.moamen.ticketmaster.interfaces.RequestStatus;
import com.example.moamen.ticketmaster.interfaces.SearchEvents;
import com.example.moamen.ticketmaster.models.Events;
import com.example.moamen.ticketmaster.models.TicketsMaster;
import com.example.moamen.ticketmaster.utils.ApiImp;

import org.json.JSONException;

import java.util.ArrayList;

import retrofit2.Response;

public class SearchEventsViewModel extends ViewModel implements RequestStatus{
    public TicketsMaster ticketsMaster;
    private ApiImp apiImp;
    private Context context;
    private SearchEvents searchEvents;

    public SearchEventsViewModel(SearchEvents searchEvents, Context context) {
        apiImp = new ApiImp(this , context);
        this.searchEvents = searchEvents;
        this.context = context;
    }

    public void getEvents(int page , String searchWords){
        if (searchEvents != null){
            searchEvents.showProgress();
        }
        apiImp.search(page , searchWords);
    }

    @Override
    public void onSuccess(Response response) {
        searchEvents.hideProgress();
        if(response.body() instanceof TicketsMaster) {
            ticketsMaster = (TicketsMaster) response.body();
            if (ticketsMaster.get_embedded() != null)
            searchEvents.getSearchResultResponse(ticketsMaster.get_embedded().getEvents());
            else
                searchEvents.showErrorMessage("No Events Found");
        } else {
            searchEvents.showErrorMessage("No Events Found");
        }
    }

    @Override
    public void onFailed(String failurReason) {
        searchEvents.showErrorMessage("No Events Found");
    }
}
