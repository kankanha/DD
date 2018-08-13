package com.example.pooja.doordashinterview;

import com.example.pooja.doordashinterview.Data.RestaurantData;
import com.example.pooja.doordashinterview.List.RestaurantListItem;

import org.json.JSONException;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import static junit.framework.Assert.assertEquals;

public class UtilsTest {

   private final String testUrlStr = "https://api.doordash.com/v2/restaurant/?lat=37.422740&lng=-122.139956&offset=0&limit=50";
    @Test
    public void getURLNull() {
        Utils utils = new Utils();
        URL url = utils.GetURL(null);
        URL expected = null;
        assertEquals(url,expected);
    }

    @Test
    public void CheckDefaultParams()
    {
      QueryParams params =  RestaurantData.GetDefaultURLParams();
      assertEquals(params.lat,37.422740);
      assertEquals(params.lng,-122.139956);
      assertEquals(params.offset,"0");
      assertEquals(params.limit,"50");

    }


    @Test
    public void getRestaurantURL() {
         String urlStr =   Utils.GetRestaurantURL(0);
         String expected = "https://api.doordash.com/v2/restaurant/0";
         assertEquals(urlStr,expected);
    }

    @Test
    public void parseURL() throws MalformedURLException {
        String urlStr =   Utils.GetRestaurantURL(0);
        URL url = Utils.parseURL(urlStr);
        URL expec = Utils.parseURL("https://api.doordash.com/v2/restaurant/0");
        assertEquals(url,expec);
    }

    @Test
    public void getHttpResponse() {
     //   HttpResponse httpResponse = mock(HttpResponse.class);
    }

    @Test
    public void getRestaurantItems() throws JSONException {
        List<RestaurantListItem> restaurantListItemList =  Utils.GetRestaurantItems(null);
        List<RestaurantListItem> expected= null;
       assertEquals(expected,restaurantListItemList);
    }

    @Test
    public void getRestaurantDetailedInfo() throws JSONException {
      RestaurantDetailInfo restaurantDetailInfo=  Utils.GetRestaurantDetailedInfo(null);
      RestaurantDetailInfo expeted = null;
      assertEquals(expeted,restaurantDetailInfo);
    }
}