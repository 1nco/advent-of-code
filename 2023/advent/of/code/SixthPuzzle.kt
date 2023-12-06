package advent.of.code

import java.util.*

class SixthPuzzle {
    companion object {

        private val day = "6";

        private var input: MutableList<String> = arrayListOf();

        private var result = 0;

        private var resultSecond = 0;

        var times = arrayListOf<Long>();
        var distances = arrayListOf<Long>();

        fun solve() {
            System.out.println(Date());
            input.addAll(Reader.readInput(day));

            var lineNum = 0;
            input.forEach { line ->
                if (lineNum == 0) {
                    times.addAll(line.replace("Time:", "").trim().split(" ").filter { l -> !l.equals("") }.map { l -> (l.trim()).toLong() });
                }

                if (lineNum == 1) {
                    distances.addAll(line.replace("Distance:", "").trim().split(" ").filter { l -> !l.equals("") }.map { l -> (l.trim()).toLong() });
                }

                lineNum++;

            }

            var winScenarios = 0
            for (i in 0..times.size - 1) {
                for (j in 0..times[i]) {
                    if (isWin(times[i], j, distances[i])) {
                        winScenarios++
                    }
                }
                if (winScenarios > 0) {
                    if (result == 0) {
                        result = winScenarios;
                    } else {
                        result = result * winScenarios;
                    }
                }
                winScenarios = 0;
            }

            System.out.println("First Puzzle Result: " + result);
            System.out.println("Second Puzzle Result: " + resultSecond);
        }

        private fun isWin(time: Long, chargeTime: Long, recordDistance: Long): Boolean {
            var distance = if (chargeTime != 0L) (time - chargeTime) * chargeTime else 0;
            return distance > recordDistance;
        }
    }
}

//Time:        38677673
//Distance:   234102711571236