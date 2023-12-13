package advent.of.code

import java.io.File

class Util {
    companion object {
        fun getNumbers(line: String, separator: String = "\\b"): List<Long> {
            return Regex("-?\\d+").findAll(line).toList().map { it.value.toLong() };
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

        fun saveMapToFile(map: MutableList<MutableList<String>>, fileName: String = "test") {
            val file = File("out/production/advent-of-code/advent/of/code/inputs/$fileName.txt");
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
    }
}