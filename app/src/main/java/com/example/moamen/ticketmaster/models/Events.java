package com.example.moamen.ticketmaster.models;

import java.util.ArrayList;

public class Events
{
    private String id;
    private Dates dates;
    private String test;
    private String locale;
    private String name;
    private ArrayList<Images> images;
    private String type;
    private String url;
    private String pleaseNote;
    private String info;
    private ArrayList<PriceRanges> priceRanges;

    public ArrayList<PriceRanges> getPriceRanges() {
        return priceRanges;
    }

    public void setPriceRanges(ArrayList<PriceRanges> priceRanges) {
        this.priceRanges = priceRanges;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public Dates getDates ()
    {
        return dates;
    }

    public void setDates (Dates dates)
    {
        this.dates = dates;
    }

    public String getTest ()
    {
        return test;
    }

    public void setTest (String test)
    {
        this.test = test;
    }

    public String getLocale ()
    {
        return locale;
    }

    public void setLocale (String locale)
    {
        this.locale = locale;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public ArrayList<Images> getImages ()
    {
        return images;
    }

    public void setImages (ArrayList<Images> images)
    {
        this.images = images;
    }

    public String getType ()
    {
        return type;
    }

    public void setType (String type)
    {
        this.type = type;
    }

    public String getUrl ()
    {
        return url;
    }

    public void setUrl (String url)
    {
        this.url = url;
    }

    public String getPleaseNote ()
    {
        return pleaseNote;
    }

    public void setPleaseNote (String pleaseNote)
    {
        this.pleaseNote = pleaseNote;
    }

    public String getInfo ()
    {
        return info;
    }

    public void setInfo (String info)
    {
        this.info = info;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", dates = "+dates+", test = "+test+", locale = "+locale+", name = "+name+", images = "+images+", type = "+type+", url = "+url+"]";
    }
}
