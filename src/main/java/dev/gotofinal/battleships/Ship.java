package dev.gotofinal.battleships;

class Ship {
    private final Vector    location;
    private final Direction direction;
    private final ShipType  shipType;
    private       int       hits = 0;

    public Ship(Vector location, Direction direction, ShipType shipType) {
        this.location = location;
        this.direction = direction;
        this.shipType = shipType;
    }

    public int getSize() {
        return this.shipType.getSize();
    }

    public Direction getDirection() {
        return this.direction;
    }

    public Vector getLocation() {
        return this.location;
    }

    public ShipType getShipType() {
        return this.shipType;
    }

    public boolean hit() {
        if (this.isDestroyed()) {
            throw new IllegalStateException("Ship already destroyed");
        }
        this.hits += 1;
        return this.isDestroyed();
    }

    public boolean isDestroyed() {
        return this.hits == this.shipType.getSize();
    }
}
