package rechercheAdversiale;

import java.io.Serializable;

public class OthelloPosition extends Position implements Serializable {

    private static final long serialVersionUID = 1L;

    private int[][] board = new int[8][8];
    private OthelloMove oMove = new OthelloMove();

    final static public int BLANK = 0;
    final static public int WHITE = 1;
    final static public int BLACK = 2;

    public OthelloPosition(int[][] board) {
        super();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                this.board[i][j] = board[i][j];
            }
        }
    }

    public OthelloPosition() {
        super();
    }

    public int[][] getBoard() {
        return board;
    }

    public OthelloMove getoMove() {
        return oMove;
    }

    public void setBoard(int[][] board) {
        this.board = board;
    }

    public void setoMove(OthelloMove oMove) {
        this.oMove = oMove;
    }
}