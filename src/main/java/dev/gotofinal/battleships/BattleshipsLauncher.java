package dev.gotofinal.battleships;

import java.io.InputStreamReader;
import java.util.Random;

public class BattleshipsLauncher {
    public static void main(String[] args) {
        Battleships game = Battleships.initializeGame(new Random(1));
        
        PlayerPrinter playerPrinter = new PlayerPrinter(System.out);
        MapRenderer mapRenderer = new MapRenderer(System.out);
        InputReader inputReader = new InputReader(new InputStreamReader(System.in), playerPrinter);
        Player player = new Player(playerPrinter, mapRenderer, inputReader);
        
        game.play(player);
    }
}
