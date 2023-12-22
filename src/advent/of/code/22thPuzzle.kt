package advent.of.code

import utils.Util
import java.util.*

object `22thPuzzle` {

    private const val DAY = "22";

    private var input: MutableList<String> = arrayListOf();

    private var result = 0L;

    private var resultSecond = 0L;


//    private var xMap: MutableList<MutableList<String>> = arrayListOf();
//    private var yMap: MutableList<MutableList<String>> = arrayListOf();

    fun solve() {
        val startingTime = Date();
        input.addAll(Reader.readInput(DAY));

        first();

        second();

        println("first: $result");
        println("second: $resultSecond");


        println(startingTime);
        println(Date());

    }

    fun first() {

        val bricks: MutableList<Brick> = arrayListOf();
        input.forEach { line ->
            bricks.add(parseLine(line));
        }

        val xMap: MutableList<MutableList<String>> =
            initMap(getBiggestXCoordinate(bricks), getBiggestZCoordinate(bricks))
        val yMap: MutableList<MutableList<String>> =
            initMap(getBiggestYCoordinate(bricks), getBiggestZCoordinate(bricks));

        fillMap(xMap, bricks, "x");
        fillMap(yMap, bricks, "y");

        tiltMapXSouth(xMap, bricks)

        Util.saveMapToFile(xMap, "xmap")
    }

    fun second() {

    }

    private fun tiltMapXSouth(
        mapX: MutableList<MutableList<String>>,
        bricks: MutableList<Brick>
    ): MutableList<MutableList<String>> {


        for (i in mapX.size - 1 downTo 0) {
            val chars = mapX[i].toSet().filter { c -> !c.equals(".") && !c.equals("-") };
            chars.forEach { char ->
                val charIndexesStart = bricks[char.toInt()].start.x
                val charIndexesEnd = bricks[char.toInt()].end.x

                var canMove = true;
                var maxMove = mapX.size;
                for (j in charIndexesStart..charIndexesEnd) {
                    var k = 1;

                    while ((i + k) < mapX.size && canMove) {
                        if (mapX[i + k - 1][j.toInt()] == char && mapX[i + k][j.toInt()] == ".") {
                            k++;
                        } else {
                            canMove = false;
                        }
                    }
                    if (k < maxMove) {
                        maxMove = k;
                    }
                }
                for (j in charIndexesStart..charIndexesEnd) {
                    if (mapX[i + maxMove - 1][j.toInt()] == char && mapX[i + maxMove][j.toInt()] == ".") {
                        mapX[i + maxMove - 1][j.toInt()] = "."
                        mapX[i + maxMove][j.toInt()] = char
                    }
                }
            }
        }

        return mapX;
    }

    private fun fillMap(map: MutableList<MutableList<String>>, bricks: MutableList<Brick>, xy: String = "x") {
        var brickId = 0L;
        bricks.forEach { b ->
            for (i in b.start.z..b.end.z) {
                var start = 0L;
                var end = 0L;
                if (xy == "x") {
                    start = b.start.x;
                    end = b.end.x;
                } else {
                    start = b.start.y;
                    end = b.end.y;
                }
                while (start <= end) {
                    map[map.size - 1 - i.toInt()][start.toInt()] = "" + brickId;
                    start++;
                }
            }
            brickId++;
        }

        map[map.size-1] = map[map.size-1].map { "-" }.toMutableList()
    }


    private fun parseLine(line: String): Brick {
        val coords = line.split("~");
        val startCoords = coords[0].split(",");
        val endCoords = coords[1].split(",");
        return Brick(
            Coordinate3D(startCoords[0].toLong(), startCoords[1].toLong(), startCoords[2].toLong()),
            Coordinate3D(endCoords[0].toLong(), endCoords[1].toLong(), endCoords[2].toLong())
        )
    }

    private fun getBiggestXCoordinate(bricks: MutableList<Brick>): Long {
        var max = 0L;
        bricks.forEach { b ->
            if (b.end.x > max) {
                max = b.end.x;
            }
        }
        return max;
    }

    private fun getBiggestYCoordinate(bricks: MutableList<Brick>): Long {
        var max = 0L;
        bricks.forEach { b ->
            if (b.end.y > max) {
                max = b.end.y;
            }
        }
        return max;
    }

    private fun getBiggestZCoordinate(bricks: MutableList<Brick>): Long {
        var max = 0L;
        bricks.forEach { b ->
            if (b.end.z > max) {
                max = b.end.z;
            }
        }
        return max;
    }

    fun initMap(xy: Long, z: Long): MutableList<MutableList<String>> {
        val map: MutableList<MutableList<String>> = arrayListOf();
        for (i in 0..z) {
            map.add(arrayListOf());
            for (j in 0..xy) {
                map[i.toInt()].add(".")
            }
        }
        return map;
    }


    data class Coordinate3D(var x: Long, var y: Long, var z: Long);

    data class Brick(var start: Coordinate3D, var end: Coordinate3D);

}