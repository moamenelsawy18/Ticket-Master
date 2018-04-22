package com.example.moamen.ticketmaster.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity (tableName = "event")
public class Event {
    @NonNull
    @PrimaryKey
    private String eventID;
    @ColumnInfo(name = "name")
    private String eventName;
    @ColumnInfo(name = "date")
    private String eventDate;
    @ColumnInfo(name = "image")
    private String image;
    @ColumnInfo(name = "info")
    private String info;
    @ColumnInfo(name = "note")
    private String pleaseNote;
    @ColumnInfo(name = "price")
    private String priceType;
    @ColumnInfo(name = "currency")
    private String priceCurrency;
    @ColumnInfo(name = "min")
    private String minPrice;
    @ColumnInfo(name = "max")
    private String maxPrice;

    public Event() {
    }

    public Event(String eventID, String eventName, String eventDate, String image, String info, String pleaseNote, String priceType, String priceCurrency, String minPrice, String maxPrice) {
        this.eventID = eventID;
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.image = image;
        this.info = info;
        this.pleaseNote = pleaseNote;
        this.priceType = priceType;
        this.priceCurrency = priceCurrency;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getPleaseNote() {
        return pleaseNote;
    }

    public void setPleaseNote(String pleaseNote) {
        this.pleaseNote = pleaseNote;
    }

    public String getPriceType() {
        return priceType;
    }

    public void setPriceType(String priceType) {
        this.priceType = priceType;
    }

    public String getPriceCurrency() {
        return priceCurrency;
    }

    public void setPriceCurrency(String priceCurrency) {
        this.priceCurrency = priceCurrency;
    }

    public String getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(String minPrice) {
        this.minPrice = minPrice;
    }

    public String getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(String maxPrice) {
        this.maxPrice = maxPrice;
    }
}
