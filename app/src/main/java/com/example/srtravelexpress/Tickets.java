package com.example.srtravelexpress;

import java.util.ArrayList;

public class Tickets {
    public String transactionid,usermail,userphone,busid,from,to,date,cost,seatamount,username,time;
    private ArrayList<String> seats;
    public Tickets() {

    }

    public Tickets(String transactionid, String usermail, String userphone, String busid, String from, String to, String date, String cost, String seatamount, String username, String time, ArrayList<String> seats) {

        this.transactionid = transactionid;
        this.usermail = usermail;
        this.userphone = userphone;
        this.busid = busid;
        this.from = from;
        this.to = to;
        this.date = date;
        this.cost = cost;
        this.seatamount = seatamount;
        this.username = username;
        this.time = time;
        this.seats = seats;
    }

    public String getTransactionid() {
        return transactionid;
    }

    public void setTransactionid(String transactionid) {
        this.transactionid = transactionid;
    }

    public String getUsermail() {
        return usermail;
    }

    public void setUsermail(String usermail) {
        this.usermail = usermail;
    }

    public String getUserphone() {
        return userphone;
    }

    public void setUserphone(String userphone) {
        this.userphone = userphone;
    }

    public String getBusid() {
        return busid;
    }

    public void setBusid(String busid) {
        this.busid = busid;
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

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getSeatamount() {
        return seatamount;
    }

    public void setSeatamount(String seatamount) {
        this.seatamount = seatamount;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public ArrayList<String> getSeats() {
        return seats;
    }

    public void setSeats(ArrayList<String> seats) {
        this.seats = seats;
    }
}
