package rechercheAdversiale;

import java.util.ArrayList;
import java.util.Iterator;

public class OthelloAlgo extends GameSearch {

    private int nbrWhite = 0, nbrBlack = 0;
    private Parameters parameters;

    public OthelloAlgo(Parameters parameters) {
        super();
        this.parameters = parameters;
    }

    @Override
    public boolean drawnPosition(Position p) {
        if (winCheck(p) && nbrBlack == nbrWhite) {
            return true;
        }
        return false;
    }

    @Override
    public boolean wonPosition(Position p, boolean player) {
        if (winCheck(p)) {
            if (player && ((parameters.isFirst() && nbrBlack > nbrWhite)
                    || (!parameters.isFirst() && nbrBlack < nbrWhite))) {
                return true;
            }
            if (!player && ((parameters.isFirst() && nbrBlack < nbrWhite)
                    || (!parameters.isFirst() && nbrBlack > nbrWhite))) {
                return true;
            }
        }
        return false;
    }

    private boolean winCheck(Position p) {
        OthelloPosition pos = (OthelloPosition) p;
        Position[] myPossibles = possibleMoves(p, true);
        Position[] hisPossibles = possibleMoves(p, false);
        nbrBlack = 0;
        nbrWhite = 0;
        if (myPossibles.length == 0 && hisPossibles.length == 0) {
            nbrsBlackWhite(pos);
            return true;
        }
        return false;
    }

    private void nbrsBlackWhite(OthelloPosition pos) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (pos.getBoard()[i][j] == OthelloPosition.BLACK) {
                    nbrBlack++;
                } else if (pos.getBoard()[i][j] == OthelloPosition.WHITE) {
                    nbrWhite++;
                }
            }
        }
    }

    @Override
    public float positionEvaluation(Position p, boolean player) {
        int i = 0;
        if (parameters.isHeuristiqueCoins()) {
            i += heuristiqueCoins(p, player);
        }
        if (parameters.isHeuristiqueCotes()) {
            i += heuristiqueCotes(p, player);
        }
        if (parameters.isHeuristiquePions()) {
            i += heuristiquePions(p, player);
        }
        if (parameters.isHeuristiqueGagnante()) {
            i += heuristiqueGagnante(p, player);
        }
        return 0;
    }

    private int heuristiqueCoins(Position p, boolean player) {
        int i = 0;
        OthelloPosition pos = (OthelloPosition) p;
        int color;
        if (parameters.isFirst()) {
            color = OthelloPosition.BLACK;
        } else {
            color = OthelloPosition.WHITE;
        }
        if (pos.getBoard()[0][0] == OthelloPosition.BLANK && makeMove(p, player, new OthelloMove(0, 0)) != null) {
            i += 100;
        }
        if (pos.getBoard()[7][7] == OthelloPosition.BLANK && makeMove(p, player, new OthelloMove(7, 7)) != null) {
            i += 100;
        }
        if (pos.getBoard()[0][7] == OthelloPosition.BLANK && makeMove(p, player, new OthelloMove(0, 7)) != null) {
            i += 100;
        }
        if (pos.getBoard()[7][0] == OthelloPosition.BLANK && makeMove(p, player, new OthelloMove(7, 0)) != null) {
            i += 100;
        }
        if (pos.getBoard()[1][1] == color && pos.getBoard()[0][0] == OthelloPosition.BLANK) {
            i -= 50;
        }
        if (pos.getBoard()[6][6] == color && pos.getBoard()[7][7] == OthelloPosition.BLANK) {
            i -= 50;
        }
        if (pos.getBoard()[1][6] == color && pos.getBoard()[0][6] == OthelloPosition.BLANK) {
            i -= 50;
        }
        if (pos.getBoard()[6][1] == color && pos.getBoard()[7][0] == OthelloPosition.BLANK) {
            i -= 50;
        }
        return i;
    }

    private int heuristiqueCotes(Position p, boolean player) {
        int i = 0;
        OthelloPosition pos = (OthelloPosition) p;
        for (int j = 0; j < 8; j++) {
            if (pos.getBoard()[j][0] == OthelloPosition.BLANK && makeMove(p, player, new OthelloMove(j, 0)) != null) {
                i += 5;
            }
            if (pos.getBoard()[j][7] == OthelloPosition.BLANK && makeMove(p, player, new OthelloMove(j, 7)) != null) {
                i += 5;
            }
        }
        for (int j = 0; j < 8; j++) {
            if (pos.getBoard()[0][j] == OthelloPosition.BLANK && makeMove(p, player, new OthelloMove(0, j)) != null) {
                i += 5;
            }
            if (pos.getBoard()[7][j] == OthelloPosition.BLANK && makeMove(p, player, new OthelloMove(7, j)) != null) {
                i += 5;
            }
        }
        return i;
    }

    private int heuristiquePions(Position p, boolean player) {
        int i = 0;
        OthelloPosition pos = (OthelloPosition) p;
        nbrsBlackWhite(pos);
        if (nbrWhite + nbrBlack > 50) {
            if (parameters.isFirst()) {
                i += 20 * (nbrBlack - nbrWhite);
            } else {
                i += 20 * (nbrWhite - nbrBlack);
            }
        }
        return i;
    }

    private int heuristiqueGagnante(Position p, boolean player) {
        int i = 0;
        OthelloPosition pos = (OthelloPosition) p;
        nbrsBlackWhite(pos);
        if (this.wonPosition(p, player) && nbrWhite + nbrBlack > 55) {
            i += 1000;
        }
        return i;
    }

    @Override
    public Position[] possibleMoves(Position p, boolean player) {
        OthelloPosition pos = (OthelloPosition) p;
        ArrayList<Position> listPositions = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Iterator<Position> itr = null;
                if (player && parameters.isFirst() && pos.getBoard()[i][j] == OthelloPosition.WHITE) {
                    itr = RemplVoisiVide(pos.getBoard(), i, j, OthelloPosition.BLACK).iterator();
                }
                if (player && !parameters.isFirst() && pos.getBoard()[i][j] == OthelloPosition.BLACK) {
                    itr = RemplVoisiVide(pos.getBoard(), i, j, OthelloPosition.WHITE).iterator();
                }
                if (!player && parameters.isFirst() && pos.getBoard()[i][j] == OthelloPosition.BLACK) {
                    itr = RemplVoisiVide(pos.getBoard(), i, j, OthelloPosition.WHITE).iterator();
                }
                if (!player && !parameters.isFirst() && pos.getBoard()[i][j] == OthelloPosition.WHITE) {
                    itr = RemplVoisiVide(pos.getBoard(), i, j, OthelloPosition.BLACK).iterator();
                }
                if (itr != null) {
                    while (itr.hasNext()) {
                        Position pp = itr.next();
                        if (!contains(listPositions, pp)) {
                            listPositions.add(pp);
                        }
                    }
                }
            }
        }
        Position[] ret = new Position[listPositions.size()];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = listPositions.get(i);
        }
        return ret;
    }

    private ArrayList<Position> RemplVoisiVide(int[][] board, int i, int j, int color) {
        ArrayList<Position> list = new ArrayList<>();
        try {
            if (board[i + 1][j] == OthelloPosition.BLANK && hasInRowOrCol(board, i, j, color, false)) {
                OthelloPosition pos = new OthelloPosition(board);
                for (int m = (i + 1); m >= 0; m--) {
                    if (pos.getBoard()[m][j] == color) {
                        break;
                    }
                    pos.getBoard()[m][j] = color;
                }
                pos.getoMove().setxMoveIndex(i + 1);
                pos.getoMove().setyMoveIndex(j);
                list.add(pos);
            }
        } catch (Exception e) {
        }
        try {
            if (board[i - 1][j] == OthelloPosition.BLANK && hasInRowOrCol(board, i, j, color, false)) {
                OthelloPosition pos = new OthelloPosition(board);
                for (int m = (i - 1); m <= 7; m++) {
                    if (pos.getBoard()[m][j] == color) {
                        break;
                    }
                    pos.getBoard()[m][j] = color;
                }
                pos.getoMove().setxMoveIndex(i - 1);
                pos.getoMove().setyMoveIndex(j);
                list.add(pos);
            }
        } catch (Exception e) {
        }
        try {
            if (board[i][j + 1] == OthelloPosition.BLANK && hasInRowOrCol(board, i, j, color, true)) {
                OthelloPosition pos = new OthelloPosition(board);
                for (int m = (j + 1); m >= 0; m--) {
                    if (pos.getBoard()[i][m] == color) {
                        break;
                    }
                    pos.getBoard()[i][m] = color;
                }
                pos.getoMove().setxMoveIndex(i);
                pos.getoMove().setyMoveIndex(j + 1);
                list.add(pos);
            }
        } catch (Exception e) {
        }
        try {
            if (board[i][j - 1] == OthelloPosition.BLANK && hasInRowOrCol(board, i, j, color, true)) {
                OthelloPosition pos = new OthelloPosition(board);
                for (int m = (j - 1); m <= 7; m++) {
                    if (pos.getBoard()[i][m] == color) {
                        break;
                    }
                    pos.getBoard()[i][m] = color;
                }
                pos.getoMove().setxMoveIndex(i);
                pos.getoMove().setyMoveIndex(j - 1);
                list.add(pos);
            }
        } catch (Exception e) {
        }
        try {
            if (board[i + 1][j + 1] == OthelloPosition.BLANK && hasInDiag(board, i, j, color, true)) {
                OthelloPosition pos = new OthelloPosition(board);
                for (int m = (i + 1), n = (j + 1); m >= 0 && n >= 0; m--, n--) {
                    if (pos.getBoard()[m][n] == color) {
                        break;
                    }
                    pos.getBoard()[m][n] = color;
                }
                pos.getoMove().setxMoveIndex(i + 1);
                pos.getoMove().setyMoveIndex(j + 1);
                list.add(pos);
            }
        } catch (Exception e) {
        }
        try {
            if (board[i - 1][j - 1] == OthelloPosition.BLANK && hasInDiag(board, i, j, color, true)) {
                OthelloPosition pos = new OthelloPosition(board);
                for (int m = (i - 1), n = (j - 1); m <= 7 && n <= 7; m++, n++) {
                    if (pos.getBoard()[m][n] == color) {
                        break;
                    }
                    pos.getBoard()[m][n] = color;
                }
                pos.getoMove().setxMoveIndex(i - 1);
                pos.getoMove().setyMoveIndex(j - 1);
                list.add(pos);
            }
        } catch (Exception e) {
        }
        try {
            if (board[i - 1][j + 1] == OthelloPosition.BLANK && hasInDiag(board, i, j, color, false)) {
                OthelloPosition pos = new OthelloPosition(board);
                for (int m = (i - 1), n = (j + 1); m <= 7 && n >= 0; m++, n--) {
                    if (pos.getBoard()[m][n] == color) {
                        break;
                    }
                    pos.getBoard()[m][n] = color;
                }
                pos.getoMove().setxMoveIndex(i - 1);
                pos.getoMove().setyMoveIndex(j + 1);
                list.add(pos);
            }
        } catch (Exception e) {
        }
        try {
            if (board[i + 1][j - 1] == OthelloPosition.BLANK && hasInDiag(board, i, j, color, false)) {
                OthelloPosition pos = new OthelloPosition(board);
                for (int m = (i + 1), n = (j - 1); m >= 0 && n <= 7; m--, n++) {
                    if (pos.getBoard()[m][n] == color) {
                        break;
                    }
                    pos.getBoard()[m][n] = color;
                }
                pos.getoMove().setxMoveIndex(i + 1);
                pos.getoMove().setyMoveIndex(j - 1);
                list.add(pos);
            }
        } catch (Exception e) {
        }
        return list;
    }

    private boolean contains(ArrayList<Position> list, Position p) {
        OthelloPosition np = (OthelloPosition) p;
        int i = 0, j = 0;
        for (Position pp : list) {
            OthelloPosition op = (OthelloPosition) pp;
            for (i = 0; i < 8; i++) {
                for (j = 0; j < 8; j++) {
                    if (op.getBoard()[i][j] != np.getBoard()[i][j]) {
                        break;
                    }
                }
                if (j != 8) {
                    break;
                }
            }
            if (i == 8) {
                return true;
            }
        }
        return false;
    }

    private boolean hasInRowOrCol(int[][] board, int x, int y, int color, boolean isRow) {
        int colorAdver = 0;
        if (color == OthelloPosition.BLACK) {
            colorAdver = OthelloPosition.WHITE;
        } else if (color == OthelloPosition.WHITE) {
            colorAdver = OthelloPosition.BLACK;
        }
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                int k = 1, v = 0;
                if (isRow && i == x && board[i][j] == color) {
                    if (j < y) {
                        v = y - j;
                    } else if (j > y) {
                        v = j - y;
                    }
                    for (k = 1; k < v; k++) {
                        if (((j < y) && board[i][j + k] != colorAdver) || ((j > y) && board[i][j - k] != colorAdver)) {
                            break;
                        }
                    }
                }
                if (!isRow && j == y && board[i][j] == color) {
                    if (i < x) {
                        v = x - i;
                    } else if (i > x) {
                        v = i - x;
                    }
                    for (k = 1; k < v; k++) {
                        if (((i < x) && board[i + k][j] != colorAdver) || ((i > x) && board[i - k][j] != colorAdver)) {
                            break;
                        }
                    }
                }
                if (k == v) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean hasInDiag(int[][] board, int x, int y, int color, boolean isLeftDiag) {
        int x1 = x, y1 = y;
        int x2 = x, y2 = y;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (isLeftDiag) {
                    if (x1 < 7 && y1 < 7) {
                        if (board[x1 + 1][y1 + 1] == color) {
                            return true;
                        } else if (board[x1 + 1][y1 + 1] == OthelloPosition.BLANK) {
                            for (int m = x1, n = y1; m >= 0 && n >= 0; m--, n--) {
                                if (board[m][n] == color) {
                                    for (int p = m + 1, q = n + 1; p < x && q < y; p++, q++) {
                                        if (board[p][q] == OthelloPosition.BLANK) {
                                            return false;
                                        }
                                    }
                                    return true;
                                }
                            }
                            return false;
                        }
                    }
                    if (x2 >= 1 && y2 >= 1) {
                        if (board[x2 - 1][y2 - 1] == color) {
                            return true;
                        } else if (board[x2 - 1][y2 - 1] == OthelloPosition.BLANK) {
                            for (int m = x2, n = y2; m < 8 && n < 8; m++, n++) {
                                if (board[m][n] == color) {
                                    for (int p = m - 1, q = n - 1; p > x && q > y; p--, q--) {
                                        if (board[p][q] == OthelloPosition.BLANK) {
                                            return false;
                                        }
                                    }
                                    return true;
                                }
                            }
                            return false;
                        }
                    }
                    x1 += 1;
                    y1 += 1;
                    x2 -= 1;
                    y2 -= 1;
                } else {
                    if (x1 < 7 && y1 >= 1) {
                        if (board[x1 + 1][y1 - 1] == color) {
                            return true;
                        } else if (board[x1 + 1][y1 - 1] == OthelloPosition.BLANK) {
                            for (int m = x1, n = y1; m >= 0 && n < 8; m--, n++) {
                                if (board[m][n] == color) {
                                    for (int p = m + 1, q = n - 1; p < x && q > y; p++, q--) {
                                        if (board[p][q] == OthelloPosition.BLANK) {
                                            return false;
                                        }
                                    }
                                    return true;
                                }
                            }
                            return false;
                        }
                    }
                    if (x2 >= 1 && y2 < 7) {
                        if (board[x2 - 1][y2 + 1] == color) {
                            return true;
                        } else if (board[x2 - 1][y2 + 1] == OthelloPosition.BLANK) {
                            for (int m = x2, n = y2; m < 8 && n >= 0; m++, n--) {
                                if (board[m][n] == color) {
                                    for (int p = m - 1, q = n + 1; p > x && q < y; p--, q++) {
                                        if (board[p][q] == OthelloPosition.BLANK) {
                                            return false;
                                        }
                                    }
                                    return true;
                                }
                            }
                            return false;
                        }
                    }
                    x1 += 1;
                    y1 -= 1;
                    x2 -= 1;
                    y2 += 1;
                }
            }
        }
        return false;
    }

    @Override
    public Position makeMove(Position p, boolean player, Move move) {
        OthelloMove m = (OthelloMove) move;
        Position[] possibles;
        ArrayList<OthelloPosition> possibleMix = new ArrayList<>();
        if (player) {
            possibles = possibleMoves(p, true);
        } else {
            possibles = possibleMoves(p, false);
        }
        for (Position pp : possibles) {
            OthelloPosition op = (OthelloPosition) pp;
            if (op.getoMove().getxMoveIndex() == m.getxMoveIndex()
                    && op.getoMove().getyMoveIndex() == m.getyMoveIndex()) {
                possibleMix.add(op);
            }
        }
        if (possibleMix.size() == 1) {
            return possibleMix.get(0);
        } else if (possibleMix.size() > 1) {
            int color;
            if (parameters.isFirst()) {
                color = OthelloPosition.BLACK;
            } else {
                color = OthelloPosition.WHITE;
            }
            int[][] brd = new int[8][8];
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    brd[i][j] = possibleMix.get(0).getBoard()[i][j];
                }
            }
            for (int k = 0; k < possibleMix.size(); k++) {
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        if (brd[i][j] != color && possibleMix.get(k).getBoard()[i][j] == color) {
                            brd[i][j] = possibleMix.get(k).getBoard()[i][j];
                        }
                    }
                }
            }
            OthelloPosition p1 = new OthelloPosition(brd);
            p1.setoMove(m);
            return p1;
        }
        return null;
    }

    @Override
    public boolean reachedMaxDepth(Position p, int depth) {
        boolean ret = false;
        if (depth >= parameters.getMaxDepth()) {
            ret = true;
        } else if (wonPosition(p, false)) {
            ret = true;
        } else if (wonPosition(p, true)) {
            ret = true;
        } else if (drawnPosition(p)) {
            ret = true;
        }
        return ret;
    }
}