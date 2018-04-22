package com.example.moamen.ticketmaster.models;

public class Start
{
    private String noSpecificTime;

    private String dateTime;

    private String dateTBA;

    private String timeTBA;

    private String localTime;

    private String dateTBD;

    private String localDate;

    public String getNoSpecificTime ()
    {
        return noSpecificTime;
    }

    public void setNoSpecificTime (String noSpecificTime)
    {
        this.noSpecificTime = noSpecificTime;
    }

    public String getDateTime ()
    {
        return dateTime;
    }

    public void setDateTime (String dateTime)
    {
        this.dateTime = dateTime;
    }

    public String getDateTBA ()
    {
        return dateTBA;
    }

    public void setDateTBA (String dateTBA)
    {
        this.dateTBA = dateTBA;
    }

    public String getTimeTBA ()
    {
        return timeTBA;
    }

    public void setTimeTBA (String timeTBA)
    {
        this.timeTBA = timeTBA;
    }

    public String getLocalTime ()
    {
        return localTime;
    }

    public void setLocalTime (String localTime)
    {
        this.localTime = localTime;
    }

    public String getDateTBD ()
    {
        return dateTBD;
    }

    public void setDateTBD (String dateTBD)
    {
        this.dateTBD = dateTBD;
    }

    public String getLocalDate ()
    {
        return localDate;
    }

    public void setLocalDate (String localDate)
    {
        this.localDate = localDate;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [noSpecificTime = "+noSpecificTime+", dateTime = "+dateTime+", dateTBA = "+dateTBA+", timeTBA = "+timeTBA+", localTime = "+localTime+", dateTBD = "+dateTBD+", localDate = "+localDate+"]";
    }
}
