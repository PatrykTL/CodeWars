package fewFirstTasks;

//import java.util.ArrayList;
//import java.util.LinkedList;
//import java.util.List;

import java.util.ArrayList;
import java.util.List;

public class PegSolitaire {
    private BoardElement[][] myBoard;
    private final char[] legalSymbols;
    private List<List<int[]>> moveList;
    private int currentRow;
    private int currentCol;
    private int positionOfDeletedMove;
    private List<int[]> deletedMove;
    private boolean noMorePossibleSolutions;
    private int numberOfPegsAtStart;
    final private List<int[]> directions;


    public PegSolitaire(String board){
        ArrayList directionList = new ArrayList<>();
        int[] upVector = new int[]{-1,0};
        int[] downVector = new int[]{1,0};
        int[] leftVector = new int[]{0,-1};
        int[] rightVector = new int[]{0,1};


        directionList.add(upVector);
        directionList.add(downVector);
        directionList.add(leftVector);
        directionList.add(rightVector);

        this.directions = new ArrayList<>(directionList);

        legalSymbols = new char[]{'O', '.', '_'};
        createBoardFromString(board);
        numberOfPegsAtStart = countPegs();

        greedyAlgorithm();
        //printSolution();
        mySolutionToSolution();
    }

    private class BoardElement {
        int id;
        int rowNumber;
        int columnNumber;
        char value;

        public BoardElement(int id, int rowNumber, int columnNumber, char value) {
            this.id = id;
            this.rowNumber = rowNumber;
            this.columnNumber = columnNumber;
            this.value = value;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getRowNumber() {
            return rowNumber;
        }

        public void setRowNumber(int rowNumber) {
            this.rowNumber = rowNumber;
        }

        public int getColumnNumber() {
            return columnNumber;
        }

        public void setColumnNumber(int columnNumber) {
            this.columnNumber = columnNumber;
        }

        public char getValue() {
            return value;
        }

        public void setValue(char value) {
            this.value = value;
        }
    }

    /////////////////////////////////VOID///////////////////////////////

    private void greedyAlgorithm(){
        moveList = new ArrayList<>();
        currentRow = 0;
        currentCol = 0;
        noMorePossibleSolutions = false;
        printMyBoard();

        while(!isSolved()&&!noMorePossibleSolutions){
            loop();
            //System.out.println(moveList.size());
        }


    }

    private void loop(){
        int[] positionOfHole = findNextHole(currentRow,currentCol);
        if(positionOfHole[0] != -1){
            int[] positionOfPeg = positionOfPegForMove(positionOfHole);
            if(positionOfPeg[0] != -1) {
                ArrayList move = new ArrayList<>();
                move.add(positionOfPeg);
                move.add(positionOfHole);
                moveList.add(move);
                makeMove(positionOfPeg,positionOfHole);
                currentRow = 0;
                currentCol = 0;
            } else {
                currentRow = positionOfHole[0];
                currentCol = positionOfHole[1];
                nextPositionInMyBoard();
            }
        } else{
            currentRow = 0;
            currentCol = 0;
            findNextMove();
        }


    }

    private void createBoardFromString(String board){
        int[] tmp = returnBoardSizes(board);
        int maxNumberOfColumns = tmp[0];
        int maxNumberOfRows = tmp[1];
        int currentColumn = 0;
        int currentRow = 0;
        int id = 1;
        myBoard = new BoardElement[maxNumberOfColumns][maxNumberOfRows];
        for (int i = 0; i < board.length(); i++) {
            if (board.charAt(i) != '\n') {
                if (isLegalSymbol(board.charAt(i))) {
                    myBoard[currentColumn][currentRow] = new BoardElement(id, currentRow, currentColumn, board.charAt(i));
                    if (board.charAt(i) == 'O' || board.charAt(i) == '.'){
                        id++;
                }
                    currentColumn++;
                }
            } else {
                currentColumn = 0;
                currentRow++;
            }
        }
    }

    private void nextPositionInMyBoard(){
        if(currentCol + 1 < myBoard[0].length){
            currentCol++;
        }else {
            currentRow++;
            currentCol = 0;
        }
    }

    private void findNextMove(){
        //printSolution();

        if(moveList.isEmpty()){
            noMorePossibleSolutions = true;
            return;
        }
        positionOfDeletedMove = moveList.size() - 1;
        deletedMove = moveList.get(positionOfDeletedMove);
        moveList.remove(positionOfDeletedMove);
        undoMove(deletedMove.get(0),deletedMove.get(1));



        //int directionId = numberOfDirectionId(lastMove.get(0),lastMove.get(1));
        //int holeRowPosition = lastMove.get(0)[0];
        //int holeColPosition = lastMove.get(0)[1];
        //System.out.println("Hole row position: " + holeRowPosition + " hole col position: " + holeColPosition + " direction id: " + directionId);
        int directionId = numberOfDirectionId(deletedMove.get(0),deletedMove.get(1));
        int holeRowPosition = deletedMove.get(1)[0];
        int holeColPosition = deletedMove.get(1)[1];
        //System.out.println("Hole row position: " + holeRowPosition + " hole col position: " + holeColPosition + " direction id: " + directionId);
        for(int i = holeRowPosition; i<myBoard.length; i++){
            for (int j = holeColPosition; j<myBoard[0].length; j++){
                for(int k = directionId + 1; k < directions.size(); k++){
                    //System.out.println("i: " + i+ "j: " + j + " k: " + k);

                    if(isMoveLegal(new int[]{i,j},directions.get(k))){
                        //System.out.println("i: " + i+ "j: " + j + " k: " + k);

                        ArrayList nextMove = new ArrayList<int[]>();
                        int[] pegPosition = new int[]{2*directions.get(k)[0]+i,2*directions.get(k)[1]+j};
                        int[] holePosition = new int[]{i,j};
                        nextMove.add(pegPosition);
                        nextMove.add(holePosition);

                        if(holePosition[1] == 1 && holePosition[0] == 5){
                            System.out.println("wtf");
                        }

                        moveList.add(new ArrayList<>(nextMove));
                        //System.out.println(" _______ Am I ever here?");
                        //System.out.println("Next move");
                        //printMyBoard();
                        makeMove(pegPosition,holePosition);
                        //System.out.println();
                        return;
                    }
                }
                directionId = 0;
            }
            holeColPosition = 0;
        }
        findNextMove();
    }

    private List mySolutionToSolution(){
        List solution = new ArrayList();
        moveList.forEach(move->{
            System.out.println(myBoard[move.get(0)[0]][move.get(0)[1]].getId());
            solution.add(myBoard[move.get(0)[0]][move.get(0)[1]].getId());
            System.out.println(myBoard[move.get(1)[0]][move.get(1)[1]].getId());
            solution.add(myBoard[move.get(1)[0]][move.get(1)[1]].getId());

        });
        return solution;
    }

    private void makeMove(int[] pegPosition, int[] holePosition){

        //System.out.println("Making move: ");
        //System.out.println(moveList.size());
        //printMyBoard();
        //printSolution();
        makePeg(holePosition);
        makeHole(pegPosition);
        makeHole(pointBetween(holePosition,pegPosition));
        //System.out.println("After: ");
        System.out.println(moveList.size());
        printMyBoard();

    }

    private void undoMove(int[] pegPosition, int[] holePosition){
        //System.out.println("Undoing move: ");
        //System.out.println(moveList.size());
        //printSolution();
        makeHole(holePosition);
        makePeg(pegPosition);
        makePeg(pointBetween(holePosition,pegPosition));
        //System.out.println("Moving back: ");
        System.out.println(moveList.size());
        printMyBoard();

    }

    private void makePeg(int[] position){
        myBoard[position[0]][position[1]].setValue('O');
    }

    private void makeHole(int[] position) {
        myBoard[position[0]][position[1]].setValue('.');
    }


    /////////////////////////////////BOOLEAN///////////////////////////////

    private boolean isMoveLegal(int[] position, int[] directionVector){
        if (
                position[0] + directionVector[0] < 0
                || position[0] + directionVector[0] >= myBoard.length
                || position[1] + directionVector[1] < 0
                || position[1] + directionVector[1] >= myBoard[0].length
        ) {
            return false;
        }
        if
        (
                position[0] + directionVector[0] * 2 < 0
                || position[0] + directionVector[0] * 2 >= myBoard.length
                || position[1] + directionVector[1] * 2 < 0
                || position[1] + directionVector[1] * 2 >= myBoard[0].length
        )
        {
            return false;
        }
        if (
                myBoard[position[0]][position[1]].getValue() == '.'
                && myBoard[position[0]+directionVector[0]][position[1]+directionVector[1]].getValue() == 'O'
                && myBoard[position[0]+(directionVector[0]*2)][position[1]+(directionVector[1]*2)].getValue() == 'O'
        )
            return true;
        return false;
    }



    private boolean isLegalSymbol(char symbol) {
        for (int i = 0; i < legalSymbols.length; i++) {
            if (symbol == legalSymbols[i]) {
                return true;
            }
        }
        return false;
    }
    private int countPegs(){
        int numberOfPegs = 0;
        for(int i =0; i < myBoard.length; i++){
            for(int j = 0; j < myBoard[0].length; j++){
                if(myBoard[i][j].getValue() == 'O'){
                    numberOfPegs++;
                }
            }
        }
        return numberOfPegs;
    }

    private boolean isSolved(){
        if(moveList.size()>=numberOfPegsAtStart-1){
            return true;
        }
        return false;
    }

    /////////////////////////////////INT[]///////////////////////////////

    private int[] returnBoardSizes(String board) {
        int[] sizes = {0, 1};   //size 0: number of columns, size 1: number of rows
        for (int i = 0; i < board.length(); i++) {
            if (board.charAt(i) == '\n') {
                sizes[1]++;
            }
        }
        for (int i = 0; i < board.length(); i++) {
            if (board.charAt(i) == '\n') {
                break;
            }
            sizes[0]++;
        }
        return sizes;
    }
    private int[] findNextHole(int currentRow, int currentCol){
        for(int row = currentRow; row < myBoard.length; row++){
            for(int col = currentCol; col < myBoard[0].length; col++){
                if(myBoard[row][col].getValue() == '.'){
                    if(row == 1 && col == 3){
                        //System.out.println("here");
                    }
                    return new int[]{row,col};
                }
            }
            currentCol = 0;
        }
        return new int[]{-1,-1};
    }
    private int[] positionOfPegForMove(int[] positionOfHole) {
        String positionOfPegFromHole = anyMoveLegal(positionOfHole);
        if(positionOfPegFromHole == "UP"){
            return new int[]{positionOfHole[0]-2,positionOfHole[1]};
        }
        if(positionOfPegFromHole == "DOWN"){
            return new int[]{positionOfHole[0]+2,positionOfHole[1]};
        }
        if(positionOfPegFromHole == "LEFT"){
            return new int[]{positionOfHole[0],positionOfHole[1]-2};
        }
        if (positionOfPegFromHole == "RIGHT"){
            return new int[]{positionOfHole[0],positionOfHole[1]+2};
        }
        return new int[]{-1,-1};
    }



    private int[] pointBetween(int[] pointOne,int[] pointTwo){
        return new int[]{(pointOne[0] + pointTwo[0])/2, (pointOne[1] + pointTwo[1])/2};
    }





    private int numberOfDirectionId(int[] positionOfPeg,int[] positionOfHole){
        if((positionOfPeg[0] - positionOfHole[0]) == -2){
            return 0;
        }
        if((positionOfPeg[0] - positionOfHole[0]) == 2){
            return 1;
        }
        if((positionOfPeg[1] - positionOfHole[1] == -2)){
            return 2;
        }
        if((positionOfPeg[1] - positionOfHole[1] == 2)){
            return 3;
        }

        return -1;
    }


    private String anyMoveLegal(int[] positionOfHole){
        if(isMoveLegal(positionOfHole,new int[]{-1,0})){
            return "UP";
        }
        if(isMoveLegal(positionOfHole,new int[]{1,0})){
            return "DOWN";
        }
        if(isMoveLegal(positionOfHole,new int[]{0,-1})){
            return "LEFT";
        }
        if(isMoveLegal(positionOfHole,new int[]{0,1})){
            return "RIGHT";
        }
        return "NO";
    }

    private void printSolution(){
        for(int i= 0; i<moveList.size(); i++){
            System.out.print(i + ": ");
            for(int j=0; j<moveList.get(i).size(); j++) {
                System.out.print("[" + moveList.get(i).get(j)[0] + "] [" + moveList.get(i).get(j)[1] + "]");
                System.out.print("\t");
            }
            System.out.println();
        }
    }

    private void printMyBoard(){
        for(int i = 0; i < myBoard.length; i++){
            for(int j = 0; j < myBoard[0].length; j++){
                System.out.print(myBoard[i][j].getValue() + " ");
            }
            System.out.println();
        }
    }

}

//    private String board;
//    private char[] legalSymbols;
//    private char[][] board2d;
//    private List<int[]> solution;
//    private int[] currentPosition;
//    private LinkedList<int[]> mySolution;
//    private LinkedList<String> directionCombination;
//    private String[] directions;
//    private String iWillFixItLater;
//
//
//    public PegSolitaire(String board) {
//        directions = new String[]{"UP","DOWN","LEFT","RIGHT"};
//        directionCombination = new LinkedList<>();
//        legalSymbols = new char[]{'O', '.', '_'};
//        this.board = board;
//        this.board2d = returnBoardAsA2DCharTable(board);
//        printBoard();
//        solution = new ArrayList<>();
//        mySolution = new LinkedList<>();
//
//    }
//
//    public List<int[]> solve(String board) {
//
//
//        return null;
//    }
//
//    public void greedyAlgorithm(){
//        currentPosition = new int[]{0, 0};
//        while(!isSolved()){
//            loop();
//            mySolutionPrint();
//        }
//    }
//
//    private void loop(){
//        int[] tmp = returnNextHolePosition(board2d,currentPosition);
//            if (areThere2PegsUp(tmp)) {
//                if(directionCombination.size()>mySolution.size()){
//
//                }else {
//                    mySolution.add(new int[]{tmp[0], tmp[1] - 2, tmp[0], tmp[1]});
//                    makeMove(new int[]{tmp[0], tmp[1] - 2}, tmp);
//                    directionCombination.add(directions[0]);
//                    return;
//                }
//            }
//
//            if (areThere2PegsDown(tmp)) {
//                if (directionCombination.size() > mySolution.size() && directionCombination.getLast() != directions[0]) {
//
//                } else {
//                    if(directionCombination.size() > mySolution.size()){
//                        directionCombination.removeLast();
//                    }
//                    mySolution.add(new int[]{tmp[0], tmp[1] + 2, tmp[0], tmp[1]});
//                    makeMove(new int[]{tmp[0], tmp[1] + 2}, tmp);
//                    directionCombination.add(directions[1]);
//                    return;
//                }
//            }
//
//            if (areThere2PegsLeft(tmp)) {
//                if (directionCombination.size() > mySolution.size() && (directionCombination.getLast() != directions[0] || directionCombination.getLast() != directions[1])) {
//
//                } else {
//                    if(directionCombination.size() > mySolution.size()){
//                        directionCombination.removeLast();
//                    }
//                    mySolution.add(new int[]{tmp[0] - 2, tmp[1], tmp[0], tmp[1]});
//                    makeMove(new int[]{tmp[0] - 2, tmp[1]}, tmp);
//                    directionCombination.add(directions[2]);
//                    return;
//                }
//            }
//
//            if (areThere2PegsRight(tmp)) {
//                if (directionCombination.size() > mySolution.size() &&
//                        (directionCombination.getLast() != directions[0]
//                                || directionCombination.getLast() != directions[1]
//                                || directionCombination.getLast() != directions[2])) {
//
//                } else {
//                    if(directionCombination.size() > mySolution.size()){
//                        directionCombination.removeLast();
//                    }
//                    mySolution.add(new int[]{tmp[0] + 2, tmp[1], tmp[0], tmp[1]});
//                    makeMove(new int[]{tmp[0] + 2, tmp[1]}, tmp);
//                    directionCombination.add(directions[3]);
//                    return;
//                }
//            }
//
//            if(directionCombination.size()>mySolution.size()){
//                directionCombination.removeLast();
//            }
//
//        if(!isSolved()&&!mySolution.isEmpty()){
//            undoMove(new int[]{mySolution.getLast()[0],mySolution.getLast()[1]},new int[]{mySolution.getLast()[2],mySolution.getLast()[3]});
//            mySolution.removeLast();
//        }
//
//    }
//
//    private void makeMove(int[] pegPosition, int[] holePosition){
//        makePeg(holePosition);
//        makeHole(pegPosition);
//        makeHole(pointBetween(holePosition,pegPosition));
//    }
//
//        private void undoMove(int[] pegPosition, int[] holePosition){
//        makeHole(holePosition);
//        makePeg(pegPosition);
//        makePeg(pointBetween(holePosition,pegPosition));
//    }
//
//    private int[] pointBetween(int[] pointOne,int[] pointTwo){
//        return new int[]{(pointOne[0] + pointTwo[0]), pointOne[1] + pointTwo[1]};
//    }
//
//    private void makeHole(int[] position){
//        board2d[position[0]][position[1]] = '.';
//    }
//
//    private void makePeg(int[] position){
//        board2d[position[0]][position[1]] = 'O';
//    }
//
//    private boolean isSolved(){
//        int numberOfPegs = 0;
//        for(int i = 0; i < board2d.length; i++){
//            for(int j = 0; j < board2d[0].length; j++){
//                if(board2d[i][j] == 'O'){
//                    numberOfPegs++;
//                }
//            }
//        }
//        if(numberOfPegs == 1){
//            return true;
//        }
//        return false;
//    }
//
//    public void printBoard() {
//        System.out.println(this.board);
//    }
//
//    private char[][] returnBoardAsA2DCharTable(String board) {
//        int[] tmp = returnBoardSizes(board);
//        int a = tmp[0];
//        int b = tmp[1];
//        int c = 0;
//        int d = 0;
//        System.out.println(a + " " + b);
//        char[][] board2D = new char[a][b];
//        for (int i = 0; i < board.length(); i++) {
//            if (board.charAt(i) != '\n') {
//                if (isLegalSymbol(board.charAt(i))) {
//                    board2D[c][d] = board.charAt(i);
//                    c++;
//                }
//            } else {
//                c = 0;
//                d++;
//            }
//        }
//        return board2D;
//    }
//
//    private boolean isLegalSymbol(char symbol) {
//        for (int i = 0; i < legalSymbols.length; i++) {
//            if (symbol == legalSymbols[i]) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    private int[] returnBoardSizes(String board) {
//        int[] sizes = {0, 1};
//        for (int i = 0; i < board.length(); i++) {
//            if (board.charAt(i) == '\n') {
//                sizes[1]++;
//            }
//        }
//        for (int i = 0; i < board.length(); i++) {
//            if (board.charAt(i) == '\n') {
//                break;
//            }
//            sizes[0]++;
//        }
//        return sizes;
//    }
//
//    private int[] returnNextHolePosition(char[][] board2d, int[] currentPosition) {
//        for (int i = currentPosition[0]; i < board2d.length; i++) {
//            for (int j = currentPosition[1]; j < board2d[0].length; j++) {
//                if (board2d[i][j] == '.') {
//                    return new int[]{i, j};
//                }
//            }
//        }
//        return new int[]{-1, -1};
//    }
//
//    private boolean areThere2PegsUp(int[] position) {
//        if (position[1] - 1 < 0) {
//            return false;
//        }
//        if (position[1] - 2 < 0) {
//            return false;
//        }
//        if (board2d[position[0]][position[1]-1] == 'O' && board2d[position[0]][position[1] - 2] == 'O')
//        return true;
//        return false;
//    }
//
//    private boolean areThere2PegsDown(int[] position) {
//        if (position[1] + 1 > board2d[0].length) {
//            return false;
//        }
//        if (position[1] + 2 > board2d[0].length) {
//            return false;
//        }
//        if (board2d[position[0]][position[1] + 1] == 'O' && board2d[position[0]][position[1] + 2] == 'O')
//            return true;
//        return false;
//    }
//
//    private boolean areThere2PegsLeft(int[] position) {
//        if (position[0] - 1 < 0) {
//            return false;
//        }
//        if (position[0] - 2 < 0) {
//            return false;
//        }
//        if(board2d[position[0]-1][position[1]] == 'O' && board2d[position[0]-2][position[1]] == 'O')
//            return true;
//        return false;
//    }
//
//    private boolean areThere2PegsRight(int[] position) {
//        if (position[0] + 1 < 0) {
//            return false;
//        }
//        if (position[0] + 2 < 0) {
//            return false;
//        }
//        if(board2d[position[0]+1][position[1]] == 'O' && board2d[position[0]+2][position[1]] == 'O')
//            return true;
//        return false;
//    }
//
//    public LinkedList<int[]> getMySolution(){
//        return mySolution;
//    }
//
//    private void mySolutionPrint(){
//
//        System.out.println("My solution: ");
//        iWillFixItLater = new String();
//        mySolution.forEach(element -> {
//            for(int i = 0; i < element.length; i++){
//                iWillFixItLater += ("[" + element[i] + "]");
//            }
//        });
//        System.out.print(iWillFixItLater);
//
//    }
