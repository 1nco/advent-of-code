package advent.of.code

import utils.Util
import java.util.*

object `16thPuzzle` {

    private const val DAY = "16";

    private var input: MutableList<String> = arrayListOf();

    private var result = 0L;

    private var resultSecond = 0L;

    private var count = 0;

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

    private fun first() {
        val map = Util.initMap(input);
        val startingBeam = Beam(0, 0)
        val beams: MutableList<Beam> = arrayListOf(startingBeam);
        val directions: Map<String, Position> = hashMapOf(
            Pair("U", Position(-1, 0)),
            Pair("D", Position(1, 0)),
            Pair("L", Position(0, -1)),
            Pair("R", Position(0, 1))
        )
        val energizedTiles: MutableList<Position> = arrayListOf(Position(0, 0));
        var beamsToAdd: MutableList<Beam> = arrayListOf();

        move(beams[0], map, energizedTiles, beamsToAdd);
        var iterations = 0;
        var stopRunning = false;
        while (!stopRunning) {
            beamsToAdd =
                beamsToAdd.filter { a -> !beams.any { b -> b.x == a.x && b.y == a.y && b.direction == a.direction } }
                    .toMutableList()
            beamsToAdd.forEach { a ->
                if (!beams.contains(a)) {
                    beams.add(a)
                }
            }
//            beams.addAll(beamsToAdd)
            beamsToAdd = arrayListOf();
            beams.removeIf { b -> !b.isActive }
//            beams.map { beam ->
//                beam.x += directions[beam.direction]!!.x;
//                beam.y += directions[beam.direction]!!.y
//            }
//                .filter { b -> b.isActive }
//            for (i in 0..<beams.size) {
            val energizedTilesSizeBefore = energizedTiles.size;
            beams.forEach { beam ->
                beam.x += directions[beam.direction]!!.x;
                beam.y += directions[beam.direction]!!.y

                move(beam, map, energizedTiles, beamsToAdd);
            }
            val energizedTilesSizeAfter = energizedTiles.size;
            if (energizedTilesSizeBefore == energizedTilesSizeAfter) {
                if (iterations > 10) {
                    stopRunning = true;
                } else {
                    iterations++;
                }
            } else {
                iterations = 0;
            }
        }
        result = energizedTiles.size.toLong();
    }

    private fun second() {
        val map = Util.initMap(input);

        var list: MutableList<Int> = arrayListOf();

        for (i in 0..<map.size) {
            for (j in 0..<map[0].size) {
                if (i == 0) {
                    if (j == 0) {
                        list.add(doLogic(Beam(i,j,"R")))
                        list.add(doLogic(Beam(i,j,"D")))
                    } else if (j == map[0].size - 1) {
                        list.add(doLogic(Beam(i,j,"L")))
                        list.add(doLogic(Beam(i,j,"D")))
                    } else {
                        list.add(doLogic(Beam(i,j,"D")))
                    }
                }

                else if (j == 0) {
                    if (i == 0) {
                        list.add(doLogic(Beam(i,j,"R")))
                        list.add(doLogic(Beam(i,j,"D")))
                    } else if (i == map.size - 1) {
                        list.add(doLogic(Beam(i,j,"R")))
                        list.add(doLogic(Beam(i,j,"U")))
                    } else {
                        list.add(doLogic(Beam(i,j,"R")))
                    }
                }

                else if (i == map.size - 1) {
                    if (j == 0) {
                        list.add(doLogic(Beam(i,j,"R")))
                        list.add(doLogic(Beam(i,j,"U")))
                    } else if (j == map[0].size - 1) {
                        list.add(doLogic(Beam(i,j,"L")))
                        list.add(doLogic(Beam(i,j,"R")))
                    } else {
                        list.add(doLogic(Beam(i,j,"U")))
                    }
                }

                else if (j == map[0].size - 1) {
                    if (i == 0) {
                        list.add(doLogic(Beam(i,j,"L")))
                        list.add(doLogic(Beam(i,j,"D")))
                    } else if (i == map.size - 1) {
                        list.add(doLogic(Beam(i,j,"L")))
                        list.add(doLogic(Beam(i,j,"R")))
                    } else {
                        list.add(doLogic(Beam(i,j,"L")))
                    }
                }
            }
        }

        list.forEach{l ->
            if (resultSecond < l) {
                resultSecond = l.toLong();
            }
        }
    }

    private fun doLogic(startingBeam: Beam): Int {
        println(count)
        println(count++)
        println(Date())
        val map = Util.initMap(input);
        val beams: MutableList<Beam> = arrayListOf(startingBeam);
        val directions: Map<String, Position> = hashMapOf(
            Pair("U", Position(-1, 0)),
            Pair("D", Position(1, 0)),
            Pair("L", Position(0, -1)),
            Pair("R", Position(0, 1))
        )
        val energizedTiles: MutableList<Position> = arrayListOf(Position(startingBeam.x, startingBeam.y));
        var beamsToAdd: MutableList<Beam> = arrayListOf();

        move(beams[0], map, energizedTiles, beamsToAdd);
        var iterations = 0;
        var stopRunning = false;
        while (!stopRunning) {
            beamsToAdd =
                beamsToAdd.filter { a -> !beams.any { b -> b.x == a.x && b.y == a.y && b.direction == a.direction } }
                    .toMutableList()
            beamsToAdd.forEach { a ->
                if (!beams.contains(a)) {
                    beams.add(a)
                }
            }
//            beams.addAll(beamsToAdd)
            beamsToAdd = arrayListOf();
            beams.removeIf { b -> !b.isActive }
//            beams.map { beam ->
//                beam.x += directions[beam.direction]!!.x;
//                beam.y += directions[beam.direction]!!.y
//            }
//                .filter { b -> b.isActive }
//            for (i in 0..<beams.size) {
            val energizedTilesSizeBefore = energizedTiles.size;
            beams.forEach { beam ->
                beam.x += directions[beam.direction]!!.x;
                beam.y += directions[beam.direction]!!.y

                move(beam, map, energizedTiles, beamsToAdd);
            }
            val energizedTilesSizeAfter = energizedTiles.size;
            if (energizedTilesSizeBefore == energizedTilesSizeAfter) {
//                stopRunning = true;
                if (iterations > 1) {
                    stopRunning = true;
                } else {
                    iterations++;
                }
            } else {
                iterations = 0;
            }
        }
        println(Date())
        return energizedTiles.size;
    }

    private fun move(
        beam: Beam,
        map: MutableList<MutableList<String>>,
        energizedTiles: MutableList<Position>,
        beamsToAdd: MutableList<Beam>
    ) {
        if (beam.x < 0 || beam.x == map.size || beam.y < 0 || beam.y == map[0].size) {
            beam.isActive = false;
        } else {
            if (!energizedTiles.contains(Position(beam.x, beam.y))) {
                energizedTiles.add(Position(beam.x, beam.y))
            }

            if (map[beam.x][beam.y] == "|") {
                if (beam.direction == "R" || beam.direction == "L") {
                    val newBeam = Beam(beam.x, beam.y);
                    newBeam.direction = "U";
                    beam.direction = "D";
                    beamsToAdd.add(newBeam);
                }
            }

            if (map[beam.x][beam.y] == "-") {
                if (beam.direction == "U" || beam.direction == "D") {
                    val newBeam = Beam(beam.x, beam.y);
                    newBeam.direction = "L";
                    beam.direction = "R";
                    beamsToAdd.add(newBeam);
                }
            }
            if (map[beam.x][beam.y] == "/") {
                if (beam.direction == "U") {
                    beam.direction = "R";
                } else if (beam.direction == "D") {
                    beam.direction = "L";
                } else if (beam.direction == "L") {
                    beam.direction = "D";
                } else if (beam.direction == "R") {
                    beam.direction = "U";
                }
            }

            if (map[beam.x][beam.y] == "\\") {
                if (beam.direction == "U") {
                    beam.direction = "L";
                } else if (beam.direction == "D") {
                    beam.direction = "R";
                } else if (beam.direction == "L") {
                    beam.direction = "U";
                } else if (beam.direction == "R") {
                    beam.direction = "D";
                }
            }
        }
    }

    data class Beam(var x: Int, var y: Int, var direction: String = "R", var isActive: Boolean = true);
    data class Position(var x: Int, var y: Int)
}