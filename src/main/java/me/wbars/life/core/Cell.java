package me.wbars.life.core;

import me.wbars.life.util.Pair;

public class Cell extends Pair<Integer, Integer> {
    public Cell(Integer row, Integer col) {
        super(row, col);
    }

    public int row() {
        return f;
    }

    public int col() {
        return s;
    }
}
