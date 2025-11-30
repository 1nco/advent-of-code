package advent.of.code.y2023

import advent.of.code.y2023.types.Pos
import java.util.*
import kotlin.math.absoluteValue
import advent.of.code.*

object `18thPuzzle` {

    private const val DAY = "18";

    private var input: MutableList<String> = arrayListOf();

    private var result = 0L;

    private var resultSecond = 0L;

    private val dirMap: Map<String, Pos> =
        mapOf(Pair("U", Pos(-1, 0)), Pair("D", Pos(1, 0)), Pair("R", Pos(0, 1)), Pair("L", Pos(0, -1)))

    fun solve() {
        val startingTime = Date();
        input.addAll(Reader.readInput("2023",DAY));

//        first();

        second();

        println("first: $result");
        println("second: $resultSecond");


        println(startingTime);
        println(Date());

    }

    private fun first() {
        val vertices: MutableList<Pos> = arrayListOf();


        vertices.add(Pos(0, 0))
        input.forEach { line ->
            val direction = line.split(" ")[0]
            val length = line.split(" ")[1].toLong()

            var v = vertices.last().copy();
            for (i in 0..<length) {
                v.line += dirMap[direction]!!.line;
                v.column += dirMap[direction]!!.column;
                result += 1;
            }
            vertices.add(v)
        }


        vertices.removeLast();
        result /= 2
        result += vertices
            .asSequence()
            .plus(vertices.first())
            .windowed(2)
            .sumOf { (a, b) -> a.line * b.column - a.column * b.line }
            .absoluteValue
            .div(2)
            .plus(1)
            .toLong()
    }

    private fun second() {
        val vertices: MutableList<Pos> = arrayListOf();


        vertices.add(Pos(0, 0))
        input.forEach { line ->
            val color = line.split(" ")[2]

            val direction =  getDirection(color.replace("(", "").replace(")", "").replace("#", "")[5].toString());
            val length = color.replace("(", "").replace(")", "").replace("#", "").dropLast(1).toLong(radix = 16)
            var v = vertices.last().copy();
            for (i in 0..<length) {
                v.line += dirMap[direction]!!.line;
                v.column += dirMap[direction]!!.column;
                resultSecond += 1;
            }
            vertices.add(v)
        }


        vertices.removeLast();
        resultSecond /= 2
        resultSecond += vertices
            .asSequence()
            .plus(vertices.first())
            .windowed(2)
            .sumOf { (a, b) -> a.line * b.column - a.column * b.line }
            .absoluteValue
            .div(2)
            .plus(1)
            .toLong()
    }

    fun getDirection(s: String): String {
        if (s == "0") {
            return "R"
        }

        if (s == "1") {
            return "D"
        }

        if (s == "2") {
            return "L"
        }

        if (s == "3") {
            return "U"
        }

        return "";
    }
}