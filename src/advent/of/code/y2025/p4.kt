package advent.of.code.y2025

import advent.of.code.Reader
import utils.Util
import java.time.LocalDateTime

class p4 {

    companion object {
        val YEAR = "2025";
        val DAY = "4";
    }


    private var input: MutableList<String> = arrayListOf();

    private var inputAsString: String = "";

    private var firstResult = 0L;

    private var secondResult = 0L;


    fun solve() {
        val startingTime = LocalDateTime.now();
        inputAsString = Reader.readInputAsString(YEAR, DAY);
        input.addAll(Reader.readInput(YEAR, DAY));


        first();
        second();

        println("first: $firstResult");
        println("second: $secondResult");


        val now = LocalDateTime.now();
        println("${startingTime}");
        println("${now}");

    }

    private fun first() {

        var map = Util.initMap(input);

        for (i in 0..<map.size) {
            for (j in 0..<map[i].size) {
                if (map[i][j] == "@") {
                    var adjacentRollCount = adjacentRollCount(map, i, j);
                    if (adjacentRollCount < 4) {
                        firstResult++;
                    }
                }
            }
        }
    }

    private fun second() {
        var map = Util.initMap(input);
        var doWhile = true;
        while (doWhile) {


            var coords = mutableListOf<Util.Coordinate>()
            for (i in 0..<map.size) {
                for (j in 0..<map[i].size) {
                    if (map[i][j] == "@") {
                        var adjacentRollCount = adjacentRollCount(map, i, j);
                        if (adjacentRollCount < 4) {
                            secondResult++;
                            coords.add(Util.Coordinate(i, j));
                        }
                    }
                }
            }
            coords.forEach { coordinate ->
                map[coordinate.x][coordinate.y] = "X";
            }
            if (coords.isEmpty()) {
                doWhile = false;
            }
            coords.clear();
        }
    }

    private fun adjacentRollCount(map: List<List<String>>, row: Int, col: Int): Int {
        var rollCount = 0;
        if (row - 1 >= 0 && map[row - 1][col] == "@") {
            rollCount++;
        }
        if (row + 1 < map.size && map[row + 1][col] == "@") {
            rollCount++;
        }
        if (col - 1 >= 0 && map[row][col - 1] == "@") {
            rollCount++;
        }
        if (col + 1 < map[row].size && map[row][col + 1] == "@") {
            rollCount++;
        }
        if (row - 1 >= 0 && col - 1 >= 0 && map[row - 1][col - 1] == "@") {
            rollCount++;
        }
        if (row - 1 >= 0 && col + 1 < map[row].size && map[row - 1][col + 1] == "@") {
            rollCount++;
        }
        if (row + 1 < map.size && col - 1 >= 0 && map[row + 1][col - 1] == "@") {
            rollCount++;
        }
        if (row + 1 < map.size && col + 1 < map[row].size && map[row + 1][col + 1] == "@") {
            rollCount++;
        }

        return rollCount;

    }

}