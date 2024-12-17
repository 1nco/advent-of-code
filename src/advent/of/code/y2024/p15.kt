package advent.of.code.y2024

import advent.of.code.Reader
import utils.Util
import utils.println
import java.util.*

class p15 {

    companion object {
        val YEAR = "2024";
        val DAY = "15";
    }

    private var input: MutableList<String> = arrayListOf();

    private var inputAsString: String = "";

    private var firstResult = 0L;

    private var secondResult = 0L;

    fun solve() {
        val startingTime = Date();
        inputAsString = Reader.readInputAsString(YEAR, DAY);
        input.addAll(Reader.readInput(YEAR, DAY));

//        first();

        second();

        println("first: $firstResult");
        println("second: $secondResult");


        println(startingTime);
        println(Date());

    }

    private fun first() {
        var map = Util.initMap(input.subList(0, input.indexOf("")))
        var moves = input.subList(input.indexOf("") + 1, input.size).reduce { acc, value -> acc + value }


        var robot: Util.Coordinate = Util.Coordinate(-1, -1);
        for (i in 0..<map.size) {
            for (j in 0..<(map[0].size)) {
                if (map[i][j] == "@") {
                    robot = Util.Coordinate(i, j);
                }
            }
        }

        for (i in 0..<moves.length) {
            if (moves[i] == '^') {
                var moved = move(map, robot, Util.Coordinate(robot.x - 1, robot.y), Util.Coordinate(-1, 0));
                if (moved) {
                    robot.x = robot.x - 1;
                }
            }
            if (moves[i] == 'v') {
                var moved = move(map, robot, Util.Coordinate(robot.x + 1, robot.y), Util.Coordinate(1, 0));
                if (moved) {
                    robot.x = robot.x + 1;
                }
            }
            if (moves[i] == '<') {
                var moved = move(map, robot, Util.Coordinate(robot.x, robot.y - 1), Util.Coordinate(0, -1));
                if (moved) {
                    robot.y = robot.y - 1;
                }
            }
            if (moves[i] == '>') {
                var moved = move(map, robot, Util.Coordinate(robot.x, robot.y + 1), Util.Coordinate(0, 1));
                if (moved) {
                    robot.y = robot.y + 1;
                }
            }
//            Util.saveMapToFile(map, "p15-$i-${moves[i]}");
        }

        Util.saveMapToFile(map, "p15-done");

        for (i in 0..<map.size) {
            for (j in 0..<(map[0].size)) {
                if (map[i][j] == "O") {
                    firstResult += 100 * i + j;
                }
            }
        }
    }

    private fun move(map: MutableList<MutableList<String>>,
                     old: Util.Coordinate,
                     new: Util.Coordinate,
                     direction: Util.Coordinate): Boolean {
        if (new.x >= 0 && new.y >= 0 && new.x < map.size && new.y < map[0].size) {
            if (map[new.x][new.y] == ".") {
                map[new.x][new.y] = map[old.x][old.y]
                map[old.x][old.y] = "."
                return true
            } else if (map[new.x][new.y] == "O") {
                var moved = move(map, new, Util.Coordinate(new.x + direction.x, new.y + direction.y), direction);
                if (moved) {
                    map[new.x][new.y] = map[old.x][old.y]
                    map[old.x][old.y] = "."
                    return true;
                }
            } else if (map[new.x][new.y] == "#") {
                return false;
                // DO NOTHING
            }
        }

        return false;
    }

    private fun second() {
        var map = Util.initMap(input.subList(0, input.indexOf("")))
        var moves = input.subList(input.indexOf("") + 1, input.size).reduce { acc, value -> acc + value }

        for (i in 0..<map.size) {
            var j = 0;
            while (j < map[0].size) {
                if (map[i][j] == "#") {
                    map[i].add(j + 1, "#")
                    j++;
                }
                if (map[i][j] == ".") {
                    map[i].add(j + 1, ".")
                    j++;
                }
                if (map[i][j] == "@") {
                    map[i].add(j + 1, ".")
                    j++;
                }
                if (map[i][j] == "O") {
                    map[i][j] = "["
                    map[i].add(j + 1, "]")
                }
                j++;
            }
        }

        Util.saveMapToFile(map, "p15-2-map");

        var robot: Util.Coordinate = Util.Coordinate(-1, -1);
        for (i in 0..<map.size) {
            for (j in 0..<(map[0].size)) {
                if (map[i][j] == "@") {
                    robot = Util.Coordinate(i, j);
                }
            }
        }

        for (i in 0..<moves.length) {
            var toMove:MutableList<Pair<Util.Coordinate, String>> = mutableListOf();
            if (moves[i] == '^') {
                var moved = moveSecond(map, robot, Util.Coordinate(-1, 0), toMove);
                if (moved) {
                    toMove.distinct().forEach {
                        map[it.first.x][it.first.y] = it.second
                    }
                    robot.x = robot.x - 1;
                }
            }
            if (moves[i] == 'v') {
                var moved = moveSecond(map, robot, Util.Coordinate(1, 0), toMove);
                if (moved) {
                    toMove.distinct().forEach {
                        map[it.first.x][it.first.y] = it.second
                    }
                    robot.x = robot.x + 1;
                }
            }
            if (moves[i] == '<') {
                var moved = moveSecond(map, robot, Util.Coordinate(0, -1), toMove);
                if (moved) {
                    toMove.distinct().forEach {
                        map[it.first.x][it.first.y] = it.second
                    }
                    robot.y = robot.y - 1;
                }
            }
            if (moves[i] == '>') {
                var moved = moveSecond(map, robot, Util.Coordinate(0, 1), toMove);
                if (moved) {
                    toMove.distinct().forEach {
                        map[it.first.x][it.first.y] = it.second
                    }
                    robot.y = robot.y + 1;
                }
            }


            toMove.clear()
//            Util.saveMapToFile(map, "p15-$i-${moves[i]}");
        }

//        Util.saveMapToFile(map, "p15-2-done");

        for (i in 0..<map.size) {
            for (j in 0..<(map[0].size)) {
                if (map[i][j] == "[") {
                    secondResult += 100 * i + j;
                }
            }
        }

    }

    private fun moveSecond(map: MutableList<MutableList<String>>,
                           current: Util.Coordinate,
                           direction: Util.Coordinate,
                           toMove: MutableList<Pair<Util.Coordinate, String>>): Boolean {
        var new = Util.Coordinate(current.x + direction.x, current.y + direction.y);
        if (new.x >= 0 && new.y >= 0 && new.x < map.size && new.y < map[0].size) {
            if (map[new.x][new.y] == ".") {
                if (map[current.x][current.y] == "@") {
                    map[new.x][new.y] = map[current.x][current.y]
                    map[current.x][current.y] = "."
                } else {
                    toMove.add(Pair(new, map[current.x][current.y]))
                    toMove.add(Pair(current, "."))
                }
                return true
            } else if (map[new.x][new.y] == "[") {
                if (direction.y != 0) {
                    var moved = moveSecond(map, new, direction, toMove);
                    if (moved) {

                        toMove.forEach {
                            map[it.first.x][it.first.y] = it.second
                        }
                        toMove.clear()

                        map[new.x][new.y] = map[current.x][current.y]
                        map[current.x][current.y] = "."
                        return true;
                    }
                } else {
                    var leftMove = moveSecond(map, new, direction, toMove);
                    var rightMove = moveSecond(map, Util.Coordinate(new.x, new.y + 1), direction, toMove);
                    if (leftMove && rightMove) {

                        toMove.add(Pair(new, map[current.x][current.y]))
                        toMove.add(Pair(current, "."))
                        return true;
                    }
                }

            } else if (map[new.x][new.y] == "]") {
                if (direction.y != 0) {
                    var moved = moveSecond(map, new, direction, toMove);
                    if (moved) {

                        toMove.forEach {
                            map[it.first.x][it.first.y] = it.second
                        }
                        toMove.clear()

                        map[new.x][new.y] = map[current.x][current.y]
                        map[current.x][current.y] = "."
                        return true;
                    }
                } else {
                    var leftMove = moveSecond(map, Util.Coordinate(new.x, new.y - 1), direction, toMove);
                    var rightMove = moveSecond(map, new, direction, toMove);
                    if (leftMove && rightMove) {

                        toMove.add(Pair(new, map[current.x][current.y]))
                        toMove.add(Pair(current, "."))
                        return true;
                    }
                }
            } else if (map[new.x][new.y] == "#") {
                return false;
                // DO NOTHING
            }
        }

        return false;
    }
}