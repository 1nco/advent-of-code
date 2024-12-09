package advent.of.code.y2024

import advent.of.code.Reader
import java.util.*

class p10 {

    companion object {
        val YEAR = "2024";
        val DAY = "10";
    }

    private var input: MutableList<String> = arrayListOf();

    private var inputAsString: String = "";

    private var firstResult = 0L;

    private var secondResult = 0L;


    fun solve() {
        val startingTime = Date();
        inputAsString = Reader.readInputAsString(YEAR, DAY);
        input.addAll(Reader.readInput(YEAR, DAY));

        first();

//        second();

        println("first: $firstResult");
        println("second: $secondResult");


        println(startingTime);
        println(Date());

    }

    private fun first() {
        var line = inputAsString;
    }

    private fun second() {
        var line = inputAsString;

    }
}