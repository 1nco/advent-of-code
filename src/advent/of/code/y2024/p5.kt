package advent.of.code.y2024

import advent.of.code.Reader
import java.util.*

class p5 {

    companion object {
        val YEAR = "2024";
        val DAY = "5";
    }

    private var input: MutableList<String> = arrayListOf();

    private var firstResult = 0L;

    private var secondResult = 0L;

    private var rules: MutableMap<Long, MutableList<Long>> = mutableMapOf();
    private var updates: MutableList<Long> = mutableListOf<Long>();

    fun solve() {
        val startingTime = Date();
//        input.add(Reader.readInputAsString(YEAR, DAY));
        input.addAll(Reader.readInput(YEAR, DAY));

        first();

        second();

        println("first: $firstResult");
        println("second: $secondResult");


        println(startingTime);
        println(Date());

    }

    private fun first() {
        input.forEach {
            if (it.contains("|")) {
                rules.getOrPut(it.split("|")[0].toLong()) { mutableListOf() }.add(it.split("|").last().toLong())
            }

            if (it.contains(",")) {
                updates.addAll(it.split(",").map { it.toLong() }.toMutableList())
            }
        }


        input.forEach {
            if (it.contains(",")) {
                var numbers = it.split(",").map { it.toLong() }.toMutableList()

                var res = "";
                for (i in 0..<numbers.size) {
                    var currentlyChecked = numbers[i];
                    var currentlyCheckedRules = rules.getOrPut(currentlyChecked) { mutableListOf() }
                    currentlyCheckedRules.forEach {
                        var idx = numbers.indexOf(it)
                        if (idx != -1 && idx < i) {
                            res = "wrong"
                        }
                    }
                }
                if (res != "wrong") {
                    println(numbers)
                    firstResult += numbers[numbers.size / 2]
                }

            }
        }
    }


    private fun second() {
        input.forEach {
            if (it.contains("|")) {
                rules.getOrPut(it.split("|")[0].toLong()) { mutableListOf() }.add(it.split("|").last().toLong())
            }

            if (it.contains(",")) {
                updates.addAll(it.split(",").map { it.toLong() }.toMutableList())
            }
        }

        input.forEach {
            if (it.contains(",")) {
                var numbers = it.split(",").map { it.toLong() }.toMutableList()
                if (!isGood(numbers)) {
                    correct(numbers);
                }
            }
        }
    }

    private fun correct(list: MutableList<Long>) {
        if (!isGood(list)) {
            for (i in 0..<list.size) {
                var currentlyChecked = list[i];
                var currentlyCheckedRules = rules.getOrPut(currentlyChecked) { mutableListOf() }
                currentlyCheckedRules.forEach {
                    var idx = list.indexOf(it)
                    if (idx != -1 && idx < i) {
                        var el = list.removeAt(idx);
                        if (list.size == i) {
                            list.add(el)
                        } else {
                            list.add(i+1, el);
                        }
                    }
                }
            }
            correct(list)
        } else {
            println(list);
            secondResult += list[list.size / 2]
        }
    }

    private fun isGood(list: MutableList<Long>): Boolean {

        var res = "";
        for (i in 0..<list.size) {
            var currentlyChecked = list[i];
            var currentlyCheckedRules = rules.getOrPut(currentlyChecked) { mutableListOf() }
            currentlyCheckedRules.forEach {
                var idx = list.indexOf(it)
                if (idx != -1 && idx < i) {
                    res = "wrong"
                }
            }
        }
        return res != "wrong";
    }
}