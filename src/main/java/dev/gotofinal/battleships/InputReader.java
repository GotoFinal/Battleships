package dev.gotofinal.battleships;

import java.util.Optional;
import java.util.Scanner;

public class InputReader {
    private static final Vector maxVector     = GridMap.size;
    private static final String INVALID_INPUT = "Invalid input!";

    private final Scanner       input;
    private final PlayerPrinter playerPrinter;

    public InputReader(Readable readable, PlayerPrinter playerPrinter) {
        this.input = new Scanner(readable);
        this.playerPrinter = playerPrinter;
    }

    public Optional<Vector> nextPosition() {
        this.playerPrinter.display("Choose field (ex: C7): ");
        String input = this.input.nextLine();
        if ((input.length() > 3) || (input.length() < 2)) {
            this.playerPrinter.display(INVALID_INPUT);
            return Optional.empty();
        }
        int column = Character.toUpperCase(input.charAt(0)) - 'A';
        String row = input.substring(1);
        int rowNumber;

        try {
            rowNumber = Integer.parseInt(row) - 1;
        }
        catch (NumberFormatException e) {
            this.playerPrinter.display(INVALID_INPUT);
            return Optional.empty();
        }

        if ((column > maxVector.getY()) || (column < 0) || (rowNumber > maxVector.getY()) || (rowNumber < 0)) {
            this.playerPrinter.display(INVALID_INPUT);
            return Optional.empty();
        }

        return Optional.of(new Vector(column, rowNumber));
    }
}
