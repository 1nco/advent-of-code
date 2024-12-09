package utils

import java.io.File
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

        fun saveMapToFile(map: MutableList<MutableList<String>>, fileName: String = "test") {
            val file = File("src/advent/of/code/inputs/$fileName.txt");
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

        private fun findLCM(a: Long, b: Long): Long {
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

        data class Coordinate(val x: Long, val y: Long);
    }
}