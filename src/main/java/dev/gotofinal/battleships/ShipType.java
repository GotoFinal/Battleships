package dev.gotofinal.battleships;

public enum ShipType {
    BATTLESHIP("Battleship", 5),
    DESTROYER("Destroyer", 4);
    
    private final String name;
    private final int size;

    ShipType(String name, int size) {
        this.name = name;
        this.size = size;
    }

    public String getName() {
        return this.name;
    }

    public int getSize() {
        return this.size;
    }
}
