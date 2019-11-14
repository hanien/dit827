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
sound_total = 0

seat = "back"
threadtime = 10.0
lock = threading.RLock()

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
    sound_total = 0
    gain_total = 0
    lux_total = 0
    ir_total = 0
    full_total = 0

sendThread = threading.Timer(10000.0, reset)


def average_and_send():
    print("averageSend Has been called")
    with lock:
        print("Sending lock has been released")
        global sendThread
        global threadtime

        sendThread = threading.Timer(threadtime, average_and_send)
        sendThread.start()

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
        r = requests.put("http://dit827aptiv.herokuapp.com/api/sensors/" + seat, data=json.dumps(req))
        reset()


def endThread():
    sendThread.cancel()
