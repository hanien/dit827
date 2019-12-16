# DIT827
___
# Raspberry Pi

### Requirements
* Install [python3](https://www.python.org/download/releases/3.0/) & [pip3](https://pip.pypa.io/en/stable/).
* Enable ```I2C``` interface from Pi's ```raspi-config```
* To check if the sensors are connected to I2C Bus, in the Terminal type: ```i2cdetect -y 1```
* Import Adafruit libraries:
  * To prepare the Raspberry pi board:
      ```sh
        $ sudo pip3 install Adafruit-Blinka
      ```
  * For Temperature, Humidity & Pressure [BME280](https://www.adafruit.com/product/2652) sensor:
      ```sh
        $ sudo pip3 install adafruit-circuitpython-bme280
      ```
      ```sh
        $ sudo pip3 install Adafruit-PlatformDetect
      ```
  * For Light, Full & Ir [TSL2591](https://www.adafruit.com/product/1980) sensor:
    ```sh
      $ sudo pip3 install python-tsl2591
    ```
  * For the Microphone [MAX9814](https://www.adafruit.com/product/1713) sensor:
      ```sh
        $ sudo pip3 install adafruit-circuitpython-mcp3xxx
      ```
      ```sh
        $ sudo pip3 install adafruit-circuitpython-mcp3xxxsudo 
      ```
  ##### NOTE: Make sure that you are using [python3](https://www.python.org/download/releases/3.0/) to run it & [pip3](https://pip.pypa.io/en/stable/) for installation
  
___

# Android


### Requirements
* Make sure you have [Java 8](https://www.oracle.com/technetwork/java/javase/overview/java8-2100321.html) installed
* Install [AndroidStudio](https://developer.android.com/studio) *use all default/standard options, unless you know what you're doing* 
  * *If you want to run the app on an android studio emulator please use emulator with 9.7 pixels.*
