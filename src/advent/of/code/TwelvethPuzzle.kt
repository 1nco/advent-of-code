package advent.of.code

import java.util.*

class TwelvethPuzzle {
    companion object {
        private const val DAY = "12";

        private var input: MutableList<String> = arrayListOf();

        private var result = 0L;

        private var resultSecond = 0L;

        private var resultThird = 0L;

        private var results = arrayListOf<Long>()


        var alreadyCheckedPerms = mutableMapOf<String, Boolean>();

        var validArrangementCounts: MutableMap<String, Long> = mutableMapOf();
        fun solve() {
            val startingTime = Date();
            input.addAll(Reader.readInput(DAY));

            first();

            second();

            println(startingTime);
            println(Date());

        }

        private fun first() {
            var lineNum = 0;
            input.forEach { line ->

                result += possibleArrangements(line);

                lineNum++;
            }
            println(result)
        }

        private fun possibleArrangements(line: String): Long {
            val arrangement = line.split(" ")[0];
            val parts = line.split(" ")[1].split(",").map { c -> c.toLong() }.toList();

            val permutations = arrayListOf<String>();
            getAllPermutations(arrangement, permutations);

            var valid = 0L;

            permutations.forEach { permutation ->
                val groups = permutation.split(".").filter { p -> p.contains("#") }
                var isPermValid = true;
                if (groups.size == parts.size) {
                    for (i in 0..parts.size - 1) {
                        if (getOccurencesOfAChar(groups[i], '#') == parts[i]) {
//                            isPermValid = true
                        } else {
                            isPermValid = false;
                        };
                    }
                } else {
                    isPermValid = false;
                }
                if (isPermValid) {
                    valid++
                }
            }
            return valid;
        }

        private fun possibleArrangementsForPartTwo(line: String) {
            alreadyCheckedPerms = mutableMapOf<String, Boolean>();
            var arrangement = line.split(" ")[0];
            var parts: MutableList<Long> = line.split(" ")[1].split(",").map { c -> c.toLong() }.toMutableList();

            val originalArrangement = arrangement;
            val originalParts = arrayListOf<Long>();
            originalParts.addAll(parts)


            val CONCAT_COUNT = 2;
//            val CONCAT_COUNT = 2;

            for (i in 0..<CONCAT_COUNT - 1) {
                arrangement += "?" + originalArrangement;
                parts.addAll(originalParts);
            }

            validArrangementCounts.set(arrangement, 0L);
            println("")
            println("")
            println("")
            println(line);
            println(Date())

            getAllPermutationsForPartTwo(arrangement, parts, arrangement);

            val withoutConcat = possibleArrangements(line);
            var withConcat = validArrangementCounts[arrangement]!!;
            var res = 0L;

            if (withConcat < withoutConcat || withConcat % withoutConcat != 0L) {
                println("valami van a levegoben");

                for (i in 0..< 3 - CONCAT_COUNT) {
                    arrangement += "?" + originalArrangement;
                    parts.addAll(originalParts);
                }
                validArrangementCounts.set(arrangement, 0L);
                getAllPermutationsForPartTwo(arrangement, parts, arrangement);
                withConcat = validArrangementCounts[arrangement]!!;
                res = withConcat;
            } else {
                val multiplier = withConcat / withoutConcat;

                res = withoutConcat * multiplier * multiplier * multiplier * multiplier;
            }



            println("withoutConcat: $withoutConcat")
            println("withConcat: $withConcat")
            println("res: $res");
            println(Date())

            resultSecond += res;

        }

        private fun checkPermutation(permutation: String, parts: List<Long>): Boolean {
            val groups = permutation.split(".").filter { p -> p.contains("#") }
            var isPermValid = true;
            if (groups.size == parts.size) {
                for (i in 0..parts.size - 1) {
                    if (getOccurencesOfAChar(groups[i], '#') == parts[i]) {

                    } else {
                        isPermValid = false;
                    };
                }
            } else {
                isPermValid = false;
            }
            return isPermValid;
        }

        private fun getAllPermutationsForPartTwo(arrangement: String, parts: List<Long>, originalArrangement: String) {
            if (arrangement.contains("?")) {
                var arr1 = arrangement.replaceFirst("?", ".")
                var arr2 = arrangement.replaceFirst("?", "#")
                if (checkHalfDonePermutation(arr1, parts)) {
                    getAllPermutationsForPartTwo(arr1, parts, originalArrangement);
                }
                if (checkHalfDonePermutation(arr2, parts)) {
                    getAllPermutationsForPartTwo(arr2, parts, originalArrangement);
                }
            } else {
                if (checkPermutation(arrangement, parts)) {
                    validArrangementCounts[originalArrangement] = validArrangementCounts[originalArrangement]!! + 1L
                }
            }
        }

        private fun checkHalfDonePermutation(arrangement: String, parts: List<Long>): Boolean {
            val groups = arrangement.split(".").filter { a -> a != "" }
            var canBeValid = true;
            var breakFor = false;
            var i = 0;
            while (canBeValid && !breakFor && i < parts.size) {
                if (i < groups.size) {
                    if (groups[i].contains("?")) {
//                        if (getOccurencesOfAChar(
//                                groups[i].split("?")[0],
//                                '#'
//                            ) == parts[i] && groups[i].length > parts[i] && groups[i][parts[i].toInt()] == '?'
//                        ) {
//                            canBeValid = true;
//                        } else if (getOccurencesOfAChar(groups[i], '#') > parts[i] && getOccurencesOfAChar(groups[i], '?') < parts[i]
//                        ) {
//                            canBeValid = false
//                        }
                        breakFor = true;
                    }
                    if (!groups[i].contains("?")) {
                        if (getOccurencesOfAChar(groups[i], '#') == parts[i]) {

                        } else {
                            canBeValid = false;
                            breakFor = true;
                        };
                    }
                }
                i++;
            }
            return canBeValid;
        }

        private fun getOccurencesOfAChar(value: String, char: Char): Long {
            var count = 0L;
            for (i in 0 until value.length) {
                if (value[i] == char) {
                    count++
                }
            }
            return count;
        }

        private fun getAllPermutations(arrangement: String, permutations: MutableList<String>) {
            if (arrangement.contains("?")) {
                var arr1 = arrangement.replaceFirst("?", ".")
                var arr2 = arrangement.replaceFirst("?", "#")
                getAllPermutations(arr1, permutations);
                getAllPermutations(arr2, permutations);
            } else {
                permutations.add(arrangement);
            }
        }


        private fun second() {
            var lineNum = 0;
            input.forEach { line ->

                possibleArrangementsForPartTwo(line);
                lineNum++;
            }
            println("")
            println("")
            println("")
            println("")
            println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!")
            println("")
            println("")
            println("")
            println("")
            println("second: $resultSecond")

        }
    }
}