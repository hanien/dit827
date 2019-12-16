package com.example.aptiv.Model.Helper;

import com.example.aptiv.Model.Class.Profile;
import com.example.aptiv.Model.Class.Zone;

import org.junit.Test;

import org.junit.Test;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import static org.junit.Assert.*;

public class ProfileHelperTest {

    private Zone comparison1 = new Zone(Zone.ZoneName.PASSENGER, "20.0", "50.0", "0.0", "60.0", "0.0", "40.0", "900.0", "30.0", "0.0", "0.0", "21.0");
    private Zone comparison2 = new Zone(Zone.ZoneName.BACK, "20.0", "50.0", "0.0", "60.0", "0.0", "40.0", "900.0", "30.0", "0.0", "0.0", "21.0");

    private Zone targetSuccess = new Zone(Zone.ZoneName.DRIVER, "20.0", "50.0", "0.0", "60.0", "0.0", "40.0", "900.0", "30.0", "0.0", "0.0", "21.0");


    @Test
    public void TempDifference_Correct()
    {
        assertTrue(ProfileHelper.checkTemp(false,targetSuccess, comparison1, comparison2));
    }

    @Test
    public void SoundDifference_Correct()
    {
        assertTrue(ProfileHelper.checkSound(false,targetSuccess, comparison1, comparison2));
    }

    @Test
    public void HumidityDifference_Correct()
    {
        assertTrue(ProfileHelper.checkHumidity(false,targetSuccess, comparison1, comparison2));
    }

    @Test
    public void PressureDifference_Correct()
    {
        assertTrue(ProfileHelper.checkAirPressure(false,targetSuccess, comparison1, comparison2));
    }

    @Test
    public void LightDifference_Correct()
    {
        assertTrue(ProfileHelper.checkLux(false,targetSuccess, comparison1, comparison2));
    }
    private Zone targetFailSmall = new Zone(Zone.ZoneName.DRIVER, "17.0", "44.0", "0.0", "60.0", "0.0", "24.0", "894.0", "24.0", "0.0", "0.0", "21.0");

    @Test
    public void TempDifference_Fail_Small()
    {
        assertFalse(ProfileHelper.checkTemp(false,targetFailSmall, comparison1, comparison2));
    }

    @Test
    public void SoundDifference_Fail_Small()
    {
        assertFalse(ProfileHelper.checkSound(false,targetFailSmall, comparison1, comparison2));
    }
    @Test
    public void HumidityDifference_Fail_Small()
    {
        assertFalse(ProfileHelper.checkHumidity(false,targetFailSmall, comparison1, comparison2));
    }
    @Test
    public void PressureDifference_Fail_Small()
    {
        assertFalse(ProfileHelper.checkAirPressure(false,targetFailSmall, comparison1, comparison2));
    }
    @Test
    public void LightDifference_Fail_Small()
    {
        assertFalse(ProfileHelper.checkLux(false,targetFailSmall, comparison1, comparison2));
    }

    private Zone targetFailMed = new Zone(Zone.ZoneName.DRIVER, "14.0", "38.0", "0.0", "60.0", "0.0", "56.0", "882.0", "12.0", "0.0", "0.0", "21.0");

    @Test
    public void TempDifference_Fail_Med()
    {
        assertFalse(ProfileHelper.checkTemp(false,targetFailMed, comparison1, comparison2));
    }

    @Test
    public void SoundDifference_Fail_Med()
    {
        assertFalse(ProfileHelper.checkSound(false,targetFailMed, comparison1, comparison2));
    }
    @Test
    public void HumidityDifference_Fail_Med()
    {
        assertFalse(ProfileHelper.checkHumidity(false,targetFailMed, comparison1, comparison2));
    }
    @Test
    public void PressureDifference_Fail_Med()
    {
        assertFalse(ProfileHelper.checkAirPressure(false,targetFailMed, comparison1, comparison2));
    }
    @Test
    public void LightDifference_Fail_Med()
    {
        assertFalse(ProfileHelper.checkLux(true,targetFailMed, comparison1, comparison2));
    }

    private Zone targetFailLarge = new Zone(Zone.ZoneName.DRIVER, "11.0", "32.0", "0.0", "60.0", "0.0", "0.0", "888.0", "18.0", "0.0", "0.0", "21.0");

    @Test
    public void TempDifference_Fail_Large()
    {
        assertFalse(ProfileHelper.checkTemp(false,targetFailLarge, comparison1, comparison2));
    }

    @Test
    public void SoundDifference_Fail_Large()
    {
        assertFalse(ProfileHelper.checkSound(false,targetFailLarge, comparison1, comparison2));
    }
    @Test
    public void HumidityDifference_Fail_Large()
    {
        assertFalse(ProfileHelper.checkHumidity(false,targetFailLarge, comparison1, comparison2));
    }
    @Test
    public void PressureDifference_Fail_Large()
    {
        assertFalse(ProfileHelper.checkAirPressure(false,targetFailLarge, comparison1, comparison2));
    }
    @Test
    public void LightDifference_Fail_Large()
    {
        assertFalse(ProfileHelper.checkLux(false,targetFailLarge, comparison1, comparison2));
    }

    @Test
    public void SampleTest_Pass()
    {
        Profile profile = new Profile("test", "10.0", "10.0", "10.0", "10.0", "10.0", "10.0", "10.0", "10.0", "10.0", "10.0", "10.0");
        Zone zone = new Zone(Zone.ZoneName.DRIVER, "10.0", "10.0", "10.0", "10.0", "10.0", "10.0", "10.0", "10.0", "10.0", "10.0", "10.0");

        Queue<Map> queue = new LinkedList<>();
        HashMap<String, Double> map = new HashMap<>(11);
        map.put("temperature", 0.0);
        map.put("humidity", 0.0);
        map.put("gain", 0.0);
        map.put("luminosity", 0.0);
        map.put("full", 0.0);
        map.put("ir", 0.0);
        map.put("pressure", 0.0);
        map.put("sound", 0.0);
        map.put("altitude", 0.0);
        map.put("light", 0.0);
        map.put("lux", 0.0);

        ProfileHelper.sampleZone(profile, zone, queue, map);
        ProfileHelper.sampleZone(profile, zone, queue, map);
        ProfileHelper.sampleZone(profile, zone, queue, map);
        ProfileHelper.sampleZone(profile, zone, queue, map);

        assertTrue(ProfileHelper.sampleZone(profile, zone, queue, map));
    }

    @Test
    public void sampleTest_Fail()
    {
        Profile profile = new Profile("test", "10.0", "10.0", "10.0", "10.0", "10.0", "10.0", "10.0", "10.0", "10.0", "10.0", "10.0");
        Zone zoneGood = new Zone(Zone.ZoneName.DRIVER, "10.0", "10.0", "10.0", "10.0", "10.0", "10.0", "10.0", "10.0", "10.0", "10.0", "10.0");
        Zone zoneBad = new Zone(Zone.ZoneName.DRIVER, "1000.0", "1000.0", "1000.0", "1000.0", "1000.0", "1000.0", "1000.0", "1000.0", "1000.0", "1000.0", "1000.0");

        Queue<Map> queue = new LinkedList<>();
        HashMap<String, Double> map = new HashMap<>(11);
        map.put("temperature", 0.0);
        map.put("humidity", 0.0);
        map.put("gain", 0.0);
        map.put("luminosity", 0.0);
        map.put("full", 0.0);
        map.put("ir", 0.0);
        map.put("pressure", 0.0);
        map.put("sound", 0.0);
        map.put("altitude", 0.0);
        map.put("light", 0.0);
        map.put("lux", 0.0);

        ProfileHelper.sampleZone(profile, zoneGood, queue, map);
        ProfileHelper.sampleZone(profile, zoneGood, queue, map);
        ProfileHelper.sampleZone(profile, zoneGood, queue, map);
        ProfileHelper.sampleZone(profile, zoneGood, queue, map);

        assertFalse(ProfileHelper.sampleZone(profile, zoneBad, queue, map));
    }

    @Test
    public void sampleTestFillStart_Fail(){
        Profile profile = new Profile("test", "10.0", "10.0", "10.0", "10.0", "10.0", "10.0", "10.0", "10.0", "10.0", "10.0", "10.0");
        Zone zoneGood = new Zone(Zone.ZoneName.DRIVER, "10.0", "10.0", "10.0", "10.0", "10.0", "10.0", "10.0", "10.0", "10.0", "10.0", "10.0");
        Zone zoneBad = new Zone(Zone.ZoneName.DRIVER, "1000.0", "1000.0", "1000.0", "1000.0", "1000.0", "1000.0", "1000.0", "1000.0", "1000.0", "1000.0", "1000.0");

        Queue<Map> queue = new LinkedList<>();
        HashMap<String, Double> map = new HashMap<>(11);
        map.put("temperature", 0.0);
        map.put("humidity", 0.0);
        map.put("gain", 0.0);
        map.put("luminosity", 0.0);
        map.put("full", 0.0);
        map.put("ir", 0.0);
        map.put("pressure", 0.0);
        map.put("sound", 0.0);
        map.put("altitude", 0.0);
        map.put("light", 0.0);
        map.put("lux", 0.0);

        ProfileHelper.sampleZone(profile, zoneGood, queue, map);
        ProfileHelper.sampleZone(profile, zoneGood, queue, map);
        ProfileHelper.sampleZone(profile, zoneGood, queue, map);
        ProfileHelper.sampleZone(profile, zoneGood, queue, map);
        ProfileHelper.sampleZone(profile, zoneGood, queue, map);

        assertFalse(ProfileHelper.sampleZone(profile, zoneBad, queue, map));
    }

    @Test
    public void sampleTestFillMid_Fail() {
        Profile profile = new Profile("test", "10.0", "10.0", "10.0", "10.0", "10.0", "10.0", "10.0", "10.0", "10.0", "10.0", "10.0");
        Zone zoneGood = new Zone(Zone.ZoneName.DRIVER, "10.0", "10.0", "10.0", "10.0", "10.0", "10.0", "10.0", "10.0", "10.0", "10.0", "10.0");
        Zone zoneBad = new Zone(Zone.ZoneName.DRIVER, "1000.0", "1000.0", "1000.0", "1000.0", "1000.0", "1000.0", "1000.0", "1000.0", "1000.0", "1000.0", "1000.0");

        Queue<Map> queue = new LinkedList<>();
        HashMap<String, Double> map = new HashMap<>(11);
        map.put("temperature", 0.0);
        map.put("humidity", 0.0);
        map.put("gain", 0.0);
        map.put("luminosity", 0.0);
        map.put("full", 0.0);
        map.put("ir", 0.0);
        map.put("pressure", 0.0);
        map.put("sound", 0.0);
        map.put("altitude", 0.0);
        map.put("light", 0.0);
        map.put("lux", 0.0);

        ProfileHelper.sampleZone(profile, zoneGood, queue, map);
        ProfileHelper.sampleZone(profile, zoneGood, queue, map);
        ProfileHelper.sampleZone(profile, zoneGood, queue, map);
        ProfileHelper.sampleZone(profile, zoneGood, queue, map);
        ProfileHelper.sampleZone(profile, zoneGood, queue, map);
        ProfileHelper.sampleZone(profile, zoneGood, queue, map);
        ProfileHelper.sampleZone(profile, zoneGood, queue, map);
        ProfileHelper.sampleZone(profile, zoneGood, queue, map);


        assertFalse(ProfileHelper.sampleZone(profile, zoneBad, queue, map));
    }
    public void sampleTestFillEnd_Fail() {
        Profile profile = new Profile("test", "10.0", "10.0", "10.0", "10.0", "10.0", "10.0", "10.0", "10.0", "10.0", "10.0", "10.0");
        Zone zoneGood = new Zone(Zone.ZoneName.DRIVER, "10.0", "10.0", "10.0", "10.0", "10.0", "10.0", "10.0", "10.0", "10.0", "10.0", "10.0");
        Zone zoneBad = new Zone(Zone.ZoneName.DRIVER, "1000.0", "1000.0", "1000.0", "1000.0", "1000.0", "1000.0", "1000.0", "1000.0", "1000.0", "1000.0", "1000.0");

        Queue<Map> queue = new LinkedList<>();
        HashMap<String, Double> map = new HashMap<>(11);
        map.put("temperature", 0.0);
        map.put("humidity", 0.0);
        map.put("gain", 0.0);
        map.put("luminosity", 0.0);
        map.put("full", 0.0);
        map.put("ir", 0.0);
        map.put("pressure", 0.0);
        map.put("sound", 0.0);
        map.put("altitude", 0.0);
        map.put("light", 0.0);
        map.put("lux", 0.0);

        ProfileHelper.sampleZone(profile, zoneGood, queue, map);
        ProfileHelper.sampleZone(profile, zoneGood, queue, map);
        ProfileHelper.sampleZone(profile, zoneGood, queue, map);
        ProfileHelper.sampleZone(profile, zoneGood, queue, map);
        ProfileHelper.sampleZone(profile, zoneGood, queue, map);
        ProfileHelper.sampleZone(profile, zoneGood, queue, map);
        ProfileHelper.sampleZone(profile, zoneGood, queue, map);
        ProfileHelper.sampleZone(profile, zoneGood, queue, map);
        ProfileHelper.sampleZone(profile, zoneGood, queue, map);
        ProfileHelper.sampleZone(profile, zoneGood, queue, map);
        ProfileHelper.sampleZone(profile, zoneGood, queue, map);

        assertFalse(ProfileHelper.sampleZone(profile, zoneBad, queue, map));
    }
}