package advent.of.code.y2023

import advent.of.code.y2023.types.Graph
import utils.Util
import java.util.*
import advent.of.code.*

object `17thPuzzle` {

    private const val DAY = "17";

    private var input: MutableList<String> = arrayListOf();

    private var result = 0L;

    private var resultSecond = 0L;

    fun solve() {
        val startingTime = Date();
        input.addAll(Reader.readInput("2023",DAY));

        first();

        second();

        println("first: $result");
        println("second: $resultSecond");


        println(startingTime);
        println(Date());

    }

    private fun first() {

        val map = Util.initMapWithNumbers(input);

        val weights: MutableMap<Pair<String, String>, Int> = mutableMapOf();

        for (i in 0..<map.size) {
            for (j in 0..<map[0].size) {
                weights.putAll(getWeightsOfVertex("$i;$j", map))
            }
        }

        val start = "0;0"
        val end = "${map.size - 1};${map[0].size - 1}"
        val shortestPathTree = dijkstra(Graph(weights), start)
        val shortestPathBetweenStartAndEnd = shortestPath(shortestPathTree, start, end);
        result = shortestPathBetweenStartAndEnd.map { vertex -> map[vertex.split(";")[0].toInt()][vertex.split(";")[1].toInt()] }.sum().toLong();
    }

    private fun getWeightsOfVertex(vertex: String, map: List<List<Int>>): Map<Pair<String, String>, Int> {
        val weights: MutableMap<Pair<String, String>, Int> = mutableMapOf();

        val directions: List<Position> = arrayListOf(
            Position(-1, 0),
            Position(1, 0),
            Position(0, -1),
            Position(0, 1)
        )

        val vx = vertex.split(";")[0].toInt()
        val vy = vertex.split(";")[1].toInt()

        directions.forEach{direction ->
            val neighborX = vx + direction.x;
            val neighborY = vy + direction.y;
            if (neighborX >= 0 && neighborX < map.size && neighborY < map[0].size && neighborY >= 0) {
                weights[Pair(vertex, "$neighborX;$neighborY")] = map[neighborX][neighborY]
            }
        }

        return weights
    }



    private fun second() {

    }

    data class Position(var x: Int, var y: Int)
}