package dev.gotofinal.battleships;

import java.io.IOException;

public class PlayerPrinter {
    private final Appendable appendable;

    public PlayerPrinter(Appendable appendable) {
        this.appendable = appendable;
    }

    public void display(String message) {
        try {
            this.appendable.append(message + "\n");
        }
        catch (IOException e) {
            throw new RuntimeException("Failed to display message", e);
        }
    }
}
