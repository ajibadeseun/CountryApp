package com.gig.ticketman.countryapp;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.List;

/**
 * Created by DAMILOLA on 8/31/2017.
 */

public class CountryLoader extends AsyncTaskLoader<List<Country>> {


    /** Tag for log messages */
    private static final String LOG_TAG = CountryLoader.class.getName();

    /** Query URL */
    private String mUrl;
    /**
     * Constructs a new {@link CountryLoader}.
     *
     * @param context of the activity
     * @param url to load data from
     *           "https://restcountries.eu/rest/v2/all"
     */

    public CountryLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    /**
     * This is on a background thread.
     */

    @Override
    public List<Country> loadInBackground() {
        Log.i(LOG_TAG,"TEST: loadInBackground() method called...");
        if (mUrl == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of countryList.
        List<Country> gitList = QueryUtils.fetchCountryData(mUrl);
        return gitList;
    }


    @Override
    protected void onStartLoading() {
        Log.i(LOG_TAG,"TEST: onStartLoading() method called...");
        forceLoad();
    }
}
