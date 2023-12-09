package advent.of.code

import java.util.*

class TenthPuzzle {
    companion object {
        private const val DAY = "10";

        private var input: MutableList<String> = arrayListOf();

        private var result = 0L;

        private var resultSecond = 0L;

        fun solve() {
            val startingTime = Date();
            input.addAll(Reader.readInput(DAY));

            var lineNum = 0;
            input.forEach { line ->

                lineNum++;
            }

            println(result)
            println(resultSecond)

            println(startingTime);
            println(Date());

        }
    }
}