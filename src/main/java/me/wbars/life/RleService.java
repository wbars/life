package me.wbars.life;

import me.wbars.life.core.Cell;
import me.wbars.life.core.Game;

import java.util.*;

import static java.lang.Integer.parseInt;

public class RleService {

    private static final int MAX_LINE_SIZE = 72;

    public static List<String> toRle(Game game) {
        int totalRows = game.maxRow() - game.minRow() + 1;
        int totalCols = game.maxCol() - game.minCol() + 1;
        List<String> res = new ArrayList<>(totalRows);
        res.add(String.format("x = %d, y = %d", totalRows, totalCols));
        StringBuilder sb = new StringBuilder();
        for (int row = game.minRow(); row <= game.maxRow(); row++) {
            int counter = 1;
            boolean alive = game.alive(row, game.minCol());
            for (int col = game.minCol() + 1; col <= game.maxCol(); col++) {
                if (game.alive(row, col) == alive) {
                    counter++;
                    continue;
                }
                if (counter > 1) sb.append(counter);
                sb.append(alive ? 'o' : 'b');
                alive = game.alive(row, col);
                counter = 1;
            }
            if (counter > 1) sb.append(counter);
            sb.append(alive ? 'o' : 'b');

            if (row == game.maxRow()) sb.append("!");
            else sb.append("$");

            if (sb.length() > MAX_LINE_SIZE) {
                res.add(sb.toString());
                sb.delete(0, Math.min(MAX_LINE_SIZE, sb.length()));
            }
        }
        if (sb.length() > 0) res.add(sb.toString());
        return res;
    }

    private static class MyIterator implements Iterator<String> {
        private final List<String> data;
        private int i = 0;

        private MyIterator(List<String> data) {
            this.data = data;
        }

        @Override
        public boolean hasNext() {
            return i < data.size();
        }

        @Override
        public String next() {
            return data.get(i++);
        }

        public String peek() {
            return data.get(i);
        }
    }

    private RleService() {
    }

    public static Game toGame(List<String> data) {
        MyIterator rows = new MyIterator(data);

        while (rows.hasNext() && (rows.peek().startsWith("#") || rows.peek().isEmpty())) {
            rows.next();
        }
        String[] split = rows.next().split(",");
        int x = parseInt(split[0].split("=")[1].trim());
        int y = parseInt(split[1].split("=")[1].trim());

        int i = 0;
        int j = 0;
        Set<Cell> cells = new HashSet<>();
        while (rows.hasNext()) {
            String next = rows.next();
            int end = 0;
            int start = 0;
            while (end < next.length()) {
                if (next.charAt(end) == '!') break;
                int count;
                if (!Character.isDigit(next.charAt(end))) {
                    count = 1;
                } else {
                    while (Character.isDigit(next.charAt(end))) end++;
                    count = parseInt(next.substring(start, end));
                }

                if (next.charAt(end) == 'o') {
                    for (int k = 0; k < count; k++, j++) cells.add(new Cell(i, j));
                } else j += count;
                end++;

                if (end < next.length() && next.charAt(end) == '$') {
                    j = 0;
                    i++;
                    end++;
                }
                start = end;
            }
        }
        return new Game(cells);

    }
}
