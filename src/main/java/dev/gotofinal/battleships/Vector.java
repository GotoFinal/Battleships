package dev.gotofinal.battleships;

import java.util.Objects;
import java.util.StringJoiner;

class Vector {
    private final int x;
    private final int y;

    public Vector(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }
    
    public Vector add(Vector other) {
        return new Vector(this.x + other.x, this.y + other.y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (! (o instanceof Vector)) {
            return false;
        }
        Vector vector = (Vector) o;
        return (this.x == vector.x) && (this.y == vector.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.x, this.y);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Vector.class.getSimpleName() + "[", "]")
                       .add("x=" + x)
                       .add("y=" + y)
                       .toString();
    }
}
