package advent.of.code

import java.util.*
import kotlin.collections.ArrayList

class TwelvethPuzzle {
    companion object {
        private const val DAY = "12";

        private var input: MutableList<String> = arrayListOf();

        private var result = 0L;

        private var resultSecond = 0L;

        private var resultThird = 0L;

        private var results = arrayListOf<Long>()


        var alreadyCheckedPerms = mutableMapOf<String, Boolean>();
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


//            val CONCAT_COUNT = 5;
            val CONCAT_COUNT = 2;

            // TODO BEGINNING OF SLOW SOLUTION
            for (i in 0..<CONCAT_COUNT - 1) {
                arrangement += "?" + originalArrangement;
                parts.addAll(originalParts);
            }
            getAllPermutationsForPartTwo(arrangement, parts);
            // TODO END OF SLOW SOLUTION

            // TODO THIRD

//            for (i in 0..<CONCAT_COUNT - 1) {
//                arrangement += "?" + originalArrangement;
//                parts.addAll(originalParts);
//            }

//            var firstPerms = arrayListOf<String>()
//            getAllPermutationsForPartTwoAndStoreInList("$originalArrangement?", firstPerms, originalParts);
//
//            var secondPerms = arrayListOf<String>()
//            getAllPermutationsForPartTwoAndStoreInList("?$originalArrangement", secondPerms, originalParts);
//
//            var firstSecondPerms = arrayListOf<String>()
//
//            firstPerms.forEach { f ->
//                secondPerms.forEach { s ->
//                    if (f.last() == s.first()) {
//                        firstSecondPerms.add(f + s.substring(1, s.length));
//                    }
//                }
//            }
//            println("firsts: " + firstPerms.size)
//            println("seconds: " + secondPerms.size)
//            resultThird += firstPerms.size * secondPerms.size;


            // TODO END THIRD


            // TODO BEGINNING OF NEW SOLUTION

//            var permutations = arrayListOf<String>()
//            var originalPermutations = arrayListOf<String>()
//            getAllPermutations(arrangement, originalPermutations)
//            permutations.addAll(originalPermutations.filter { p -> checkPermutation(p, originalParts) });
//
//            permutations.forEach { p ->
//                var arr = p;
//                for (i in 0..<CONCAT_COUNT - 1) {
////                arrangement += "?" + originalArrangement;
////                    parts.addAll(originalParts);
//                    arr += "?" + originalArrangement;
//                    parts.addAll(originalParts);
//                }
//                getAllPermutationsForPartTwo(p, parts);
//            }
            // TODO END OF NEW SOLUTION


//
//
//            var p1p2 = addNewPermutation(permutations, originalPermutations, originalParts, 2);
//            var p2p3 = addNewPermutation(p1p2, originalPermutations, originalParts, 3);
//            var p3p4 = addNewPermutation(p2p3, originalPermutations, originalParts, 4);
//            var p4p5 = addNewPermutation(p3p4, originalPermutations, originalParts, 5);
//
//            resultSecond += p4p5.size;
//
//            results.add(resultSecond);

        }

        private fun addNewPermutation(validPermutations: List<String>, originalPermutations: List<String>, parts: List<Long>, multiply: Int): List<String> {
            val perms = arrayListOf<String>()
            validPermutations.forEach { v ->
                originalPermutations.forEach { o ->
                    val partsList = multiplyList(parts, multiply);
                    getAllPermutationsForPartTwoAndStoreInList(v + "?" + o, perms, partsList)
                }
            }
            return perms;
        }

        private fun multiplyList(list: List<Long>, multiplyBy: Int): MutableList<Long> {
            var multiplied = arrayListOf<Long>()
            for (i in 0..multiplyBy - 1) {
                multiplied.addAll(list)
            }
            return multiplied;
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

        private fun getAllPermutationsForPartTwo(arrangement: String, parts: List<Long>) {
            if (arrangement.contains("?")) {
                var arr1 = arrangement.replaceFirst("?", ".")
                var arr2 = arrangement.replaceFirst("?", "#")
                if (checkHalfDonePermutation(arr1, parts)) {
                    getAllPermutationsForPartTwo(arr1, parts);
                }
                if (checkHalfDonePermutation(arr2, parts)) {
                    getAllPermutationsForPartTwo(arr2, parts);
                }
            } else {
                if (checkPermutation(arrangement, parts)) {
                    resultSecond++;
                }
            }
        }

        private fun getAllPermutationsForPartTwoCOPY(arrangement: String, parts: List<Long>) {
            // TODO call itself
            if (arrangement.contains("?")) {
                var arr1 = arrangement.replaceFirst("?", ".")
                var arr2 = arrangement.replaceFirst("?", "#")
                if (checkHalfDonePermutation(arr1, parts)) {
                    getAllPermutationsForPartTwo(arr1, parts);
                }
                if (checkHalfDonePermutation(arr2, parts)) {
                    getAllPermutationsForPartTwo(arr2, parts);
                }
            } else {
                if (checkPermutation(arrangement, parts)) {
                    resultSecond++;
                }
            }
        }

        private fun getAllPermutationsForPartTwoAndStoreInList(arrangement: String, permutations: MutableList<String>, parts: List<Long>) {
            if (arrangement.contains("?")) {
                var arr1 = arrangement.replaceFirst("?", ".")
                var arr2 = arrangement.replaceFirst("?", "#")

                if (checkHalfDonePermutation(arr1, parts)) {
                    getAllPermutationsForPartTwoAndStoreInList(arr1, permutations, parts);
                }
                if (checkHalfDonePermutation(arr2, parts)) {
                    getAllPermutationsForPartTwoAndStoreInList(arr2, permutations, parts);
                }
            } else {
                if (checkPermutation(arrangement, parts)) {
                    permutations.add(arrangement);
                }
            }
        }

        private fun checkHalfDonePermutation(arrangement: String, parts: List<Long>): Boolean {
            val groups = arrangement.split(".").filter { a -> a != "" }
            var canBeValid = true;
            var breakFor = false;
            var i = 0;
            while (canBeValid && !breakFor && i < parts.size) {
//            }
//            for (i in 0..parts.size - 1) {
//                if (!breakFor) {
                if (i < groups.size) {
                    if (groups[i].contains("?")) {
//                            if (getOccurencesOfAChar(groups[i], '#') > parts[i]) {
//                                canBeValid = false
//                            }
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
//                }
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
                println(lineNum)
                lineNum++;
            }
            println("second: $resultSecond")

            println("third: $resultThird");
        }
    }
}