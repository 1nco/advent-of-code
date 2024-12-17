package advent.of.code.y2024

import advent.of.code.Reader
import utils.Util.*
import utils.Util.Companion.initMap
import utils.println
//import utils.Util
import java.util.*

class p12 {

    companion object {
        val YEAR = "2024";
        val DAY = "12";
    }

    private var input: MutableList<String> = arrayListOf();

    private var inputAsString: String = "";

    private var firstResult = 0L;

    private var secondResult = 0L;

    fun solve() {
        val startingTime = Date();
        inputAsString = Reader.readInputAsString(YEAR, DAY);
        input.addAll(Reader.readInput(YEAR, DAY));

//        first();

        second();

        println("first: $firstResult");
        println("second: $secondResult");


        println(startingTime);
        println(Date());

    }

    private fun first() {
        var map = initMap(input);
        var graph: MutableMap<CoordinateWithType, MutableList<CoordinateWithType>> = mutableMapOf();
        for (i in 0..<map.size) {
            for (j in 0..<(map[i].size)) {
                graph.getOrPut(CoordinateWithType(i, j, map[i][j])) { mutableListOf() }.addAll(getNeighbours(i,
                    j,
                    map));
            }
        }

        var visited = mutableListOf<CoordinateWithType>();
        graph.entries.forEach {
            if (!visited.contains(it.key)) {
                var traversal = bfs(graph, it.key)
                visited.addAll(traversal);
                traversal.println();
                var perimeter = calculatePerimeter(traversal.toMutableList())
                firstResult += traversal.size * perimeter;
            }
        }
    }

    private fun second() {
        var map = initMap(input);
        var graph: MutableMap<CoordinateWithType, MutableList<CoordinateWithType>> = mutableMapOf();
        for (i in 0..<map.size) {
            for (j in 0..<(map[i].size)) {
                graph.getOrPut(CoordinateWithType(i, j, map[i][j])) { mutableListOf() }.addAll(getNeighbours(i,
                    j,
                    map));
            }
        }

        var visited = mutableListOf<CoordinateWithType>();
        graph.entries.forEach {
            if (!visited.contains(it.key)) {
                var traversal = bfs(graph, it.key)
                visited.addAll(traversal);
//                var perimeter = calculateSides(traversal.toMutableList())
                var perimeter = calculateSidesV2(traversal.toMutableList())
                println("${it.key.type}: ${traversal.size} * ${perimeter} = ${traversal.size * perimeter}");
                secondResult += traversal.size * perimeter;

                // WRONG
                // 806050
                // 786555

                // GOOD: 805880
            }
        }
    }

    fun bfs(graph: Map<CoordinateWithType, List<CoordinateWithType>>,
            start: CoordinateWithType): Set<CoordinateWithType> {
        val visited = mutableSetOf<CoordinateWithType>()
        val queue = ArrayDeque<CoordinateWithType>()
        queue.add(start)
        while (queue.isNotEmpty()) {
            val vertex = queue.removeFirst()
            if (vertex !in visited) {
                visited.add(vertex)
                graph[vertex]?.let { neighbors -> queue.addAll(neighbors.filterNot { it in visited }.filter { n -> n.type == vertex.type }) }
            }
        }
        return visited
    }

    private fun getNeighbours(i: Int, j: Int, map: MutableList<MutableList<String>>): List<CoordinateWithType> {
        var list: MutableList<CoordinateWithType> = mutableListOf<CoordinateWithType>();
        if (i - 1 >= 0) {
            list.add(CoordinateWithType(i - 1, j, map[i - 1][j]))
        }
        if (j - 1 >= 0) {
            list.add(CoordinateWithType(i, j - 1, map[i][j - 1]))
        }
        if (i + 1 < map.size) {
            list.add(CoordinateWithType(i + 1, j, map[i + 1][j]))
        }
        if (j + 1 < map[0].size) {
            list.add(CoordinateWithType(i, j + 1, map[i][j + 1]))
        }
        return list;
    }


    private fun calculatePerimeter(visited: MutableList<CoordinateWithType>): Long {
        var perimeter = 0L;
        for (i in 0..<visited.size) {
            var el = visited[i];
            if (!visited.contains(CoordinateWithType(el.x - 1, el.y, el.type))) {
                perimeter++;
            }
            if (!visited.contains(CoordinateWithType(el.x + 1, el.y, el.type))) {
                perimeter++;
            }
            if (!visited.contains(CoordinateWithType(el.x, el.y - 1, el.type))) {
                perimeter++;
            }
            if (!visited.contains(CoordinateWithType(el.x, el.y + 1, el.type))) {
                perimeter++;
            }
        }
        return perimeter;
    }

    private fun calculateSides(visited: MutableList<CoordinateWithType>): Long {
        var fencesCalculated: MutableList<CoordinateWithType> = mutableListOf();
        var fenceCount = 0L;
        for (i in 0..<visited.size) {
            var el = visited[i];
            if (!visited.contains(CoordinateWithType(el.x - 1, el.y, el.type))) {
                if (fencesCalculated.filter { fence -> fence.x == el.x - 1 && fence.type == "T" && (fence.y == el.y - 1 || fence.y == el.y + 1) }.isEmpty()) {
                    fenceCount++;
                }
                fencesCalculated.add(CoordinateWithType(el.x - 1, el.y, "T"));
            }
            if (!visited.contains(CoordinateWithType(el.x + 1, el.y, el.type))) {
                if (fencesCalculated.filter { fence -> fence.x == el.x + 1 && fence.type == "B" && (fence.y == el.y - 1 || fence.y == el.y + 1) }.isEmpty()) {
                    fenceCount++;
                }
                fencesCalculated.add(CoordinateWithType(el.x + 1, el.y, "B"));
            }
            if (!visited.contains(CoordinateWithType(el.x, el.y - 1, el.type))) {
                if (fencesCalculated.filter { fence -> fence.y == el.y - 1 && fence.type == "L" && (fence.x == el.x - 1 || fence.x == el.x + 1) }.isEmpty()) {
                    fenceCount++;
                }
                fencesCalculated.add(CoordinateWithType(el.x, el.y - 1, "L"));
            }
            if (!visited.contains(CoordinateWithType(el.x, el.y + 1, el.type))) {
                if (fencesCalculated.filter { fence -> fence.y == el.y + 1 && fence.type == "R" && (fence.x == el.x - 1 || fence.x == el.x + 1) }.isEmpty()) {
                    fenceCount++;
                }
                fencesCalculated.add(CoordinateWithType(el.x, el.y + 1, "R"));
            }
        }
        return fenceCount;
    }

    private fun calculateSidesV2(visited: MutableList<CoordinateWithType>): Long {
        var fences: MutableList<CoordinateWithType> = mutableListOf();
        var fenceCount = 0L;
        for (i in 0..<visited.size) {
            var el = visited[i];
            if (!visited.contains(CoordinateWithType(el.x - 1, el.y, el.type))) {
                fences.add(CoordinateWithType(el.x - 1, el.y, "T"));
            }
            if (!visited.contains(CoordinateWithType(el.x + 1, el.y, el.type))) {
                fences.add(CoordinateWithType(el.x + 1, el.y, "B"));
            }
            if (!visited.contains(CoordinateWithType(el.x, el.y - 1, el.type))) {
                fences.add(CoordinateWithType(el.x, el.y - 1, "L"));
            }
            if (!visited.contains(CoordinateWithType(el.x, el.y + 1, el.type))) {
                fences.add(CoordinateWithType(el.x, el.y + 1, "R"));
            }
        }

        var alreadyCalculated: MutableList<CoordinateWithType> = mutableListOf();
        for (i in 0..<fences.size) {
            var el = fences[i];
            if (el.type == "T" && !alreadyCalculated.contains(el)) {
                markFencesDone(el, fences, alreadyCalculated)
                fenceCount++
            }

            if (el.type == "B" && !alreadyCalculated.contains(el)) {
                markFencesDone(el, fences, alreadyCalculated)
                fenceCount++
            }

            if (el.type == "L" && !alreadyCalculated.contains(el)) {
                markFencesDone(el, fences, alreadyCalculated)
                fenceCount++
            }

            if (el.type == "R" && !alreadyCalculated.contains(el)) {
                markFencesDone(el, fences, alreadyCalculated)
                fenceCount++
            }
        }
        return fenceCount;
    }

    private fun markFencesDone(el: CoordinateWithType,
                               fences: MutableList<CoordinateWithType>,
                               alreadyCalculated: MutableList<CoordinateWithType>) {
        if (el.type == "T") {
            alreadyCalculated.add(el);
            fences.filter { fence -> fence.x == el.x && (fence.type == "T") && (fence.y == el.y - 1 || fence.y == el.y + 1) }.forEach { f ->
                if (!alreadyCalculated.contains(f)) {
                    markFencesDone(f, fences, alreadyCalculated)
                }
            }
        }
        if (el.type == "B") {
            alreadyCalculated.add(el);
            fences.filter { fence -> fence.x == el.x && (fence.type == "B") && (fence.y == el.y - 1 || fence.y == el.y + 1) }.forEach { f ->
                if (!alreadyCalculated.contains(f)) {
                    markFencesDone(f, fences, alreadyCalculated)
                }
            }
        }

        if (el.type == "L") {
            alreadyCalculated.add(el);
            fences.filter { fence -> fence.y == el.y && (fence.type == "L") && (fence.x == el.x - 1 || fence.x == el.x + 1) }.forEach { f ->
                if (!alreadyCalculated.contains(f)) {
                    markFencesDone(f, fences, alreadyCalculated)
                }
            }
        }

        if (el.type == "R") {
            alreadyCalculated.add(el);
            fences.filter { fence -> fence.y == el.y && (fence.type == "R") && (fence.x == el.x - 1 || fence.x == el.x + 1) }.forEach { f ->
                if (!alreadyCalculated.contains(f)) {
                    markFencesDone(f, fences, alreadyCalculated)
                }
            }
        }
    }
}