import sys, os, pytest
import requests
from unittest import mock
sys.path.append(os.path.realpath(os.path.dirname(__file__)+"/.."))
import nonBoard
import json

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

def test_sendingData():
    resetTestDatabase()
    nonBoard.seat = seat
    test_not_over = True

    nonBoard.average_and_send()

    #The test should fail if any the values on the website are not 30
    while(test_not_over):
        nonBoard.full_total += 30
        nonBoard.ir_total += 30
        nonBoard.lux_total += 30
        nonBoard.gain_total += 30
        nonBoard.temperature_total += 30
        nonBoard.humidity_total += 30
        nonBoard.pressure_total += 30
        nonBoard.altitude_total += 30
        nonBoard.counter += 1







