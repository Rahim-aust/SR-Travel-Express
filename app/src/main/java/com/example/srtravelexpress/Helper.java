package com.example.srtravelexpress;

public class Helper {
    private String husername,hemail;

    public Helper() {

    }

    public Helper(String husername, String hemail) {
        this.husername = husername;
        this.hemail = hemail;
    }

    public String getHusername() {
        return husername;
    }

    public void setHusername(String husername) {
        this.husername = husername;
    }

    public String getHemail() {
        return hemail;
    }

    public void setHemail(String hemail) {
        this.hemail = hemail;
    }
}
