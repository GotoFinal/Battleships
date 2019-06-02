package dev.gotofinal.battleships;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.UnaryOperator;

class GridMap {
    public static final Vector size = new Vector(10, 10);

    private final Cell[][]   grid;
    private final List<Ship> ships = new ArrayList<>();

    public GridMap() {
        this.grid = new Cell[size.getY()][size.getX()];
        for (int y = 0; y < size.getY(); y++) {
            Arrays.fill(this.grid[y], Cell.EMPTY);
        }
    }

    public Cell getCell(Vector location) {
        return this.grid[location.getY()][location.getX()];
    }

    public boolean tryToPlaceShip(Vector location, ShipType shipType, Direction direction) {
        if (this.isOutsideGrid(location)) {
            return false;
        }
        Ship ship = new Ship(location, direction, shipType);
        if (! this.doesShipFitOnMap(ship)) {
            return false;
        }
        this.placeShip(ship);
        return true;
    }

    public HitResult hit(Vector location) {
        Cell cell = this.getCell(location);
        if (cell.isHit()) {
            return HitResult.MISS;
        }
        Cell hitCell = cell.hit();
        this.setCell(location, hitCell);
        if (! hitCell.hasShip()) {
            return HitResult.MISS;
        }
        return new HitResult(hitCell.ship, ! this.anyShipsLeft());
    }

    private boolean anyShipsLeft() {
        for (Ship ship : this.ships) {
            if (! ship.isDestroyed()) {
                return true;
            }
        }
        return false;
    }

    private void placeShip(Ship ship) {
        Vector shipPartLocation = ship.getLocation();
        for (int i = 0; i < ship.getSize(); i++) {
            this.transformCell(shipPartLocation, gridCell -> gridCell.placeShip(ship));
            shipPartLocation = shipPartLocation.add(ship.getDirection().asVector());
        }
        this.ships.add(ship);
    }

    private void setCell(Vector location, Cell newCell) {
        this.grid[location.getY()][location.getX()] = newCell;
    }

    private void transformCell(Vector location, UnaryOperator<Cell> gridCellTransform) {
        this.setCell(location, gridCellTransform.apply(this.getCell(location)));
    }

    private boolean doesShipFitOnMap(Ship ship) {
        Vector shipPartLocation = ship.getLocation();
        for (int i = 0; i < ship.getSize(); i++) {
            if (this.isOutsideGrid(shipPartLocation)) {
                return false;
            }
            if (this.isCellOccupied(shipPartLocation)) {
                return false;
            }
            shipPartLocation = shipPartLocation.add(ship.getDirection().asVector());
        }
        return true;
    }

    private boolean isCellOccupied(Vector location) {
        return this.grid[location.getY()][location.getX()].hasShip();
    }

    private boolean isOutsideGrid(Vector location) {
        return (location.getX() >= size.getX()) || (location.getX() < 0) ||
                       (location.getY() >= size.getY()) || (location.getY() < 0);
    }

    public static class Cell {
        private static final Cell EMPTY = new Cell(null, false);

        private final Ship    ship;
        private final boolean hit;

        public Cell(Ship ship, boolean hit) {
            this.ship = ship;
            this.hit = hit;
        }

        public boolean hasShip() {
            return this.ship != null;
        }

        public Ship getShip() {
            if (! this.hasShip()) {
                throw new IllegalStateException("There is no ship on this cell");
            }
            return this.ship;
        }

        public boolean isHit() {
            return this.hit;
        }

        private Cell placeShip(Ship ship) {
            return new Cell(ship, false);
        }

        private Cell hit() {
            if (this.hit) {
                return this;
            }
            if (this.ship != null) {
                this.ship.hit();
            }
            return new Cell(this.ship, true);
        }
    }
}
