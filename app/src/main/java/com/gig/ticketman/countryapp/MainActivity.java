package com.gig.ticketman.countryapp;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Country>>, RecyclerItemTouchHelper.RecyclerItemTouchHelperListener  {
    private static final String LOG_TAG = CountryAdapter.class.getName();

    private static final int COUNTRY_LOADER_ID = 1;

    /**
     * URL for github data from the Github API dataset
     */
    private static final String COUNTRY_REQUEST_URL = "https://restcountries.eu/rest/v2/all";
    private CountryAdapter mAdapter;
    private RecyclerView listView;
    private RelativeLayout mainLayout;
    private List<Country> mData;
    private ProgressBar progressBar;
    private ImageView retryImg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainLayout = (RelativeLayout) findViewById(R.id.main_layout);

        // initializing the progress bar
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        // initializing the retry img
//        retryImg = (ImageView) findViewById(R.id.retry_img);
//        retryImg.setVisibility(View.GONE);






        listView = (RecyclerView) findViewById(R.id.list_view);
        listView.setHasFixedSize(true);
        listView.setLayoutManager(new LinearLayoutManager(this));
        listView.setItemAnimator(new DefaultItemAnimator());
        listView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));


        // adding item touch helper
        // only ItemTouchHelper.LEFT added to detect Right to Left swipe
        // if you want both Right -> Left and Left -> Right
        // add pass ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT as param
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(listView);


        fetchCountryList(COUNTRY_LOADER_ID);











    }

    private void fetchCountryList(int LOADER_ID){
        // Get a reference to the LoaderManager , in order to interact with the loaders
        LoaderManager loaderManager = getLoaderManager();

        Log.i(LOG_TAG, "TEST: calling initLoader()...");
        loaderManager.initLoader(LOADER_ID, null, this);
        if(isThereNetwork()){

            // Get a reference to the LoaderManager, in order to interact with loaders.
            loaderManager = getLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(COUNTRY_LOADER_ID, null, this);
        }
        else{
//            retryImg.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            Toast.makeText(MainActivity.this,"No internet connection",Toast.LENGTH_LONG).show();
        }
    }

    // an utility method to check the internet connection
    // returns true if there is network and false if no network connection
    private boolean isThereNetwork() {
        ConnectivityManager connMgr = (ConnectivityManager) MainActivity.this.
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

    @Override
    public Loader<List<Country>> onCreateLoader(int id, Bundle args) {
        Log.i(LOG_TAG, "TEST: onCreateLoader() called ...");
        // Create a new loader for the given URL
        return new CountryLoader(this, COUNTRY_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<Country>> loader, List<Country> data) {

        mData = new ArrayList<>();

        // If there is a valid list of {@link Country}s, then add them to the adapter's
        // data set. This will trigger the RecyclerView to update.
        if (data != null && !data.isEmpty()) {
            mData.addAll(data);
//            retryImg.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
            // Create a new adapter that takes a list of country data as input
            mAdapter = new CountryAdapter(this, mData);
            // Set the adapter on the {@link ListView}
            // so the list can be populated in the user interface
            listView.setAdapter(mAdapter);
        }
        else{
            Toast.makeText(MainActivity.this,"Error retrieving countries list",Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onLoaderReset(Loader<List<Country>> loader) {
        Log.i(LOG_TAG, "TEST: onLoaderReset() called...");
        // Loader reset, so we can clear out our existing data.
        mAdapter.clear();
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof CountryAdapter.MyViewHolder) {
            // get the removed item name to display it in snack bar
            String name = mData.get(viewHolder.getAdapterPosition()).getCountryName();

            // backup of removed item for undo purpose
            final Country deletedItem = mData.get(viewHolder.getAdapterPosition());
            final int deletedIndex = viewHolder.getAdapterPosition();

            // remove the item from recycler view
            mAdapter.removeItem(viewHolder.getAdapterPosition());

            // showing snack bar with Undo option
            Snackbar snackbar = Snackbar
                    .make(mainLayout, name + " removed from list!", Snackbar.LENGTH_LONG);
            snackbar.setAction("UNDO", new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // undo is selected, restore the deleted item
                    mAdapter.restoreItem(deletedItem, deletedIndex);
                }
            });
            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();
        }
    }
}
