package advent.of.code.y2025

import advent.of.code.Reader
import utils.println
import java.time.LocalDateTime

class p3 {

    companion object {
        val YEAR = "2025";
        val DAY = "3";
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
        input.forEach { line ->
            var numbers = line.map { it.toString().toInt() }.toMutableList();
            firstResult += getJoltage(numbers)
        }
    }

    private fun second() {
        input.forEach { line ->
            var numbers = line.map { it.toString().toInt() }.toMutableList();
            secondResult += getJoltageSecond(numbers)
        }
    }

    private fun getJoltage(numbers: MutableList<Int>): Long {

        var last = numbers.last();
        var nums = numbers.subList(0, numbers.size - 1);

        var max = nums.max()
        var idx = nums.indexOf(max);
        nums = nums.subList(idx + 1, nums.size);

        var secondMax = 0;
        if (nums.size > 0) {
            secondMax = nums.max()
        }

        var joltage = max.toString()

        if (secondMax > last) {
            joltage += secondMax.toString();
        } else {
            joltage += last.toString();
        }

        return joltage.toLong()
    }

    private fun getJoltageSecond(numbers: MutableList<Int>): Long {
        var joltage = "";
        var lastTwelve: MutableList<Int> = numbers.subList(numbers.size - 11, numbers.size).toMutableList();
        var nums: MutableList<Int> = numbers.subList(0, numbers.size-11).toMutableList()
        for (i in 12 downTo 1) {
            var max = nums.max()
            var idx = nums.indexOf(max);
            nums = nums.subList(idx + 1, nums.size);

//            if (lastTwelve.size != 1) {
            if (lastTwelve.isNotEmpty()) {
                var lastTwelveFirst = lastTwelve.first();
                nums.add(lastTwelveFirst)
                lastTwelve = lastTwelve.subList(1, lastTwelve.size);
            }

//            } else {
//                if (lastTwelve.first() > max) {
//                    max = lastTwelve.first();
//                }
//            }
            joltage += max.toString();
        }
        return joltage.toLong()
    }
}