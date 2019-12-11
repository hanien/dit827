package com.example.aptiv.Model.Class;

public class Mode {

    private String title;
    private String lux;
    private String temp;
    private String volume;
    private String airp;
    private String humidity;

    public Mode(String title, String lux, String temp, String volume, String airp, String humidity) {
        this.title = title;
        this.lux = lux;
        this.temp = temp;
        this.volume = volume;
        this.airp = airp;
        this.humidity = humidity;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLux() {
        return lux;
    }

    public void setLux(String lux) {
        this.lux = lux;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getAirp() {
        return airp;
    }

    public void setAirp(String airp) {
        this.airp = airp;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }
}

