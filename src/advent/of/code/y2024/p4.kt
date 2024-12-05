package advent.of.code.y2024

import advent.of.code.Reader
import utils.Util.Companion.initMap
import java.util.*


class p4 {

    companion object {
        val YEAR = "2024";
        val DAY = "4";
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
        input.forEach {
            firstResult += countOccurrences(it, "XMAS")
            firstResult += countOccurrences(it.reversed(), "XMAS")
        }

        for (j in 0..<map[0].size) {
            var column = "";
            for (i in 0..<map.size) {
                column += map[i][j];
            }
            println(column);
            firstResult += countOccurrences(column, "XMAS");
            firstResult += countOccurrences(column.reversed(), "XMAS");
        }

        for (diagnum in -map.size..<map.size) {
            var diag = ""
            for (i in 0..<map.size) {

                for (j in 0..<map[0].size) {
                    // Condition for principal diagonal

                    if (i - diagnum == j) {
                        diag += map[i][j];
                    }
                }
            }

            println(diag)
            firstResult += countOccurrences(diag, "XMAS");
            firstResult += countOccurrences(diag.reversed(), "XMAS");
        }

        for (diagnum in -map.size..<map.size) {
            var diag = ""
            for (i in 0..<map.size) {
                for (j in 0..<map[0].size) {

                    // Condition for principal diagonal
                    if ((i + j - diagnum) == (map.size - 1)) {
                        diag += map[i][j];
                    }
                }
            }

            println(diag)
            firstResult += countOccurrences(diag, "XMAS");
            firstResult += countOccurrences(diag.reversed(), "XMAS");
        }

    }

    fun countOccurrences(str: String, searchStr: String): Int {
        var count = 0
        var startIndex = 0

        while (startIndex < str.length) {
            val index = str.indexOf(searchStr, startIndex)
            if (index >= 0) {
                count++
                startIndex = index + searchStr.length
            } else {
                break
            }
        }

        return count
    }


    private fun second() {
        var map = initMap(input);
        for (i in 0..<map.size) {
            for (j in 0..<map[0].size) {

                if (map[i][j] == "A") {
                    if (i - 1 >= 0 && j - 1 >= 0 && i + 1 < map.size && j + 1 < map[0].size) {
                        if (
                            ((map[i - 1][j - 1] == "M" && map[i+1][j + 1] == "S")
                            || (map[i - 1][j - 1] == "S" && map[i+1][j + 1] == "M") )
                            &&
                            ((map[i + 1][j - 1] == "M" && map[i - 1][j + 1] == "S")
                                    || (map[i + 1][j - 1] == "S" && map[i - 1][j + 1] == "M") )
                            ) {
                            secondResult += 1;
                        }
                    }
                }
            }
        }

    }
}