package dev.gotofinal.battleships;

import java.util.Optional;

public class Player {
    private final PlayerPrinter playerPrinter;
    private final MapRenderer   renderer;
    private final InputReader   inputReader;

    Player(PlayerPrinter playerPrinter, MapRenderer renderer, InputReader inputReader) {
        this.playerPrinter = playerPrinter;
        this.renderer = renderer;
        this.inputReader = inputReader;
    }

    public void render(GridMap computerMap) {
        this.renderer.render(computerMap);
    }

    public void displayMessage(String message) {
        this.playerPrinter.display(">> " + message);
    }

    public Vector getNextMove() {
        Optional<Vector> nextPosition = this.inputReader.nextPosition();
        while (nextPosition.isEmpty()) {
            nextPosition = this.inputReader.nextPosition();
        }
        return nextPosition.get();
    }
}
