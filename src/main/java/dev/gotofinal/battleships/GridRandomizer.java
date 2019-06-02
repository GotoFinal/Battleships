package dev.gotofinal.battleships;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static dev.gotofinal.battleships.GridMap.size;

// This could be done better, but we don't need anything advanced to place 3 ships on random positions.
class GridRandomizer {
    private static final int MAX_TRIES = 100;

    private final Map<ShipType, Integer> shipsToPlace;
    private final Random                 random;

    public GridRandomizer(LinkedHashMap<ShipType, Integer> shipsToPlace, Random random) {
        this.shipsToPlace = shipsToPlace;
        this.random = random;
    }

    public void placeShips(GridMap gridMap) {
        this.shipsToPlace.forEach((shipType, amount) -> {
            for (Integer i = 0; i < amount; i++) {
                this.placeRandomShip(gridMap, shipType);
            }
        });
    }

    private void placeRandomShip(GridMap gridMap, ShipType shipType) {
        for (int i = 0; i < MAX_TRIES; i++) {
            if (this.tryToPlaceRandomShip(gridMap, shipType)) {
                return;
            }
        }
        throw new IllegalStateException("Can't place all ships on the map");
    }

    private boolean tryToPlaceRandomShip(GridMap gridMap, ShipType type) {
        Vector randomVector = this.randomVectorInsideGrid();
        List<Direction> directions = new ArrayList<>(List.of(Direction.values()));
        boolean successful;
        do {
            Direction randomDirection = this.randomDirection(directions);
            successful = gridMap.tryToPlaceShip(randomVector, type, randomDirection);
        }
        while (! successful && ! directions.isEmpty());
        return successful;
    }

    private Direction randomDirection(List<Direction> directions) {
        return directions.remove(this.random.nextInt(directions.size()));
    }

    private Vector randomVectorInsideGrid() {
        int x = this.random.nextInt(size.getX());
        int y = this.random.nextInt(size.getY());
        return new Vector(x, y);
    }
}
