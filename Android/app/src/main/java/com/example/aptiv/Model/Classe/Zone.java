package com.example.aptiv.Model.Classe;

public class Zone {
    public String temperature;
    private String humidity;
    private String gain;
    private String luminosity;
    private String full;
    private String ir;
    private String pressure;
    private String sound;
    private String altitiude;
    private String light;

    public Zone(String temperature, String humidity, String gain, String luminosity, String full, String ir,
                String pressure , String sound, String altitiude, String lightt){
        this.temperature = temperature;
        this.humidity = humidity;
        this.gain = gain;
        this.luminosity = luminosity;
        this.full = full;
        this.ir = ir;
        this.pressure = pressure;
        this.sound = sound;
        this.altitiude = altitiude;
        this.light = lightt;

    }
    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getHumidity() {
        return humidity;
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
        return ir;
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
        return altitiude;
    }

    public void setAltitiude(String altitiude) {
        this.altitiude = altitiude;
    }

    public String getLight() {
        return light;
    }

    public void setLight(String light) {
        this.light = light;
    }
}
