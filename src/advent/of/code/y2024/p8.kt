package advent.of.code.y2024

import advent.of.code.Reader
import utils.*
import java.util.*
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

class p8 {

    companion object {
        val YEAR = "2024";
        val DAY = "8";
    }

    private var input: MutableList<String> = arrayListOf();

    private var inputAsString: String = "";

    private var firstResult = 0L;

    private var secondResult = 0L;


    fun solve() {
        val startingTime = Date();
//        inputAsString = Reader.readInputAsString(YEAR, DAY);
        input.addAll(Reader.readInput(YEAR, DAY));

//        first();

        second();

        println("first: $firstResult");
        println("second: $secondResult");


        println(startingTime);
        println(Date());

    }

    private fun first() {
        var uniqueLocations: MutableList<Util.Companion.Coordinate> = mutableListOf();
        var antennas: MutableMap<String, MutableList<Util.Companion.Coordinate>> = mutableMapOf();
        var map = Util.initMap(input);
        var i = 0L;
        map.forEach { line ->
            var j = 0L;
            line.forEach { column ->
                if (column != ".") {
                    antennas.getOrPut(column) { mutableListOf() }.add(Util.Companion.Coordinate(i, j))
                }
                j++;
            }
            i++;
        }

        for (i in 0..<map.size) {
            for (j in 0..<(map[0].size)) {
                antennas.forEach { coordinates ->
                    oneLineAndDoubleDist(Util.Companion.Coordinate(i.toLong(), j.toLong()),
                        coordinates.value,
                        uniqueLocations)
                }
            }
        }

        firstResult = uniqueLocations.distinct().size.toLong();
    }

    private fun second() {
        var uniqueLocations: MutableList<Util.Companion.Coordinate> = mutableListOf();
        var antennas: MutableMap<String, MutableList<Util.Companion.Coordinate>> = mutableMapOf();
        var map = Util.initMap(input);
        var i = 0L;
        map.forEach { line ->
            var j = 0L;
            line.forEach { column ->
                if (column != ".") {
                    antennas.getOrPut(column) { mutableListOf() }.add(Util.Companion.Coordinate(i, j))
                }
                j++;
            }
            i++;
        }

        for (i in 0..<map.size) {
            for (j in 0..<(map[0].size)) {
                antennas.forEach { coordinates ->
                    oneLine(Util.Companion.Coordinate(i.toLong(), j.toLong()), coordinates.value, uniqueLocations, map)
                }
            }
        }

        antennas.forEach { coordinates ->
            if (coordinates.value.size > 1) {
                coordinates.value.forEach { j ->
                    addToUniqueLocations(uniqueLocations, j)
                }
            }
        }


        uniqueLocations.distinct()
        secondResult = uniqueLocations.distinct().size.toLong();
    }

    fun oneLine(c1: Util.Companion.Coordinate,
                cList: List<Util.Companion.Coordinate>,
                uniqueLocations: MutableList<Util.Companion.Coordinate>,
                map: MutableList<MutableList<String>>) {
        for (i in 0..<cList.size) {
            for (j in 0..<cList.size) {
                if (i != j) {
                    if ((c1.x - cList[i].x) == (cList[i].x - cList[j].x) && (c1.y - cList[i].y) == (cList[i].y - cList[j].y)) {
                        addToUniqueLocations(uniqueLocations, c1)
                        addToUniqueLocations(uniqueLocations, cList[i])
                        addToUniqueLocations(uniqueLocations, cList[j])
                        var k = 0;
                        var done = false;
                        while (!done) {
                            val newCoord = Util.Companion.Coordinate(c1.x + (k * (cList[i].x - cList[j].x)),
                                c1.y + (k * (cList[i].y - cList[j].y)));
                            if (newCoord.x >= 0 && newCoord.x < map.size && newCoord.y >= 0 && newCoord.y < map[0].size) {
                                addToUniqueLocations(uniqueLocations, newCoord)
                                k++
                            } else {
                                done = true;
                            }
                        }

                    }
                }
            }
        }
    }

    fun addToUniqueLocations(uniqueLocations: MutableList<Util.Companion.Coordinate>, c: Util.Companion.Coordinate) {
        if (uniqueLocations.none { l -> c.x == l.x && c.y == l.y }) {
            uniqueLocations.add(c)
        }
    }

    fun distance(c1: Util.Companion.Coordinate, c2: Util.Companion.Coordinate): Long {
        return abs(c2.x - c1.x) + abs(c2.y - c1.y);
    }

    fun oneLineAndDoubleDist(c1: Util.Companion.Coordinate,
                             cList: List<Util.Companion.Coordinate>,
                             uniqueLocations: MutableList<Util.Companion.Coordinate>) {
        for (i in 0..<cList.size) {
            for (j in 0..<cList.size) {
                if (i != j) {
                    if ((c1.x - cList[i].x) == (cList[i].x - cList[j].x) && (c1.y - cList[i].y) == (cList[i].y - cList[j].y) && (distance(
                            c1,
                            cList[i]) * 2 == distance(c1, cList[j]) || distance(c1, cList[j]) * 2 == distance(c1,
                            cList[i]))
                    ) {
                        uniqueLocations.add(c1);
                    }
                }
            }
        }
    }


}