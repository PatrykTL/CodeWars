package com.company;

public class MaxMinArrays {

    public static int[] solve (int[] arr){
        for(int i = 0; i < arr.length - 1; i=i+2){
            System.out.println();
            int idMax = getIdOfMax(arr, i);
            switchPlacesInTable(arr, i, idMax);
            int idMin = getIdOfMin(arr, i+1);
            switchPlacesInTable(arr, i+1, idMin);
        }
        return arr;
    }

    private static int getIdOfMax(int[] arr, int startingPoint){
        int max = arr[startingPoint];
        int idMax = startingPoint;
            for (int j = startingPoint+1; j < arr.length; j++) {
                if (max <= arr[j]) {
                    max = arr[j];
                    idMax = j;
                }
            }
        return idMax;
    }

    private static int getIdOfMin(int[] arr, int startingPoint){

        int min = arr[startingPoint];
        int idMin = startingPoint;
            for (int j = startingPoint+1; j < arr.length; j++) {
                if (min > arr[j]) {
                    min = arr[j];
                    idMin = j;
                }
            }
        return idMin;
    }

    private static void switchPlacesInTable(int[] arr, int firstPosition, int secondPosition){
        int tmp = arr[firstPosition];
        arr[firstPosition] = arr[secondPosition];
        arr[secondPosition] = tmp;
    }
}
