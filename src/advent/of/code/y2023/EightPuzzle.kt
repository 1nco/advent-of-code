package advent.of.code.y2023

import java.util.*
import advent.of.code.*

class EightPuzzle {
    companion object {

        private val day = "8";

        private var input: MutableList<String> = arrayListOf();

        private var result = 0L;

        private var resultSecond = 0L;

        private var instructions: String = "";

        private var map = hashMapOf<String, Node>();

        private var needToFind = "ZZZ"

        private var startingNodes = arrayListOf<Path>();

        fun solve() {
            val startingTime = Date();
            input.addAll(Reader.readInput("2023",DAY));

            var lineNum = 0;
            input.forEach { line ->

                if (lineNum == 0) {
                    instructions = line;
                } else if (line.isNotBlank()) {
                    val node = Node(line.split("=")[0].trim(), line.split("=")[1].trim().split(", ")[0].replace("(", "").trim(), line.split("=")[1].trim().split(", ")[1].replace(")", "").trim())
//                    map.add(node);
                    map[line.split("=")[0].trim()] = node;
                }

                lineNum++;
            }

            // FIRST

//            var found = false;
//            var currentNodeValue = "AAA";
//            var i = 0;
//            while (!found) {
//                if (currentNodeValue == needToFind) {
//                    found = true;
//                } else {
//                    if (instructions[i].toString() == "L") {
//                        currentNodeValue = map.get(currentNodeValue)!!.left;
//                    }
//
//                    if (instructions[i].toString() == "R") {
//                        currentNodeValue = map.get(currentNodeValue)!!.right;
//                    }
//                    result++;
//                }
//
//                i++;
//                if (i == instructions.length) {
//                    i = 0;
//                }
//            }

            println(result)

            // SECOND
            startingNodes.addAll(map.filter { node -> node.value.value[2].toString() == "A" }.map { node -> Path(node.value.value, node.value.value) });

            var i = 0;
            while (!allFound()) {

                startingNodes.forEach{ node ->
                    move(instructions[i].toString(), node);
                }
                resultSecond++;

                i++;
                if (i == instructions.length) {
                    i = 0;
                }
            }

//            var isLcm = false;
//            var lcmValue = startingNodes.map { n -> n.firstZOccurence }.sorted().get(startingNodes.size - 1)
//            while (!isLcm) {
//                isLcm = startingNodes.map { n -> n.firstZOccurence }.all { v -> lcmValue % v == 0L  };
//                lcmValue++;
//            }

            var lcmValue = findLCMOfListOfNumbers(startingNodes.map { n -> n.firstZOccurence })

            println(lcmValue);



            println(startingTime);
            println(Date());

        }

        private fun allFound(): Boolean {
            return startingNodes.size == startingNodes.filter { node -> node.firstZOccurence != 0L }.size;
        }

        private fun move(instruction: String, path: Path) {
            if (path.firstZOccurence == 0L && path.currentNode[2].toString() == "Z") {
                path.firstZOccurence = resultSecond;
            }
            if (instruction == "L" && path.firstZOccurence == 0L) {
                path.currentNode = map.get(path.currentNode)!!.left;
            }

            if (instruction == "R" && path.firstZOccurence == 0L) {
                path.currentNode = map.get(path.currentNode)!!.right;
            }
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
    }
}

class Path(startingNode: String, currentNode: String) {
    var startingNode: String;
    var currentNode: String;
    var firstZOccurence: Long;

    init {
        this.startingNode = startingNode;
        this.currentNode = currentNode;
        this.firstZOccurence = 0L
    }
}


class Node(value: String, left: String, right: String) {
    var value: String;
    var left: String;
    var right: String;

    init {
        this.value = value;
        this.left = left;
        this.right = right;
    }
}