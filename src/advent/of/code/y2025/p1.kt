package advent.of.code.y2025

import advent.of.code.Reader
import utils.Util
import utils.println
import java.time.LocalDateTime

class p1 {

    companion object {
        val YEAR = "2025";
        val DAY = "1";
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
        var current = 50L;
        input.forEach { line ->
            if (line.contains("L")) {
                var prevCurrent = current;
                current -= Util.getNumbers(line).first();
                while (current <= 0L) {
                    if (prevCurrent != 0L) {
                        firstResult++
                    } else if (((current == 0L))) {
                        firstResult++;
                    }

                    current = 100 + current;
                    prevCurrent = current;
                }
            } else if (line.contains("R")) {
                var prevCurrent = current;
                current += Util.getNumbers(line).first();
                if (current == 0L) {
                    firstResult++;
                }
                while (current > 99L) {
                    if (prevCurrent != 100L) {
                        firstResult++
                    } else if (((current == 100L) && Util.getNumbers(line).first() > 100)) {
                        firstResult++;
                    }
                    current = current - 100;
                    prevCurrent = current;
                }
            }
        }
    }

    private fun second() {

    }
}