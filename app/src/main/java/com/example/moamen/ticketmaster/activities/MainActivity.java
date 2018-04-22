package com.example.moamen.ticketmaster.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NavigationRes;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.moamen.ticketmaster.R;
import com.example.moamen.ticketmaster.fragments.EventDetailsFragment;
import com.example.moamen.ticketmaster.fragments.MarkedEventsFragment;
import com.example.moamen.ticketmaster.fragments.SearchFragment;
import com.example.moamen.ticketmaster.interfaces.Navigator;
import com.example.moamen.ticketmaster.utils.ActivityUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements Navigator{

    @BindView(R.id.bottom_navigation) BottomNavigationView bottomNavigationView;
    @BindView(R.id.back) ImageView back;
    private MarkedEventsFragment eventsFragment;
    private SearchFragment searchFragment;
    private EventDetailsFragment eventDetailsFragment;
    private boolean twoPane ;
    public static boolean offline ;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        getSupportActionBar().hide();

        eventsFragment = new MarkedEventsFragment();
        searchFragment = new SearchFragment();
        preferences = this.getSharedPreferences("Show",Context.MODE_PRIVATE);
        editor = preferences.edit();

        ActivityUtils.replaceFragmentInActivity(getSupportFragmentManager(),
                searchFragment, R.id.fragmentFrame);

        twoPane = findViewById(R.id.eventDetailsFragment) != null;

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.search:
                        back.setVisibility(View.GONE);
                        offline = false;
                        ActivityUtils.replaceFragmentInActivity(getSupportFragmentManager(),
                                searchFragment, R.id.fragmentFrame);
                        break;
                    case R.id.marked:
                        back.setVisibility(View.GONE);
                        offline = true;
                        ActivityUtils.replaceFragmentInActivity(getSupportFragmentManager(),
                                eventsFragment, R.id.fragmentFrame);
                        break;

                    default:
                }
                return true;
            }
        });
    }

    @OnClick(R.id.back)
    public void back(View view){
        bottomNavigationView.setVisibility(View.VISIBLE);
        back.setVisibility(View.GONE);
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        bottomNavigationView.setVisibility(View.VISIBLE);
        back.setVisibility(View.GONE);
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
        bottomNavigationView.setVisibility(View.VISIBLE);
        back.setVisibility(View.GONE);
    }

    @Override
    public void navigateTo(String view) {
        bottomNavigationView.setVisibility(View.GONE);
        eventDetailsFragment = new EventDetailsFragment();
        editor.putBoolean("TwoPane" , twoPane);
        editor.commit();

        if (view.equals("details")) {
            if (twoPane) {
                ActivityUtils.replaceFragmentInActivity(getSupportFragmentManager(),
                        eventDetailsFragment, R.id.eventDetailsFragment);
            } else {
                back.setVisibility(View.VISIBLE);
                ActivityUtils.replaceFragmentInActivity(getSupportFragmentManager(),
                        eventDetailsFragment, R.id.fragmentFrame, "EventDetails");
            }
        }
    }
}
