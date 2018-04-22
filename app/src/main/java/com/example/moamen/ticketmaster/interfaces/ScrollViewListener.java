package com.example.moamen.ticketmaster.interfaces;


import com.example.moamen.ticketmaster.utils.ScrollViewExt;

/**
 * Created by MoaMeN on 4/21/2018.
 */


public interface ScrollViewListener {
    void onScrollChanged(ScrollViewExt scrollView,
                         int x, int y, int oldx, int oldy);
}
