package advent.of.code.y2024

import advent.of.code.Reader
import utils.Util
import utils.println
import java.time.LocalDateTime

class p18 {

    companion object {
        val YEAR = "2024";
        val DAY = "18";
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

    }

    private fun second() {

    }
}