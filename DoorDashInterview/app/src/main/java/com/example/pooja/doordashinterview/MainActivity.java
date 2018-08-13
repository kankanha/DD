package com.example.pooja.doordashinterview;


import android.content.Intent;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.pooja.doordashinterview.Data.RestaurantData;
import com.example.pooja.doordashinterview.Interface.RecyclerViewCLickListener;
import com.example.pooja.doordashinterview.List.RestaurantInfoAdapter;
import com.example.pooja.doordashinterview.List.RestaurantListItem;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderCallbacks<List<RestaurantListItem>>, RecyclerViewCLickListener {

    RestaurantInfoAdapter restaurantInfoAdapter;
    RecyclerView restaurantRecyclerVw;
    TextView txtErrorDisplay;
   // List<RestaurantListItem> restaurantItemList;
    private static final int LOADER_ID = 111;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        restaurantRecyclerVw = (RecyclerView) findViewById(R.id.recyclerVw);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        restaurantRecyclerVw.setLayoutManager(linearLayoutManager);
        restaurantRecyclerVw.setHasFixedSize(true);

        restaurantInfoAdapter = new RestaurantInfoAdapter(getApplicationContext(),this);
       // restaurantInfoAdapter.SetRecyclerViewOnClickListener(this);
        restaurantRecyclerVw.setAdapter(restaurantInfoAdapter);

        txtErrorDisplay = (TextView) findViewById(R.id.errorMessage);
        txtErrorDisplay.setVisibility(View.INVISIBLE);

       LoaderCallbacks<List<RestaurantListItem>> callback = MainActivity.this;
       getSupportLoaderManager().initLoader(LOADER_ID,null,callback);

    }


    @Override
    public Loader<List<RestaurantListItem>> onCreateLoader(int i, Bundle bundle) {
        return new AsyncTaskLoader<List<RestaurantListItem>>(this) {
            List<RestaurantListItem> restaurantItemList = null;

            @Override
            protected void onStartLoading() {
               // super.onStartLoading();
                if(restaurantItemList!=null)
                    deliverResult(restaurantItemList);
                else
                    forceLoad();

            }

            @Override
            public List<RestaurantListItem> loadInBackground() {
                URL queryURL = Utils.GetURL(RestaurantData.GetDefaultURLParams());
                try {
                    String restResponse = Utils.getHttpResponse(queryURL);
                    List<RestaurantListItem> restaurantListItems = null;
                    if(restResponse!=null)
                        restaurantListItems = Utils.GetRestaurantItems(restResponse);
                    return restaurantListItems;
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return null;
            }

            public void deliverResult(List<RestaurantListItem> data) {
                restaurantItemList = data;
                super.deliverResult(data);
            }


        };
    }

    @Override
    public void onLoadFinished(Loader<List<RestaurantListItem>> loader, List<RestaurantListItem> data) {
        restaurantInfoAdapter.PopulateRestaurantList(data);
        if (data ==null) {
            DisplayErrorView();
        } else {
            DisplayRestaurantView();
        }
    }



    @Override
    public void onLoaderReset(Loader<List<RestaurantListItem>> loader) {

    }


    @Override
    public void OnRecyclerItemClicked(int restID, int id) {

        String restaurantUrl = Utils.GetRestaurantURL(restID);
        Intent intent = new Intent(this, RestaurantInfo.class);
        intent.putExtra(getString(R.string.URL),restaurantUrl);
        startActivity(intent);

    }

    private void DisplayRestaurantView() {
        restaurantRecyclerVw.setVisibility(View.VISIBLE);
        txtErrorDisplay.setVisibility(View.INVISIBLE);
    }

    private void DisplayErrorView() {
        restaurantRecyclerVw.setVisibility(View.INVISIBLE);
        txtErrorDisplay.setVisibility(View.VISIBLE);
    }
}
