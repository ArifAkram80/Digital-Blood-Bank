package com.example.arif.digitalbloodbank;

import android.location.Location;

/**
 * Created by Arif on 11/21/2017.
 */

public class Temp_Data_Holder {

    private String user_name, user_password, user_email, user_phone_number, user_blood_group, user_gender;
    private Location user_location;


    Temp_Data_Holder(String Name, String Password, String Email, String Phone_Number, String Blood_Group, String Gender, Location location){

        this.user_name = Name;
        this.user_password = Password;
        this.user_email = Email;
        this.user_phone_number = Phone_Number;
        this.user_blood_group = Blood_Group;
        this.user_gender = Gender;
        this.user_location = location;
    }




    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_phone_number() {
        return user_phone_number;
    }

    public void setUser_phone_number(String user_phone_number) {
        this.user_phone_number = user_phone_number;
    }

    public String getUser_blood_group() {
        return user_blood_group;
    }

    public void setUser_blood_group(String user_blood_group) {
        this.user_blood_group = user_blood_group;
    }

    public String getUser_gender() {
        return user_gender;
    }

    public void setUser_gender(String user_gender) {
        this.user_gender = user_gender;
    }

    public Location getUser_location() {
        return user_location;
    }

    public void setUser_location(Location user_location) {
        this.user_location = user_location;
    }
}
