package com.example.arif.digitalbloodbank.home;

/**
 * Created by Anik on 11/21/2017.
 */

public class user {
    String name;
    String email;
    String phone;
    String gender;
    String id;

    user()
    {

    }
    public user(String UserName, String UserEmail, String UserPhone, String UserGender,String id) {

        name = UserName;
        email = UserEmail;
        phone = UserPhone;
        gender = UserGender;
        this.id=id;

    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getGender() {
        return gender;
    }
    public String getId(){return  id;}

}
