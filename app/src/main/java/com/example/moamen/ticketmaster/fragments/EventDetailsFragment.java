package com.example.moamen.ticketmaster.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moamen.ticketmaster.R;
import com.example.moamen.ticketmaster.activities.MainActivity;
import com.example.moamen.ticketmaster.interfaces.DetailsEvents;
import com.example.moamen.ticketmaster.interfaces.SearchEvents;
import com.example.moamen.ticketmaster.models.Event;
import com.example.moamen.ticketmaster.models.Events;
import com.example.moamen.ticketmaster.utils.EventsAdapter;
import com.example.moamen.ticketmaster.viewmodel.DetailsViewModel;
import com.example.moamen.ticketmaster.viewmodel.SearchEventsViewModel;
import com.squareup.picasso.Picasso;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;

public class EventDetailsFragment extends Fragment implements DetailsEvents {
    @BindView(R.id.eventImage) ImageView eventImage;
    @BindView(R.id.eventName) TextView eventName;
    @BindView(R.id.eventDate) TextView eventDate;
    @BindView(R.id.detailsLayout) RelativeLayout detailsLayout;
    @BindView(R.id.priceLayout) RelativeLayout priceLayout;
    @BindView(R.id.addImage) ImageView addImage;
    @BindView(R.id.eventInfo) TextView eventInfo;
    @BindView(R.id.addToMarked) TextView addToMarked;
    @BindView(R.id.pleaseNote) TextView pleaseNote;
    @BindView(R.id.priceType) TextView priceType;
    @BindView(R.id.priceCurrency) TextView priceCurrency;
    @BindView(R.id.minPrice) TextView minPrice;
    @BindView(R.id.maxPrice) TextView maxPrice;

    private DetailsViewModel detailsViewModel;
    private ProgressDialog myProgressDialog;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Typeface font;
    private Events event;
    private Event eventItem;
    private boolean added;
    private String url;

    public EventDetailsFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.event_details, container, false);
        ButterKnife.bind(this , view);
        detailsViewModel = new DetailsViewModel(this , getActivity());
        font = Typeface.createFromAsset(getActivity().getAssets(), "JF_Flat_regular.ttf");
        overrideFonts(getActivity() , detailsLayout);

        myProgressDialog = new ProgressDialog(getActivity());
        preferences = getActivity().getSharedPreferences("Show", Context.MODE_PRIVATE);
        editor = preferences.edit();
        event = new Events();

        if (MainActivity.offline)
            detailsViewModel.getEvent(preferences.getString("EventID",""));
        else
            detailsViewModel.getEventDetails(preferences.getString("EventID",""));

        return view;
    }

    @OnClick(R.id.addImage)
    public void addEvent(View view){
        if (MainActivity.offline){

        }else {
            eventItem = new Event();
            eventItem.setEventID(event.getId());
            eventItem.setEventName(event.getName());
            eventItem.setEventDate(event.getDates().getStart().getLocalTime());
            eventItem.setImage(detailsViewModel.getImagePath(url, event.getId()));
            eventItem.setInfo(event.getInfo());
            eventItem.setPleaseNote(event.getPleaseNote());
            if (event.getPriceRanges() != null) {
                eventItem.setPriceType(event.getPriceRanges().get(0).getType());
                eventItem.setPriceCurrency(event.getPriceRanges().get(0).getCurrency());
                eventItem.setMinPrice(event.getPriceRanges().get(0).getMin());
                eventItem.setMaxPrice(event.getPriceRanges().get(0).getMax());
            }
        }
        if (!added) {
            addImage.setImageDrawable(Objects.requireNonNull(getActivity()).getResources().getDrawable((R.drawable.fav_artical_active)));
            addToMarked.setText(R.string.added_to_marked_events);
            detailsViewModel.insertEvent(eventItem);
            added = true;
        } else{
            addImage.setImageDrawable(Objects.requireNonNull(getActivity()).getResources().getDrawable((R.drawable.fav_artical)));
            addToMarked.setText(R.string.add_to_marked_events);
            detailsViewModel.deleteEvent(eventItem);
            added = false;
        }
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
    public void getEventDetailsResponse(Events eventDetails) {
        if (eventDetails != null) {
            detailsViewModel.getEvent(preferences.getString("EventID",""));
            event = eventDetails;
            eventName.setText("Event Name : " + event.getName());
            eventDate.setText("Event Date : " + event.getDates().getStart().getLocalTime());
            eventInfo.setText("Info : " + event.getInfo());
            pleaseNote.setText("Please Note : " + event.getPleaseNote());

            for (int i=0 ; i < event.getImages().size() ;i++){
                if (preferences.getBoolean("TwoPane" , false)){
                    if (event.getImages().get(i).getUrl().contains("RETINA_PORTRAIT_16_9"))
                        url = event.getImages().get(i).getUrl();
                } else{
                    if (event.getImages().get(i).getUrl().contains("TABLET_LANDSCAPE_16_9"))
                        url = event.getImages().get(i).getUrl();
                }
            }
             Picasso.with(getActivity()).load(url).into(eventImage);

            if (event.getPriceRanges() != null) {
                priceLayout.setVisibility(View.VISIBLE);
                priceType.setText("Price Type : " + event.getPriceRanges().get(0).getType());
                priceCurrency.setText("Price Currency : " + event.getPriceRanges().get(0).getCurrency());
                minPrice.setText("Minimum Price : " + event.getPriceRanges().get(0).getMin());
                maxPrice.setText("Maximum Price  : " + event.getPriceRanges().get(0).getMax());
            }
        } else{
            Toast.makeText(getActivity(), "Error , pleas try again", Toast.LENGTH_SHORT).show();
            getActivity().onBackPressed();
        }
    }

    @Override
    public void getEventDB(Event event) {
        if (event != null) {
            added = true;
            addImage.setImageDrawable(Objects.requireNonNull(getActivity()).getResources().getDrawable((R.drawable.fav_artical_active)));
            addToMarked.setText(R.string.added_to_marked_events);
        }else {
            added = false;
            addImage.setImageDrawable(Objects.requireNonNull(getActivity()).getResources().getDrawable((R.drawable.fav_artical)));
            addToMarked.setText(R.string.add_to_marked_events);
        }

        if (MainActivity.offline) {
            if (event != null) {
                eventItem = event;
                eventName.setText("Event Name : " + event.getEventName());
                eventDate.setText("Event Date : " + event.getEventDate());
                eventInfo.setText("Info : " + event.getInfo());
                pleaseNote.setText("Please Note : " + event.getPleaseNote());
                eventImage.setImageBitmap(loadImageBitmap(getActivity().getApplicationContext(), event.getImage()));

                if (event.getPriceType() != null) {
                    priceLayout.setVisibility(View.VISIBLE);
                    priceType.setText("Price Type : " + event.getPriceType());
                    priceCurrency.setText("Price Currency : " + event.getPriceCurrency());
                    minPrice.setText("Minimum Price : " + event.getMinPrice());
                    maxPrice.setText("Maximum Price  : " + event.getMaxPrice());
                }
            }else{
                Toast.makeText(getActivity(), "Error , pleas try again", Toast.LENGTH_SHORT).show();
                getActivity().onBackPressed();
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
        Toast.makeText(getActivity(), failurReason, Toast.LENGTH_SHORT).show();
        getActivity().onBackPressed();
    }

    public Bitmap loadImageBitmap(Context context, String imageName) {
        Bitmap bitmap = null;
        FileInputStream fiStream;
        try {
            fiStream    = context.openFileInput(imageName);
            bitmap      = BitmapFactory.decodeStream(fiStream);
            fiStream.close();
        } catch (Exception e) {
            Log.d("saveImage", "Exception 3, Something went wrong!");
            e.printStackTrace();
        }
        return bitmap;
    }
}