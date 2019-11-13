import board #depends on rpi.gpio and says there is not Rpi module
import digitalio
import busio
import time
import adafruit_bme280 as bme280 
import python_tsl2591 as tsl2591 
import RPi.GPIO as GPIO #Can only be run on a raspberry pi
import requests
import json
import threading

counter = 1
temperature_total = 0
humidity_total = 0
pressure_total = 0
altitude_total = 0
gain_total = 0
lux_total = 0
ir_total = 0
full_total = 0

def reset():
    global counter
    global temperature_total
    global humidity_total
    global pressure_total
    global altitude_total
    global gain_total
    global lux_total
    global ir_total
    global full_total
    counter = 1
    temperature_total = 0
    humidity_total = 0
    pressure_total = 0
    altitude_total = 0
    gain_total = 0
    lux_total = 0
    ir_total = 0
    full_total = 0

def average_and_send():
    threading.Timer(10.0, average_and_send).start()
    
    ## Assign request data
    req = {
    "temperature": str(round(temperature_total/counter, 1)),
    "humidity": str(round(humidity_total/counter, 1)),
    "pressure": str(round(pressure_total/counter, 1)),
    "altitude": str(round(altitude_total/counter, 1)),
    "gain": str(round(gain_total/counter, 1)),
    "lux": str(round(lux_total/counter, 1)),
    "ir": str(round(ir_total/counter, 1)),
    "full": str(round(full_total/counter, 1))
    }
    r = requests.put("http://dit827aptiv.herokuapp.com/api/sensors/back", data=json.dumps(req))
    print(r.content)
    reset()
    
    
## SETUP

#i2c = busio.I2C(board.SCL, board.SDA)

average_and_send()
while True:
    (full, ir) = tsl2591_sensor.get_full_luminosity() #full and ir spectrum
    lux = tsl2591_sensor.calculate_lux(full, ir)
    full_total += full
    ir_total += ir
    lux_total += lux
    gain_total += tsl2591_sensor.get_current()["gain"]
    temperature_total += bme280_sensor.temperature
    humidity_total += bme280_sensor.humidity
    pressure_total += bme280_sensor.pressure
    altitude_total += bme280_sensor.altitude
    counter+=1

