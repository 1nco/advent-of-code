package advent.of.code

import java.util.*

class NinthPuzzle {
    companion object {

        private val day = "9";

        private var input: MutableList<String> = arrayListOf();

        private var result = 0L;

        private var resultSecond = 0L;

        fun solve() {
            var startingTime = Date();
            input.addAll(Reader.readInput(day));

            var lineNum = 0;
            input.forEach { line ->


                lineNum++;
            }

            System.out.println(result)

            System.out.println(startingTime);
            System.out.println(Date());

        }
    }
}