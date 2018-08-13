package com.example.pooja.doordashinterview;

import android.net.Uri;

import com.example.pooja.doordashinterview.List.RestaurantListItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Utils {

    //URL: https://api.doordash.com/v2/restaurant/?lat=37.422740&lng=-122.139956&offset=0&limit=50
    private static final String BASE_URL = "https://api.doordash.com/v2/restaurant";
    private final static String LAT_PARAM = "lat";
    private final static String LNG_PARAM = "lng";
    private final static String OFFSET_PARAM = "offset";
    private final static String LIMIT_PARAM = "limit";

    //JSON
    private final static String BUSINESS_PARAM = "business";
    private final static String ID_PARAM = "id";
    private final static String NAME_PARAM = "name";
    private final static String IMAGE_PARAM = "cover_img_url";
    private final static String DELIVERY_FEE_PARAM = "delivery_fee";
    private final static String DESCRIPTION_PARAM = "description";
    private final static String STATUS_PARAM = "status";

    private final static String PHONENO_PARAM = "phone_number";
    private final static String RATING_PARAM = "average_rating";
    private static final String STATUS_TYPE_PARAM = "status_type";
    private static final String DELIVERY_FEE_DETAILS = "delivery_fee_details";
    private static final String ORIGINAL_FEE = "original_fee";
    private static final String DISPLAY_STRING = "display_string";
    private static final String ADDRESS_PARAM = "address";
    private static final String PRINTABLE_ADDR_PARAM ="printable_address" ;


    public static URL GetURL(QueryParams params) {
        URL url = null;
        if(params!=null) {
            Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                    .appendQueryParameter(LAT_PARAM, String.valueOf(params.lat))
                    .appendQueryParameter(LNG_PARAM, String.valueOf(params.lng))
                    .appendQueryParameter(OFFSET_PARAM, params.offset)
                    .appendQueryParameter(LIMIT_PARAM, params.limit)
                    .build();
            try {
                if(builtUri!=null)
                url = new URL(builtUri.toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        return url;
    }

    public static String GetRestaurantURL(int position) {
        String restURL = BASE_URL+"/"+position;
        return restURL;
    }

    public static URL parseURL(String urlStr) throws MalformedURLException {
        URL url = null;
        if(urlStr!=null) {

            Uri uri = Uri.parse(urlStr);
            if(uri!=null)
              url = new URL(uri.toString());
            return url;
        }
        return url;
    }


    public static String getHttpResponse(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            if(urlConnection!=null) {
                InputStream in = urlConnection.getInputStream();

                Scanner scanner = new Scanner(in);
                scanner.useDelimiter("\\A");

                boolean hasInput = scanner.hasNext();
                if (hasInput) {
                    return scanner.next();
                } else {
                    return null;
                }
            }else return null;
        } finally {
            urlConnection.disconnect();
        }
    }


    public static List<RestaurantListItem> GetRestaurantItems(String response)
            throws JSONException {

        List<RestaurantListItem> restaurantListItems= null;
        if(response!=null) {
            JSONArray restaurantsArray = new JSONArray(response);
            restaurantListItems = new ArrayList<>();
            int length = restaurantsArray.length();
            for (int i = 0; i < length; i++) {
                int id;
                int deliveryFee;
                String name;
                String img;
                String status;
                String description;

                JSONObject restaurantItem = restaurantsArray.getJSONObject(i);
                if (restaurantItem.toString() != null) {

                    img = restaurantItem.getString(IMAGE_PARAM);
                    deliveryFee = restaurantItem.getInt(DELIVERY_FEE_PARAM);
                    description = restaurantItem.getString(DESCRIPTION_PARAM);
                    status = restaurantItem.getString(STATUS_PARAM);
                    id = restaurantItem.getInt(ID_PARAM);

                    JSONObject restBusiness = restaurantItem.getJSONObject(BUSINESS_PARAM);
                    name = restBusiness.getString(NAME_PARAM);

                    restaurantListItems.add(new RestaurantListItem(id, img, name, description, status, deliveryFee));
                }
            }
        }


        return restaurantListItems;
    }


    public static RestaurantDetailInfo GetRestaurantDetailedInfo(String restResponse) throws  JSONException {
        RestaurantDetailInfo restaurantDetailInfo = null;

        // JSONArray infoArray = new JSONArray(restResponse);

            JSONObject info = new JSONObject(restResponse);
            if(info.toString()!=null)
            {
            String phoneNo = info.getString(PHONENO_PARAM);
            String rating = info.getString(RATING_PARAM);
            String description = info.getString(DESCRIPTION_PARAM);
            String name = info.getString(NAME_PARAM);
            String imgPath = info.getString(IMAGE_PARAM);
            String statusType = info.getString(STATUS_TYPE_PARAM);

            JSONObject deliveryFeeDetails = info.getJSONObject(DELIVERY_FEE_DETAILS);
            JSONObject originalFee = deliveryFeeDetails.getJSONObject(ORIGINAL_FEE);
            String deliveryFee = originalFee.getString(DISPLAY_STRING);

            JSONObject address = info.getJSONObject(ADDRESS_PARAM);
            String displayAdd = address.getString(PRINTABLE_ADDR_PARAM);
            restaurantDetailInfo = new RestaurantDetailInfo(name, description, phoneNo, rating, imgPath, displayAdd,deliveryFee,statusType);

        }
        return restaurantDetailInfo;
    }
}
