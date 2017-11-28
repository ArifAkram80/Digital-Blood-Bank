package com.example.arif.digitalbloodbank.home;

/**
 * Created by Anik on 11/21/2017.
 */

public class user {
    String name;
    String id;
    String email;
    String phone;
    String blood, gender;
    String location;


    public user(String id, String UserName, String UserEmail, String UserPhone, String UserBG, String UserGender, String UserLocation ) {

        this.id = id;
        name = UserName;
        email = UserEmail;
        phone = UserPhone;
        blood = UserBG;
        gender = UserGender;
        location = UserLocation;

    }


    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getBlood() {
        return blood;
    }

    public String getGender() {
        return gender;
    }

    public String getLocation() {
        return location;
    }
}
