package dev.gotofinal.battleships;

import java.util.LinkedHashMap;
import java.util.Random;

public class Battleships {
    private final GridRandomizer gridRandomizer;
    private final GridMap        computerMap;
    private final Random         random;

    private Battleships(GridRandomizer gridRandomizer, GridMap computerMap, Random random) {
        this.gridRandomizer = gridRandomizer;
        this.computerMap = computerMap;
        this.random = random;
    }

    public static Battleships initializeGame(Random random) {
        GridMap computerMap = new GridMap();

        LinkedHashMap<ShipType, Integer> shipsToPlace = new LinkedHashMap<>();
        shipsToPlace.put(ShipType.DESTROYER, 2);
        shipsToPlace.put(ShipType.BATTLESHIP, 1);
        
        GridRandomizer gridRandomizer = new GridRandomizer(shipsToPlace, random);
        return new Battleships(gridRandomizer, computerMap, random);
    }

    public void play(Player player) {
        this.gridRandomizer.placeShips(this.computerMap);
        BattleshipsInstance battleshipsInstance = new BattleshipsInstance(player, this.computerMap, this.random);
        battleshipsInstance.start();
    }
}
