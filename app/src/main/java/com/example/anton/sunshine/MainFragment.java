package com.example.anton.sunshine;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {
    private final static String API_KEY = "19a5918b5a98b8cac3bf4c9a3d028ad2";

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.forecastfragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_refresh) {
            FetchWeatherTask weatherTask = new FetchWeatherTask();
            weatherTask.execute();
            return true;
        }
        return super.onOptionsItemSelected(item);
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

    private class FetchWeatherTask extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... strings) {

            HttpURLConnection urlConnection = null;
            String forecastJsonStr;
            BufferedReader reader = null;

            try {
                String baseUrl = "http://api.openweathermap.org/data/2.5/forecast/daily?q=94043&mode=json&units=metric&cnt=7";
                String apiKey = "&APPID=" + API_KEY;
                URL url = new URL(baseUrl + apiKey);

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null){
                    return null;
                }

                reader = new BufferedReader(new InputStreamReader(inputStream));
                String currentLine;
                while ((currentLine = reader.readLine()) != null) {
                    buffer.append(currentLine);
                }

                if (buffer.length() == 0) {
                    return null;
                }
                forecastJsonStr = buffer.toString();
                Log.v("Data","Forecast JSON String: " + forecastJsonStr);

            } catch (IOException e){
                Log.e("FetchWeatherTask", "Error", e);
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();;
                }
                if (reader!=null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        Log.e("FetchWeatherTask","Error closing stream",e);
                    }
                }
            }

            return null;
        }
    }

}
