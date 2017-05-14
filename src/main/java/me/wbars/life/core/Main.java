package me.wbars.life.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.lang.System.out;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        List<String> rows = new ArrayList<>();
        while (true) {
            String row = in.nextLine();
            if (row.isEmpty()) break;
            rows.add(row);
        }
        String init = rows.stream().reduce((s, s2) -> s + "\n" + s2).orElse("");
        Game game = Game.init(init);
        while (true) {
            out.println(game.toString());
            out.println();
            out.println();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            game.nextGeneration();
        }
    }
}
