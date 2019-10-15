import board
import digitalio
import busio
import time
import adafruit_bme280 as bme280
import python_tsl2591 as tsl2591
import RPi.GPIO as GPIO
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
    threading.Timer(4.0, average_and_send).start()
    
    ## Assign request data
    req = {
    "temperature": str(temperature_total/counter),
    "humidity": str(humidity_total/counter),
    "pressure": str(pressure_total/counter),
    "altitude": str(altitude_total/counter),
    "gain": str(gain_total/counter),
    "lux": str(lux_total/counter),
    "ir": str(ir_total/counter),
    "full": str(full_total/counter)
    }
    r = requests.put("http://dit827aptiv.herokuapp.com/api/sensors/back", data=json.dumps(req))
    #r = requests.put("http://127.0.0.1:5000/api/sensors/driver", data=json.dumps(req))
    print(r.status_code)
    print(r.content)
    reset()
    
    
## SETUP
i2c = busio.I2C(board.SCL, board.SDA)

tsl2591_sensor = tsl2591.tsl2591()
bme280_sensor = bme280.Adafruit_BME280_I2C(i2c)
bme280_sensor.sea_level_pressure = 1013.7

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
