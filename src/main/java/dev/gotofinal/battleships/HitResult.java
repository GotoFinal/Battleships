package dev.gotofinal.battleships;

class HitResult {
    static final HitResult MISS = new HitResult(null, false);

    private final Ship    ship;
    private final boolean lastShipDestroyed;

    HitResult(Ship ship, boolean lastShipDestroyed) {
        this.ship = ship;
        this.lastShipDestroyed = lastShipDestroyed;
    }

    public boolean isLastShipDestroyed() {
        return this.lastShipDestroyed;
    }

    public boolean isShipDestroyed() {
        if (! this.isHit()) {
            throw new IllegalStateException("Ship not hit");
        }
        return this.ship.isDestroyed();
    }

    public boolean isHit() {
        return this.ship != null;
    }

    public ShipType getTypeOfShip() {
        if (! this.isHit()) {
            throw new IllegalStateException("Ship not hit");
        }
        return this.ship.getShipType();
    }
}
