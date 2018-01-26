package com.example.arif.digitalbloodbank.home;

/**
 * Created by Arif on 1/17/2018.
 */

public class Post {
    String name;
    String details;
    String phone;
    String time;
    String date;
    String group;
    String loc;
    String userID;


    public Post() {

    }


    public Post(String name, String details, String phone, String time, String date, String group, String loc, String userID) {
        this.name = name;
        this.details = details;
        this.phone = phone;
        this.time = time;
        this.date = date;
        this.group = group;
        this.loc = loc;
        this.userID = userID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserID() {
        return userID;
    }

    public String getName() {
        return name;
    }

    public String getDetails() {
        return details;
    }

    public String getPhone() {
        return phone;
    }

    public String getTime() {
        return time;
    }

    public String getDate() {
        return date;
    }

    public String getGroup() {
        return group;
    }

    public String getLoc() {
        return loc;
    }
}
