package com.company;

public class TwoNumbersArePositive {
    public TwoNumbersArePositive(){

    }
    private boolean twoArePositive(int a, int b, int c){
        int i = 0;
        if(a>0){
            i++;
        }
        if(b>0){
            i++;
        }
        if(c>0){
            i++;
        }
        if(i == 2){
            return true;
        }
        return false;
    }
}
