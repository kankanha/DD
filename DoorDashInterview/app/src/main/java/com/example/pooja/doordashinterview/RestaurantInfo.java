package com.example.pooja.doordashinterview;

import android.content.Intent;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.io.IOException;

public class RestaurantInfo extends AppCompatActivity implements LoaderManager.LoaderCallbacks<RestaurantDetailInfo> {

    TextView txtTitleName;
    TextView txtGenre;
    TextView txtPhoneNo;
    TextView txtRating;
    TextView txtError;
    TextView txtAddress;
    TextView txtFee;
    TextView txtOpenClosed;
    ImageView imgVw;

    String urlStr;
    private static final int LOADER_ID = 222;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant_info);

        txtTitleName = (TextView) findViewById(R.id.txtTileName);
        txtGenre = (TextView) findViewById(R.id.txtGenre);
        txtPhoneNo = (TextView) findViewById(R.id.phoneNo);
        txtRating = (TextView) findViewById(R.id.txtRating);
        txtAddress = (TextView) findViewById(R.id.address);
        txtFee = (TextView) findViewById(R.id.txtFee);
        txtOpenClosed = (TextView) findViewById(R.id.txtOpenClosed);
        txtError = (TextView) findViewById(R.id.errorMessage);
        imgVw = (ImageView) findViewById(R.id.imgRestVw);

        Intent intent = getIntent();
        urlStr = intent.getStringExtra(getString(R.string.URL));
        LoaderManager.LoaderCallbacks<RestaurantDetailInfo> detailCallback = this;
        getSupportLoaderManager().initLoader(LOADER_ID, null, detailCallback);
    }

    @Override
    public Loader<RestaurantDetailInfo> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<RestaurantDetailInfo>(this) {

            RestaurantDetailInfo restaurantDetailInfo = null;

            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                if (restaurantDetailInfo != null)
                    deliverResult(restaurantDetailInfo);
                else
                    forceLoad();
            }

            @Override
            public RestaurantDetailInfo loadInBackground() {
                try {
                    String restResponse = Utils.getHttpResponse(Utils.parseURL(urlStr));
                    RestaurantDetailInfo restaurantDetailInfo = null;
                    if (restResponse != null)
                        restaurantDetailInfo = Utils.GetRestaurantDetailedInfo(restResponse);
                    return restaurantDetailInfo;
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            public void deliverResult(RestaurantDetailInfo data) {
                restaurantDetailInfo = data;
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<RestaurantDetailInfo> loader, RestaurantDetailInfo data) {
        if (data == null)
            DisplayError();
        else
            SetDetailView(data);

    }

    private void DisplayError() {
        HideDetailView();
        txtError.setVisibility(View.VISIBLE);
    }

    private void SetDetailView(RestaurantDetailInfo data) {
        txtTitleName.setText(data.name);
        txtGenre.setText(data.description);
        txtRating.setText(getString(R.string.rating)+ " "+ data.rating);
        txtPhoneNo.setText(getString(R.string.phoneNo)+" " +data.phoneNo);
        txtOpenClosed.setText(getString(R.string.now)+ " "+ data.status);
        txtFee.setText(getString(R.string.deliveryFee)+" "+data.deliveryFee);
        txtAddress.setText(data.address);
        txtError.setVisibility(View.INVISIBLE);
        String imgPath = data.imgPath;
        if(imgPath!=null)
            Picasso.with(this).load(data.imgPath).into(imgVw);
        else
            Picasso.with(this).load(R.mipmap.ic_launcher).into(imgVw);

        ShowDetailView();
    }

    private void ShowDetailView() {
        txtTitleName.setVisibility(View.VISIBLE);
        txtPhoneNo.setVisibility(View.VISIBLE);
        txtRating.setVisibility(View.VISIBLE);
        txtGenre.setVisibility(View.VISIBLE);
    }

    private void HideDetailView() {
        txtTitleName.setVisibility(View.INVISIBLE);
        txtPhoneNo.setVisibility(View.INVISIBLE);
        txtRating.setVisibility(View.INVISIBLE);
        txtGenre.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onLoaderReset(Loader<RestaurantDetailInfo> loader) {

    }
}
