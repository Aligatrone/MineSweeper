package org.example;

import org.example.GameComponents.Board.Board;
import org.example.GameComponents.Difficulty;
import org.example.GameComponents.Move.Move;
import org.example.SystemInReader.DifficultyReader;
import org.example.SystemInReader.MoveReader;

public class Main {
    public static void main(String[] args) {
        while(true) {
            Difficulty gameDifficulty = new DifficultyReader().read();

            Board board = new Board(gameDifficulty);

            while(!board.isOver()) {
                board.printBoard();

                Move move = new MoveReader().read();

                board.makeMove(move);
            }

            board.printBoard();

            board.printGameEndState();
        }
    }
}
