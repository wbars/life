package me.wbars.life.core;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.junit.MatcherAssert.assertThat;

public class CoreTest {
    private void doTest(String initial, String... generations) {
        Game game = Game.init(initial);
        for (String generation : generations) {
            game.nextGeneration();
            assertThat(game.toString(), is(generation));
        }
    }

    @Test
    public void testStable() throws Exception {
        doTest("**\n**",
                "**\n**");
    }

    @Test
    public void testBlinker() throws Exception {
        doTest("***",
                ".*.\n.*.\n.*.", "...\n***\n...", ".*.\n.*.\n.*.", "...\n***\n...");
    }

    @Test
    public void testGlider() throws Exception {
        doTest(".*.\n..*\n***",
                "...\n*.*\n.**\n.*.", "...\n..*\n*.*\n.**", "....\n.*..\n..**\n.**.", "....\n..*.\n...*\n.***");
    }
}
