package com.example.pooja.doordashinterview;

public class RestaurantDetailInfo {

    String name;
    String description;
    String phoneNo;
    String rating;
    String imgPath;
    String address;
    String deliveryFee;
    String status;

    public RestaurantDetailInfo(String name,String description,String phoneNo,String rating,String imgPath,String address,String deliveryFee,String status)
    {
        this.name=name;
        this.description=description;
        this.phoneNo=phoneNo;
        this.rating=rating;
        this.imgPath = imgPath;
        this.address = address;
        this.deliveryFee=deliveryFee;
        this.status = status;
    }
}
