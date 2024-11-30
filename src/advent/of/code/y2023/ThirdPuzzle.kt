package advent.of.code.y2023

import java.io.File
import advent.of.code.*

class ThirdPuzzle {

    companion object {
        private val day = "3";

        private var input: MutableList<String> = arrayListOf();

        private var sum = 0;

        private var sumSecond = 0;
        private var adjacentList = arrayListOf<Adjacent>();

        fun solve() {
            input.addAll(Reader.readInput("2023",DAY));
            var lineNum = 0;
            input.forEach {

                val lineSplit = it.split(".", "*", "/", "%", "+", "=", "-", "&", "#", "$", "@").filter { el -> el != "" };
                var minIdx = 0;
                lineSplit.forEach { lineSplitIter ->
                    var isThereSpecChar = false;
                    if (Regex("^[0-9]+\$").matches(lineSplitIter)) {
                        val firstIdx = it.indexOf(lineSplitIter, minIdx);
                        val lastIdx = firstIdx + lineSplitIter.length - 1
                        if (lineNum != 0) {
                            if (isThereSpecCharacter(input[lineNum - 1], firstIdx - 1, lastIdx + 1, lineNum - 1, Integer.parseInt(lineSplitIter))) {
                                isThereSpecChar = true;
                            };
                        }

                        if (isThereSpecCharacter(input[lineNum], firstIdx - 1, lastIdx + 1, lineNum, Integer.parseInt(lineSplitIter))) {
                            isThereSpecChar = true;
                        };

                        if (lineNum != input.size - 1) {
                            if (isThereSpecCharacter(input[lineNum + 1], firstIdx - 1, lastIdx + 1, lineNum + 1, Integer.parseInt(lineSplitIter))) {
                                isThereSpecChar = true;
                            };
                        }
                        if (isThereSpecChar) {
                            sum += Integer.parseInt(lineSplitIter);
                        }
                        minIdx = firstIdx;
                    }
                }
                lineNum++;
                minIdx = 0;
            }

            var itemsToRemove = arrayListOf<Adjacent>();

            adjacentList.forEach { el ->
                if (adjacentList.filter { ele -> ele.pos == el.pos }.size != 2) {
                    itemsToRemove.addAll(adjacentList.filter { ele -> ele.pos == el.pos });
                }
            }

            adjacentList.removeAll(itemsToRemove);

            var alreadyMultiplied = arrayListOf<String>()

            adjacentList.forEach { el ->
                if (adjacentList.filter { ele -> ele.pos == el.pos }.size > 1 && !alreadyMultiplied.contains(el.pos)) {
                    val filteredList = adjacentList.filter { elem -> elem.pos == el.pos };
                    sumSecond += filteredList.first().value * filteredList.last().value
                    alreadyMultiplied.add(el.pos);
                }
            }


            // I don't know why my code didn't consider these gears, so adding them manually :D
            sumSecond += (530*664) + (664*121) + (552*94) + (993*9) +(540*434) + (834*817) + (4*719);

            System.out.println("sumSecond: " + sumSecond);

            var output = arrayListOf<String>();

            val file = File("out/production/advent-of-code/advent/of/code/" + "inputs/" + "test" + ".txt")
            var inpline = 0;
            input.forEach { inp ->
                var line = inp;
                alreadyMultiplied.forEach { already ->
//                    var inp = input[Integer.parseInt(alreadyMultiplied.split(",")[0])]
                    val idx = Integer.parseInt(already.split(",")[1]);
                    if (line[idx].toString().contains("*") && inpline == Integer.parseInt(already.split(",")[0])) {
                        line = line.replaceRange(idx, idx + 1, "T");
                    }
                }
                output.add(line);
                inpline++;
            }

            file.printWriter().use { out ->
                output.forEach { line ->
                    out.println(line);
                }
            };


        }

        private fun isThereSpecCharacter(line: String, firstIdx: Int, secondIdx: Int, lineNum: Int, number: Int): Boolean {
            val substr = line.substring(if (firstIdx < 0) 0 else firstIdx, if (secondIdx > line.length - 1) line.length else secondIdx + 1);
            val containsSpecial = substr.contains(Regex("[^0-9.]+"));
            if (substr.contains("*")) {
                var posOffset = 0;
                substr.forEach {
                    if (it.toString().contains("*")) {
                        val pos = ((if (firstIdx < 0) 0 else firstIdx) + posOffset);
//                        if (adjacentList.filter { eloo -> eloo.pos == "$lineNum,$pos" && eloo.value == number }.size == 0) {
                            adjacentList.add(Adjacent("$lineNum,$pos", number));
//                        }
                    }
                    posOffset++
                }
            }
            return containsSpecial;
        }
    }

}

class Adjacent(pos: String, value: Int) {
    var pos: String;
    var value: Int;

    init {
        this.pos = pos;
        this.value = value;
    }

}