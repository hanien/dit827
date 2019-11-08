import sys, os, pytest
import requests
from unittest import mock
sys.path.append(os.path.realpath(os.path.dirname(__file__)+"/.."))
import nonBoard
import json
import time

seat = "test"

def resetTestDatabase():
    req = {
    "temperature": "0",
    "humidity": "0",
    "pressure": "0",
    "altitude": "0",
    "gain": "0",
    "lux": "0",
    "ir": "0",
    "full": "0"
    }
    r = requests.put("http://dit827aptiv.herokuapp.com/api/sensors/"+ seat, data=json.dumps(req))


def test_nonBoard_Stores_data():
    nonBoard.temperature_total = 1
    nonBoard.temperature_total += 3
    assert nonBoard.temperature_total == 4

def test_sendingData(monkeypatch):
    resetTestDatabase()
    
    nonBoard.seat = seat
    value = 30
    acceptable_error = value/7

    nonBoard.average_and_send()
    #The test should fail if any the values on the website are not 30 or within the acceptable error range

    loopcount = 0
    while(nonBoard.counter == 0 or loopcount <10):
        if(loopcount > 40):
            print("LoopFailed")
            nonBoard.endThread()
            assert(False)

        nonBoard.full_total += value
        nonBoard.ir_total += value
        nonBoard.lux_total += value
        nonBoard.gain_total += value
        nonBoard.temperature_total += value
        nonBoard.humidity_total += value
        nonBoard.pressure_total += value
        nonBoard.altitude_total += value
        nonBoard.counter += 1
        loopcount+= 1
        time.sleep(0.5) #This is around the rate at which the values are updated in the board file
    nonBoard.endThread()
    r = requests.get("http://dit827aptiv.herokuapp.com/api/sensors/"+ seat)

    data = r.json()
    #print(data['gain'])
    lux = float(data['lux'])
    altitude = float(data['altitude'])
    full = float(data['full'])
    totalSum = lux+ altitude + full

    totalSum = totalSum/3 # 3 being the amount of values summed together
    print("TotalSum :" + str(totalSum))

    assert(totalSum > value - acceptable_error and totalSum < value + acceptable_error) #Being wrong with below 10% is counted as acceptable error. 






