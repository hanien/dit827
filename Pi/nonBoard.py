import requests
import json
import threading
import time

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