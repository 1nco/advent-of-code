package advent.of.code

import java.util.*

object `13thPuzzle` {

    private const val DAY = "13";

    private var input: MutableList<String> = arrayListOf();

    private var result = 0L;

    private var resultSecond = 0L;

    private var mapCount = 1;

    fun solve() {
        val startingTime = Date();
        input.addAll(Reader.readInput(DAY));

        first();

        second();

        println("first: $result");
        println("second: $resultSecond");


        println(startingTime);
        println(Date());

    }

    fun first() {
        mapCount = 1;
        var lines: MutableList<String> = arrayListOf();
        input.forEach { l ->
            if (l != "") {
                lines.add(l)
            } else {
                result += checkMap(Util.initMap(lines))!!
                mapCount++;
                lines = arrayListOf();
            }
        }
        result += checkMap(Util.initMap(lines))!!;
    }

    fun second() {
        mapCount = 1;
        var lines: MutableList<String> = arrayListOf();
        input.forEach { l ->
            if (l != "") {
                lines.add(l)
            } else {
                val newReflection = fixSmudgeAndGetNewReflection(Util.initMap(lines))
                if (newReflection != null) {
                    resultSecond += newReflection;
                } else {
                    println("Something's wrong with map $mapCount");
                }
                mapCount++;
                lines = arrayListOf();
            }
        }

        val newReflection = fixSmudgeAndGetNewReflection(Util.initMap(lines))
        if (newReflection != null) {
            resultSecond += newReflection;
        } else {
            println("Something's wrong with map $mapCount");
        }
    }

    private fun fixSmudgeAndGetNewReflection(map: MutableList<MutableList<String>>): Long? {
        val verticalReflectionPositions: MutableList<Long> = arrayListOf();
        val horizontalReflectionPositions: MutableList<Long> = arrayListOf();
        for (i in 0..<map.size) {
            for (j in 0..<map[0].size) {
                val verticalReflectionPosition = checkReflectionVerticalP2(fixSmudge(map, i, j), map);
                if (verticalReflectionPosition != null) {
                    verticalReflectionPositions.add(verticalReflectionPosition);
                }

                val horizontalReflectionPosition = checkReflectionHorizontalP2(fixSmudge(map, i, j), map);
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
        val verticalLinePosition = checkReflectionVertical(map);
        if (verticalLinePosition != null) {
            return verticalLinePosition;
        } else {
            val horizontalLinePosition = checkReflectionHorizontal(map);
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

        val reflectionPosition = reflectionPositions[0].filter { p -> reflectionPositions.all { l -> l.contains(p) } }.filter { r -> r != checkMap(originalMap) }.getOrNull(0);

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

        val reflectionPosition = reflectionPositions.filter { r -> r * 100 != checkMap(originalMap) }.getOrNull(0);

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
        return Util.initMap(rotatedMapLines);
    }


}