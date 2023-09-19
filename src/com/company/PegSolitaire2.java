package com.company;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

public class PegSolitaire2 {
    private static char[][] myBoard;
    private static ArrayDeque<ArrayList<int[]>> dequeOfMoves;
    private static List<int[]> listOfDirections;

    public static List<int[]> solve(String board) {
        stringToMyBoard(board);
        setListOfDirections();
        depthFirstSearchAlgorithm();
        return changeMySolutionToRequiredSolution();
    }

    //////////////////// Function changing my solution to format required in task

    private static List<int[]> changeMySolutionToRequiredSolution(){
        ArrayList<int[]> requiredSolution = new ArrayList<>();
        dequeOfMoves.forEach(move->{
            int[] requiredMove = new int[]{myFieldIdToRequiredId(move.get(0)),myFieldIdToRequiredId(move.get(1))};
            requiredSolution.add(requiredMove);
        });
        return requiredSolution;
    }


    private static int myFieldIdToRequiredId(int[] myFieldId){
        int requiredId = 0;
        for(int row = 0; row < myBoard.length; row++){
            for(int col = 0; col < myBoard[0].length; col++){
                if(myBoard[row][col] == 'O' || myBoard[row][col] == '.'){
                    requiredId++;
                }
                if(row == myFieldId[0] && col == myFieldId[1]){
                    return requiredId;
                }
            }
        }
        return -1;
    }

    //////////////////// Board representation
    private static void stringToMyBoard(String board) {
        int maxRowNumber = boardNumberOfRows(board);
        int maxColNumber = boardColNumber(board);
        myBoard = new char[maxRowNumber][maxColNumber];
        int row = 0;
        int col = 0;
        for (int numberOfCurrentChar = 0; numberOfCurrentChar < board.length(); numberOfCurrentChar++) {
            if (board.charAt(numberOfCurrentChar) != '\n') {
                if (board.charAt(numberOfCurrentChar) == '_' || board.charAt(numberOfCurrentChar) == '.' || board.charAt(numberOfCurrentChar) == 'O') {
                    myBoard[row][col] = board.charAt(numberOfCurrentChar);
                }
                col++;
            } else {
                col = 0;
                row++;
            }
        }
    }

    private static int boardNumberOfRows(String board){
        int numberOfRows = 1;
        for(int i = 0; i < board.length(); i++){
            if(board.charAt(i) == '\n'){
                numberOfRows++;
            }
        }
        return numberOfRows;
    }

    private static int boardColNumber(String board){
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
    private static boolean isMoveLegal(int[] positionOfPeg, int[] positionOfHole){
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
        return myBoard[pointBetween[0]][pointBetween[1]] == 'O';
    }

    private static boolean isMoveInsideBoard(int[] positionOfPeg, int[] positionOfHole){
        if(positionOfPeg == null || positionOfHole == null){
            return false;
        }
        if(positionOfHole[0] < myBoard.length
                && positionOfHole[1] < myBoard[0].length
                && positionOfPeg[0] < myBoard.length
                && positionOfPeg[1] < myBoard[0].length){
            return positionOfHole[0] >= 0
                    && positionOfHole[1] >= 0
                    && positionOfPeg[0] >= 0
                    && positionOfPeg[1] >= 0;
        }
        return false;
    }

    private static int[] pointInBetween(int[] firstPoint, int[] secondPoint){
        return new int[]{(firstPoint[0]+secondPoint[0])/2,(firstPoint[1]+secondPoint[1])/2};
    }

    //////////////////// Function making move
    private static void makeMove(int[] positionOfPeg, int[] positionOfHole){
        myBoard[positionOfPeg[0]][positionOfPeg[1]] = '.';
        myBoard[positionOfHole[0]][positionOfHole[1]] = 'O';
        int[] pointBetween = pointInBetween(positionOfPeg,positionOfHole);
        myBoard[pointBetween[0]][pointBetween[1]] = '.';

    }

    //////////////////// Function unmaking move
    private static void unmakeMove(int[] positionOfPeg, int[] positionOfHole){
        myBoard[positionOfPeg[0]][positionOfPeg[1]] = 'O';
        myBoard[positionOfHole[0]][positionOfHole[1]] = '.';
        int[] pointBetween = pointInBetween(positionOfPeg,positionOfHole);
        myBoard[pointBetween[0]][pointBetween[1]] = 'O';
    }

    //////////////////// Function testing if there is only one peg on myBoard
    private static boolean isThereOnlyOnePeg(){
        int numberOfPegs = 0;
        for (char[] chars : myBoard) {
            for (int col = 0; col < myBoard[0].length; col++) {
                if (chars[col] == 'O') {
                    numberOfPegs++;
                    if (numberOfPegs > 1) {
                        return false;
                    }
                }
            }
        }
        return numberOfPegs == 1;
    }

    ////////////////////__ Functions solving the problem
    private static void depthFirstSearchAlgorithm(){
        dequeOfMoves = new ArrayDeque<>();
        int[] startPosition = new int[]{0,0};
        while(!isThereOnlyOnePeg()){
            if(startPosition == null){
                startPosition = new int[]{0,0};
            }
            startPosition = findClosestPegPosition(startPosition);
            if(startPosition != null) {
                int[] endPosition = returnEndPositionForFirstLegalMoveFromPosition(startPosition);
                if(endPosition != null) {
                    goDeeperInTreeBranch(startPosition,endPosition);
                    startPosition=new int[]{0,0};
                }else{
                    startPosition = nextPosition(startPosition);
                    if(startPosition == null){
                        switchTreeBranch();
                    }
                }

            } else {
                if (dequeOfMoves.isEmpty()){
                    return;
                }
                switchTreeBranch();
            }
        }
    }
    private static int[] nextPosition(int[] position){
        if(position[1] + 1 < myBoard[0].length){
            position[1]++;
        }else {
            if(position[0] + 1 >= myBoard.length){
                return null;
            }
            position[0]++;
            position[1] = 0;
        }
        return position;
    }
    private static void goDeeperInTreeBranch(int[] startPosition, int[] endPosition){
        makeMove(startPosition, endPosition);
        dequeOfMoves.add(returnTwoPositionsAsMove(startPosition,endPosition));
    }
    private static void switchTreeBranch(){
        ArrayList<int[]> lastMove = dequeOfMoves.removeLast();
        unmakeMove(lastMove.get(0),lastMove.get(1));
        int directionNumber = returnDirectionNumberFromMove(lastMove);
        if(directionNumber == -1){
            return;
        }
        directionNumber++;
        for(int row = lastMove.get(0)[0]; row < myBoard.length; row++){
            for(int col = lastMove.get(0)[1]; col < myBoard[0].length; col++){
                for (; directionNumber<listOfDirections.size(); directionNumber++){
                    if(isMoveLegal(new int[]{row,col},findPointTwoFieldsInDirection(new int[]{row,col},directionNumber))){
                        ArrayList<int[]> newMove = new ArrayList<>();
                        newMove.add(new int[]{row,col});
                        newMove.add(findPointTwoFieldsInDirection(new int[]{row,col},directionNumber));
                        dequeOfMoves.add(newMove);
                        makeMove(newMove.get(0),newMove.get(1));
                        return;
                    }
                }
                directionNumber = 0;
            }
            lastMove.get(0)[1] = 0;
        }
        switchTreeBranch();
    }
    private static int returnDirectionNumberFromMove(ArrayList<int[]> move){
        int[] direction = new int[]{(move.get(1)[0] - move.get(0)[0])/2,(move.get(1)[1] - move.get(0)[1])/2};
        return returnIndexOfDirectionWithThisVector(direction);
    }
    private static int returnIndexOfDirectionWithThisVector(int[] vector){
        for(int i = 0; i < listOfDirections.size(); i ++){
            if(areTablesEquals(listOfDirections.get(i),vector)){
                return i;
            }
        }
        return -1;
    }
    private static boolean areTablesEquals(int[] tab1, int[] tab2){
        if(tab1.length!=tab2.length){
            return false;
        }
        for(int i = 0; i < tab1.length; i++){
            if(tab1[i]!=tab2[i]){
                return false;
            }
        }
        return true;
    }
    private static int[] returnEndPositionForFirstLegalMoveFromPosition(int[] startPosition){
        for (int directionNumber = 0; directionNumber < listOfDirections.size(); directionNumber++) {
            int[] endPosition = findPointTwoFieldsInDirection(startPosition, directionNumber);
            if (isMoveLegal(startPosition, endPosition)) {
                return endPosition;
            }
        }
        return null;
    }
    private static ArrayList<int[]> returnTwoPositionsAsMove(int[] startPosition, int[] endPosition){
        ArrayList<int[]> movePositions = new ArrayList<>();
        movePositions.add(startPosition);
        movePositions.add(endPosition);
        return movePositions;
    }
    private static int[] findClosestPegPosition(int[] position){
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
    private static int[] findPointTwoFieldsInDirection(int[] position, int directionNumber){
        if(position[0]+(listOfDirections.get(directionNumber)[0] * 2) >= 0 && position[1] + (listOfDirections.get(directionNumber)[1] * 2) >= 0) {
            return new int[]{position[0] + (listOfDirections.get(directionNumber)[0] * 2), position[1] + (listOfDirections.get(directionNumber)[1] * 2)};
        }
        return null;
    }
    // Direction numbers: 0 - DOWN, 1 - RIGHT, 2 - LEFT, 3 - UP
    private static void setListOfDirections(){
        listOfDirections = new ArrayList<>();
        listOfDirections.add(new int[]{1,0}); //DOWN
        listOfDirections.add(new int[]{0,1}); //RIGHT
        listOfDirections.add(new int[]{0,-1}); //LEFT
        listOfDirections.add(new int[]{-1,0}); //UP
    }


}
