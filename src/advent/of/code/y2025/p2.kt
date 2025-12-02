package advent.of.code.y2025

import advent.of.code.Reader
import java.time.LocalDateTime

class p2 {

    companion object {
        val YEAR = "2025";
        val DAY = "2";
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
        var ids = inputAsString.split(",");
        ids.forEach { idPair ->
            var first = idPair.split("-")[0]
            var second = idPair.split("-")[1]

            for (i in 0..<second.toLong() - first.toLong() + 1) {
                if (isInvalid(first)) {
                    firstResult += first.toLong();
                }
                first = (first.toLong() + 1L).toString();
            }
        }
    }

    private fun second() {
        var ids = inputAsString.split(",");
        ids.forEach { idPair ->
            var first = idPair.split("-")[0]
            var second = idPair.split("-")[1]

            for (i in 0..<second.toLong() - first.toLong() + 1) {
                if (isInvalidSecond(first)) {
                    secondResult += first.toLong();
                }
                first = (first.toLong() + 1L).toString();
            }
        }
    }

    private fun isInvalid(num: String): Boolean {
        var len = num.length;
        var first = num.substring(0, len / 2);
        var second = num.substring(len / 2);

        if (first == second) return true;
        return false;
    }

    private fun isInvalidSecond(num: String): Boolean {
        var len = num.length;
        for (i in 1..len) {
            var first = num.substring(0, len / i);
            if (isAllSame(first, num)) {
                return true;
            }
        }

        return false;
    }

    private fun isAllSame(first: String, num: String): Boolean {
        var times = num.length / first.length;
        if (times == 1) {
            return false
        }

        if (first.repeat(times) == num) {
            return true
        }
        return false;
    }
}