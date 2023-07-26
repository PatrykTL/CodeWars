package com.company;

import java.util.ArrayList;
import java.util.List;

public class PegSolitaire {
    private String board;
    private char[] legalSymbols;
    private char[][] board2d;
    private List<int[]> solution;

    public PegSolitaire(String board){
        legalSymbols = new char[]{'O', '.', '_'};
        this.board = board;
        this.board2d = returnBoardAsA2DCharTable(board);
        printBoard();
    }

    public List<int[]> solve(String board) {
        solution = new ArrayList<>();

        return null;
    }

    public void printBoard(){
        System.out.println(this.board);
    }

    private char[][] returnBoardAsA2DCharTable(String board){
        int[] tmp = returnBoardSizes(board);
        int a = tmp[0];
        int b = tmp[1];
        int c = 0;
        int d = 0;
        System.out.println(a + " " + b);
        char[][] board2D = new char[a][b];
        for(int i = 0; i<board.length(); i++){
            if(board.charAt(i) != '\n'){
                if(isLegalSymbol(board.charAt(i))){
                    board2D[c][d] = board.charAt(i);
                    c++;
                }
            } else{
                c = 0;
                d++;
            }
        }
        return board2D;
    }

    private boolean isLegalSymbol(char symbol) {
        for (int i = 0; i < legalSymbols.length; i++) {
            if (symbol == legalSymbols[i]) {
                return true;
            }
        }
        return false;
    }

    private int[] returnBoardSizes(String board){
        int[] sizes = {0,1};
        for(int i  = 0; i<board.length() ; i++){
            if(board.charAt(i) == '\n'){
                sizes[1]++;
            }
        }
        for(int i = 0; i < board.length() ; i++ ){
            if(board.charAt(i) == '\n'){
                break;
            }
            sizes[0]++;
        }
        return sizes;
    }

    private int[] returnNextHolePosition(char[][] board2d, int[] currentPosition){
        for(int i = currentPosition[0]; i<board2d.length; i++){
            for (int j = currentPosition[1]; j<board2d[0].length;j++){
                if(board2d[i][j] == '.'){
                    return new int[]{i, j};
                }
            }
        }
        return new int[]{-1,-1};
    }

    //private List<int[]> listOfPossibleMoves(String board){
//
    //}

    //private int[] listOfAllHoles(){
//
    //}

    private boolean areThere2PegsUp(int[] position){
        if(position[1] - 1 < 0){
            return false;
        }
        if(position[1] - 2 < 0){
            return false;
        }
        if(board2d[position[0]][position[1]-1] == 'O' && board2d[position[0]]position[1]-2 == 'O')
return true;
return false;
    }

    private boolean areThere2PegsDown(int[] position){
    if(position[1] + 1 > board2d[0].length()){
return false;
}
    if(position[1] + 2 > board2d[0].length()){
return false;
}
    if(board2d[position[0]][position[1]+1] == 'O' && board2d[position[0]][position[1]+2] == 'O')
return true;
return false;
    }

    private boolean areThere2PegsLeft(int[] position){
if(position[0]-1 < 0){
return false;
}
if(position[0]-2 < 0)
    }

    private boolean areThere2PegsRight(){

    }





}
