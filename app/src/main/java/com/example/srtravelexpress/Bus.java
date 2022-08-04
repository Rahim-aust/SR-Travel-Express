package com.example.srtravelexpress;

public class Bus {
    String busId,from,to,date,times;
    public Bus()
    {

    }
    public Bus(String busId, String from, String to, String date, String times) {
        this.busId = busId;
        this.from = from;
        this.to = to;
        this.date = date;
        this.times = times;

    }

    public String getBusId() {
        return busId;
    }

    public void setBusId(String busId) {
        this.busId = busId;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return times;
    }

    public void setTime(String times) {
        this.times = times;
    }
}
