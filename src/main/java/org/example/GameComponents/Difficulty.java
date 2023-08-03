package org.example.GameComponents;

import org.example.GameComponents.Board.BoardSize;

public enum Difficulty {
    BEGINNER(10, new BoardSize(9, 9)),
    INTERMEDIATE(40, new BoardSize(16, 16)),
    EXPERT(99, new BoardSize(16, 30));

    private final int numberOfMines;
    private final BoardSize boardSize;

    Difficulty(int numberOfMines, BoardSize boardSize) {
        this.numberOfMines = numberOfMines;
        this.boardSize = boardSize;
    }

    public int getNumberOfMines() {
        return numberOfMines;
    }

    public int getNumberOfRows() {
        return boardSize.numberOfRows();
    }

    public int getNumberOfColumns() {
        return boardSize.numberOfColumns();
    }
}
