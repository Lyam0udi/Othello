package rechercheAdversiale;

import java.io.Serializable;

public class Parameters implements Serializable {

    private static final long serialVersionUID = 1L;

    final private String file = "users.txt";
    private boolean isFirst = true;
    private boolean isMyTurn = true;
    private boolean isWithHuman = false;
    private int maxDepth = 2;
    private boolean heuristiqueCoins = false;
    private boolean heuristiqueCotes = false;
    private boolean heuristiquePions = false;
    private boolean heuristiqueGagnante = false;
    private boolean hasConfiguration = false;

    public String getFile() {
        return file;
    }

    public boolean isFirst() {
        return isFirst;
    }

    public void setFirst(boolean isFirst) {
        this.isFirst = isFirst;
    }

    public boolean isMyTurn() {
        return isMyTurn;
    }

    public void setMyTurn(boolean isMyTurn) {
        this.isMyTurn = isMyTurn;
    }

    public boolean isWithHuman() {
        return isWithHuman;
    }

    public void setWithHuman(boolean isWithHuman) {
        this.isWithHuman = isWithHuman;
    }

    public int getMaxDepth() {
        return maxDepth;
    }

    public void setMaxDepth(int maxDepth) {
        this.maxDepth = maxDepth;
    }

    public boolean isHeuristiqueCoins() {
        return heuristiqueCoins;
    }

    public void setHeuristiqueCoins(boolean heuristiqueCoins) {
        this.heuristiqueCoins = heuristiqueCoins;
    }

    public boolean isHeuristiqueCotes() {
        return heuristiqueCotes;
    }

    public void setHeuristiqueCotes(boolean heuristiqueCotes) {
        this.heuristiqueCotes = heuristiqueCotes;
    }

    public boolean isHeuristiquePions() {
        return heuristiquePions;
    }

    public void setHeuristiquePions(boolean heuristiquePions) {
        this.heuristiquePions = heuristiquePions;
    }

    public boolean isHeuristiqueGagnante() {
        return heuristiqueGagnante;
    }

    public void setHeuristiqueGagnante(boolean heuristiqueGagnante) {
        this.heuristiqueGagnante = heuristiqueGagnante;
    }

    public boolean isHasConfiguration() {
        return hasConfiguration;
    }

    public void setHasConfiguration(boolean hasConfiguration) {
        this.hasConfiguration = hasConfiguration;
    }
}