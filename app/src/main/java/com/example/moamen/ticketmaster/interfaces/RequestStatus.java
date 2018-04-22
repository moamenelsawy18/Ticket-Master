package com.example.moamen.ticketmaster.interfaces;

import retrofit2.Response;

/**
 * Created by PC on 7/10/2017.
 */

public interface RequestStatus {
    void onSuccess(Response response);
    void onFailed(String failurReason);
}
