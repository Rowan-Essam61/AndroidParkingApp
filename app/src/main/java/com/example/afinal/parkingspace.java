package com.example.afinal;

import java.io.Serializable;

public class parkingspace implements Serializable {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int id;
    public String name;
    public String capacity;
    public String location ;
    public String price ;
    public String img ;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public parkingspace(String name, String capacity, String location, String price, int id, String img) {
        this.name = name;
        this.id=id;
        this.capacity = capacity;
        this.location = location;
        this.price = price;
        this.img = img;
    }


}
