package advent.of.code

import java.io.File
import java.util.*
import kotlin.math.absoluteValue

class TenthPuzzle {
    companion object {
        private const val DAY = "10";

        private var input: MutableList<String> = arrayListOf();

        private var result = 0L;

        private var resultSecond = 0L;

        private var map: MutableList<MutableList<String>> = arrayListOf();

        private var linkedList = arrayListOf<Part>();

        private lateinit var current: Part;

        private lateinit var previous: Part;
        private var found = false;

        private var maxColPos = 0;

        private var maxLinePos = 0;


        var adj: MutableList<MutableList<Pair<Int, Int>>> = mutableListOf()

        fun solve() {
            val startingTime = Date();
            input.addAll(Reader.readInput(DAY));
            initMap();
            var lineNum = 0;
            input.forEach { line ->
                var colNum = 0;
                line.forEach { l ->
                    map[lineNum][colNum] = l.toString();
                    colNum++;
                }
                lineNum++;
            }

            maxLinePos = map.size;
            maxColPos = map.get(0).size;

            initTubesMap();
            result = (linkedList.size / 2).toLong();

            println("first: $result")

            solveSecond()

            println("second: $resultSecond")

            println(startingTime);
            println(Date());

        }

        private fun initMap() {
            var currLine = 0;

            input.forEach {
                map.add(arrayListOf());
                it.forEach {
                    map[currLine].add("");
                }
                currLine++
            }
        }

        private fun initTubesMap() {
            var lineNum = 0;
            map.forEach { l ->
                var colNum = 0;
                l.toMutableList().forEach { col ->
                    if (col == "S") {
                        var startingPoint = Part(map[lineNum][colNum], lineNum, colNum, 0, null, null);
                        var next = Part(map[lineNum][colNum + 1], lineNum, colNum + 1, 1, startingPoint, null)
                        current = next;
                        previous = startingPoint;
                        while (!found) {
                            createNewPartType(linkedList, current, previous)
                        }
                    }
                    colNum++
                }
                lineNum++
            }
        }

        private fun createNewPartType(list: MutableList<Part>, curr: Part, prev: Part) {
            when (curr.type) {
                "|" -> {
                    if (prev.x != curr.x - 1 && prev.y == curr.y) {
                        addToListAndGoFurther(list, curr, prev, curr.x - 1, curr.y);
                    } else {
                        addToListAndGoFurther(list, curr, prev, curr.x + 1, curr.y);
                    }
                }

                "-" -> {
                    if (prev.x == curr.x && prev.y != curr.y - 1) {
                        addToListAndGoFurther(list, curr, prev, curr.x, curr.y - 1);
                    } else {
                        addToListAndGoFurther(list, curr, prev, curr.x, curr.y + 1);
                    }
                }

                "L" -> {
                    if (prev.x != curr.x - 1) {
                        addToListAndGoFurther(list, curr, prev, curr.x - 1, curr.y);
                    } else {
                        addToListAndGoFurther(list, curr, prev, curr.x, curr.y + 1);
                    }
                }

                "J" -> {
                    if (prev.x != curr.x - 1) {
                        addToListAndGoFurther(list, curr, prev, curr.x - 1, curr.y);
                    } else {
                        addToListAndGoFurther(list, curr, prev, curr.x, curr.y - 1);
                    }
                }

                "7" -> {
                    if (prev.y != curr.y - 1) {
                        addToListAndGoFurther(list, curr, prev, curr.x, curr.y - 1);
                    } else {

                        addToListAndGoFurther(list, curr, prev, curr.x + 1, curr.y);
                    }
                }

                "F" -> {
                    if (prev.y != curr.y + 1) {
                        addToListAndGoFurther(list, curr, prev, curr.x, curr.y + 1);
                    } else {
                        addToListAndGoFurther(list, curr, prev, curr.x + 1, curr.y);
                    }
                }

                "." -> println("something's wrong")
                "S" -> {
                    curr.distance = 0;
                    var next = list[0];
                    curr.next = next;
                    list.add(curr);
                    found = true;
                }
            }
        }

        private fun addToListAndGoFurther(list: MutableList<Part>, curr: Part, prev: Part, nextX: Int, nextY: Int) {
            curr.distance = prev.distance + 1;
            var next = Part(map[nextX][nextY], nextX, nextY, prev.distance + 2, null, null);
//            curr.next = next;
            list.add(curr);
            current = next;
            previous = curr;
//            createNewPartType(list, next, curr);
        }

        private fun solveSecond() {

//            val file = File("out/production/advent-of-code/advent/of/code/" + "inputs/" + "test" + ".txt");
//            var output = arrayListOf<String>()


            // Stolen from the internet approach
            resultSecond = linkedList
                    .reversed()
                    .asSequence()
                    .plus(linkedList.last())
                    .windowed(2)
                    .sumOf { (a, b) -> a.x * b.y - a.y * b.x}
                    .absoluteValue
                    .minus(linkedList.size)
                    .div(2)
                    .plus(1)
                    .toLong()

            // Own approach - https://www.youtube.com/watch?v=0KjG8Pg6LGk

//            val corners = linkedList.reversed().filter { p -> map[p.x][p.y] != "|" && map[p.x][p.y] != "-" }.toMutableList();
//            corners.add(corners[0]);
//            var tmp = 0;
//            for (i in 0..<corners.size - 1) {
//                tmp += (corners[i].x * corners[i + 1].y) - (corners[i].y * corners[i + 1].x)
//            }
//            // A teruletbol ki kell vonni magat a tube-ot, hogy az ne szamolodjon bele
//            tmp = tmp - linkedList.size + 1;
//            resultSecond = (tmp / 2).toLong();



//            file.printWriter().use { out ->
//                output.forEach { line ->
//                    out.println(line);
//                }
//            };
        }
    }
}

class Part(type: String, x: Int, y: Int, distance: Long, prev: Part?, next: Part?) {
    var type: String;
    var x: Int;
    var y: Int;
    var distance: Long;
    lateinit var prev: Part;
    lateinit var next: Part;

    init {
        this.type = type;
        this.x = x;
        this.y = y;
        this.distance = distance;
        if (prev != null) {
            this.prev = prev
        };
        if (next != null) {
            this.next = next
        };
    }
}
data class Pos(var line: Int, var column: Int);