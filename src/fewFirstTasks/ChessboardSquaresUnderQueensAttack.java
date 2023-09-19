package fewFirstTasks;

public class ChessboardSquaresUnderQueensAttack {

    private int rows;
    private int cols;

    public ChessboardSquaresUnderQueensAttack(int row, int cols) {
        this.rows = row;
        this.cols = cols;
        System.out.println(countFields());
    }

    public int countFields(){
        int count = 0;
        for(int i = 1; i<= rows; i++){
            for (int j = 1; j<=cols; j++){
                count+= squaresUnderQueenAttack(i,j);
            }
        }
        return count;
    }

    private int squaresUnderQueenAttack(int x, int y) {
        if (x <= rows && y <= cols && x > 0 && y > 0) {
            return line(x,y,1,0) + line(x,y,1,1)
                    + line(x,y,0,1) + line(x,y,-1,1)
                    + line(x,y,-1,0) + line(x,y,-1,-1)
                    + line(x,y,0,- 1) + line(x,y, 1, -1);
        }
        return -1;
    }

    private int line(int x, int y, int vx, int vy) {
        int attackedSquares = 0;
        for (int i = x + vx, j = y + vy; i <= rows && i > 0 && j <= cols && j > 0; i += vx, j += vy) {
                attackedSquares++;
            }
        return attackedSquares;
    }
}
