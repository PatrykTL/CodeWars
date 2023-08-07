package com.company;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
	// write your code here
        //ChessboardSquaresUnderQueensAttack chessboardSquaresUnderQueensAttack = new ChessboardSquaresUnderQueensAttack(8,8);
        //CornerFill cornerFill = new CornerFill(4);
        //String board = "__OOO__\n" +
        //        "__OOO__\n" +
        //        "OOOOOOO\n" +
        //        "OOO.OOO\n" +
        //        "OOOOOOO\n" +
        //        "__OOO__\n" +
        //        "__OOO__";

        //String board = String.join("\n",
        //        "_O__",
        //        "_.OO",
        //        "_O.O",
        //        "OOOO"
        //);

        String board = String.join("\n",
                "_O.",
                ".OO",
                "O.."
        );




        PegSolitaire2.solve(board);
        //pegSolitaire.loop();
    }
}
