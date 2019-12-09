package com.example.aptiv.Model.Helper;

import com.example.aptiv.Model.Classe.Zone;

public class DifferenceChecker {


    private static boolean movingToThreshold(boolean increasing, boolean aboveAverage){
        if((aboveAverage && !increasing) || (!aboveAverage && increasing))
        {
            return true;
        }
        else {
            return false;
        }
    }

    public static boolean checkTemp(boolean increasing, Zone checked, Zone other1, Zone other2){
        double checkedTemp = Double.parseDouble(checked.getTemperature());
        double otherTemp1= Double.parseDouble(other1.getTemperature());
        double otherTemp2 = Double.parseDouble(other2.getTemperature());

        double averageDiff = checkedTemp - (otherTemp2 + otherTemp1 + checkedTemp)/3;
        boolean aboveAverage = averageDiff > 0;


        double diffFirst = Math.abs(checkedTemp - otherTemp1);
        double diffSecond = Math.abs(checkedTemp - otherTemp2);
        double diffOthers = Math.abs(otherTemp1 - otherTemp2);

        if(diffOthers+2 < diffFirst && diffOthers+2 < diffSecond)
        {
            //If its decreasing and the zone is higher than the rest
            //Or if it is increasing and the zone is below the rest
            return movingToThreshold(increasing,aboveAverage);

        }
        return true;
    }

    public static boolean checkAirPressure(boolean increasing, Zone checked, Zone other1, Zone other2){
        double checkedPressure = Double.parseDouble(checked.getPressure());
        double otherPressure1= Double.parseDouble(other1.getPressure());
        double otherPressure2 = Double.parseDouble(other2.getPressure());

        double averageDiff = checkedPressure - (otherPressure1 + otherPressure2 + checkedPressure)/3;
        boolean aboveAverage = averageDiff > 0;

        double diffFirst = Math.abs(checkedPressure - otherPressure1);
        double diffSecond = Math.abs(checkedPressure - otherPressure2);
        double diffOthers = Math.abs(otherPressure1 - otherPressure2);

        if(diffOthers+5 < diffFirst && diffOthers+5 < diffSecond)
        {
            return movingToThreshold(increasing,aboveAverage);
        }
        return true;
    }

    public static boolean checkLux(boolean increasing, Zone checked, Zone other1, Zone other2){
        double checkedIr = Double.parseDouble(checked.getIr());
        double otherIr1= Double.parseDouble(other1.getIr());
        double otherIr2 = Double.parseDouble(other2.getIr());

        double averageDiff = checkedIr - (otherIr1 + checkedIr + otherIr2)/3;
        boolean aboveAverage = averageDiff > 0;

        double diffFirst = Math.abs(checkedIr - otherIr1);
        double diffSecond = Math.abs(checkedIr - otherIr2);
        double diffOthers = Math.abs(otherIr1 - otherIr2);

        if(diffOthers+15 < diffFirst && diffOthers+15 < diffSecond)
        {
            return movingToThreshold(increasing,aboveAverage);
        }
        return true;
    }

    public static boolean checkSound(boolean increasing,Zone checked, Zone other1, Zone other2){
        double checkedSound = Double.parseDouble(checked.getSound());
        double otherSound1= Double.parseDouble(other1.getSound());
        double otherSound2 = Double.parseDouble(other2.getSound());

        double averageDiff = checkedSound - (checkedSound + otherSound1 + otherSound2)/3;
        boolean aboveAverage = averageDiff > 0;

        double diffFirst = Math.abs(checkedSound - otherSound1);
        double diffSecond = Math.abs(checkedSound - otherSound2);
        double diffOthers = Math.abs(otherSound1 - otherSound2);

        if(diffOthers+5 < diffFirst && diffOthers+5 < diffSecond)
        {
            return movingToThreshold(increasing,aboveAverage);
        }
        return true;
    }

    public static boolean checkHumidity(boolean increasing, Zone checked, Zone other1, Zone other2){
        double checkedHumidity = Double.parseDouble(checked.getHumidity());
        double otherHumidity1= Double.parseDouble(other1.getHumidity());
        double otherHumidity2 = Double.parseDouble(other2.getHumidity());

        double averageDiff = checkedHumidity - (otherHumidity2 + otherHumidity1 + checkedHumidity)/3;
        boolean aboveAverage = averageDiff > 0;

        double diffFirst = Math.abs(checkedHumidity - otherHumidity1);
        double diffSecond = Math.abs(checkedHumidity - otherHumidity2);
        double diffOthers = Math.abs(otherHumidity1 - otherHumidity2);

        if(diffOthers+5 < diffFirst && diffOthers+5 < diffSecond)
        {
            return movingToThreshold(increasing,aboveAverage);
        }
        return true;
    }
}
