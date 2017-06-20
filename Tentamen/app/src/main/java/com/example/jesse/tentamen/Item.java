package com.example.jesse.tentamen;

import java.io.Serializable;

/**
 * Created by Jesse on 17-6-2017.
 */



public class Item implements Serializable {
    private String title, description, releaseyear, rentalduratiom, rentalrate, length, replacementcost, rating, specialfeatures, lastupdate;
    private int filmid, inventoryid;
    //private double rating;

    public Item(){

    }




    public Item(int filmid, int inventoryid, String title, String description, String releaseyear, String rentalduratiom, String rentalrate, String length, String replacementcost, String rating, String specialfeatures, String lastupdate ){
        this.title = title;
        this.description = description;
        this.releaseyear = releaseyear;
        this.rentalduratiom = rentalduratiom;
        this.rentalrate = rentalrate;
        this.length = length;
        this.replacementcost = replacementcost;
        this.rating = rating;
        this.specialfeatures = specialfeatures;
        this.lastupdate = lastupdate;
        this.filmid = filmid;
        this.inventoryid = inventoryid;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getReleaseyear() {
        return releaseyear;
    }
    public void setReleaseyear(String releaseyear) {
        this.releaseyear = releaseyear;
    }


    public String getRentalduratiom() {
        return rentalduratiom;
    }

    public void setRentalduratiom(String rentalduratiom) {
        this.rentalduratiom = rentalduratiom;
    }

    public String getRentalrate() {
        return rentalrate;
    }

    public void setRentalrate(String rentalrate) {
        this.rentalrate = rentalrate;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getReplacementcost() {
        return replacementcost;
    }

    public void setReplacementcost(String replacementcost) {
        this.replacementcost = replacementcost;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getSpecialfeatures() {
        return specialfeatures;
    }

    public void setSpecialfeatures(String specialfeatures) {
        this.specialfeatures = specialfeatures;
    }

    public String getLastupdate() {
        return lastupdate;
    }

    public void setLastupdate(String lastupdate) {
        this.lastupdate = lastupdate;
    }

    public int getFilmid() {
        return filmid;
    }

    public void setFilmid(int filmid) {
        this.filmid = filmid;
    }

    public int getInventoryid() {
        return inventoryid;
    }

    public void setInventoryid(int inventoryid) {
        this.inventoryid = inventoryid;
    }
}