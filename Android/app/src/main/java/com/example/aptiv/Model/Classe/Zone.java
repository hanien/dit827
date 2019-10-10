package com.example.aptiv.Model.Classe;

public class Zone {
    private int Temperature;
    private int Humidity;
    private int Gain;
    private int Luminosity;
    private int Spectrum;
    private int Ir;


    public Zone(int _temp, int _humidity, int _gain, int _luminosity, int _spectrum, int _ir){
        this.Temperature = _temp;
        this.Humidity = _humidity;
        this.Gain = _gain;
        this.Luminosity = _luminosity;
        this.Spectrum = _spectrum;
        this.Ir = _ir;
    }


    public int getTemperature() {
        return Temperature;
    }

    public void setTemperature(int temperature) {
        Temperature = temperature;
    }

    public int getHumidity() {
        return Humidity;
    }

    public void setHumidity(int humidity) {
        Humidity = humidity;
    }

    public int getGain() {
        return Gain;
    }

    public void setGain(int gain) {
        Gain = gain;
    }

    public int getLuminosity() {
        return Luminosity;
    }

    public void setLuminosity(int luminosity) {
        Luminosity = luminosity;
    }

    public int getSpectrum() {
        return Spectrum;
    }

    public void setSpectrum(int spectrum) {
        Spectrum = spectrum;
    }

    public int getIr() {
        return Ir;
    }

    public void setIr(int ir) {
        Ir = ir;
    }
}
