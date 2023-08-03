package org.example.GameComponents.Board;

public class Cell {
    private boolean isMine = false;
    private boolean isHidden = true;
    private boolean isMarked = false;
    private int numberOfNeighbourMines = 0;

    public boolean isMine() {
        return isMine;
    }

    public void setMine(boolean mine) {
        isMine = mine;
    }

    public int getNumberOfNeighbourMines() {
        return numberOfNeighbourMines;
    }

    public void setNumberOfNeighbourMines(int numberOfNeighbourMines) {
        this.numberOfNeighbourMines = numberOfNeighbourMines;
    }

    public boolean isHidden() {
        return isHidden;
    }

    public void setHidden(boolean hidden) {
        isHidden = hidden;
    }

    public boolean isMarked() {
        return isMarked;
    }

    public void setMarked(boolean marked) {
        isMarked = marked;
    }
}
