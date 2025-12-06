package advent.of.code.y2025

import advent.of.code.Reader
import utils.Util
import java.time.LocalDateTime

class p6 {

    companion object {
        val YEAR = "2025";
        val DAY = "6";
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
        var map: MutableList<MutableList<Long>> = mutableListOf();
        var action: MutableList<String> = mutableListOf();


        var firstline = true;
        input.forEach { line ->
            var numbers = Util.getNumbers(line)

            if (firstline) {
                numbers.forEach { line ->
                    map.add(mutableListOf())
                }
            }

            if (numbers.isEmpty()) {
                action = line.replace(" ", "").map { char -> char.toString() }.toMutableList();
            } else {
                for (i in 0..<map.size) {
                    map[i].add(numbers[i])
                }
            }

            firstline = false;
        }

        var results: MutableList<Long> = mutableListOf();

        for (i in 0..<map.size) {
            results.add(map[i].reduce { a, b -> red(a, b, action[i]) })
        }

        firstResult = results.reduce { a, b -> a + b }
    }

    private fun second() {
        var map: MutableList<MutableList<String>> = mutableListOf();
        var action: MutableList<String> = mutableListOf();


        var lineNum = 0;
        input.forEach { line ->
            if (lineNum == input.size - 1) {
                action = line.replace(" ", "").map { char -> char.toString() }.toMutableList();
            }
            lineNum++
        }

        map.add(mutableListOf())
        var idx = 0;
        for (j in 0..<input[0].length) {
            var num = ""
            for (i in 0..<input.size) {
                if (input[i][j].toString() != " " && input[i][j].toString() != "+" && input[i][j].toString() != "*") {
                    num += input[i][j];
                }
            }
            if (num == "") {
                map.add(mutableListOf())
                idx++;
            } else {
                map[idx].add(num)
            }
        }

        var results: MutableList<Long> = mutableListOf();

        for (i in 0..<map.size) {
            results.add(map[i].map { it.toLong() }.reduce { a, b -> red(a, b, action[i]) })
        }

        secondResult = results.reduce { a, b -> a + b }
    }

    private fun red(a: Long, b: Long, action: String): Long {
        if (action == "*") {
            return a * b;
        }

        if (action == "+") {
            return a + b;
        }

        return a + b;
    }

}