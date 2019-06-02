package dev.gotofinal.battleships;

enum Direction {
    UP(new Vector(0, 1)),
    DOWN(new Vector(0, - 1)),
    LEFT(new Vector(- 1, 0)),
    RIGHT(new Vector(1, 0));
    private final Vector directionVector;

    Direction(Vector directionVector) {
        this.directionVector = directionVector;
    }

    public Vector asVector() {
        return this.directionVector;
    }
}
