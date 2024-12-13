package advent.of.code.y2024

import advent.of.code.Reader
import utils.Util
import utils.println
import java.util.*

class p11 {

    companion object {
        val YEAR = "2024";
        val DAY = "11";
    }

    private var input: MutableList<String> = arrayListOf();

    private var inputAsString: String = "";

    private var firstResult = 0L;

    private var secondResult = 0L;

    fun solve() {
        val startingTime = Date();
        inputAsString = Reader.readInputAsString(YEAR, DAY);
        input.addAll(Reader.readInput(YEAR, DAY));

//        first();

        second();

        println("first: $firstResult");
        println("second: $secondResult");


        println(startingTime);
        println(Date());

    }

    private fun first() {
        var line = inputAsString;
        var stones = line.split(" ").toMutableList();
        for (i in 0..<25) {
            changeStones(stones)
        }

        firstResult = stones.size.toLong();
    }

    private fun second() {
        var line = inputAsString;
        var stonesList = line.split(" ");
        var map: HashMap<String, Long> = hashMapOf();


        stonesList.forEach { stone ->
            map.set(stone, map.getOrDefault(stone.trim(), 0L) + 1L);
        }

        doSecond(map, 0);
    }

    private fun doSecond(map: HashMap<String, Long>, blink: Int) {
        if (blink < 75) {
            var newMap: HashMap<String, Long> = hashMapOf();
            map.entries.filter { e -> e.value > 0 }.forEach { entry ->
                var j = 0;
                var entryCount = entry.value;
                    var doneSomething = false;
                    if (entry.key == "0") {
                        newMap.set(entry.key, newMap.getOrDefault(entry.key, map.getOrDefault(entry.key, 0)) - entryCount);
                        newMap.set("1", newMap.getOrDefault("1", map.getOrDefault("1", 0)) + entryCount);
                        doneSomething = true;
                    }

                    if (entry.key.length % 2 == 0) {
                        var left = entry.key.substring(0, entry.key.length / 2);
                        var right = entry.key.substring(entry.key.length / 2);
                        right = removeLeadingZeros(right)
                        newMap.set(entry.key, newMap.getOrDefault(entry.key, map.get(entry.key)!!) - entryCount)
                        newMap.set(left, newMap.getOrDefault(left,map.getOrDefault(left, 0)) + entryCount)
                        newMap.set(right, newMap.getOrDefault(right, map.getOrDefault(right, 0)) + entryCount)
                        doneSomething = true;
                    }

                    if (!doneSomething) {
                        val value = (entry.key.toLong() * 2024L).toString();
                        newMap.set(entry.key, newMap.getOrDefault(entry.key, map.get(entry.key)!!) - entryCount);
                        newMap.set(value, newMap.getOrDefault(value, map.getOrDefault(value, 0)) + entryCount)
                    }
                    j++;
            }
            doSecond(newMap, blink + 1);
        } else {
            map.entries.filter { entry -> entry.value > 0 }.map { entry -> entry.value }.forEach { secondResult += it };
        }
    }


    private fun changeStones(stones: MutableList<String>) {
        var j = 0;
        while (j < stones.size) {
            var doneSomething = false;
            if (stones[j] == "0") {
                stones[j] = "1";
                doneSomething = true;
            }

            if (stones[j].length % 2 == 0) {
                var left = stones[j].substring(0, stones[j].length / 2);
                var right = stones[j].substring(stones[j].length / 2);
                right = removeLeadingZeros(right)
                stones.removeAt(j);
                stones.add(j, left);
                stones.add(j + 1, right);
                j++;
                doneSomething = true;
            }

            if (!doneSomething) {
                val value = (stones[j].toLong() * 2024L).toString();
                stones[j] = value;
            }
            j++;
        }
    }

    private fun change(stone: String, count: Int) {
//        if (count < 25) {
        var doneSomething = false;
        if (stone == "0") {
            change("1", count + 1);
            doneSomething = true;
        }

        if (stone.length % 2 == 0) {
            var left = stone.substring(0, stone.length / 2);
            var right = stone.substring(stone.length / 2);
            right = removeLeadingZeros(right)
            secondResult++;
            change(left, count + 1);
            change(right, count + 1);
            doneSomething = true;
        }

        if (!doneSomething) {
            val value = (stone.toLong() * 2024L).toString();
            change(value, count + 1);
        }
//        }
    }

    fun removeLeadingZeros(num: String): String {
        //traverse the entire string
        for (i in 0..<num.length) {
            //check for the first non-zero character

            if (num[i] != '0') {
                //return the remaining string
                val res = num.substring(i)
                return res
            }
        }


        //If the entire string is traversed
        //that means it didn't have a single
        //non-zero character, hence return "0"
        return "0"
    }
}