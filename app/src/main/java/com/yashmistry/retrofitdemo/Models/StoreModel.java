package com.yashmistry.retrofitdemo.Models;

public class StoreModel {
    String name;
    String category;
    String address;

    public StoreModel() {
    }

    public StoreModel(String name, String category, String address) {
        this.name = name;
        this.category = category;
        this.address = address;
    }

    public StoreModel(StoreModel store) {
        this.address = store.getAddress();
        this.name = store.getName();
        this.category = store.getCategory();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
