package com.example.poultryapp;

public class Logs {
    String date, time, log;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public String getTime() {
        return time;
    }

    public String getLog() {
        return log;
    }

    public Logs(String date, String time, String log){
        this.log= log;
        this.time = time;
        this.date = date;
    }
}
