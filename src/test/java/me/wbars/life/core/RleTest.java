package me.wbars.life.core;

import me.wbars.life.RleService;
import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.junit.MatcherAssert.assertThat;

public class RleTest {
    @Test
    public void testGliderFromRle() throws Exception {
        List<String> data = asList(
                "#C This is a glider.",
                "x = 3, y = 3",
                "bo$2bo$3o!"
        );
        assertThat(RleService.toGame(data).toString(), is(".*.\n..*\n***"));
    }

    @Test
    public void testGliderToRle() throws Exception {
        Game game = Game.init(".*.\n..*\n***");
        assertThat(RleService.toRle(game), is(asList(
                "x = 3, y = 3",
                "bob$2bo$3o!"
        )));
    }

}
