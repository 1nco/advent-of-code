package advent.of.code

import advent.of.code.types.Pos
import utils.Util
import java.util.*

object `23thPuzzle` {

    private const val DAY = "23";

    private var input: MutableList<String> = arrayListOf();

    private var result = 0L;

    private var resultSecond = 0L;

    private var max = 0;
    private var maxSecond = 0;
    private val visited = mutableSetOf<Pos>()

    fun solve() {
        val startingTime = Date();
        input.addAll(Reader.readInput(DAY));

//        first();

        second();

        println("first: $result");
        println("second: $resultSecond");


        println(startingTime);
        println(Date());

    }

    private fun first() {
        val map = Util.initMap(input);

        dfs(Pos(0, map[0].indexOf(".")), Pos((map.size - 1), map.last().lastIndexOf(".")), 0, map)
        result = max.toLong();
    }


    private fun second() {
        val map =
            Util.initMap(input.map { it.replace(">", ".").replace("<", ".").replace("v", ".").replace("^", ".") });

        resultSecond = dfsSecond(
            makeAdjacencies(map),
            Pos(0, map[0].indexOf(".")),
            Pos((map.size - 1), map.last().lastIndexOf(".")),
            map,
            mutableMapOf()
        )!!.toLong()
    }

    private fun dfs(cur: Pos, end: Pos, steps: Int = 0, map: MutableList<MutableList<String>>) {
        if (cur == end) {
            max = maxOf(max, steps)
            return
        }
        visited.add(cur)
        getNeighbours(cur, map).filter { it !in visited }.forEach { dfs(it, end, steps + 1, map) }
        visited.remove(cur)
    }

    private fun getNeighbours(curr: Pos, map: MutableList<MutableList<String>>): List<Pos> {
        var neighbours: MutableList<Pos> = arrayListOf();
        var currValue = ".";
        if (curr.line >= 0 && curr.line < map.size && curr.column >= 0 && curr.column < map[0].size) {
            currValue = map[curr.line][curr.column];
        }

        val directions = when (currValue) {
            ">" -> arrayListOf(Pos(0, 1))
            "<" -> arrayListOf(Pos(0, -1))
            "^" -> arrayListOf(Pos(-1, 0))
            "v" -> arrayListOf(Pos(1, 0))
            else -> arrayListOf(Pos(1, 0), Pos(-1, 0), Pos(0, 1), Pos(0, -1));
        }

        directions.forEach { dir ->
            val neighbourVertex = Pos(curr.line + dir.line, curr.column + dir.column);
            if (neighbourVertex.line >= 0 && neighbourVertex.line < map.size && neighbourVertex.column >= 0 && neighbourVertex.column < map[0].size) {
                val neighbourVertexValue = map[neighbourVertex.line][neighbourVertex.column];
                if (neighbourVertexValue != "#") {
                    neighbours.add(neighbourVertex);
                }
            }
        }
        return neighbours;
    }

    private fun dfsSecond(
        graph: Map<Pos, Map<Pos, Int>>,
        cur: Pos,
        end: Pos,
        map: MutableList<MutableList<String>>,
        seen: MutableMap<Pos, Int>
    ): Int? {
        if (cur == end) {
            return seen.map { s -> s.value }.sum();
        }
        var best: Int? = null;
        graph[cur]!!.forEach { (neighbour, steps) ->
            if (neighbour !in seen) {
                seen[neighbour] = steps;
                val res = dfsSecond(graph, neighbour, end, map, seen)
                if (best == null || (res != null && res > best!!)) {
                    best = res
                }
                seen.remove(neighbour)
            }
        }
        return best
    }

    private fun makeAdjacencies(map: MutableList<MutableList<String>>): Map<Pos, Map<Pos, Int>> {
        val adjacencies = map.indices.flatMap { x ->
            map[0].indices.mapNotNull { y ->
                if (map[x][y] != "#") {
                    val directions = arrayListOf(Pos(1, 0), Pos(-1, 0), Pos(0, 1), Pos(0, -1));
                    Pos(x, y) to directions.mapNotNull { (dx, dy) ->
                        val nx = x + dx
                        val ny = y + dy
                        if (nx in map.indices && ny in map[0].indices && map[nx][ny] != "#") Pos(nx, ny) to 1 else null
                    }.toMap(mutableMapOf())
                } else null
            }
        }.toMap(mutableMapOf())

        adjacencies.keys.toList().forEach { key ->
            adjacencies[key]?.takeIf { it.size == 2 }?.let { neighbors ->
                val left = neighbors.keys.first()
                val right = neighbors.keys.last()
                val totalSteps = neighbors[left]!! + neighbors[right]!!
                adjacencies.getOrPut(left) { mutableMapOf() }.merge(right, totalSteps, ::maxOf)
                adjacencies.getOrPut(right) { mutableMapOf() }.merge(left, totalSteps, ::maxOf)
                listOf(left, right).forEach { adjacencies[it]?.remove(key) }
                adjacencies.remove(key)
            }
        }
        return adjacencies
    }


}