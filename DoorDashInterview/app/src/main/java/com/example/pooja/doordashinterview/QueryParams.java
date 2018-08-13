package com.example.pooja.doordashinterview;

public class QueryParams {

    double lat,lng;
    String offset,limit;

    public QueryParams(double lat,double lng,String offset,String limit)
    {
        this.lat = lat;
        this.lng = lng;
        this.offset = offset;
        this.limit = limit;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public String getLimit() {
        return limit;
    }

    public String getOffset() {
        return offset;
    }

    public void setOffset(String offset) {
        this.offset = offset;
    }
}
