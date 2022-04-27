package com.example.afinal;

public class registeration {


    public String name;
    public String slot;
    public String status ;
    public String checkin ;
    public String checkout ;
    public String date ;
    public String day ;
    public String user_id ;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlot() {
        return slot;
    }

    public void setSlot(String slot) {
        this.slot = slot;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCheckin() {
        return checkin;
    }

    public void setCheckin(String checkin) {
        this.checkin = checkin;
    }

    public String getCheckout() {
        return checkout;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setCheckout(String checkout) {
        this.checkout = checkout;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public registeration(String name, String slot, String status, String checkin, String checkout, String date, String day, String user_id) {
        this.name = name;
        this.slot = slot;
        this.status = status;
        this.checkin = checkin;
        this.checkout = checkout;
        this.date = date;
        this.day = day;
        this.user_id=user_id;
    }
}
