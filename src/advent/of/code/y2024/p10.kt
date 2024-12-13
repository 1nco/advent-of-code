package advent.of.code.y2024

import advent.of.code.Reader
import utils.Util
import java.util.*

class p10 {

    companion object {
        val YEAR = "2024";
        val DAY = "10";
    }

    private var input: MutableList<String> = arrayListOf();

    private var inputAsString: String = "";

    private var firstResult = 0L;

    private var secondResult = 0L;


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
        var map = Util.initMap(input);
        var graph: MutableMap<CoordinateWithValue, MutableList<CoordinateWithValue>> = mutableMapOf();

        for (i in 0..<map.size) {
            for (j in 0..<(map[i].size)) {
                graph.getOrPut(CoordinateWithValue(i, j, map[i][j].toInt())) { mutableListOf() }.addAll(getNeighbours(i,
                    j,
                    map));
            }
        }

        graph.entries.forEach {
            if (it.key.value == 0) {
                var visited = mutableListOf<CoordinateWithValue>();
                dfs(graph, it.key, visited)
                firstResult += visited.filter { it.value == 9 }.size;
            }
        }

    }

    private fun second() {
        var map = Util.initMap(input);
        var graph: MutableMap<CoordinateWithValue, MutableList<CoordinateWithValue>> = mutableMapOf();

        for (i in 0..<map.size) {
            for (j in 0..<(map[i].size)) {
                graph.getOrPut(CoordinateWithValue(i, j, map[i][j].toInt())) { mutableListOf() }.addAll(getNeighbours(i,
                    j,
                    map));
            }
        }

        graph.entries.forEach {
            if (it.key.value == 0) {
                var travers = bfsSecond(graph, it.key);
//                secondResult += travers.filter { it.value == 9 }.size
            }
        }
    }

    fun dfs(graph: Map<CoordinateWithValue, List<CoordinateWithValue>>,
            start: CoordinateWithValue,
            visited: MutableList<CoordinateWithValue>) {
        visited.add(start);
        for (v in graph[start]!!) {
            if (!visited.contains(v) && (v.value == start.value + 1)) {
                dfs(graph, v, visited);
            }
        }
    }

    fun bfs(graph: Map<CoordinateWithValue, List<CoordinateWithValue>>,
            start: CoordinateWithValue): Set<CoordinateWithValue> {
        val visited = mutableSetOf<CoordinateWithValue>()
        val queue = ArrayDeque<CoordinateWithValue>()
        queue.add(start)
        while (queue.isNotEmpty()) {
            val vertex = queue.removeFirst()
            if (vertex !in visited) {
                visited.add(vertex)
                graph[vertex]?.let { neighbors -> queue.addAll(neighbors.filterNot { it in visited }.filter { n -> n.value == vertex.value + 1 }) }
            }
        }
        return visited
    }

    fun bfsSecond(graph: Map<CoordinateWithValue, List<CoordinateWithValue>>,
                  start: CoordinateWithValue): Set<CoordinateWithValue> {
        var asd = 0;
        val visited = mutableSetOf<CoordinateWithValue>()
        val queue = ArrayDeque<CoordinateWithValue>()
        queue.add(start)
        while (queue.isNotEmpty()) {
            val vertex = queue.removeFirst()
//            if (vertex !in visited) {
            if (vertex.value == 9) {
                asd++;
            }
            visited.add(vertex)
            graph[vertex]?.let { neighbors -> queue.addAll(neighbors/*.filterNot { it in visited }*/.filter { n -> n.value == vertex.value + 1 }) }
//            }
        }
        secondResult += asd;
        return visited
    }

    private fun getNeighbours(i: Int, j: Int, map: MutableList<MutableList<String>>): List<CoordinateWithValue> {
        var list: MutableList<CoordinateWithValue> = mutableListOf<CoordinateWithValue>();
        if (i - 1 >= 0) {
            list.add(CoordinateWithValue(i - 1, j, map[i - 1][j].toInt()))
        }
        if (j - 1 >= 0) {
            list.add(CoordinateWithValue(i, j - 1, map[i][j - 1].toInt()))
        }
        if (i + 1 < map.size) {
            list.add(CoordinateWithValue(i + 1, j, map[i + 1][j].toInt()))
        }
        if (j + 1 < map[0].size) {
            list.add(CoordinateWithValue(i, j + 1, map[i][j + 1].toInt()))
        }
        return list;
    }

    data class CoordinateWithValue(var x: Int, var y: Int, var value: Int) {}
}