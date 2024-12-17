package advent.of.code.y2024

import advent.of.code.Reader
import utils.Util
import utils.println
import java.util.*

class p16 {

    companion object {
        val YEAR = "2024";
        val DAY = "16";
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
        var graph: MutableMap<Util.Coordinate, Pair<MutableList<Pair<Util.Coordinate, Int>>, String>> = mutableMapOf();

        for (i in 0..<map.size) {
            for (j in 0..<(map[i].size)) {
                if (map[i][j] != "#") {
                    graph[Util.Coordinate(i, j)] = Pair(getNeighbours(i, j, map).map { Pair(it, 1) }.toMutableList(),
                        "E");
                }
            }
        }

        var path: Map<Util.Coordinate, Int> = mutableMapOf()
        for (i in 0..<map.size) {
            for (j in 0..<map[0].size) {
                if (map[i][j] == "S") {
                    path = dijkstra(graph, Util.Coordinate(i, j));
                }
            }
        }

        firstResult = path.entries.filter { n -> map[n.key.x][n.key.y] == "E" }.map { n -> n.value }[0].toLong()

    }

    private fun second() {
        var map = Util.initMap(input);
        var graphS: MutableMap<Util.Coordinate, Pair<MutableList<Pair<Util.Coordinate, Int>>, String>> = mutableMapOf();
        var graphE1: MutableMap<Util.Coordinate, Pair<MutableList<Pair<Util.Coordinate, Int>>, String>> = mutableMapOf();
        var graphE2: MutableMap<Util.Coordinate, Pair<MutableList<Pair<Util.Coordinate, Int>>, String>> = mutableMapOf();
        var graphE3: MutableMap<Util.Coordinate, Pair<MutableList<Pair<Util.Coordinate, Int>>, String>> = mutableMapOf();
        var graphE4: MutableMap<Util.Coordinate, Pair<MutableList<Pair<Util.Coordinate, Int>>, String>> = mutableMapOf();
        var graphPath: MutableMap<Util.Coordinate, Pair<MutableList<Pair<Util.Coordinate, Int>>, String>> = mutableMapOf();

        for (i in 0..<map.size) {
            for (j in 0..<(map[i].size)) {
                if (map[i][j] != "#") {
                    graphS[Util.Coordinate(i, j)] = Pair(getNeighbours(i, j, map).map { Pair(it, 1) }.toMutableList(), "E");
                    graphE1[Util.Coordinate(i, j)] = Pair(getNeighboursE(i, j, map).map { Pair(it, 1) }.toMutableList(), "E");
                    graphE2[Util.Coordinate(i, j)] = Pair(getNeighboursE(i, j, map).map { Pair(it, 1) }.toMutableList(), "S");
                    graphE3[Util.Coordinate(i, j)] = Pair(getNeighboursE(i, j, map).map { Pair(it, 1) }.toMutableList(), "N");
                    graphE4[Util.Coordinate(i, j)] = Pair(getNeighboursE(i, j, map).map { Pair(it, 1) }.toMutableList(), "W");
                    graphPath[Util.Coordinate(i, j)] = Pair(getNeighbours(i, j, map).map {
                        Pair(it, 1)
                    }.toMutableList(), "E");
                }
            }
        }

        var pathFromS: Map<Util.Coordinate, Int> = mutableMapOf()
        var pathFromE1: Map<Util.Coordinate, Int> = mutableMapOf()
        var pathFromE2: Map<Util.Coordinate, Int> = mutableMapOf()
        var pathFromE3: Map<Util.Coordinate, Int> = mutableMapOf()
        var pathFromE4: Map<Util.Coordinate, Int> = mutableMapOf()
        for (i in 0..<map.size) {
            for (j in 0..<map[0].size) {

                if (map[i][j] == "E") {
                    pathFromE1 = dijkstra(graphE1, Util.Coordinate(i, j));
                    pathFromE2 = dijkstra(graphE2, Util.Coordinate(i, j));
                    pathFromE3 = dijkstra(graphE3, Util.Coordinate(i, j));
                    pathFromE4 = dijkstra(graphE4, Util.Coordinate(i, j));
                }

                if (map[i][j] == "S") {

                    pathFromS = dijkstra(graphS, Util.Coordinate(i, j));

                }

            }
        }

        var shortestPathPoints = pathFromS.entries.filter { n -> map[n.key.x][n.key.y] == "E" }.map { n -> n.value }[0]

        var shortestPathNodes: MutableList<Util.Coordinate> = mutableListOf();

        pathFromS.entries.forEach { s ->
            pathFromE1.entries.forEach { e ->
                if (s.key.x == e.key.x && s.key.y == e.key.y) {
                    if (e.value + s.value == shortestPathPoints) {
                        shortestPathNodes.add(e.key)
                    }
                }
            }

            pathFromE2.entries.forEach { e ->
                if (s.key.x == e.key.x && s.key.y == e.key.y) {
                    if (e.value + s.value == shortestPathPoints) {
                        shortestPathNodes.add(e.key)
                    }
                }
            }

            pathFromE3.entries.forEach { e ->
                if (s.key.x == e.key.x && s.key.y == e.key.y) {
                    if (e.value + s.value == shortestPathPoints) {
                        shortestPathNodes.add(e.key)
                    }
                }
            }

            pathFromE4.entries.forEach { e ->
                if (s.key.x == e.key.x && s.key.y == e.key.y) {
                    if (e.value + s.value == shortestPathPoints) {
                        shortestPathNodes.add(e.key)
                    }
                }
            }
        }

        secondResult = shortestPathNodes.distinct().size.toLong();

    }

    fun dijkstra(graph: MutableMap<Util.Coordinate, Pair<MutableList<Pair<Util.Coordinate, Int>>, String>>,
                 start: Util.Coordinate): Map<Util.Coordinate, Int> {
        val distances = mutableMapOf<Util.Coordinate, Int>().withDefault { Int.MAX_VALUE }
        val priorityQueue = PriorityQueue<Pair<Util.Coordinate, Int>>(compareBy { it.second }).apply { add(start to 0) }

        distances[start] = 0

        while (priorityQueue.isNotEmpty()) {
            val (node, currentDist) = priorityQueue.poll()
            graph[node]?.first!!.forEach { (adjacent, weight) ->
                var w = weight;
                if (graph[node]?.second != getDirection(adjacent, node)) {
                    w = 1001;
                }
                val totalDist = currentDist + w
                if (totalDist < distances.getValue(adjacent)) {
                    distances[adjacent] = totalDist
                    priorityQueue.add(adjacent to totalDist)
                    graph.set(adjacent, graph[adjacent]!!.copy(second = getDirection(adjacent, node)));
                }
            }
        }
        return distances
    }

    fun dijkstraPath(graph: MutableMap<Util.Coordinate, Pair<MutableList<Pair<Util.Coordinate, Int>>, String>>,
                     start: Util.Coordinate): MutableMap<Util.Coordinate, Pair<Util.Coordinate, Long>> {
        val distances = mutableMapOf<Util.Coordinate, Int>().withDefault { Int.MAX_VALUE }
        val priorityQueue = PriorityQueue<Pair<Util.Coordinate, Int>>(compareBy { it.second }).apply { add(start to 0) }

        distances[start] = 0

        val previous: MutableMap<Util.Coordinate, Pair<Util.Coordinate, Long>> = mutableMapOf();

        while (priorityQueue.isNotEmpty()) {
            val (node, currentDist) = priorityQueue.poll()
            graph[node]?.first!!.forEach { (adjacent, weight) ->
                var w = weight;
                if (graph[node]?.second != getDirection(adjacent, node)) {
                    w = 1001;
                }
                val totalDist = currentDist + w
                if (totalDist < distances.getValue(adjacent)) {
                    distances[adjacent] = totalDist
                    priorityQueue.add(adjacent to totalDist)
                    graph.set(adjacent, graph[adjacent]!!.copy(second = getDirection(adjacent, node)));
                    previous.put(adjacent, Pair(node, totalDist.toLong()));
                }

            }
        }
        return previous
    }

    private fun getNeighbours(i: Int, j: Int, map: MutableList<MutableList<String>>): MutableList<Util.Coordinate> {
        var list: MutableList<Util.Coordinate> = mutableListOf<Util.Coordinate>();
        if (map[i][j] != "E") {
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
        }
        return list;
    }

    private fun getNeighboursE(i: Int, j: Int, map: MutableList<MutableList<String>>): MutableList<Util.Coordinate> {
        var list: MutableList<Util.Coordinate> = mutableListOf<Util.Coordinate>();
        if (map[i][j] != "S") {
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
        }
        return list;
    }

    private fun getDirection(neighbour: Util.Coordinate, node: Util.Coordinate): String {
        if (neighbour.x == node.x && neighbour.y == node.y + 1) {
            return "E"
        }

        if (neighbour.x == node.x && neighbour.y == node.y - 1) {
            return "W"
        }

        if (neighbour.x == node.x - 1 && neighbour.y == node.y) {
            return "N"
        }

        if (neighbour.x == node.x + 1 && neighbour.y == node.y) {
            return "S"
        }

        return "E"
    }
}