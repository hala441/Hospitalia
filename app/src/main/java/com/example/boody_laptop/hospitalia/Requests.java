package com.example.boody_laptop.hospitalia;

public class Requests {
    public String date, type;

    public Requests(){

    }

    public Requests (String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    public String getType() {
        return type;
    }
}
