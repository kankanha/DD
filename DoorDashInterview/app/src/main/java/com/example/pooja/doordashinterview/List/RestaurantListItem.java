package com.example.pooja.doordashinterview.List;

public class RestaurantListItem {

    int restId;
    String restImg;
    String restName;
    String restDescription;
    String restStatus;
    int restDeliveryFee;

    public RestaurantListItem(int idNo, String image,String name,String description,String status, int fee)
    {
        restId = idNo;
        restImg = image;
        restName = name;
        restDescription = description;
        restStatus = status;
        restDeliveryFee = fee;

    }


    public int getId() {
        return restId;
    }

    public void setId(int id) {
        this.restId = id;
    }

    public String getRestName() {
        return restName;
    }

    public void setRestName(String restName) {
        this.restName = restName;
    }

    public String getRestDescription() {
        return restDescription;
    }

    public void setRestDescription(String restDescription) {
        this.restDescription = restDescription;
    }

    public String getRestStatus() {
        return restStatus;
    }

    public void setRestStatus(String restStatus) {
        this.restStatus = restStatus;
    }

    public int getRestDeliveryFee() {
        return restDeliveryFee;
    }

    public void setRestDeliveryFee(int restDeliveryFee) {
        this.restDeliveryFee = restDeliveryFee;
    }

    public void setRestImg(String restImg) {
        this.restImg = restImg;
    }

    public String getRestImg()
    {
        return restImg;
    }
}
