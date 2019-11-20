package com.example.aptiv.Model.Classe;

import static java.lang.Float.parseFloat;

public class Zone {
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
    private Profile profile;

    public Zone(String temperature, String humidity, String gain, String luminosity, String full, String ir,
                String pressure , String sound, String altitude, String light,String lux){
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
        this.profile = new Profile("name",temperature,humidity,gain,luminosity,full,ir,pressure,sound,altitude,light,lux);
    }

    public Profile getProfile(){
        return this.profile;
    }

    public void setProfile(Profile profile){
        this.profile = profile;
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
        return lux;
    }

    public void setLight(String light) {
        this.light = light;
    }
}
