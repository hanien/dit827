import board
import digitalio
import busio
import time
import adafruit_bme280 as bme280
import python_tsl2591 as tsl2591
import RPi.GPIO as GPIO

i2c = busio.I2C(board.SCL, board.SDA)

# light sensor
tsl2591_sensor = tsl2591.tsl2591()
(full, ir) = tsl2591_sensor.get_full_luminosity() #full and ir spectrum
lux = tsl2591_sensor.calculate_lux(full, ir)
print("Lux: %0.3f" % lux)
print(tsl2591_sensor.get_current())

# temp, pressure, humidity sensor
"""bme280_sensor = bme280.Adafruit_BME280_I2C(i2c)
bme280_sensor.sea_level_pressure = 1013.7

print("\n Temperature: %0.1f C" % bme280_sensor.temperature)
print("\n Humidity: %0.1f %%" % bme280_sensor.humidity)
print("\n Pressure: %0.1f hPa" % bme280_sensor.pressure)
print("\n Altitude: %0.3f meters" % bme280_sensor.altitude)"""
