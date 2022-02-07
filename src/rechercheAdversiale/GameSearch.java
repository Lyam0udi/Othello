package rechercheAdversiale;

import java.util.*;

import javax.swing.JOptionPane;

import othello.Login;
import othello.Othello;
import othello.User;

public abstract class GameSearch {

    public static final boolean DEBUG = false;

    /*
     * Note: the abstract Position also needs to be
     *       subclassed to write a new game program.
     */
 /*
     * Note: the abstract class Move also needs to be subclassed.
     *       
     */
    public static boolean PROGRAM = false;
    public static boolean HUMAN = true;
    public static boolean printNextElements = true;

    /**
     * Notes: PROGRAM false -1, HUMAN true 1
     */

    /*
     * Abstract methods:
     */
    public abstract boolean drawnPosition(Position p);

    public abstract boolean wonPosition(Position p, boolean player);

    public abstract float positionEvaluation(Position p, boolean player);

    public abstract Position[] possibleMoves(Position p, boolean player);

    public abstract Position makeMove(Position p, boolean player, Move move);

    public abstract boolean reachedMaxDepth(Position p, int depth);

    /*
     * Search utility methods:
     */
    public Vector alphaBeta(int depth, Position p, boolean player) {
        Vector v = alphaBetaHelper(depth, p, player, 1000000.0f, -1000000.0f);
        //System.out.println("^^ v(0): " + v.elementAt(0) + ", v(1): " + v.elementAt(1));
        return v;
    }

    protected Vector alphaBetaHelper(int depth, Position p, boolean player, float alpha, float beta) {
        if (GameSearch.DEBUG) {
            System.out.println("alphaBetaHelper(" + depth + "," + p + "," + alpha + "," + beta + ")");
        }
        if (reachedMaxDepth(p, depth)) {
            Vector v = new Vector(2);
            float value = positionEvaluation(p, player);
            v.addElement(new Float(value));
            v.addElement(null);
            if (GameSearch.DEBUG) {
                System.out.println(" alphaBetaHelper: mx depth at " + depth
                        + ", value=" + value);
            }
            return v;
        }
        Vector best = new Vector();
        Position[] moves = possibleMoves(p, player);
        for (int i = 0; i < moves.length; i++) {
            Vector v2 = alphaBetaHelper(depth + 1, moves[i], !player, -beta, -alpha);
            //  if (v2 == null || v2.size() < 1) continue;
            float value = -((Float) v2.elementAt(0)).floatValue();
            if (value > beta) {
                if (GameSearch.DEBUG) {
                    System.out.println(" ! ! ! value=" + value + ", beta=" + beta);
                }
                beta = value;
                best = new Vector();
                best.addElement(moves[i]);
                Enumeration enum2 = v2.elements();
                enum2.nextElement(); // skip previous value
                while (enum2.hasMoreElements()) {
                    Object o = enum2.nextElement();
                    if (o != null) {
                        best.addElement(o);
                    }
                }
            }
            /**
             * Use the alpha-beta cutoff test to abort search if we found a move
             * that proves that the previous move in the move chain was dubious
             */
            if (beta >= alpha) {
                break;
            }
        }
        Vector v3 = new Vector();
        v3.addElement(new Float(beta));
        Enumeration enum2 = best.elements();
        while (enum2.hasMoreElements()) {
            v3.addElement(enum2.nextElement());
        }
        return v3;
    }

    public void playGame(User u, Move move) {
        OthelloMove m = (OthelloMove) move;
        Position startingPosition = u.getOPosition();
        Vector<?> v = null;
        if (u.getParameters().isFirst() == false
                && u.getParameters().isWithHuman() == false && m.getxMoveIndex() == -1) {
            v = alphaBeta(0, startingPosition, PROGRAM);
            programmePlayed(u, v, 500);
        } else if (u.getParameters().isWithHuman() == false && m.getxMoveIndex() != -1) {
            if (possibleMoves(startingPosition, true).length > 0) {
                startingPosition = makeMove(u.getOPosition(), HUMAN, move);
                playerPlayed(u, startingPosition);
            }
            if (possibleMoves(startingPosition, false).length > 0) {
                v = alphaBeta(0, startingPosition, PROGRAM);
                programmePlayed(u, v, 1500);
            }
        } else if (u.getParameters().isWithHuman() == true) {
            startingPosition = makeMove(u.getOPosition(), HUMAN, move);
            playerPlayed(u, startingPosition);
        }
    }

    private void playerPlayed(User u, Position p) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                u.setOPosition((OthelloPosition) p);
                u.update();
                if (u.getParameters().isWithHuman()) {
                    if (verifier(u, true)) {
                        return;
                    }
                    u.getParameters().setFirst(!u.getParameters().isFirst());
                    u.update();
                    if (possibleMoves(u.getOPosition(), true).length == 0 && !verifier(u, false)) {
                        u.getParameters().setFirst(!u.getParameters().isFirst());
                        u.update();
                    }
                }
                verifier(u, true);
            }
        });
        t.start();
    }

    private void programmePlayed(User u, Vector<?> v, int millsec) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                u.getParameters().setMyTurn(false);
                try {
                    Thread.sleep(millsec);
                } catch (InterruptedException e) {
                }
                u.setOPosition((OthelloPosition) (Position) v.elementAt(1));
                u.getParameters().setMyTurn(true);
                u.update();
                if (verifier(u, true)) {
                    return;
                }
                while (possibleMoves(u.getOPosition(), true).length == 0
                        && possibleMoves(u.getOPosition(), false).length > 0) {
                    u.getParameters().setMyTurn(false);
                    try {
                        Thread.sleep(millsec);
                    } catch (InterruptedException e) {
                    }
                    Vector<?> v2 = alphaBeta(0, u.getOPosition(), PROGRAM);
                    u.setOPosition((OthelloPosition) (Position) v2.elementAt(1));
                    u.getParameters().setMyTurn(true);
                    u.update();
                    if (verifier(u, true)) {
                        return;
                    }
                }
            }
        });
        t.start();
    }

    private boolean verifier(User u, boolean affiche) {
        if (drawnPosition(u.getOPosition())) {
            if (affiche) {
                JOptionPane.showMessageDialog(Othello.instance, "Drawn game");
            }
            return true;
        }
        if (wonPosition(u.getOPosition(), false) && u.getParameters().isWithHuman() == false) {
            if (affiche) {
                JOptionPane.showMessageDialog(Othello.instance, "Program won");
            }
            return true;
        } else if (wonPosition(u.getOPosition(), true) && u.getParameters().isWithHuman() == false) {
            if (affiche) {
                JOptionPane.showMessageDialog(Othello.instance, "Human won");
            }
            return true;
        }

        if (u.getParameters().isWithHuman()) {
            if (wonPosition(u.getOPosition(), true)) {
                if (u.getParameters().isFirst() && affiche) {
                    JOptionPane.showMessageDialog(Othello.instance, "Black won");
                } else if (u.getParameters().isFirst() == false && affiche) {
                    JOptionPane.showMessageDialog(Othello.instance, "White won");
                }
                return true;
            }
        }
        return false;
    }
}