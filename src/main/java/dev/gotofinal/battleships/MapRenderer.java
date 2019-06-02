package dev.gotofinal.battleships;

import dev.gotofinal.battleships.GridMap.Cell;

import java.io.IOException;
import java.util.function.Function;

public class MapRenderer {
    private static final char[] columnLetters = "ABCDEFGHIJ".toCharArray();

    private static final String UNKNOWN = "[]";
    private static final String HIT     = "##";
    private static final String MISS    = "  ";

    private final Appendable output;

    public MapRenderer(Appendable output) {
        this.output = output;
    }

    public void render(GridMap computerMap) {
        StringBuilder renderOutput = new StringBuilder();
        this.renderMap(renderOutput, computerMap);

        renderOutput.append(HIT).append(" - hit").append('\n')
                    .append(UNKNOWN).append(" - undiscovered field").append('\n')
                    .append(MISS).append(" - (empty) miss").append('\n')
                    .append("==================================")
                    .append('\n');

        try {
            this.output.append(renderOutput.toString());
        }
        catch (IOException e) {
            throw new RuntimeException("Failed to display rendered map", e);
        }
    }

    private void renderMap(StringBuilder renderOutput, GridMap enemyMap) {
        renderOutput.append("View of enemy map: \n");
        String[][] cellMarkers = this.buildGridView(enemyMap, this::getCellRepresentation);
        this.renderView(renderOutput, cellMarkers, GridMap.size.getX());
    }

    private String getCellRepresentation(Cell cell) {
        if (cell.isHit()) {
            return cell.hasShip() ? HIT : MISS;
        }
        return UNKNOWN;
    }

    private String[][] buildGridView(GridMap map, Function<Cell, String> cellRenderer) {
        Vector size = GridMap.size;
        String[][] result = new String[size.getY()][size.getX()];
        for (int y = 0; y < size.getY(); y++) {
            for (int x = 0; x < size.getX(); x++) {
                Cell cell = map.getCell(new Vector(x, y));
                result[y][x] = cellRenderer.apply(cell);
            }
        }
        return result;
    }

    private void renderView(StringBuilder renderOutput, String[][] cells, int columns) {
        this.renderColumnsRow(renderOutput, columns);
        for (int row = 0; row < cells.length; row++) {
            String[] cellsRow = cells[row];
            this.renderCellRow(renderOutput, row + 1, cellsRow);
        }
    }

    private void renderCellRow(StringBuilder renderOutput, int rowNumber, String[] cellsRow) {
        renderOutput.append(rowNumber);
        if (rowNumber < 10) {
            renderOutput.append(' ');
        }
        for (String cell : cellsRow) {
            renderOutput.append(' ').append(cell);
        }
        renderOutput.append(' ').append('\n');
    }

    private void renderColumnsRow(StringBuilder renderOutput, int size) {
        renderOutput.append("   ");
        for (int i = 0; i < size; i++) {
            renderOutput.append(' ').append(columnLetters[i]).append(' ');
        }
        renderOutput.append('\n');
    }
}
