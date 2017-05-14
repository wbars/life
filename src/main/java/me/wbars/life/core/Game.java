package me.wbars.life.core;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Game {
    private State state;

    public Game(Set<Cell> living) {
        state = new State(living, State.DEFAULT);
    }

    public static Game init(String data) {
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
            if (e.getValue() == 3 || state.contains(e.getKey()) && e.getValue() == 2) {
                survived.add(e.getKey());
            }
        }
        this.state = new State(survived, state);
    }

    private Map<Cell, Integer> getCellsWithNeighbours() {
        Map<Cell, Integer> neighboursCount = new HashMap<>();
        for (Cell cell : state.living()) {
            for (int i = cell.row() - 1; i <= cell.row() + 1; i++) {
                for (int j = cell.col() - 1; j <= cell.col() + 1; j++) {
                    if (i == cell.row() && j == cell.col()) continue;
                    neighboursCount.merge(new Cell(i, j), 1, (a, b) -> a + b);
                }
            }
        }
        return neighboursCount;
    }

    public Set<Cell> living() {
        return state.living();
    }

    @Override
    public String toString() {
        return state.toString();
    }

    public void addCell(int row, int col) {
        state.addCell(new Cell(row, col));
    }
}
