package dev.gotofinal.battleships;

import java.util.Random;

public class BattleshipsInstance {
    private final Player  player;
    private final GridMap computerMap;
    private final Random  random;

    public BattleshipsInstance(Player player, GridMap computerMap, Random random) {
        this.player = player;
        this.computerMap = computerMap;
        this.random = random;
    }

    public void start() {
        this.player.displayMessage("Welcome to battleships!");

        while (true) {
            this.player.render(this.computerMap);
            Vector move = this.player.getNextMove();
            
            HitResult hitResult = this.computerMap.hit(move);

            this.handleHit(move, hitResult);
            if (hitResult.isLastShipDestroyed()) {
                return;
            }
        }
    }

    private void handleHit(Vector location, HitResult hitResult) {
        String coordinates = this.vectorToUserCoordinates(location);
        if (hitResult.isHit()) {
            String type = hitResult.getTypeOfShip().getName();
            if (hitResult.isLastShipDestroyed()) {
                this.player.displayMessage("You hit and destroyed (" + type + ") last ship of your opponent and won this" + " game!");
            }
            else if (hitResult.isShipDestroyed()) {
                this.player.displayMessage("You hit and destroyed " + type + " ship!");
            }
            else {
                this.player.displayMessage("You hit " + type + " ship!");
            }
        }
        else {
            this.player.displayMessage("You missed!");
        }
    }

    private String vectorToUserCoordinates(Vector vector) {
        char column = (char) ('A' + vector.getX());
        return column + Integer.toString(vector.getY() + 1);
    }
}
