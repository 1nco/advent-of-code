package advent.of.code.y2024

import advent.of.code.Reader
import java.util.*

object p1 {

    const val YEAR = "2024";
    const val DAY = "1";

    private var input: MutableList<String> = arrayListOf();

    private var firstResult = 0L;

    private var secondResult = 0L;

    fun solve() {
        val startingTime = Date();
        input.addAll(Reader.readInput(YEAR, DAY));

        first();

//        second();

        println("first: $firstResult");
        println("second: $secondResult");


        println(startingTime);
        println(Date());

    }

    private fun first() {
//        val map = Util.initMap(input);

        firstResult = 0;
    }


    private fun second() {

//        val map = Util.initMap(input);

        secondResult = 0;
    }
}