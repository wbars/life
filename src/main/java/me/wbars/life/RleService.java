package me.wbars.life;

import me.wbars.life.core.Cell;
import me.wbars.life.core.Game;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static java.lang.Integer.parseInt;

public class RleService {
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
