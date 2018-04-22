package com.example.moamen.ticketmaster.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.ArrayRes;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moamen.ticketmaster.R;
import com.example.moamen.ticketmaster.interfaces.EventSelected;
import com.example.moamen.ticketmaster.interfaces.Navigator;
import com.example.moamen.ticketmaster.interfaces.ScrollViewListener;
import com.example.moamen.ticketmaster.interfaces.SearchEvents;
import com.example.moamen.ticketmaster.models.Events;
import com.example.moamen.ticketmaster.utils.EventsAdapter;
import com.example.moamen.ticketmaster.utils.ScrollViewExt;
import com.example.moamen.ticketmaster.viewmodel.SearchEventsViewModel;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collection;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnEditorAction;

public class SearchFragment extends Fragment implements SearchEvents , EventSelected , ScrollViewListener {
    @BindView(R.id.searchResultRecycler) RecyclerView searchResultRecycler;
    @BindView(R.id.searchText) EditText searchEditText;
    @BindView(R.id.searchHint) TextView searchHint;
    @BindView(R.id.searchWordsLayout) RelativeLayout searchWordsLayout;
    @BindView(R.id.searchResultLayout) RelativeLayout searchResultLayout;
    @BindView(R.id.searchLayout) RelativeLayout searchLayout;
    @BindView(R.id.scrollViewExt) ScrollViewExt scrollView;

    private SearchEventsViewModel searchEventsModel;
    private ProgressDialog myProgressDialog;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private EventsAdapter eventsAdapter;
    private LinearLayoutManager mLayoutManager;
    private Typeface font;
    private ArrayList<Events> events;
    private Navigator navigator;
    private static int page , oldPage = 0;
    private boolean loadORChange;
    View view;

    public SearchFragment() {
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
    public void onResume() {
        super.onResume();
        page = oldPage = 0;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_search, container, false);
            ButterKnife.bind(this, view);
            searchEventsModel = new SearchEventsViewModel(this, getActivity());
            font = Typeface.createFromAsset(getActivity().getAssets(), "JF_Flat_regular.ttf");
            overrideFonts(getActivity(), searchLayout);

            mLayoutManager = new LinearLayoutManager(getActivity());
            searchResultRecycler.setLayoutManager(mLayoutManager);
            myProgressDialog = new ProgressDialog(getActivity());
            preferences = getActivity().getSharedPreferences("Show", Context.MODE_PRIVATE);
            editor = preferences.edit();
            scrollView.setScrollViewListener(this);

            events = new ArrayList<Events>();
        }
        searchEditText.setText("");
        return view;
    }


    @OnEditorAction(R.id.searchText)
    protected boolean search(int actionId) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            performSearch();
            loadORChange = true;
            page = oldPage = 0;
            if (eventsAdapter != null)
                eventsAdapter.clear ();

            events.clear();
            mLayoutManager.scrollToPositionWithOffset(0, 0);

            return true;
        }
        return false;
    }

    private void performSearch() {
        InputMethodManager in = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(searchEditText.getWindowToken(), 0);

        if (searchEditText.getText().toString().equals(""))
            Toast.makeText(getActivity(), "Enter The Search Words", Toast.LENGTH_SHORT).show();
        else
            searchEventsModel.getEvents(page , searchEditText.getText().toString());
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
    public void getSearchResultResponse(ArrayList<Events> eventsList) {
        if (eventsList != null) {
            if (eventsAdapter != null && loadORChange) {
                Log.w("@@##First" , "first");
                loadORChange = false;
                events = eventsList;
            } else {
                if (!events.isEmpty()) {
                    Log.w("@@##First" , "seconde");
                    events.addAll(eventsList);
                }
                else {
                    Log.w("@@##First" , "third");
                    events = eventsList;
                }
            }
            if (events.size() != 0) {
                searchResultLayout.setVisibility(View.VISIBLE);
                searchWordsLayout.setVisibility(View.GONE);
                if (page == 0) {
                    eventsAdapter = new EventsAdapter(getActivity(), events, this);
                    searchResultRecycler.setAdapter(eventsAdapter);
                } else
                    eventsAdapter.addAll(events);
            } else {
                searchResultLayout.setVisibility(View.GONE);
                searchWordsLayout.setVisibility(View.VISIBLE);
                searchHint.setText("No Events Found");
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
        else {
            searchResultLayout.setVisibility(View.GONE);
            searchWordsLayout.setVisibility(View.VISIBLE);
            searchHint.setText(failurReason);
        }
    }

    @Override
    public void selectEvent(String id) {
        editor.putString("EventID" , id);
        editor.commit();
        navigator.navigateTo("details");
    }

    @Override
    public void onScrollChanged(ScrollViewExt scrollView, int x, int y, int oldx, int oldy) {
        View view = (View) scrollView.getChildAt(scrollView.getChildCount() -1);
        int diff = (view.getBottom() - (scrollView.getHeight() + scrollView.getScrollY()));

        if (y == (scrollView.getChildAt(0).getMeasuredHeight() - scrollView.getMeasuredHeight())){
            loadORChange = false;
            loadMore();
        }
    }

    public void loadMore() {
        if(page != oldPage){
            oldPage = page;
        } else {
            page += 1;
            searchEventsModel.getEvents(page, searchEditText.getText().toString());
        }
    }
}