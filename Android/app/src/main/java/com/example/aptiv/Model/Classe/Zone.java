package com.example.aptiv.Model.Classe;

import java.util.HashMap;

import static java.lang.Float.parseFloat;

public class Zone {

    public enum ZoneName {
        DRIVER,
        PASSENGER,
        MIDDLE,
        BACK
    }

    private ZoneName name;
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

    public Zone(ZoneName name, String temperature, String humidity, String gain, String luminosity, String full, String ir,
                String pressure , String sound, String altitude, String light,String lux){
        this.name = name;
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

    public ZoneName getName() { return name; }

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

    public HashMap<String, Double> getAll()
    {
        HashMap<String, Double> values = new HashMap<>(11);
        values.put("temperature", Double.parseDouble(this.getTemperature()));
        values.put("humidity", Double.parseDouble(this.getHumidity()));
        values.put("gain", Double.parseDouble(this.getGain()));
        values.put("luminosity", Double.parseDouble(this.getLuminosity()));
        values.put("full", Double.parseDouble(this.getFull()));
        values.put("ir", Double.parseDouble(this.getIr()));
        values.put("pressure", Double.parseDouble(this.getPressure()));
        values.put("sound", Double.parseDouble(this.getSound()));
        values.put("altitude", Double.parseDouble(this.getAltitiude()));
        values.put("light", Double.parseDouble(this.getLight()));
        values.put("lux", Double.parseDouble(this.getLux()));

        return values;
    }
    public void setAll(HashMap<String, Double> values){
        this.setTemperature((values.get("temperature")).toString());
        this.setHumidity((values.get("humidity")).toString());
        this.setGain((values.get("gain")).toString());
        this.setLuminosity((values.get("luminosity")).toString());
        this.setFull((values.get("full")).toString());
        this.setIr((values.get("ir")).toString());
        this.setPressure((values.get("pressure")).toString());
        this.setSound((values.get("sound")).toString());
        this.setAltitiude((values.get("altitude")).toString());
        this.setLight((values.get("light")).toString());
        this.setLux((values.get("lux")).toString());
    }
}
