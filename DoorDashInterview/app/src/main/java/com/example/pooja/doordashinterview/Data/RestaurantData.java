package com.example.pooja.doordashinterview.Data;

import com.example.pooja.doordashinterview.QueryParams;

public class RestaurantData {

   private final static double defaultLat = 37.422740;
    private final static double defaultLng = -122.139956;
    private final static String defaultOffset = "0";
    private final static String defaultLimit = "50";


    public static QueryParams GetDefaultURLParams()
    {
        QueryParams params = new QueryParams(defaultLat,defaultLng,defaultOffset,defaultLimit);
        return params;
    }

}
