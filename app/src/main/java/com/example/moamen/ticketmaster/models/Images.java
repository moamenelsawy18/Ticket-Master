package com.example.moamen.ticketmaster.models;

public class Images
{
    private String fallback;

    private String height;

    private String ratio;

    private String width;

    private String url;

    public String getFallback ()
    {
        return fallback;
    }

    public void setFallback (String fallback)
    {
        this.fallback = fallback;
    }

    public String getHeight ()
    {
        return height;
    }

    public void setHeight (String height)
    {
        this.height = height;
    }

    public String getRatio ()
    {
        return ratio;
    }

    public void setRatio (String ratio)
    {
        this.ratio = ratio;
    }

    public String getWidth ()
    {
        return width;
    }

    public void setWidth (String width)
    {
        this.width = width;
    }

    public String getUrl ()
    {
        return url;
    }

    public void setUrl (String url)
    {
        this.url = url;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [fallback = "+fallback+", height = "+height+", ratio = "+ratio+", width = "+width+", url = "+url+"]";
    }
}

