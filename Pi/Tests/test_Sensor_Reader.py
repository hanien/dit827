import sys, os, pytest
import requests
from unittest import mock
import json
import time
sys.path.append(os.path.realpath(os.path.dirname(__file__)+"/.."))
#import sensor 

import nonBoard

seat = "test"

reqJson = json.loads('{"altitude":"0.0","full":"0.0","gain":"0.0","humidity":"0.0","ir":"0.0","lux":"0.0","pressure":"0.0","sound":"0","temperature":"0.0"}')

def resetVariables():
    req = {
    "temperature": "0",
    "humidity": "0",
    "pressure": "0",
    "altitude": "0",
    "gain": "0",
    "sound": "o",
    "lux": "0",
    "ir": "0",
    "full": "0"
    }
    reqJson = json.dumps(req)
    nonBoard.threadtime = 3
    nonBoard.seat = seat

def updateSensorValue(value, first):
    nonBoard.full_total += value
    nonBoard.ir_total += value
    nonBoard.lux_total += value
    nonBoard.gain_total += value
    nonBoard.sound_total += value
    nonBoard.temperature_total += value
    nonBoard.humidity_total += value
    nonBoard.pressure_total += value
    nonBoard.altitude_total += value
    if(first != 0):
        nonBoard.counter += 1
    


def test_nonBoard_Stores_data():
    nonBoard.temperature_total = 1
    nonBoard.temperature_total += 3
    assert nonBoard.temperature_total == 4


def test_sendingData(monkeypatch):
    print("SendingDataTest begins \n")
    resetVariables()

    def fakePutRequest(path, data):
        global reqJson
        reqJson = json.loads(data)
        print("Mock called")
        class mockObj(object):
            content = ""
        mockRequest = mockObj
        return mockRequest

    monkeypatch.setattr(requests, "put", fakePutRequest)
    
    global reqJson
    value = 30

    nonBoard.average_and_send()
    #The test should fail if any the values on the website are not 30 or within the acceptable error range
    updateSensorValue(value,0)
    #This is the loop where the values are updated. Because most of this code is in the boardController we can't reuse it for this part.
    while(str(reqJson['lux']) == '0.0'):
        with nonBoard.lock:
            updateSensorValue(value, 1)
        time.sleep(0.5) #This is around the rate at which the values are updated in the board file
    nonBoard.endThread()

    #Here we get the data and see if it is accurate to what was inputted.
    print(str(reqJson))
    lux = float(reqJson['lux'])
    altitude = float(reqJson['altitude'])
    full = float(reqJson['full'])
    temperature = float(reqJson['temperature'])
    humidity = float(reqJson['humidity'])
    pressure = float(reqJson['pressure'])
    #sound = float(reqJson['sound'])
    gain = float(reqJson['gain'])
    ir = float(reqJson['ir'])

    totalSum = lux + gain + altitude + full + temperature + humidity + pressure + ir #+sound
    totalSum = totalSum/8 # 3 being the amount of values summed together
    
    print("SendingDataTest ends \n")
    assert(totalSum == value) #Being wrong with below 10% is counted as acceptable error. 





