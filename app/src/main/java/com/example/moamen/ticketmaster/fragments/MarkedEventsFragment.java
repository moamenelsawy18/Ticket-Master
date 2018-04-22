package com.example.moamen.ticketmaster.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moamen.ticketmaster.R;
import com.example.moamen.ticketmaster.interfaces.EventSelected;
import com.example.moamen.ticketmaster.interfaces.MarkedEvents;
import com.example.moamen.ticketmaster.interfaces.Navigator;
import com.example.moamen.ticketmaster.interfaces.ScrollViewListener;
import com.example.moamen.ticketmaster.models.Event;
import com.example.moamen.ticketmaster.models.Events;
import com.example.moamen.ticketmaster.utils.EventsAdapter;
import com.example.moamen.ticketmaster.utils.OfflineEventsAdapter;
import com.example.moamen.ticketmaster.utils.ScrollViewExt;
import com.example.moamen.ticketmaster.viewmodel.MarkedEventsViewModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MarkedEventsFragment extends Fragment implements MarkedEvents, EventSelected {

    @BindView(R.id.markedResultRecycler) RecyclerView markedResultRecycler;
    @BindView(R.id.markedEvensLayout) RelativeLayout markedEvensLayout;

    private MarkedEventsViewModel markedEventsViewModel;
    private ProgressDialog myProgressDialog;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private OfflineEventsAdapter eventsAdapter;
    private LinearLayoutManager mLayoutManager;
    private Typeface font;
    private ArrayList<Event> events;
    private Navigator navigator;
    private static int page , oldPage = 0;
    private boolean loadedMore;
    View view;

    public MarkedEventsFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        navigator = (Navigator) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_events, container, false);
            ButterKnife.bind(this, view);
            markedEventsViewModel = new MarkedEventsViewModel(this, getActivity());
            font = Typeface.createFromAsset(getActivity().getAssets(), "JF_Flat_regular.ttf");
            overrideFonts(getActivity(), markedEvensLayout);

            mLayoutManager = new LinearLayoutManager(getActivity());
            markedResultRecycler.setLayoutManager(mLayoutManager);
            myProgressDialog = new ProgressDialog(getActivity());
            preferences = getActivity().getSharedPreferences("Show", Context.MODE_PRIVATE);
            editor = preferences.edit();

            events = new ArrayList<Event>();
        }
        markedEventsViewModel.getEventsDB();

        return view;
    }


    private void overrideFonts(final Context context, final View v) {
        try {
            if (v instanceof ViewGroup) {
                ViewGroup vg = (ViewGroup) v;
                for (int i = 0; i < vg.getChildCount(); i++) {
                    View child = vg.getChildAt(i);
                    overrideFonts(context, child);
                }
            } else if (v instanceof TextView) {
                ((TextView) v).setTypeface(font);
            }
        } catch (Exception e) {
        }
    }

    @Override
    public void getMarkedEvents(ArrayList<Event> eventsList) {
        hideProgress();
        if (eventsList != null) {
            if (eventsList.size() > 0) {
                events = eventsList;
                eventsAdapter = new OfflineEventsAdapter(getActivity(), events, this);
                    markedResultRecycler.setAdapter(eventsAdapter);
            } else {
                eventsAdapter = new OfflineEventsAdapter(getActivity(), eventsList, this);
                markedResultRecycler.setAdapter(eventsAdapter);
                Toast.makeText(getActivity(), "No Events Found", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void showProgress() {
        myProgressDialog.setMessage("Loading ...");
        myProgressDialog.setCancelable(false);
        myProgressDialog.show();
    }

    @Override
    public void hideProgress() {
        myProgressDialog.hide();
    }

    @Override
    public void onPause() {
        super.onPause();
        myProgressDialog.dismiss();
    }


    @Override
    public void showErrorMessage(String failurReason) {
        if (page>0)
            Toast.makeText(getActivity(), "No More Events", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(getActivity(), failurReason, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void selectEvent(String id) {
        editor.putString("EventID" , id);
        editor.commit();
        navigator.navigateTo("details");
    }

}