package advent.of.code

import java.util.*

object `16thPuzzle` {

    private const val DAY = "15";

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

    private fun first() {

    }

    private fun second() {

    }
}