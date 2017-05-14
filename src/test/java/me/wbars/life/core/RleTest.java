package me.wbars.life.core;

import me.wbars.life.RleService;
import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.junit.MatcherAssert.assertThat;

public class RleTest {
    @Test
    public void testGlider() throws Exception {
        List<String> data = asList(
                "#C This is a glider.",
                "x = 3, y = 3",
                "bo$2bo$3o!"
        );
        assertThat(RleService.toGame(data).toString(), is(".*.\n..*\n***"));
    }

    @Test
    public void testNoException() throws Exception {
        List<String> data = asList("#C c/4 diagonal switch-engine breeder",
                "#C two of Hartmut Holzwart's diagonal c/4 tub-and-beehive puffer",
                "#C and a c/2 period 392 backward rake interact to produce",
                "#C block-laying switch engines: David Bell, 23 July 2005",
                "x = 152, y = 94, rule = B3/S23",
                "77b2o$77bobo$77bo$80bo$80b2o$80b2o$79b2o$75b2ob2o$75bo4bo$75bob4o$79b",
                "2o$79b2o$79b2o$81bob3o$77bo2bo$76b5o6bo$76bo2b2o5bobo$74bobo10bo$73b3o",
                "4bo$73bo2b2o$77b3o$77b2o10bo$79bo8bobo3bo$77bobo8bobo2bobo$70b2o4b2o",
                "11bo4bo$69b2o5b2obo$71bo4b2ob2obo2b2o$76b3obobo2bobo$67b2o3b2o2b2o7bo",
                "10bo$66b2o3b2o15bo6bobo3bo$68bo2b3o14b2o5bobo2bobo$70b4o14b2o6bo4bo$",
                "69bobo15b2o$68b2o13b2ob2o$68bobo12bo4bo$67bo15bob4o14bo$68b2o17b2o13bo",
                "bo3bo$87b2o13bobo2bobo$87b2o14bo4bo$89bo$85bo2bo4bo$84b5o4bo$84bo2b2o",
                "4bo16bo$82bobo12bo11bobo3bo$81b3o4bo7bobo10bobo2bobo$81bo2b2o11bo12bo",
                "4bo$85b3o$55bobo27b2o$54bo2bo29bo$53b2o30bobo11bo17bo$52bo25b2o4b2o12b",
                "obo3bo11bobo3bo$51b4o22b2o5b2obo10bobo2bobo10bobo2bobo$50bo4bo6bo16bo",
                "4b2ob2obo8bo4bo12bo4bo$50bo2bo5b2obo21b3obobo$50bo2bo5bo15b2o3b2o2b2o$",
                "51bo7bo14b2o3b2o$52b4obo6b2o10bo2b3o24bo17bo$53bo3bo4bo4bo10b4o23bobo",
                "3bo11bobo3bo$54bo6bo15bobo25bobo2bobo10bobo2bobo$54b5o2bo5bo8b2o28bo4b",
                "o12bo4bo$61b6o9bobo$54b5o16bo$54bo21b2o$53bo3bo2bo24b2o26bo17bo$11bobo",
                "12b2o24b4obo25bo4bo23bobo3bo11bobo3bo$10bo2bo10bo4bo21bo7bob2o19bo29bo",
                "bo2bobo10bobo2bobo$9b2o7bo4bo26bo2bo5bo2bo6bo12bo5bo24bo4bo12bo4bo$8bo",
                "6b2obo4bo5bo20bo2bo5bo2bo4bobo12b6o9b2o$7b6o2bo7b6o21bo4bo4b2o6b2o5bo",
                "20b4o$4b2o7bo3b4o30b4o18bobo19b2ob2o$3bo3b3obo4bo35bo21b2o5bo14b2o22bo",
                "17bo$2bo3bo3b2obo2b2obo2bo30b2o24bobo5bo31bobo3bo11bobo3bo$2bo5b2o3bo",
                "5bo6b2o26bo2bo22b2o5bo31bobo2bobo10bobo2bobo$2b3o3b4obo7bobo2b3o26bobo",
                "27bobo32bo4bo12bo4bo$11bo9bo6bo57b2o$2b3o6bobo7bo6bo5bo73b2o$bo5bo5bo",
                "8bo4bo4bobo3b2o2b2o2b2o2b2o2b2o2b2o2b2o2b2o2b2o2b3ob2o27bo2bo3b3o$o3b",
                "2obo3b2o11b2o7b2o3b2o2b2o2b2o2b2o2b2o2b2o2b2o2b2o2b2o2b4o2bo27b2o6bo",
                "10bo17bo$o3bo6b3o62bob2o35bo10bobo3bo11bobo3bo$o3b2obo3b2o11b2o7b2o3b",
                "2o2b2o2b2o2b2o2b2o2b2o2b2o2b2o2b2o2b2o50bobo2bobo10bobo2bobo$bo5bo5bo",
                "8bo4bo4bobo3b2o2b2o2b2o2b2o2b2o2b2o2b2o2b2o2b2o2b2o34b2o15bo4bo12bo4bo",
                "$2b3o6bobo7bo6bo5bo75bo2bo$11bo9bo6bo80b2o$2b3o3b4obo7bobo2b3o78bo$2bo",
                "5b2o3bo5bo6b2o50b2o27bo10b2o14bo$2bo3bo3b2obo2b2obo2bo54b2ob2o25bo4bo",
                "5b2o13bobo3bo$3bo3b3obo4bo61b4o6b2o19b3o21bobo2bobo$4b2o7bo3b4o58b2o6b",
                "2ob3o41bo4bo$7b6o2bo7b6o59b5o$8bo6b2obo4bo5bo59b3o$9b2o7bo4bo95b2o$10b",
                "o2bo10bo4bo88b2ob4o$11bobo12b2o91b6o$120b4o!");
        System.out.println(RleService.toGame(data).toString());
    }
}