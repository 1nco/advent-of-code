package advent.of.code

import utils.Util
import utils.println
import java.util.*

object `22thPuzzle` {

    private const val DAY = "22";

    private var input: MutableList<String> = arrayListOf();

    private var result = 0L;

    private var resultSecond = 0L;


    private var cbSet: MutableMap<String, MutableList<String>> = mutableMapOf();

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

        var bricks: MutableList<Brick> = arrayListOf();
        var lineNumber = 0L;
        input.forEach { line ->
            bricks.add(parseLine(line, lineNumber));
            lineNumber++
        }

        bricks = fall(bricks);

        result = countDisintegratableBricks(bricks)
    }

    fun second() {


        var bricks: MutableList<Brick> = arrayListOf();
        var lineNumber = 0L;
        input.forEach { line ->
            bricks.add(parseLine(line, lineNumber));
            lineNumber++
        }

        bricks = fall(bricks);

        resultSecond = countSecond(bricks)
    }

    private fun countSecond(bricks: MutableList<Brick>): Long {
        val overlaps: MutableList<MutableList<Brick>> = arrayListOf();
        bricks.forEach { brick ->
            overlaps.add(bricks.minus(brick).filter { sb -> isOverlap(sb, brick) }.toMutableList())
        }

        val chainReactionBricks: MutableList<Brick> = arrayListOf()

        val brickIds = bricks.map { b -> b.id }.toMutableList();
        overlaps.forEach{ o ->
            if (o.size == 1) {
                brickIds.removeIf { o[0].id == it }
            }
        }

        chainReactionBricks.addAll(bricks.filter { b -> !brickIds.contains(b.id) }.toMutableList())

        chainReactionBricks.forEach{cb ->
            cbSet.set(cb.id, arrayListOf());
            simulateFallForPt2(bricks.filter { b -> b.id != cb.id }.toMutableList(), cb.id)
        }

        return cbSet.map { cb -> cb.value.size }.sum().toLong();
    }

    private fun simulateFallForPt2(bricks: MutableList<Brick>, cbId: String): MutableList<Brick> {
        val sortedBricks = bricks.map { Brick(it.id, Coordinate3D(it.start.x,it.start.y,it.start.z,), Coordinate3D(it.end.x,it.end.y,it.end.z,)) }.toMutableList();
        sortedBricks.forEach { brick ->
            var done = false;
            while (!done) {
                if ((brick.start.z - 1 > 0) && sortedBricks.minus(brick).all { sb -> !isOverlap(sb, brick) }) {
                    brick.start.z -= 1;
                    brick.end.z -= 1;
                    if (!cbSet[cbId]!!.contains(brick.id)) {
                        cbSet[cbId]!!.add(brick.id);
                    }
                } else {
                    done = true;
                }
            }
        }
        return sortedBricks.toMutableList();
    }

    private fun fall(bricks: MutableList<Brick>): MutableList<Brick> {
        val sortedBricks = bricks.sortedBy { b -> b.start.z }
        sortedBricks.forEach { brick ->
            var done = false;
            while (!done) {
                if ((brick.start.z - 1 > 0) && sortedBricks.minus(brick).all { sb -> !isOverlap(sb, brick) }) {
                    brick.start.z -= 1;
                    brick.end.z -= 1;
                } else {
                    done = true;
                }
            }
        }
        return sortedBricks.toMutableList();
    }

    private fun countDisintegratableBricks(bricks: MutableList<Brick>): Long {
        val overlaps: MutableList<MutableList<Brick>> = arrayListOf();
        bricks.forEach { brick ->
            overlaps.add(bricks.minus(brick).filter { sb -> isOverlap(sb, brick) }.toMutableList())
        }

        var brickIds = bricks.map { b -> b.id }.toMutableList();
        overlaps.forEach{ overlaps ->
            if (overlaps.size == 1) {
                brickIds.removeIf { overlaps[0].id == it }
            }
        }
        return brickIds.size.toLong();
    }

    private fun isOverlap(brick1: Brick, brick2: Brick): Boolean {
        return ((isXOverlap(brick1, brick2)
                && isYOverlap(brick1, brick2)))
                && (brick1.start.z <= (brick2.end.z - 1) && (brick1.end.z) >= brick2.start.z - 1);
    }

    private fun isXOverlap(brick1: Brick, brick2: Brick): Boolean {
        return brick1.start.x <= brick2.end.x && brick1.end.x >= brick2.start.x
    }

    private fun isYOverlap(brick1: Brick, brick2: Brick): Boolean {
        return brick1.start.y <= brick2.end.y && brick1.end.y >= brick2.start.y
    }


    private fun parseLine(line: String, lineNumber: Long): Brick {
        val coords = line.split("~");
        val startCoords = coords[0].split(",");
        val endCoords = coords[1].split(",");
        return Brick(
            lineNumber.toString(),
            Coordinate3D(startCoords[0].toLong(), startCoords[1].toLong(), startCoords[2].toLong()),
            Coordinate3D(endCoords[0].toLong(), endCoords[1].toLong(), endCoords[2].toLong())
        )
    }

    data class Coordinate3D(var x: Long, var y: Long, var z: Long);

    data class Brick(var id: String, var start: Coordinate3D, var end: Coordinate3D);

}