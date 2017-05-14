package me.wbars.life.core;

import java.util.*;

public class State {
    private final Set<Cell> living;
    private int minRow;
    private int minCol;
    private int maxRow;
    private int maxCol;

    public static final State DEFAULT = new State();

    public State(Set<Cell> living, State state) {
        this.living = new HashSet<>();
        this.minRow = state.minRow;
        this.minCol = state.minCol;

        this.maxRow = state.maxRow;
        this.maxCol = state.maxCol;
        for (Cell cell : living) {
            addCell(cell);
        }
    }

    private State() {
        this.living = Collections.emptySet();
        this.minRow = Integer.MAX_VALUE;
        this.minCol = Integer.MAX_VALUE;

        this.maxRow = Integer.MIN_VALUE;
        this.maxCol = Integer.MIN_VALUE;
    }

    private void updateBounds(Cell cell) {
        if (cell.row() < minRow) minRow = cell.row();
        else if (cell.row() > maxRow) maxRow = cell.row();

        if (cell.col() < minCol) minCol = cell.col();
        else if (cell.col() > maxCol) maxCol = cell.col();
    }

    @Override
    public String toString() {
        List<String> rows = new ArrayList<>(maxRow - minRow);
        for (int i = minRow; i <= maxRow; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = minCol; j <= maxCol; j++) {
                if (living.contains(new Cell(i, j))) sb.append("*");
                else sb.append(".");
            }
            rows.add(sb.toString());
        }
        return rows.stream().reduce((s, s2) -> s + "\n" + s2).orElse("");
    }

    Set<Cell> living() {
        return new HashSet<>(living);
    }

    boolean contains(Cell cell) {
        return living.contains(cell);
    }

    void addCell(Cell cell) {
        living.add(cell);
        updateBounds(cell);
    }
}
