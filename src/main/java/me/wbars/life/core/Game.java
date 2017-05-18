package me.wbars.life.core;

import java.util.*;

public class Game {
    private final Set<Cell> living;
    private int minRow;
    private int minCol;
    private int maxRow;
    private int maxCol;

    public int minRow() {
        return minRow;
    }

    public int minCol() {
        return minCol;
    }

    public int maxRow() {
        return maxRow;
    }

    public int maxCol() {
        return maxCol;
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

    public Set<Cell> living() {
        return new HashSet<>(living);
    }

    private void addCell(Cell cell) {
        living.add(cell);
        updateBounds(cell);
    }

    public Game(Set<Cell> living) {
        this.living = new HashSet<>();
        this.minRow = Integer.MAX_VALUE;
        this.minCol = Integer.MAX_VALUE;

        this.maxRow = Integer.MIN_VALUE;
        this.maxCol = Integer.MIN_VALUE;
        for (Cell cell : living) {
            addCell(cell);
        }
    }

    static Game init(String data) {
        String[] rows = data.split("\n");
        Set<Cell> living = new HashSet<>();
        for (int i = 0; i < rows.length; i++) {
            for (int j = 0; j < rows[i].length(); j++) {
                if (rows[i].charAt(j) == '*') living.add(new Cell(i, j));
            }
        }
        return new Game(living);
    }

    public void nextGeneration() {
        Set<Cell> survived = new HashSet<>();
        for (Map.Entry<Cell, Integer> e : getCellsWithNeighbours().entrySet()) {
            if (e.getValue() == 3 || living.contains(e.getKey()) && e.getValue() == 2) {
                survived.add(e.getKey());
            }
        }
        living.clear();
        survived.forEach(this::addCell);
    }

    public boolean alive(int row, int col) {
        return living.contains(new Cell(row, col));
    }

    private Map<Cell, Integer> getCellsWithNeighbours() {
        Map<Cell, Integer> neighboursCount = new HashMap<>();
        for (Cell cell : living()) {
            for (int i = cell.row() - 1; i <= cell.row() + 1; i++) {
                for (int j = cell.col() - 1; j <= cell.col() + 1; j++) {
                    if (i == cell.row() && j == cell.col()) continue;
                    neighboursCount.merge(new Cell(i, j), 1, (a, b) -> a + b);
                }
            }
        }
        return neighboursCount;
    }

    public void addCell(int row, int col) {
        addCell(new Cell(row, col));
    }
}
