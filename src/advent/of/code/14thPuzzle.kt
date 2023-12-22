package advent.of.code

import utils.Util
import java.util.*

object `14thPuzzle` {

    private const val DAY = "14";

    private var input: MutableList<String> = arrayListOf();

    private var result = 0L;

    private var resultSecond = 0L;

    fun solve() {
        val startingTime = Date();
        input.addAll(Reader.readInput(DAY));

        first();

        second();

        println("first: $result");
        println("second: $resultSecond");


        println(startingTime);
        println(Date());

    }

    fun first() {
        var map = Util.createCopy(Util.initMap(input));
        map = tiltMapNorth(map);
        Util.saveMapToFile(map, "test_14");
        result = countLoad(map);
    }

    fun second() {
//        val cycleCount = 1000000000;
        val cycleCount = 1000;
        var map: MutableList<MutableList<String>> = Util.createCopy(Util.initMap(input));

        println(Date());
        for (i in 0..<cycleCount) {
            map = tiltMapEast(tiltMapSouth(tiltMapWest(tiltMapNorth(map))))

            if (i % 1000000 == 0) {
                println(i);
                println(Date());
            }
        }
        Util.saveMapToFile(map, "test_14_2");
        resultSecond = countLoad(map)
    }

    private fun tiltMapNorth(map: MutableList<MutableList<String>>): MutableList<MutableList<String>> {


        for (i in 0..<map.size) {
            for (j in 0..<map[0].size) {
                var k = 1;

                var canMove = true;
                while ((i - k) >= 0 && canMove) {
                    if (map[i - k + 1][j] == "O" && map[i - k][j] == ".") {
                        map[i - k + 1][j] = "."
                        map[i - k][j] = "O"
                    } else {
                        canMove = false;
                    }
                    k++;
                }
            }
        }

        return map;
    }

    private fun tiltMapSouth(map: MutableList<MutableList<String>>): MutableList<MutableList<String>> {
//        val tiltedMap = Util.createCopy(map);


        for (i in map.size - 1 downTo 0) {
            for (j in map[0].size - 1 downTo 0) {
                var k = 1;

                var canMove = true;
                while ((i + k) < map.size && canMove) {
                    if (map[i + k - 1][j] == "O" && map[i + k][j] == ".") {
                        map[i + k - 1][j] = "."
                        map[i + k][j] = "O"
                    } else {
                        canMove = false;
                    }
                    k++;
                }
            }
        }

        return map;
    }

    private fun tiltMapWest(map: MutableList<MutableList<String>>): MutableList<MutableList<String>> {

        for (j in 0..<map[0].size) {
            for (i in 0..<map.size) {
                var k = 1;

                var canMove = true;
                while ((j - k) >= 0 && canMove) {
                    if (map[i][j - k + 1] == "O" && map[i][j - k] == ".") {
                        map[i][j - k + 1] = "."
                        map[i][j - k] = "O"
                    } else {
                        canMove = false;
                    }
                    k++;
                }
            }
        }

        return map;
    }

    private fun tiltMapEast(map: MutableList<MutableList<String>>): MutableList<MutableList<String>> {

            for (i in map.size - 1 downTo 0) {
                for (j in map[0].size - 1 downTo 0) {
                var k = 1;

                var canMove = true;
                while ((j + k) < map[0].size && canMove) {
                    if (map[i][j + k - 1] == "O" && map[i][j + k] == ".") {
                        map[i][j + k - 1] = "."
                        map[i][j + k] = "O"
                    } else {
                        canMove = false;
                    }
                    k++;
                }
            }
        }

        return map;
    }

    private fun countLoad(map: MutableList<MutableList<String>>): Long {
        var load = 0L;
        for (i in 0..<map.size) {
            for (j in 0..<map[0].size) {
                if (map[i][j] == "O") {
                    load += map.size - i;
                }
            }
        }
        return load;
    }
}