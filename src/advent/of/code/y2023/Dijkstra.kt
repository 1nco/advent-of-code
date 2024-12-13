package advent.of.code.y2023

import advent.of.code.y2023.types.Graph

fun dijkstra(graph: Graph<String>, start: String): Map<String, String?> {
    var S: MutableSet<String> = mutableSetOf() // a subset of vertices, for which we know the true distance

    /*
     * delta represents the length of the shortest distance paths
     * from start to v, for v in vertices.
     *
     * The values are initialized to infinity, as we'll be getting the key with the min value
     */
    var delta = graph.vertices.map { it to Int.MAX_VALUE }.toMap().toMutableMap()
    delta[start] = 0

    val previous: MutableMap<String, String?> = graph.vertices.map { it to null }.toMap().toMutableMap()

    while (S != graph.vertices) {
        // let v be the closest vertex that has not yet been visited
        val v: String = delta
            .filter { !S.contains(it.key) }
            .minBy { it.value }!!
            .key

        graph.edges.getValue(v).minus(S).forEach { neighbor ->

            var last = "D"
            if (previous[v] != null) {
                last = getMovementDirection(previous[v]!!, v);
            }
            val movement = getMovementDirection(v, neighbor);
            val checkableNeighbors: MutableMap<String, String> = mutableMapOf();

            if ((last == "D" || last == "U")) {
                if (movement == "R") {
                    val vertex: Pos = Pos(v.split(";")[0].toInt(), v.split(";")[1].toInt());
                    val neighborVertex: Pos = Pos(neighbor.split(";")[0].toInt(), neighbor.split(";")[1].toInt());
                    for (i in 0..2) {
                        checkableNeighbors.put(
                            "${vertex.line};${vertex.column + i}",
                            "${neighborVertex.line};${neighborVertex.column + i}"
                        )
                    }
                }

                if (movement == "L") {
                    val vertex: Pos = Pos(v.split(";")[0].toInt(), v.split(";")[1].toInt());
                    val neighborVertex: Pos = Pos(neighbor.split(";")[0].toInt(), neighbor.split(";")[1].toInt());
                    for (i in 0..2) {
                        checkableNeighbors.put(
                            "${vertex.line};${vertex.column - i}",
                            "${neighborVertex.line};${neighborVertex.column - i}"
                        )
                    }
                }
            } else if ((last == "R" || last == "L")) {
                if (movement == "D") {
                    val vertex: Pos = Pos(v.split(";")[0].toInt(), v.split(";")[1].toInt());
                    val neighborVertex: Pos = Pos(neighbor.split(";")[0].toInt(), neighbor.split(";")[1].toInt());
                    for (i in 0..2) {
                        checkableNeighbors.put(
                            "${vertex.line + i};${vertex.column}",
                            "${neighborVertex.line + i};${neighborVertex.column}"
                        )
                    }
                }

                if (movement == "U") {
                    val vertex: Pos = Pos(v.split(";")[0].toInt(), v.split(";")[1].toInt());
                    val neighborVertex: Pos = Pos(neighbor.split(";")[0].toInt(), neighbor.split(";")[1].toInt());
                    for (i in 0..2) {
                        checkableNeighbors.put(
                            "${vertex.line - i};${vertex.column}",
                            "${neighborVertex.line - i};${neighborVertex.column}"
                        )
                    }
                }
            }

            checkableNeighbors.forEach { nv ->
                if (delta.contains(nv.value)) {
                    val newPath = delta.getValue(nv.key) + graph.weights.getValue(Pair(nv.key, nv.value))
                    if (newPath < delta.getValue(nv.value)) {
                        delta[nv.value] = newPath
                        previous[nv.value] = nv.key
                    }
                }
            }


//            var last = "R"
//            if (previous[v] != null) {
//                last = getMovementDirection(previous[v]!!, v);
//            }
//            val movement = getMovementDirection(v, neighbor);
//
//            val newPath = delta.getValue(v) + graph.weights.getValue(Pair(v, neighbor))
//            if (newPath < delta.getValue(neighbor) && !wasLast3MovesTheSame(previous, v, movement) && notGoingBackwards(last, movement)) {
//                delta[neighbor] = newPath
//                previous[neighbor] = v
//            }
        }

        S.add(v)
    }

    return previous.toMap()
}


//private fun <T> getValue(d: MutableMap<T, Int>, prev: MutableMap<T, T?>, v: T): Int {
//    val previous = prev as MutableMap<String, String>;
//    val vertex = v.toString()
//    val delta = d as MutableMap<String, Int>
//    return if (wasLast3MovesTheSame(previous.toMutableMap(), vertex)) {
//        Int.MAX_VALUE;
//    } else {
//        delta[vertex]!!
//    }
//}

private fun notGoingBackwards(last: String, current: String): Boolean {
    if ((last == "U" && current == "D") || (last == "D" && current == "U") || (last == "R" && current == "L") || (last == "L" && current == "R")) {
        return false;
    } else {
        return true;
    }
}

private fun <T> wasLast3MovesTheSame(prev: MutableMap<T, T?>, v: T, curr: String): Boolean {
    val previous = prev as LinkedHashMap<String, String>;
    val vertex = v.toString()
    if (previous[vertex] != null && previous[previous[vertex]] != null) {
        val last = getMovementDirection(previous[vertex]!!, vertex)
        val beforeLast = getMovementDirection(previous[previous[vertex]]!!, previous[vertex]!!)

        if (last == beforeLast && last == curr && beforeLast == curr) {
            return true
        } else {
            return false
        }
    } else {
        return false;
    }
//    return if(lastMoves.size > 2) lastMoves.subList(lastMoves.size - 3, lastMoves.size).all { it == move } else false;
}

private fun getMovementDirection(v: String, neighbor: String): String {
    val vx = v.split(";")[0].toInt();
    val vy = v.split(";")[1].toInt();

    val nx = neighbor.split(";")[0].toInt();
    val ny = neighbor.split(";")[1].toInt();

    if (vx < nx) {
        return "D";
    }

    if (vx > nx) {
        return "U"
    }

    if (vy < ny) {
        return "R"
    }

    if (vy > ny) {
        return "L"
    }

    return "";
}

fun <T> shortestPath(shortestPathTree: Map<T, T?>, start: T, end: T): List<T> {
    fun pathTo(start: T, end: T): List<T> {
        if (shortestPathTree[end] == null) return listOf(end)
        return listOf(pathTo(start, shortestPathTree[end]!!), listOf(end)).flatten()
    }

    return pathTo(start, end)
}


data class Pos(var line: Int, var column: Int);