package com.company;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
	// write your code here
        //ChessboardSquaresUnderQueensAttack chessboardSquaresUnderQueensAttack = new ChessboardSquaresUnderQueensAttack(8,8);
        //CornerFill cornerFill = new CornerFill(4);
        String board = "__OOO__\n" +
                "__OOO__\n" +
                "OOOOOOO\n" +
                "OOO.OOO\n" +
                "OOOOOOO\n" +
                "__OOO__\n" +
                "__OOO__";
        PegSolitaire pegSolitaire = new PegSolitaire(board);
    }
}
