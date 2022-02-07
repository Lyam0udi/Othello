package rechercheAdversiale;

import java.io.Serializable;

public class OthelloMove extends Move implements Serializable {

    private static final long serialVersionUID = 1L;

    private int xMoveIndex;
    private int yMoveIndex;

    public OthelloMove(int xMoveIndex, int yMoveIndex) {
        super();
        this.xMoveIndex = xMoveIndex;
        this.yMoveIndex = yMoveIndex;
    }

    public OthelloMove() {
        super();
    }

    public int getxMoveIndex() {
        return xMoveIndex;
    }

    public int getyMoveIndex() {
        return yMoveIndex;
    }

    public void setxMoveIndex(int xMoveIndex) {
        this.xMoveIndex = xMoveIndex;
    }

    public void setyMoveIndex(int yMoveIndex) {
        this.yMoveIndex = yMoveIndex;
    }

}