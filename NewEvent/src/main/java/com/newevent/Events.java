package com.newevent;

import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Events {

    int id;
    String name,city;
    String username;
    String date;
    int price, amount;
    int id_user;

    public Events(int id, String name, String city, String date, int price, int amount, int id_user, String username) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.date = date;
        this.price = price;
        this.amount = amount;
        this.id_user = id_user;
        this.username = username;
    }

    @Override
    public String toString() {
        return "Events{" +
                "id=" + id +
                ", name='" + name + '\'';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }
}
