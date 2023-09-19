package com.company;

import java.util.ArrayList;

public class MaxMinArrays {


    public static int[] solve (int[] arr){
        int[] tmp = new int[arr.length];
        for(int i = 0; i < arr.length - 1; i++){
            int idMax = getIdOfMax(arr);
            int idMin = getIdOfMin(arr);
            tmp[i] = arr[idMax];
            tmp[i+1] = arr[idMin];
        }
        return tmp;
    }




    private static int getIdOfMax(int[] arr){
        int max = arr[0];
        for(int i = 1; i < arr.length; i++) {
            for (int j = i; j < arr.length; j++) {
                if (max <= arr[j]) {
                    max = arr[j];
                }
            }
        }
        return max;
    }
    private static int getIdOfMin(int[] arr){

        int min = arr[0];
        for(int i = 1; i < arr.length; i++) {
            for (int j = i; j < arr.length; j++) {
                if (min > arr[j]) {
                    min = arr[j];
                }
            }
        }
        return min;
    }
    private static void switchPlacesInTable(int[] arr, int firstPosition, int secondPosition){

    }
}
