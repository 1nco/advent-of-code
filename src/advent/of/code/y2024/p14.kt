package advent.of.code.y2024

import advent.of.code.Reader
import utils.Util
import utils.Util.Companion.initMap
import utils.println
import java.util.*

class p14 {

    companion object {
        val YEAR = "2024";
        val DAY = "14";
    }

    private var input: MutableList<String> = arrayListOf();

    private var inputAsString: String = "";

    private var firstResult = 0L;

    private var secondResult = 0L;

    private var wide = 101;
    private var tall = 103;
//    private var wide = 11;
//    private var tall = 7;

    fun solve() {
        val startingTime = Date();
        inputAsString = Reader.readInputAsString(YEAR, DAY);
        input.addAll(Reader.readInput(YEAR, DAY));

        first();

        second();

        println("first: $firstResult");
        println("second: $secondResult");


        println(startingTime);
        println(Date());

    }

    private fun first() {
        var robots: MutableList<Pair<Util.CoordinateLong, Util.CoordinateLong>> = mutableListOf();
        input.forEach {
            var position = Util.getNumbers(it.split(" ")[0])
            var velocity = Util.getNumbers(it.split(" ")[1])
            robots.add(Pair(Util.CoordinateLong(position[0], position[1]), Util.CoordinateLong(velocity[0], velocity[1])))
        }

        for (i in 0..<100) {
            robots.forEach { robot ->
                robot.first.x += robot.second.x;
                robot.first.y += robot.second.y;
                if (robot.first.x < 0) {
                    robot.first.x = wide + robot.first.x
                }

                if (robot.first.y < 0) {
                    robot.first.y = tall + robot.first.y
                }

                if (robot.first.x > wide - 1) {
                    robot.first.x = robot.first.x - wide;
                }

                if (robot.first.y > tall - 1) {
                    robot.first.y = robot.first.y - tall;
                }
            }
        }

        var q1Count = robots.filter { it.first.x < wide / 2 && it.first.y < tall / 2 }.count()
        var q2Count = robots.filter { it.first.x > wide / 2 && it.first.y < tall / 2 }.count()
        var q3Count = robots.filter { it.first.x < wide / 2 && it.first.y > tall / 2 }.count()
        var q4Count = robots.filter { it.first.x > wide / 2 && it.first.y > tall / 2 }.count()

        q1Count.println()
        q2Count.println()
        q3Count.println()
        q4Count.println()

        firstResult = q1Count * q2Count * q3Count * q4Count.toLong();


    }

    private fun second() {
        var robots: MutableList<Pair<Util.CoordinateLong, Util.CoordinateLong>> = mutableListOf();
        input.forEach {
            var position = Util.getNumbers(it.split(" ")[0])
            var velocity = Util.getNumbers(it.split(" ")[1])
            robots.add(Pair(Util.CoordinateLong(position[0], position[1]), Util.CoordinateLong(velocity[0], velocity[1])))
        }

        var done = false;
        var seconds = 0;
        while (!done) {
            robots.forEach { robot ->
                robot.first.x += robot.second.x;
                robot.first.y += robot.second.y;
                if (robot.first.x < 0) {
                    robot.first.x = wide + robot.first.x
                }

                if (robot.first.y < 0) {
                    robot.first.y = tall + robot.first.y
                }

                if (robot.first.x > wide - 1) {
                    robot.first.x = robot.first.x - wide;
                }

                if (robot.first.y > tall - 1) {
                    robot.first.y = robot.first.y - tall;
                }
            }
            seconds++
            var q1Count = robots.filter { it.first.x < wide / 2 && it.first.y < tall / 2 }.count()
            var q2Count = robots.filter { it.first.x > wide / 2 && it.first.y < tall / 2 }.count()
            var q3Count = robots.filter { it.first.x < wide / 2 && it.first.y > tall / 2 }.count()
            var q4Count = robots.filter { it.first.x > wide / 2 && it.first.y > tall / 2 }.count()

            if (q1Count > robots.size / 2 || q2Count > robots.size / 2 || q3Count > robots.size / 2 || q4Count > robots.size / 2) {
                if (secondResult == 0L) {
                    secondResult = seconds.toLong();
                    done = true;
                }
            }
        }

        var map: MutableList<MutableList<String>> = mutableListOf()

        for (i in 0..<tall) {
            map.add(arrayListOf())
            for (j in 0..<wide) {
                map[i].add(".")
            }
        }

        robots.forEach { robot ->
            map[robot.first.y.toInt()][robot.first.x.toInt()] = "x"
        }

        Util.saveMapToFile(map, "2024-p14-$seconds")

        secondResult = seconds.toLong();

    }

}