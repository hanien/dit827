import board #depends on rpi.gpio and says there is not Rpi module
import digitalio
import busio
import adafruit_bme280 as bme280 
import python_tsl2591 as tsl2591 
import RPi.GPIO as GPIO #Can only be run on a raspberry pi
import nonBoard
import time
import math

GPIO.setmode(GPIO.BCM)
GPIO.setwarnings(False)
TRIG = 16
ECHO = 12
LED = 21
GPIO.setup(TRIG, GPIO.OUT)
GPIO.setup(ECHO, GPIO.IN)
GPIO.setup(LED, GPIO.OUT)
StartTime = time.time()
StopTime = time.time()

## SETUP

i2c = busio.I2C(board.SCL, board.SDA)
tsl2591_sensor = tsl2591.tsl2591()
bme280_sensor = bme280.Adafruit_BME280_I2C(i2c)
bme280_sensor.sea_level_pressure = 1013.7

nonBoard.average_and_send()
while True:
    with nonBoard.lock:
        GPIO.output(TRIG, False)
        time.sleep(0.5)
        GPIO.output(TRIG,True)
        time.sleep(0.0001)
        GPIO.output(TRIG,False)
        GPIO.output(LED,GPIO.LOW)    
        while GPIO.input(ECHO) ==0:
            #print ('NOT DETECTED!!')
            StartTime = time.time()
            
        while GPIO.input(ECHO) ==1:
            #print ('DETECTED!')
            StopTime = time.time()
                
        TimeElapsed = StopTime - StartTime
        #distance = TimeElapsed * 17150
        distance = round(TimeElapsed * 17150, 2)
        if distance > 50:
            GPIO.output(LED,GPIO.HIGH)
        else:
            GPIO.output(LED,GPIO.LOW)
        time.sleep(0.005)
        
        (full, ir) = tsl2591_sensor.get_full_luminosity() #full and ir spectrum
        lux = tsl2591_sensor.calculate_lux(full, ir)
        nonBoard.full_total += full
        nonBoard.ir_total += ir
        nonBoard.lux_total += lux
        nonBoard.gain_total += tsl2591_sensor.get_current()["gain"]
        nonBoard.temperature_total += bme280_sensor.temperature
        nonBoard.humidity_total += bme280_sensor.humidity
        nonBoard.pressure_total += bme280_sensor.pressure
        nonBoard.altitude_total += bme280_sensor.altitude
        nonBoard.counter+=1
                
        time.sleep(0.1)

