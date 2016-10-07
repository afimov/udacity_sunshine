package com.example.anton.sunshine;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {


    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        List<String> forecast = new ArrayList<>();
        forecast.add("Today-Sunny-88/63");
        forecast.add("Tomorrow-Foggy-70/46");
        forecast.add("Weds-Cloudy-72/63");
        forecast.add("Thurs-Rainy-64/51");
        forecast.add("Fri-Foggy-70/46");
        forecast.add("Sat-Sunny-76/68");

        ArrayAdapter<String> forecastAdapter =
                new ArrayAdapter<String>(
                        getActivity(),
                        R.layout.list_item_forecast,
                        R.id.list_item_forecast_textview,
                        forecast
                );

        ListView forecastListView = (ListView) rootView.findViewById(R.id.listview_forecast);

        forecastListView.setAdapter(forecastAdapter);

        return rootView;
    }
    

}
