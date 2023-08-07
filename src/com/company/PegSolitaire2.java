package com.company;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

public class PegSolitaire2 {
    private static char[][] myBoard;
    private ArrayDeque<ArrayList<int[]>> dequeOfMoves;
    private static List<int[]> listOfDirections;

    //////////////////// Board representation
    private void stringToMyBoard(String board){
        int maxRowNumber = boardNumberOfRows(board);
        int maxColNumber = boardColNumber(board);
        myBoard = new char[maxRowNumber][maxColNumber];
        int numberOfCurrentChar=0;
        for(int row = 0; row < maxRowNumber; row++){
            for(int col = 0; col < maxColNumber; col++){
                if(board.charAt(numberOfCurrentChar) == '_' || board.charAt(numberOfCurrentChar) == '.' || board.charAt(numberOfCurrentChar) == 'O') {
                    myBoard[row][col] = board.charAt(numberOfCurrentChar);
                }
                numberOfCurrentChar++;
            }
        }
    }
    private int boardNumberOfRows(String board){
        int numberOfRows = 0;
        for(int i = 0; i < board.length(); i++){
            if(board.charAt(i) == '\n'){
                numberOfRows++;
            }
        }
        return numberOfRows;
    }

    private int boardColNumber(String board){
        int numberOfCols = 0;
        for(int i = 0; i < board.length(); i++){
            if(board.charAt(i) == '\n'){
                return numberOfCols;
            }
            numberOfCols++;
        }
        return numberOfCols;
    }

    //////////////////// Functions testing is move is legal
    private boolean isMoveLegal(int[] positionOfPeg, int[] positionOfHole){
        if(!isMoveInsideBoard(positionOfPeg,positionOfHole)){
            return false;
        }
        if(myBoard[positionOfPeg[0]][positionOfPeg[1]] != 'O'){
            return false;
        }
        if(myBoard[positionOfHole[0]][positionOfHole[1]] != '.'){
            return false;
        }
        int[] pointBetween = pointInBetween(positionOfPeg,positionOfHole);
        return myBoard[pointBetween[0]][pointBetween[1]] != 'O';
    }

    private boolean isMoveInsideBoard(int[] positionOfPeg, int[] positionOfHole){
        if(positionOfHole[0] < myBoard.length
                && positionOfHole[1] < myBoard[0].length
                && positionOfPeg[0] < myBoard.length
                && positionOfPeg[1] < myBoard[0].length){
            if(positionOfHole[0] >= 0
                    && positionOfHole[1] >= 0
                    && positionOfPeg[0] >= 0
                    && positionOfPeg[1] >= 0){
                return true;
            }
        }
        return false;
    }

    private int[] pointInBetween(int[] firstPoint, int[] secondPoint){
        return new int[]{(firstPoint[0]+secondPoint[0])/2,(firstPoint[1]+secondPoint[1])/2};
    }

    //////////////////// Function making move
    private void makeMove(int[] positionOfPeg, int[] positionOfHole){
        myBoard[positionOfPeg[0]][positionOfPeg[1]] = '.';
        myBoard[positionOfHole[0]][positionOfHole[1]] = 'O';
        int[] pointBetween = pointInBetween(positionOfPeg,positionOfHole);
        myBoard[pointBetween[0]][pointBetween[1]] = '.';
    }

    //////////////////// Function unmaking move
    private void unmakeMove(int[] positionOfPeg, int[] positionOfHole){
        myBoard[positionOfPeg[0]][positionOfPeg[1]] = 'O';
        myBoard[positionOfHole[0]][positionOfHole[1]] = '.';
        int[] pointBetween = pointInBetween(positionOfPeg,positionOfHole);
        myBoard[pointBetween[0]][pointBetween[1]] = 'O';
    }

    //////////////////// Function testing if there is only one peg on myBoard
    private boolean isThereOnlyOnePeg(){
        int numberOfPegs = 0;
        for(int row = 0; row<myBoard.length; row++){
            for (int col = 0; col<myBoard[0].length; col++){
                if(myBoard[row][col] == 'O'){
                    numberOfPegs++;
                    if(numberOfPegs > 1){
                        return false;
                    }
                }
            }
        }
        return numberOfPegs == 1;
    }

    ////////////////////__ Functions solving the problem
    private void depthFirstSearchAlgorithm(){
        dequeOfMoves = new ArrayDeque<>();
        int[] startPosition = new int[]{0,0};
        while(!isThereOnlyOnePeg()){
            startPosition = findNextPegPosition(startPosition);
            if(startPosition != null) {
                int[] endPosition = returnEndPositionForFirstLegalMoveFromPosition(startPosition);
                if(endPosition != null) {
                    goDeeperInTreeBranch(startPosition,endPosition);
                }
            } else {
                switchTreeBranch();
            }
        }
    }
    private void goDeeperInTreeBranch(int[] startPosition,int[] endPosition){
        makeMove(startPosition, endPosition);
        dequeOfMoves.add(returnTwoPositionsAsMove(startPosition,endPosition));
    }
    private void switchTreeBranch(){
        ArrayList<int[]> lastMove = dequeOfMoves.pop();
        unmakeMove(lastMove.get(0),lastMove.get(1));
    }
    private int[] returnEndPositionForFirstLegalMoveFromPosition(int[] startPosition){
        for (int directionNumber = 0; directionNumber < listOfDirections.size(); directionNumber++) {
            int[] endPosition = findPointTwoFieldsInDirection(startPosition, directionNumber);
            if (isMoveLegal(startPosition, endPosition)) {
                return endPosition;
            }
        }
        return null;
    }
    private ArrayList<int[]> returnTwoPositionsAsMove(int[] startPosition, int[] endPosition){
        ArrayList<int[]> movePositions = new ArrayList<>();
        movePositions.add(startPosition);
        movePositions.add(endPosition);
        return movePositions;
    }
    private int[] findNextPegPosition(int[] position){
        for(int row = position[0]; row<myBoard.length; row++){
            for(int col = position[1]; col<myBoard[0].length; col++){
                if(myBoard[row][col] == 'O'){
                    return new int[]{row,col};
                }
            }
            position[1] = 0;
        }
        return null;
    }
    // Direction numbers: 0 - DOWN, 1 - RIGHT, 2 - LEFT, 3 - UP
    private int[] findPointTwoFieldsInDirection(int[] position, int directionNumber){
        return new int[]{position[0]+(listOfDirections.get(directionNumber)[0] * 2), position[1] + (listOfDirections.get(directionNumber)[1] * 2)};
    }
    // Direction numbers: 0 - DOWN, 1 - RIGHT, 2 - LEFT, 3 - UP
    private static void setListOfDirections(){
        listOfDirections.add(new int[]{1,0}); //DOWN
        listOfDirections.add(new int[]{0,1}); //RIGHT
        listOfDirections.add(new int[]{0,-1}); //LEFT
        listOfDirections.add(new int[]{-1,0}); //UP
    }

}
