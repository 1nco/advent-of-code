package advent.of.code.y2024

import advent.of.code.Reader
import java.util.*


class p3 {

    companion object {
        val YEAR = "2024";
        val DAY = "3";
    }

    private var input: MutableList<String> = arrayListOf();

    private var firstResult = 0L;

    private var secondResult = 0L;

    fun solve() {
        val startingTime = Date();
        input.add(Reader.readInputAsString(YEAR, DAY));

        first();

        second();

        println("first: $firstResult");
        println("second: $secondResult");


        println(startingTime);
        println(Date());

    }

    private fun first() {

        input.forEach { line ->

            var stringList = Regex("mul\\(-?\\d+,-?\\d+\\)").findAll(line).toList().map {
                it.value.replace("mul(", "").replace(")", "")
            }.toMutableList()
            var numList: MutableList<Long> = arrayListOf();
            stringList.map { i -> i.split(",") }.map { numList.addAll(it.map { it.toLong() }) }
            var i = 0;
            while (i < numList.size) {
                firstResult += numList[i] * numList[i + 1];
                i = i + 2;
            }

        }
    }


    private fun second() {
        var stringList: MutableList<String> = mutableListOf();
        var numList: MutableList<Long> = mutableListOf();
        input.forEach { line ->
            line.split("do()").forEach { doo ->

                stringList = Regex("mul\\(-?\\d+,-?\\d+\\)").findAll(doo.split("don't()")[0]).toList().map {
                    it.value.replace("mul(", "").replace(")", "")
                }.toMutableList()

                stringList.map { i -> i.split(",") }.map { numList.addAll(it.map { it.toLong() }) }
            }

        }

        var i = 0;
        while (i < numList.size) {
            secondResult += numList[i] * numList[i + 1];
            i = i + 2;
        }
    }
}