package fewFirstTasks;

public class TwoNumbersArePositive {
    public static boolean twoArePositive(int a, int b, int c){
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
    public static boolean twoArePositive2(int... ints){
        int i = 0;
        for (int anInt : ints) {
            if(anInt>0){
                i+=1;
            }
        }
        return i==2;
    }
}
