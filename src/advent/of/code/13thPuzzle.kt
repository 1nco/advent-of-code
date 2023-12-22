package advent.of.code

import utils.Util
import java.util.*

object `13thPuzzle` {

    private const val DAY = "13";

    private var input: MutableList<String> = arrayListOf();

    private var result = 0L;

    private var resultSecond = 0L;

    private var mapCount = 1;

    fun solve() {
        val startingTime = Date();
        advent.of.code.`13thPuzzle`.input.addAll(advent.of.code.Reader.Companion.readInput(advent.of.code.`13thPuzzle`.DAY));

        advent.of.code.`13thPuzzle`.first();

        advent.of.code.`13thPuzzle`.second();

        println("first: ${advent.of.code.`13thPuzzle`.result}");
        println("second: ${advent.of.code.`13thPuzzle`.resultSecond}");


        println(startingTime);
        println(Date());

    }

    fun first() {
        advent.of.code.`13thPuzzle`.mapCount = 1;
        var lines: MutableList<String> = arrayListOf();
        advent.of.code.`13thPuzzle`.input.forEach { l ->
            if (l != "") {
                lines.add(l)
            } else {
                advent.of.code.`13thPuzzle`.result += advent.of.code.`13thPuzzle`.checkMap(
                    Util.Companion.initMap(
                        lines
                    )
                )!!
                advent.of.code.`13thPuzzle`.mapCount++;
                lines = arrayListOf();
            }
        }
        advent.of.code.`13thPuzzle`.result += advent.of.code.`13thPuzzle`.checkMap(
            Util.Companion.initMap(
                lines
            )
        )!!;
    }

    fun second() {
        advent.of.code.`13thPuzzle`.mapCount = 1;
        var lines: MutableList<String> = arrayListOf();
        advent.of.code.`13thPuzzle`.input.forEach { l ->
            if (l != "") {
                lines.add(l)
            } else {
                val newReflection =
                    advent.of.code.`13thPuzzle`.fixSmudgeAndGetNewReflection(Util.Companion.initMap(lines))
                if (newReflection != null) {
                    advent.of.code.`13thPuzzle`.resultSecond += newReflection;
                } else {
                    println("Something's wrong with map ${advent.of.code.`13thPuzzle`.mapCount}");
                }
                advent.of.code.`13thPuzzle`.mapCount++;
                lines = arrayListOf();
            }
        }

        val newReflection =
            advent.of.code.`13thPuzzle`.fixSmudgeAndGetNewReflection(Util.Companion.initMap(lines))
        if (newReflection != null) {
            advent.of.code.`13thPuzzle`.resultSecond += newReflection;
        } else {
            println("Something's wrong with map ${advent.of.code.`13thPuzzle`.mapCount}");
        }
    }

    private fun fixSmudgeAndGetNewReflection(map: MutableList<MutableList<String>>): Long? {
        val verticalReflectionPositions: MutableList<Long> = arrayListOf();
        val horizontalReflectionPositions: MutableList<Long> = arrayListOf();
        for (i in 0..<map.size) {
            for (j in 0..<map[0].size) {
                val verticalReflectionPosition = advent.of.code.`13thPuzzle`.checkReflectionVerticalP2(
                    advent.of.code.`13thPuzzle`.fixSmudge(
                        map,
                        i,
                        j
                    ), map
                );
                if (verticalReflectionPosition != null) {
                    verticalReflectionPositions.add(verticalReflectionPosition);
                }

                val horizontalReflectionPosition = advent.of.code.`13thPuzzle`.checkReflectionHorizontalP2(
                    advent.of.code.`13thPuzzle`.fixSmudge(
                        map,
                        i,
                        j
                    ), map
                );
                if (horizontalReflectionPosition != null) {
                    horizontalReflectionPositions.add(horizontalReflectionPosition);
                }
            }
        }
        val validVerticalPositions = verticalReflectionPositions/*.filter { r -> r != checkMap(map) }*/
        if (validVerticalPositions.isNotEmpty()) {
            return validVerticalPositions.first();
        }

        val validHorizontalPositions = horizontalReflectionPositions.map{it * 100}/*.filter { r -> r != checkMap(map) }*/
        if (validHorizontalPositions.isNotEmpty()) {
            return validHorizontalPositions.first();
        }

        return null;
    }

    private fun fixSmudge(map: MutableList<MutableList<String>>, x: Int, y: Int): MutableList<MutableList<String>> {
        val fixedMap: MutableList<MutableList<String>> = arrayListOf();

        for (i in 0..<map.size) {
            fixedMap.add(arrayListOf());
            for (j in 0..<map[0].size) {
                if (x == i && y == j) {
                    if (map[i][j] == ".") {
                        fixedMap[i].add("#");
                    } else {
                        fixedMap[i].add(".")
                    }
                } else {
                    fixedMap[i].add(map[i][j])
                }
            }
        }
        return fixedMap;
    }

    private fun checkMap(map: MutableList<MutableList<String>>): Long? {
        val verticalLinePosition = advent.of.code.`13thPuzzle`.checkReflectionVertical(map);
        if (verticalLinePosition != null) {
            return verticalLinePosition;
        } else {
            val horizontalLinePosition = advent.of.code.`13thPuzzle`.checkReflectionHorizontal(map);
            if (horizontalLinePosition != null) {
                return horizontalLinePosition * 100;
            } else {
                return null;
            }
        }
    }

    private fun checkReflectionVertical(map: MutableList<MutableList<String>>): Long? {
        val reflectionPositions: MutableList<MutableList<Long>> = arrayListOf();
        map.forEach {
            reflectionPositions.add(arrayListOf());
        }
        for (i in 0..<map.size) {
            for (j in 0..<map[0].size) {
                var k = 1;
                var isReflection = true;
                var wasInWhile = false;
                while (((j + k) < map[0].size) && (j - k + 1) >= 0) {
                    wasInWhile = true;
                    if (map[i][j - k + 1] != map[i][j + k]) {
                        isReflection = false;
                    }
                    k++;
                }
                if (isReflection && wasInWhile) {
                    reflectionPositions[i].add(j + 1L)
                }
            }
        }

        val reflectionPosition = reflectionPositions[0].filter { p -> reflectionPositions.all { l -> l.contains(p) } }.getOrNull(0);

        return reflectionPosition;
    }

    private fun checkReflectionVerticalP2(map: MutableList<MutableList<String>>, originalMap: MutableList<MutableList<String>>): Long? {
        val reflectionPositions: MutableList<MutableList<Long>> = arrayListOf();
        map.forEach {
            reflectionPositions.add(arrayListOf());
        }
        for (i in 0..<map.size) {
            for (j in 0..<map[0].size) {
                var k = 1;
                var isReflection = true;
                var wasInWhile = false;
                while (((j + k) < map[0].size) && (j - k + 1) >= 0) {
                    wasInWhile = true;
                    if (map[i][j - k + 1] != map[i][j + k]) {
                        isReflection = false;
                    }
                    k++;
                }
                if (isReflection && wasInWhile) {
                    reflectionPositions[i].add(j + 1L)
                }
            }
        }

        val reflectionPosition = reflectionPositions[0].filter { p -> reflectionPositions.all { l -> l.contains(p) } }.filter { r -> r != advent.of.code.`13thPuzzle`.checkMap(
            originalMap
        )
        }.getOrNull(0);

        return reflectionPosition;
    }

    private fun checkReflectionHorizontal(map: MutableList<MutableList<String>>): Long? {
        val reflectionPositions: MutableList<Long> = arrayListOf();

        for (i in 0..<map.size) {
            var k = 1;
            var isReflection = true;
            var wasInWhile = false
            while (((i + k) < map.size) && (i - k + 1) >= 0) {
                wasInWhile = true;
                if (map[i - k + 1].joinToString("") != map[i + k].joinToString("")) {
                    isReflection = false;
                }
                k++;
            }
            if (isReflection && wasInWhile) {
                reflectionPositions.add(i + 1L)
            }
        }

        val reflectionPosition = reflectionPositions.getOrNull(0);

        return reflectionPosition;
    }

    private fun checkReflectionHorizontalP2(map: MutableList<MutableList<String>>, originalMap: MutableList<MutableList<String>>): Long? {
        val reflectionPositions: MutableList<Long> = arrayListOf();

        for (i in 0..<map.size) {
            var k = 1;
            var isReflection = true;
            var wasInWhile = false
            while (((i + k) < map.size) && (i - k + 1) >= 0) {
                wasInWhile = true;
                if (map[i - k + 1].joinToString("") != map[i + k].joinToString("")) {
                    isReflection = false;
                }
                k++;
            }
            if (isReflection && wasInWhile) {
                reflectionPositions.add(i + 1L)
            }
        }

        val reflectionPosition = reflectionPositions.filter { r -> r * 100 != advent.of.code.`13thPuzzle`.checkMap(
            originalMap
        )
        }.getOrNull(0);

        return reflectionPosition;
    }

    private fun rotateMap90Degrees(map: MutableList<MutableList<String>>): MutableList<MutableList<String>> {
        val rotatedMapLines: MutableList<String> = arrayListOf();
        for (j in 0..<map[0].size) {
            var line = "";
            for (i in map.size - 1 downTo 0) {
                line += map[i][j];
            }
            rotatedMapLines.add(line);
        }
        return Util.Companion.initMap(rotatedMapLines);
    }


}