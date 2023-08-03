package org.example.SystemInReader;

import org.example.GameComponents.Move.Move;
import org.example.GameComponents.Move.MoveType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MoveReader implements InputReader<Move> {
    @Override
    public Move read() {
        System.out.print("Make move: ");

        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));

        Move move = new Move();

        try {
            String moveAsString = bufferRead.readLine();

            String[] coords = moveAsString.split("-");

            move.setXCoordinates(Integer.parseInt(coords[0]));
            move.setYCoordinates(Integer.parseInt(coords[1]));

            switch (coords[2]) {
                case "l" -> move.setMoveType(MoveType.LEFT_CLICK);
                case "r" -> move.setMoveType(MoveType.RIGHT_CLICK);
            }

        } catch (IOException e) {
            System.out.println(e);
        }

        System.out.println();

        return  move;
    }
}
