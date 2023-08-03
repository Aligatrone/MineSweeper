package org.example.GameComponents.Move;

public class Move {
    private int xCoordinates;
    private int yCoordinates;

    private MoveType moveType;

    public Move() {
    }

    public Move(int xCoordinates, int yCoordinates) {
        this.xCoordinates = xCoordinates;
        this.yCoordinates = yCoordinates;
    }

    public int getXCoordinates() {
        return xCoordinates;
    }

    public void setXCoordinates(int xCoordinates) {
        this.xCoordinates = xCoordinates;
    }

    public int getYCoordinates() {
        return yCoordinates;
    }

    public void setYCoordinates(int yCoordinates) {
        this.yCoordinates = yCoordinates;
    }

    public MoveType getMoveType() {
        return moveType;
    }

    public void setMoveType(MoveType moveType) {
        this.moveType = moveType;
    }
}
