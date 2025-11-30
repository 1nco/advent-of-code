package utils

import java.io.File
import java.util.PriorityQueue
import kotlin.math.absoluteValue

class Util {
    companion object {
        fun getNumbers(line: String, separator: String = "\\b"): List<Long> {
            return Regex("-?\\d+").findAll(line).toList().map { it.value.toLong() };
        }

        fun getAlphaNumericalCharacters(line: String): String {
            return Regex("([A-Za-z])+").findAll(line).toList().map { it.value.toString() }.joinToString("");
        }

        fun initMap(input: List<String>): MutableList<MutableList<String>> {
            val map: MutableList<MutableList<String>> = arrayListOf();
            var currLine = 0;
            input.forEach {
                map.add(arrayListOf());
                var currCol = 0;
                it.forEach {c ->
                    map[currLine].add(c.toString())
                    currCol++
                }
                currLine++
            }
            return map;
        }

        fun initMapWithNumbers(input: List<String>): MutableList<MutableList<Int>> {
            val map: MutableList<MutableList<Int>> = arrayListOf();
            var currLine = 0;
            input.forEach {
                map.add(arrayListOf());
                var currCol = 0;
                it.forEach {c ->
                    map[currLine].add(Integer.parseInt(c.toString()))
                    currCol++
                }
                currLine++
            }
            return map;
        }

        fun createCopy(map: MutableList<MutableList<String>>): MutableList<MutableList<String>> {
            val copy: MutableList<MutableList<String>> = arrayListOf();

            for (i in 0..<map.size) {
                copy.add(arrayListOf());
                for (j in 0..<map[0].size) {
                    copy[i].add(map[i][j])
                }
            }
            return copy;
        }

        fun saveMapToFile(map: MutableList<MutableList<String>>, fileName: String = "test", output: String = "src/advent/of/code/output") {
            val file = File("$output/$fileName.txt");
            val output = arrayListOf<String>()
            map.forEach { l ->
                var lineToPrint = "";
                l.forEach { c ->
                    lineToPrint += c;
                }
                output.add(lineToPrint);
            }
            file.printWriter().use { out ->
                output.forEach { line ->
                    out.println(line);
                }
            };
        }

        fun findLCMOfListOfNumbers(numbers: List<Long>): Long {
            var result = numbers[0]
            for (i in 1 until numbers.size) {
                result = findLCM(result, numbers[i])
            }
            return result
        }

        fun findLCM(a: Long, b: Long): Long {
            val larger = if (a > b) a else b
            val maxLcm = a * b
            var lcm = larger
            while (lcm <= maxLcm) {
                if (lcm % a == 0L && lcm % b == 0L) {
                    return lcm
                }
                lcm += larger
            }
            return maxLcm
        }

        fun mapLineToLongList(line: String, split: String = " "): MutableList<Long> {
            return line.split(split).map(String::toLong).toMutableList();
        }

        fun shoelace(list: List<Coordinate>): Long {
            return list
                .reversed()
                .asSequence()
                .plus(list.last())
                .windowed(2)
                .sumOf { (a, b) -> a.x * b.y - a.y * b.x}
                .absoluteValue
                .minus(list.size)
                .div(2)
                .plus(1)
                .toLong()
        }

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

    data class CoordinateLong(var x: Long, var y: Long);
    data class Coordinate(var x: Int, var y: Int);
    data class CoordinateWithType(var x: Int, var y: Int, var type: String) {}
    data class CoordinateWithValue(var x: Int, var y: Int, var value: Int) {}
}