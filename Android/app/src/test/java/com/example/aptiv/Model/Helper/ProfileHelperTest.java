package com.example.aptiv.Model.Helper;

import com.example.aptiv.Model.Classe.Zone;

import org.junit.Test;

import org.junit.Test;
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
        assertTrue(ProfileHelper.checkTemp(targetSuccess, comparison1, comparison2));
    }

    @Test
    public void SoundDifference_Correct()
    {
        assertTrue(ProfileHelper.checkSound(targetSuccess, comparison1, comparison2));
    }

    @Test
    public void HumidityDifference_Correct()
    {
        assertTrue(ProfileHelper.checkHumidity(targetSuccess, comparison1, comparison2));
    }

    @Test
    public void PressureDifference_Correct()
    {
        assertTrue(ProfileHelper.checkAirPressure(targetSuccess, comparison1, comparison2));
    }

    @Test
    public void LightDifference_Correct()
    {
        assertTrue(ProfileHelper.checkLux(targetSuccess, comparison1, comparison2));
    }
    private Zone targetFailSmall = new Zone(Zone.ZoneName.DRIVER, "17.0", "44.0", "0.0", "60.0", "0.0", "24.0", "894.0", "24.0", "0.0", "0.0", "21.0");

    @Test
    public void TempDifference_Fail_Small()
    {
        assertFalse(ProfileHelper.checkTemp(targetFailSmall, comparison1, comparison2));
    }

    @Test
    public void SoundDifference_Fail_Small()
    {
        assertFalse(ProfileHelper.checkSound(targetFailSmall, comparison1, comparison2));
    }
    @Test
    public void HumidityDifference_Fail_Small()
    {
        assertFalse(ProfileHelper.checkHumidity(targetFailSmall, comparison1, comparison2));
    }
    @Test
    public void PressureDifference_Fail_Small()
    {
        assertFalse(ProfileHelper.checkAirPressure(targetFailSmall, comparison1, comparison2));
    }
    @Test
    public void LightDifference_Fail_Small()
    {
        assertFalse(ProfileHelper.checkLux(targetFailSmall, comparison1, comparison2));
    }

    private Zone targetFailMed = new Zone(Zone.ZoneName.DRIVER, "14.0", "38.0", "0.0", "60.0", "0.0", "56.0", "882.0", "12.0", "0.0", "0.0", "21.0");

    @Test
    public void TempDifference_Fail_Med()
    {
        assertFalse(ProfileHelper.checkTemp(targetFailMed, comparison1, comparison2));
    }

    @Test
    public void SoundDifference_Fail_Med()
    {
        assertFalse(ProfileHelper.checkSound(targetFailMed, comparison1, comparison2));
    }
    @Test
    public void HumidityDifference_Fail_Med()
    {
        assertFalse(ProfileHelper.checkHumidity(targetFailMed, comparison1, comparison2));
    }
    @Test
    public void PressureDifference_Fail_Med()
    {
        assertFalse(ProfileHelper.checkAirPressure(targetFailMed, comparison1, comparison2));
    }
    @Test
    public void LightDifference_Fail_Med()
    {
        assertFalse(ProfileHelper.checkLux(targetFailMed, comparison1, comparison2));
    }

    private Zone targetFailLarge = new Zone(Zone.ZoneName.DRIVER, "11.0", "32.0", "0.0", "60.0", "0.0", "0.0", "888.0", "18.0", "0.0", "0.0", "21.0");

    @Test
    public void TempDifference_Fail_Large()
    {
        assertFalse(ProfileHelper.checkTemp(targetFailLarge, comparison1, comparison2));
    }

    @Test
    public void SoundDifference_Fail_Large()
    {
        assertFalse(ProfileHelper.checkSound(targetFailLarge, comparison1, comparison2));
    }
    @Test
    public void HumidityDifference_Fail_Large()
    {
        assertFalse(ProfileHelper.checkHumidity(targetFailLarge, comparison1, comparison2));
    }
    @Test
    public void PressureDifference_Fail_Large()
    {
        assertFalse(ProfileHelper.checkAirPressure(targetFailLarge, comparison1, comparison2));
    }
    @Test
    public void LightDifference_Fail_Large()
    {
        assertFalse(ProfileHelper.checkLux(targetFailLarge, comparison1, comparison2));
    }
}