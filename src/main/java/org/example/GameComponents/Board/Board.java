package org.example.GameComponents.Board;

import org.example.GameComponents.Difficulty;
import org.example.GameComponents.Move.Move;
import org.example.GameComponents.Move.MoveType;

import java.util.Random;
import java.util.Stack;

public class Board {
    private Cell[][] gameBoard;
    private final Difficulty difficulty;
    private boolean isOver = false;
    private boolean gameWon = false;
    private int numberOfHiddenCells;
    private boolean firstMoveDone = false;

    public Board(Difficulty difficulty) {
        this.difficulty = difficulty;
        this.numberOfHiddenCells = difficulty.getNumberOfColumns() * difficulty.getNumberOfRows();

        initializeBoard(this.difficulty);
    }

    private void initializeBoard(Difficulty difficulty) {
        gameBoard = new Cell[difficulty.getNumberOfRows()][difficulty.getNumberOfColumns()];

        createCells();
    }

    private void createCells() {
        for(int row = 0; row < gameBoard.length; row++)
            for(int col = 0; col < gameBoard[row].length; col++)
                gameBoard[row][col] = new Cell();
    }

    public boolean isOver() {
        return isOver;
    }

    public void makeMove(Move move) {
        if(move.getMoveType() == MoveType.LEFT_CLICK)
            makeLeftClickMove(move);
        else
            makeRightClickMove(move);

        checkIfWon();
    }

    private void checkIfWon() {
        if(numberOfHiddenCells != difficulty.getNumberOfMines())
            return;

        isOver = true;
        gameWon = true;

        for (Cell[] rows : gameBoard)
            for (Cell cell : rows)
                if (cell.isHidden()) cell.setMarked(true);
    }

    private void makeLeftClickMove(Move move) {
        if(gameBoard[move.getXCoordinates()][move.getYCoordinates()].isMarked())
            return;

        if(!gameBoard[move.getXCoordinates()][move.getYCoordinates()].isHidden()) {
            if(gameBoard[move.getXCoordinates()][move.getYCoordinates()].getNumberOfNeighbourMines() == 0)
                return;

            discoverMap(move);
            return;
        }

        gameBoard[move.getXCoordinates()][move.getYCoordinates()].setHidden(false);
        numberOfHiddenCells--;

        checkFirstMove();

        if(gameBoard[move.getXCoordinates()][move.getYCoordinates()].isMine()) {
            isOver = true;
            return;
        }

        if(gameBoard[move.getXCoordinates()][move.getYCoordinates()].getNumberOfNeighbourMines() == 0)
            dfsZero(move);
    }

    public void checkFirstMove() {
        if(firstMoveDone)
            return;

        createMines();

        countNeighbours();

        firstMoveDone = true;
    }

    public void createMines() {
        Random random = new Random();

        int numberOfBombs = difficulty.getNumberOfMines();

        while(numberOfBombs > 0) {
            int xCoordinates = random.nextInt(gameBoard.length);
            int yCoordinates = random.nextInt(gameBoard[xCoordinates].length);

            if(!gameBoard[xCoordinates][yCoordinates].isHidden() || gameBoard[xCoordinates][yCoordinates].isMine())
                continue;

            gameBoard[xCoordinates][yCoordinates].setMine(true);

            numberOfBombs--;
        }
    }

    public void countNeighbours() {
        for(int row = 0 ; row < gameBoard.length; row++)
            for(int col = 0; col < gameBoard[row].length; col++)
                gameBoard[row][col].setNumberOfNeighbourMines(countCellNeighbours(row, col));
    }

    public int countCellNeighbours(int xCoordinates, int yCoordinates) {
        int numberOfMines = 0;

        for(int row = xCoordinates - 1; row <= xCoordinates + 1; row++)
            for(int col = yCoordinates - 1; col <= yCoordinates + 1; col++) {
                if(row < 0 || row >= gameBoard.length || col < 0 || col >= gameBoard[row].length)
                    continue;

                if(gameBoard[row][col].isMine())
                    numberOfMines++;
            }

        return numberOfMines;
    }

    private void makeRightClickMove(Move move) {
        if(gameBoard[move.getXCoordinates()][move.getYCoordinates()].isHidden())
            gameBoard[move.getXCoordinates()][move.getYCoordinates()].setMarked(!gameBoard[move.getXCoordinates()][move.getYCoordinates()].isMarked());
    }

    private int numberOfMarks(Move move) {
        int numberOfMarks = 0;

        for(int row = move.getXCoordinates() - 1; row <= move.getXCoordinates() + 1; row++)
            for(int col = move.getYCoordinates() - 1; col <= move.getYCoordinates() + 1; col++) {
                if(row < 0 || row >= gameBoard.length || col < 0 || col >= gameBoard[0].length)
                    continue;

                if(gameBoard[row][col].isMarked())
                    numberOfMarks++;
            }

        return numberOfMarks;
    }

    public void discoverMap(Move move) {
        if(numberOfMarks(move) != gameBoard[move.getXCoordinates()][move.getYCoordinates()].getNumberOfNeighbourMines())
            return;

        Stack<Move> zerosToReveal = new Stack<>();

        for(int row = move.getXCoordinates() - 1; row <= move.getXCoordinates() + 1; row++)
            for(int col = move.getYCoordinates() - 1; col <= move.getYCoordinates() + 1; col++) {
                if(row < 0 || row >= gameBoard.length || col < 0 || col >= gameBoard[0].length)
                    continue;

                if(gameBoard[row][col].isMarked())
                    continue;

                if(!gameBoard[row][col].isHidden())
                    continue;

                gameBoard[row][col].setHidden(false);
                numberOfHiddenCells--;

                if(gameBoard[row][col].isMine())
                    isOver = true;

                if(gameBoard[row][col].getNumberOfNeighbourMines() == 0)
                    zerosToReveal.push(new Move(row, col));
            }

        if(isOver)
            return;

        while(!zerosToReveal.isEmpty())
            dfsZero(zerosToReveal.pop());
    }

    public void dfsZero(Move move) {
        for(int row = move.getXCoordinates() - 1; row <= move.getXCoordinates() + 1; row++)
            for(int col = move.getYCoordinates() - 1; col <= move.getYCoordinates() + 1; col++) {
                if(row < 0 || row >= gameBoard.length || col < 0 || col >= gameBoard[0].length)
                    continue;

                if(!gameBoard[row][col].isHidden())
                    continue;

                gameBoard[row][col].setHidden(false);
                numberOfHiddenCells--;

                if(gameBoard[row][col].getNumberOfNeighbourMines() == 0)
                    dfsZero(new Move(row, col));
            }
    }

    public void printGameEndState() {
        if(gameWon)
            System.out.println("You won!");
        else System.out.println("Game Over!");

        System.out.println();
    }

    public void printBoard() {
        for (Cell[] row : gameBoard) {
            for (Cell cell : row) {
                if(cell.isMarked()) {
                    System.out.print("? ");
                    continue;
                }

                if(cell.isHidden()) {
                    System.out.print("# ");
                    continue;
                }

                if(cell.isMine())
                    System.out.print("x ");
                else
                    System.out.print(cell.getNumberOfNeighbourMines() + " ");
            }

            System.out.println();
        }

        System.out.println();
    }
}
