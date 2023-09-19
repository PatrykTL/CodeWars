package com.company;

public class Pillars{

    public static int pillars(int numberOfPillars, int distanceBetween, int pillarWidth){
        if(numberOfPillars == 1){
            return 0;
        }
        return (numberOfPillars-2)*pillarWidth+distanceBetween*100*(numberOfPillars-1);
    }

}
