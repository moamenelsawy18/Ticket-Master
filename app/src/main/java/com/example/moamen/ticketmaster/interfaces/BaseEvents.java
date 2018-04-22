package com.example.moamen.ticketmaster.interfaces;

/**
 * Created by Gemyman on 10/22/2017.
 */

public interface BaseEvents {
    void showProgress();

    void hideProgress();

    void showErrorMessage(String failurReason);
}
