package com.company;

public class CornerFill {

    public CornerFill(int squareSize){
        int[] tab = new int[squareSize*squareSize];
        for(int i = 0 ; i < squareSize*squareSize; i++) {
            //System.out.println(tab[i] = cornerFillPositionXDown(i, squareSize,0));
            System.out.println(tab[i] = cornerFillPositionYDown(i, squareSize, 0));

        }
    }

    public int[] cornerFill(int[][] square){
        int n = square.length*square.length;
        int[] tab = new int[n];
        for(int i =0; i < n; i++){
            tab[i] = square[cornerFillPositionXDown(i,square.length,0)][cornerFillPositionYDown(i,square.length,0)];
        }
        return tab;
    }

    private int[][] cornerFillPosition(int elementNumber,int squareSize){
        int[][] position = new int[1][1];
        //if()
        //int col = elementNumber;
        return position;
    }

    private int n(int y ){
        return y;
    }

    private int e(int x) {
        return x;
    }

    private int s(int y){
        return y;
    }

    private int w(int x){
        return x;
    }

    private int cornerFillPositionXDown(int elementNumber,int squareSize, int i){

        if(elementNumber < 2 * squareSize - 1){
            if((elementNumber) < squareSize ){
                //System.out.print("down1 ");
                return i;
            }else{
                //System.out.print("down2 ");
                return elementNumber + 1 - squareSize + i;
            }
        }else {
            i++;
            return cornerFillPositionXUp(elementNumber - (2 * squareSize - 1), squareSize - 1,i);
        }
    }

    private int cornerFillPositionXUp(int elementNumber, int squareSize, int i){
        if(elementNumber < 2 * squareSize - 1){
            if((elementNumber) < squareSize){
                //System.out.print("up1 ");
                return -elementNumber + squareSize + i -1;
            }else{
                //System.out.print("up2 ");
                return i;
            }
        }else {
            i++;
            return cornerFillPositionXDown(elementNumber - (2 * squareSize - 1), squareSize - 1,i);
        }
    }
/////////////////////////
    private int cornerFillPositionYDown(int elementNumber, int squareSize, int i){

        if(elementNumber < 2 * squareSize - 1){
            if((elementNumber) < squareSize){
                System.out.print("down1 ");
                return elementNumber;
            }else{
                System.out.print("down2 ");
                return squareSize - 1 ;
            }
        }else {
            i++;
            return cornerFillPositionYUp(elementNumber - (2 * squareSize - 1), squareSize - 1,i);
        }

    }

    private int cornerFillPositionYUp(int elementNumber, int squareSize, int i){
        if(elementNumber < 2 * squareSize - 1){
            if((elementNumber) < squareSize ){
                System.out.print("up1 ");
                return squareSize - 1 ;
            }else{
                System.out.print("up2 ");
                return 2 * squareSize - elementNumber - 2;
            }
        }else {
            i++;
            return cornerFillPositionYDown(elementNumber - (2 * squareSize - 1), squareSize - 1,i);
        }
    }



}
