package com.example.afinal;

import android.widget.EditText;
import java.io.Serializable;
import java.util.Date;

public class user implements Serializable {


    public String username;
    public String email;
    public String phone ;
    public String address ;
    public String nid ;
    public String gender ;

    public String getCarnumber() {
        return carnumber;
    }

    public void setCarnumber(String carnumber) {
        this.carnumber = carnumber;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getColors() {
        return colors;
    }

    public void setColors(String colors) {
        this.colors = colors;
    }

    public String password ;
    public String dob;
    public String carnumber;
    public String category;
    public String type;
    public String colors;

    public user(String carnumber, String category, String type, String colors) {

        this.carnumber = carnumber;
        this.category = category;
        this.type = type;
        this.colors = colors;
    }

    public user(String username, String email, String phone, String nid, String address, String password, String gender, String dob) {
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.nid = nid;
        this.address = address;
        this.gender = gender;
        this.password = password;
        this.dob = dob;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public String  getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }


}