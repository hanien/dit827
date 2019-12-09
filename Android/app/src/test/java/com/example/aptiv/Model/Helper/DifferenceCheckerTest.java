package com.example.aptiv.Model.Helper;

import com.example.aptiv.Model.Classe.Zone;

import org.junit.Test;

import org.junit.Test;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import static org.junit.Assert.*;

public class DifferenceCheckerTest {

    private Zone comparison1 = new Zone(Zone.ZoneName.PASSENGER, "20.0", "50.0", "0.0", "60.0", "0.0", "40.0", "900.0", "30.0", "0.0", "0.0", "21.0");
    private Zone comparison2 = new Zone(Zone.ZoneName.BACK, "20.0", "50.0", "0.0", "60.0", "0.0", "40.0", "900.0", "30.0", "0.0", "0.0", "21.0");

    private Zone targetSuccess = new Zone(Zone.ZoneName.DRIVER, "20.0", "50.0", "0.0", "60.0", "0.0", "40.0", "900.0", "30.0", "0.0", "0.0", "21.0");


    @Test
    public void TempDifference_Correct()
    {
        assertTrue(DifferenceChecker.checkTemp(targetSuccess, comparison1, comparison2));
    }

    @Test
    public void SoundDifference_Correct()
    {
        assertTrue(DifferenceChecker.checkSound(targetSuccess, comparison1, comparison2));
    }

    @Test
    public void HumidityDifference_Correct()
    {
        assertTrue(DifferenceChecker.checkHumidity(targetSuccess, comparison1, comparison2));
    }

    @Test
    public void PressureDifference_Correct()
    {
        assertTrue(DifferenceChecker.checkAirPressure(targetSuccess, comparison1, comparison2));
    }

    @Test
    public void LightDifference_Correct()
    {
        assertTrue(DifferenceChecker.checkLux(targetSuccess, comparison1, comparison2));
    }
    private Zone targetFailSmall = new Zone(Zone.ZoneName.DRIVER, "17.0", "44.0", "0.0", "60.0", "0.0", "24.0", "894.0", "24.0", "0.0", "0.0", "21.0");

    @Test
    public void TempDifference_Fail_Small()
    {
        assertFalse(DifferenceChecker.checkTemp(targetFailSmall, comparison1, comparison2));
    }

    @Test
    public void SoundDifference_Fail_Small()
    {
        assertFalse(DifferenceChecker.checkSound(targetFailSmall, comparison1, comparison2));
    }
    @Test
    public void HumidityDifference_Fail_Small()
    {
        assertFalse(DifferenceChecker.checkHumidity(targetFailSmall, comparison1, comparison2));
    }
    @Test
    public void PressureDifference_Fail_Small()
    {
        assertFalse(DifferenceChecker.checkAirPressure(targetFailSmall, comparison1, comparison2));
    }
    @Test
    public void LightDifference_Fail_Small()
    {
        assertFalse(DifferenceChecker.checkLux(targetFailSmall, comparison1, comparison2));
    }

    private Zone targetFailMed = new Zone(Zone.ZoneName.DRIVER, "14.0", "38.0", "0.0", "60.0", "0.0", "56.0", "882.0", "12.0", "0.0", "0.0", "21.0");

    @Test
    public void TempDifference_Fail_Med()
    {
        assertFalse(DifferenceChecker.checkTemp(targetFailMed, comparison1, comparison2));
    }

    @Test
    public void SoundDifference_Fail_Med()
    {
        assertFalse(DifferenceChecker.checkSound(targetFailMed, comparison1, comparison2));
    }
    @Test
    public void HumidityDifference_Fail_Med()
    {
        assertFalse(DifferenceChecker.checkHumidity(targetFailMed, comparison1, comparison2));
    }
    @Test
    public void PressureDifference_Fail_Med()
    {
        assertFalse(DifferenceChecker.checkAirPressure(targetFailMed, comparison1, comparison2));
    }
    @Test
    public void LightDifference_Fail_Med()
    {
        assertFalse(DifferenceChecker.checkLux(targetFailMed, comparison1, comparison2));
    }

    private Zone targetFailLarge = new Zone(Zone.ZoneName.DRIVER, "11.0", "32.0", "0.0", "60.0", "0.0", "0.0", "888.0", "18.0", "0.0", "0.0", "21.0");

    @Test
    public void TempDifference_Fail_Large()
    {
        assertFalse(DifferenceChecker.checkTemp(targetFailLarge, comparison1, comparison2));
    }

    @Test
    public void SoundDifference_Fail_Large()
    {
        assertFalse(DifferenceChecker.checkSound(targetFailLarge, comparison1, comparison2));
    }
    @Test
    public void HumidityDifference_Fail_Large()
    {
        assertFalse(DifferenceChecker.checkHumidity(targetFailLarge, comparison1, comparison2));
    }
    @Test
    public void PressureDifference_Fail_Large()
    {
        assertFalse(DifferenceChecker.checkAirPressure(targetFailLarge, comparison1, comparison2));
    }
    @Test
    public void LightDifference_Fail_Large()
    {
        assertFalse(DifferenceChecker.checkLux(targetFailLarge, comparison1, comparison2));
    }
}