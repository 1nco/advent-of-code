package advent.of.code.y2023

import utils.readInput
import advent.of.code.*

class FirstPuzzle {
    companion object {
        private val day = "1";

        private var input: MutableList<String> = arrayListOf();

        fun solve() {
            input.addAll(readInput(day));
            var sum = 0;
            input.forEach {
                sum += getLineValue(replaceStringWithNumber( it ));
            }
            System.out.println(sum);
        }

        private fun getLineValue(line: String): Int {
            var lineWithOnlyNumbers = line.replace(Regex("\\D"), "");
            return Integer.parseInt(lineWithOnlyNumbers.first().toString() + lineWithOnlyNumbers.last().toString());
        }

        private fun replaceStringWithNumber(line: String): String {
            val map = linkedMapOf<String, Int?>("twone" to 21, "eightwo" to 82, "eighthree" to 83, "oneight" to 18, "fiveight" to 58, "sevenine" to 79, "one" to 1, "two" to 2, "three" to 3, "four" to 4, "five" to 5, "six" to 6, "seven" to 7, "eight" to 8, "nine" to 9);
            var lineReplaced = line;
            map.forEach {
                lineReplaced = lineReplaced.replace(it.key, it.value.toString());
            }
            return lineReplaced;
        }
    }
}