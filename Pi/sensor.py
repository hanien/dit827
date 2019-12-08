import board #depends on rpi.gpio and says there is not Rpi module
import digitalio
import busio
import adafruit_bme280 as bme280 
import python_tsl2591 as tsl2591 
import RPi.GPIO as GPIO #Can only be run on a raspberry pi
import adafruit_mcp3xxx.mcp3008 as MCP
from adafruit_mcp3xxx.analog_in import AnalogIn
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

#microphone setup
spi = busio.SPI(clock=board.SCK, MISO=board.MISO, MOSI=board.MOSI)
cs = digitalio.DigitalInOut(board.D5)
mcp = MCP.MCP3008(spi, cs, ref_voltage=3.3)
channel = AnalogIn(mcp, MCP.P0)

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
        print(distance)
        time.sleep(0.005)
        #init data required for sampling sound
        start_milli = int(round(time.time() * 1000))
        peak_to_peak = 0
        signal_max = 0
        signal_min = 32768
        sample_window = 150
        
        
        print("counter: "+ str(nonBoard.counter))
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
        
        #loop that samples raw analog data
        while (int(round(time.time()*1000))) - start_milli < sample_window:
            #init pins and sensor references
            spi = busio.SPI(clock=board.SCK, MISO=board.MISO, MOSI=board.MOSI)
            cs = digitalio.DigitalInOut(board.D5)
            mcp = MCP.MCP3008(spi, cs, ref_voltage=3.3)
            channel = AnalogIn(mcp, MCP.P0)
            #read audio voltage to be sampled
            sample = channel.value
            if sample < signal_min:
                if sample > signal_max:
                    signal_max = sample
                elif sample < signal_min:
                    signal_min = sample
        
            #calculate raw sound data
        peak_to_peak = signal_max - signal_min
        peak_to_peak = peak_to_peak / 32768
        if peak_to_peak != 0:
            peak_to_peak = -20*math.log(math.fabs(peak_to_peak), 10)
        else:
            #TODO: behavior for invalid values when sensor is not working properly
            peak_to_peak = 0
        
        nonBoard.sound_total += peak_to_peak
        nonBoard.counter+=1
        
    print("sound: " +str(nonBoard.sound_total))
        
    time.sleep(0.1)

