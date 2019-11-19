package com.example.aptiv.Model.Classe;

import java.io.Serializable;

import static java.lang.Float.parseFloat;

public class Profile implements Serializable {
    private String ProfileName;
    private String temperature;
    private String humidity;
    private String gain;
    private String luminosity;
    private String full;
    private String ir;
    private String pressure;
    private String sound;
    private String altitude;
    private String light;
    private String lux;


    public Profile (String ProfileName,String temperature, String humidity, String gain, String luminosity, String full, String ir,
                    String pressure , String sound, String altitude, String light,String lux) {
        this.ProfileName = ProfileName;
        this.temperature = temperature;
        this.humidity = humidity;
        this.gain = gain;
        this.luminosity = luminosity;
        this.full = full;
        this.ir = ir;
        this.pressure = pressure;
        this.sound = sound;
        this.altitude = altitude;
        this.light = light;
        this.lux = lux;
    }

    public String getProfileName(){return this.ProfileName;}

    public void setProfileName(String profileName) {
        ProfileName = profileName;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getHumidity() {
        String hum = String.format("%.2f", parseFloat(humidity));
        return hum;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getGain() {
        return gain;
    }

    public void setGain(String gain) {
        this.gain = gain;
    }

    public String getLuminosity() {
        return luminosity;
    }

    public void setLuminosity(String luminosity) {
        this.luminosity = luminosity;
    }

    public String getFull() {
        return full;
    }

    public void setFull(String full) {
        this.full = full;
    }

    public String getIr() {
        String infra = String.format("%.2f", parseFloat(ir));
        return infra;
    }

    public void setIr(String ir) {
        this.ir = ir;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getSound() {
        return sound;
    }

    public void setSound(String sound) {
        this.sound = sound;
    }

    public String getAltitiude() {
        return altitude;
    }

    public void setAltitiude(String altitiude) {
        this.altitude = altitiude;
    }

    public String getLight() {
        return light;
    }

    public void setLight(String light) {
        this.light = light;
    }

    public String getLux() { return lux; }

    public void setLux(String lux) { this.lux = lux; }
}
