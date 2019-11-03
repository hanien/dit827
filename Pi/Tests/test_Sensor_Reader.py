import sys, os, pytest
import requests
from unittest import mock
sys.path.append(os.path.realpath(os.path.dirname(__file__)+"/.."))
import nonBoard
import json

seat = "test"

def test_resetTestDatabase():
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


def test_calling_Sensor():
    nonBoard.temperature_total = 1
    assert nonBoard.temperature_total == 1

def test_sendingData():
    nonBoard.seat = seat
    test_not_over = True






