package advent.of.code.y2024

import advent.of.code.Reader
import utils.Util
import utils.println
import java.time.LocalDateTime
import java.util.*

class p18 {

    companion object {
        val YEAR = "2024";
        val DAY = "18";
    }

    private var input: MutableList<String> = arrayListOf();

    private var inputAsString: String = "";

    private var firstResult = 0L;

    private var secondResult = "";


    fun solve() {
        val startingTime = LocalDateTime.now();
        inputAsString = Reader.readInputAsString(YEAR, DAY);
        input.addAll(Reader.readInput(YEAR, DAY));


//        first();
        second();

        println("first: $firstResult");
        println("second: $secondResult");


        val now = LocalDateTime.now();
        println("${startingTime}");
        println("${now}");

    }

    private fun first() {
        var width = 71;
//        var width = 7;
        var height = 71;
//        var height = 7;
//        var simulationSize = 12;
        var simulationSize = 1024;

        var map: MutableList<MutableList<String>> = mutableListOf();

        for (i in 0..<height) {
            map.add(mutableListOf())
            for (j in 0..<width) {
                map[i].add(".")
            }
        }

        var bytes = input.map { Util.Coordinate(Util.getNumbers(it)[1].toInt(), Util.getNumbers(it)[0].toInt()) }

        for (i in 0..<simulationSize) {
//        for (i in 0..<bytes.size) {
            var byte = bytes[i];
            map[byte.x][byte.y] = "#";
        }

        var graph: MutableMap<Util.Coordinate, MutableList<Pair<Util.Coordinate, Int>>> = mutableMapOf();

        for (i in 0..<map.size) {
            for (j in 0..<(map[i].size)) {
                if (map[i][j] != "#") {
                    graph[Util.Coordinate(i, j)] = getNeighbours(i, j, map).map { Pair(it, 1) }.toMutableList();
                }
            }
        }

//        Util.saveMapToFile(map);

        var path: Map<Util.Coordinate, Int> = mutableMapOf()
        path = dijkstra(graph, Util.Coordinate(0, 0));

        firstResult = path.entries.filter { n -> n.key.x == map.size - 1 && n.key.y == map[0].size - 1 }.map { n -> n.value }[0].toLong()
    }

    fun dijkstra(graph: MutableMap<Util.Coordinate, MutableList<Pair<Util.Coordinate, Int>>>,
                 start: Util.Coordinate): Map<Util.Coordinate, Int> {
        val distances = mutableMapOf<Util.Coordinate, Int>().withDefault { Int.MAX_VALUE }
        val priorityQueue = PriorityQueue<Pair<Util.Coordinate, Int>>(compareBy { it.second }).apply { add(start to 0) }
        val visited = mutableSetOf<Pair<Util.Coordinate, Int>>()

        distances[start] = 0

        while (priorityQueue.isNotEmpty()) {
            val (node, currentDist) = priorityQueue.poll()
            if (visited.add(node to currentDist)) {
                graph[node]?.forEach { (adjacent, weight) ->
                    var w = weight;
                    val totalDist = currentDist + w
                    if (totalDist < distances.getValue(adjacent)) {
                        distances[adjacent] = totalDist
                        priorityQueue.add(adjacent to totalDist)
                    }
                }
            }
        }
        return distances
    }

    private fun getNeighbours(i: Int, j: Int, map: MutableList<MutableList<String>>): MutableList<Util.Coordinate> {
        var list: MutableList<Util.Coordinate> = mutableListOf<Util.Coordinate>();
//        if (i != map.size -1 && j != map[0].size -1) {
        if (i - 1 >= 0 && map[i - 1][j] != "#") {
            list.add(Util.Coordinate(i - 1, j))
        }
        if (j - 1 >= 0 && map[i][j - 1] != "#") {
            list.add(Util.Coordinate(i, j - 1))
        }
        if (i + 1 < map.size && map[i + 1][j] != "#") {
            list.add(Util.Coordinate(i + 1, j))
        }
        if (j + 1 < map[0].size && map[i][j + 1] != "#") {
            list.add(Util.Coordinate(i, j + 1))
        }
//        }
        return list;
    }

    private fun second() {
        var width = 71;
//        var width = 7;
        var height = 71;
//        var height = 7;
//        var simulationSize = 12;
        var simulationSize = 1024;

        var map: MutableList<MutableList<String>> = mutableListOf();

        for (i in 0..<height) {
            map.add(mutableListOf())
            for (j in 0..<width) {
                map[i].add(".")
            }
        }

        var bytes = input.map { Util.Coordinate(Util.getNumbers(it)[1].toInt(), Util.getNumbers(it)[0].toInt()) }

        var done = false;
        while (!done) {

            for (i in 0..<simulationSize) {
                var byte = bytes[i];
                map[byte.x][byte.y] = "#";
            }

            var graph: MutableMap<Util.Coordinate, MutableList<Pair<Util.Coordinate, Int>>> = mutableMapOf();

            for (i in 0..<map.size) {
                for (j in 0..<(map[i].size)) {
                    if (map[i][j] != "#") {
                        graph[Util.Coordinate(i, j)] = getNeighbours(i, j, map).map { Pair(it, 1) }.toMutableList();
                    }
                }
            }


            var path: Map<Util.Coordinate, Int> = mutableMapOf()
            path = dijkstra(graph, Util.Coordinate(0, 0));

            (path.entries.filter { n -> n.key.x == map.size - 1 && n.key.y == map[0].size - 1 }.size > 0).println();
            done = path.entries.filter { n -> n.key.x == map.size - 1 && n.key.y == map[0].size - 1 }.size == 0
            secondResult = "${bytes[simulationSize - 1].y},${bytes[simulationSize - 1].x}";
            simulationSize++;
        }
    }
}