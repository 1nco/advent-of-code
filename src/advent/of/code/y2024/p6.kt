package advent.of.code.y2024

import advent.of.code.Reader
import utils.Util.Companion.createCopy
import utils.Util.Companion.initMap
import java.util.*

class p6 {

    companion object {
        val YEAR = "2024";
        val DAY = "6";
    }

    private var input: MutableList<String> = arrayListOf();

    private var firstResult = 0L;

    private var secondResult = 0L;


    fun solve() {
        val startingTime = Date();
//        input.add(Reader.readInputAsString(YEAR, DAY));
        input.addAll(Reader.readInput(YEAR, DAY));

        first();

        second();

        println("first: $firstResult");
        println("second: $secondResult");


        println(startingTime);
        println(Date());

    }

    private fun first() {
        var map = initMap(input);

        var through = false;
        var positions: MutableList<String> = arrayListOf();

        var i = 0;
        var j = 0;

        for (line in 0..<map.size) {
            for (column in 0..<(map[0].size)) {
                if (map[line][column] == "^") {
                    i = line;
                    j = column;
                }
            }
        }
        positions.add("$i;$j");
        while (!through) {
            if (i == 0 || i == map.size - 1 || j == 0 || j == map[0].size - 1) {
                through = true;
            } else {
                if (map[i][j] == "^") {
                    if (map[i - 1][j] == "#") {
                        map[i][j] = ">"
                    } else {
                        map[i - 1][j] = "^";
                        map[i][j] = ".";
                        i = i - 1;
                        positions.add("$i;$j");
                    }
                }
                if (map[i][j] == ">") {
                    if (map[i][j + 1] == "#") {
                        map[i][j] = "v"
                    } else {
                        map[i][j + 1] = ">";
                        map[i][j] = ".";
                        j = j + 1
                        positions.add("$i;$j");
                    }
                }
                if (map[i][j] == "<") {
                    if (map[i][j - 1] == "#") {
                        map[i][j] = "^"
                    } else {
                        map[i][j - 1] = "<";
                        map[i][j] = ".";
                        j = j - 1;
                        positions.add("$i;$j");
                    }
                }
                if (map[i][j] == "v") {
                    if (map[i + 1][j] == "#") {
                        map[i][j] = "<"
                    } else {
                        map[i + 1][j] = "v";
                        map[i][j] = ".";
                        i = i + 1;
                        positions.add("$i;$j");
                    }
                }
            }
        }
        println(positions.distinct())
        firstResult = positions.distinct().count().toLong();
    }


    private fun second() {
        var map = initMap(input);

        for (line in 0..<map.size) {
            for (column in 0..<(map[0].size)) {

                var mapCopy = initMap(input);
                mapCopy[line][column] = "#";
                if (hasLoop(mapCopy)) {
                    secondResult++;
                }

            }
        }

    }

    private fun hasLoop(map: MutableList<MutableList<String>>): Boolean {
        var through = false;
        var positions: MutableList<String> = arrayListOf();

        var i = 0;
        var j = 0;

        for (line in 0..<map.size) {
            for (column in 0..<(map[0].size)) {
                if (map[line][column] == "^") {
                    i = line;
                    j = column;
                }
            }
        }
        positions.add("$i;$j;^");
        var iterations = 0;
        var end = false
        while (!end) {
            if (i == 0 || i == map.size - 1 || j == 0 || j == map[0].size - 1) {
                through = true;
                end = true;
            } else {
                if (map[i][j] == "^") {
                    if (map[i - 1][j] == "#") {
                        map[i][j] = ">"
                        positions.add("$i;$j;>");
                    } else {
                        map[i - 1][j] = "^";
                        map[i][j] = ".";
                        i = i - 1;

                        if (positions.contains("$i;$j;^")) {
                            through = false;
                            end = true;
                        }
                        positions.add("$i;$j;^");
                    }
                }
                if (map[i][j] == ">") {
                    if (map[i][j + 1] == "#") {
                        map[i][j] = "v"
                        positions.add("$i;$j;v");
                    } else {
                        map[i][j + 1] = ">";
                        map[i][j] = ".";
                        j = j + 1

                        if (positions.contains("$i;$j;>")) {
                            through = false;
                            end = true;
                        }
                        positions.add("$i;$j;>");

                    }
                }
                if (map[i][j] == "<") {
                    if (map[i][j - 1] == "#") {
                        map[i][j] = "^"
                        positions.add("$i;$j;^")
                    } else {
                        map[i][j - 1] = "<";
                        map[i][j] = ".";
                        j = j - 1;

                        if (positions.contains("$i;$j;<")) {
                            through = false;
                            end = true;
                        }
                        positions.add("$i;$j;<");

                    }
                }
                if (map[i][j] == "v") {
                    if (map[i + 1][j] == "#") {
                        map[i][j] = "<"
                        positions.add("$i;$j;<");
                    } else {
                        map[i + 1][j] = "v";
                        map[i][j] = ".";
                        i = i + 1;

                        if (positions.contains("$i;$j;v")) {
                            through = false;
                            end = true;
                        }

                        positions.add("$i;$j;v");

                    }
                }
            }
            iterations++;
        }
        return !through;
    }
}