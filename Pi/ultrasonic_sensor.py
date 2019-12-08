import RPi.GPIO as GPIO
import time

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
try:
    while True:
        GPIO.output(TRIG, False)
        time.sleep(1)
        GPIO.output(TRIG,True)
        time.sleep(0.0001)
        GPIO.output(TRIG,False)
        GPIO.output(LED,GPIO.LOW)    
        while GPIO.input(ECHO) ==0:
            print ('NOT DETECTED!!')
            StartTime = time.time()
            
        while GPIO.input(ECHO) ==1:
            print ('DETECTED!')
            StopTime = time.time()
                
        TimeElapsed = StopTime - StartTime
        distance = TimeElapsed * 17150
        distance = round(distance, 2)
        if distance > 40:
            GPIO.output(LED,GPIO.HIGH)
        else:
            GPIO.output(LED,GPIO.LOW)
        print(distance)
        time.sleep(0.005)
except KeyboardInterrupt:
    print('ex')
    GPIO.cleanup()