package org.example.SystemInReader;

import org.example.GameComponents.Difficulty;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class DifficultyReader implements InputReader<Difficulty> {
    @Override
    public Difficulty read() {
        System.out.println("difficulties: beginner (B), intermediate (I), expert (E)");
        System.out.print("Enter difficulty: ");

        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));

        String inputString = "";

        try {
            inputString = bufferRead.readLine();
        } catch (IOException e) {
            System.out.println(e);
        }

        System.out.println();

        return switch (inputString) {
            case "I" -> Difficulty.INTERMEDIATE;
            case "E" -> Difficulty.EXPERT;
            default -> Difficulty.BEGINNER;
        };
    }
}
