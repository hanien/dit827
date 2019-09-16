interface sensorReader{
   double getLux();
   double getLumen();
   double getLight();
   double getTemperature();
   double getPressure();
   double getHumidity();
   double getSound();
}

interface sensorReader2{
    double[] getSensorValues();
 }