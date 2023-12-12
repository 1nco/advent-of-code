package advent.of.code

import java.io.File
import java.util.*


class EleventhPuzzle {
    companion object {
        private val day = "11";

        private var input: MutableList<String> = arrayListOf();

        private var result = 0L;

        private var resultSecond = 0L;

        private var universe: MutableList<MutableList<String>> = arrayListOf();

        private var listOfPairs = arrayListOf<Pair<String, String>>()

        private var weights = mutableMapOf<Pair<String, String>, Int>();

        private var galaxies = mutableListOf<String>()

        private var vertices: MutableMap<String, MutableList<String>> = mutableMapOf();

        var expansion = 1000000;

        var linesExpanded = arrayListOf<Int>()
        var colsExpanded = arrayListOf<Int>();


        fun solve() {
            var startingTime = Date();
            input.addAll(Reader.readInput(day));

            universe = getExpandedUniverse();

//            saveLinesToFile(universe);

            for (i in 0..universe.size - 1) {
                for (j in 0..universe[0].size - 1) {

                    if (universe[i][j] == "#") {
                        galaxies.add("$i;$j");
                    }

                    vertices.set("$i;$j", mutableListOf())

//                    weights[Pair("$i;$j", "$i;$j")] = 0
//
                    var adjacents = getAdjacents(GraphNode(Pos(i, j)));
                    adjacents.forEach { adj ->
                        addEdge("$i;$j", adj.pos.line.toString() + ";" + adj.pos.column);
                    }
                }
            }

//
            var alreadyCounted = arrayListOf<String>()
            var pairs = 0;
            galaxies.forEach { start ->
                println((Math.addExact(galaxies.indexOf(start), 1)))
                galaxies.forEach { end ->
                    if (!alreadyCounted.contains(start + " - " + end) && !alreadyCounted.contains(end + " - " + start) && start != end) {
//                        var res = Math.abs((start.split(";")[0].toLong() + 1)- (end.split(";")[0].toLong() + 1)) + Math.abs((start.split(";")[1].toLong() + 1) - (end.split(";")[1].toLong() + 1));
                        val startLine = getIndexToCountWith(start, 0);
                        val startCol = getIndexToCountWith(start, 1)

                        val endLine = getIndexToCountWith(end, 0);
                        val endCol = getIndexToCountWith(end, 1)

                        var res = Math.abs(getIndexToCountWith(start, 0) - (getIndexToCountWith(end, 0))) +
                                Math.abs(getIndexToCountWith(start, 1) - (getIndexToCountWith(end, 1)));
//                        println("galaxy " + (Math.addExact(galaxies.indexOf(start), 1)) + " and " + Math.addExact(galaxies.indexOf(end), 1) + ": " + res);
//                        result += printShortestDistance(start, end)!!;
                        res = res + addExpansion(Pos(startLine.toInt(), startCol.toInt()), Pos(endLine.toInt(), endCol.toInt()));
                        result += res
                        pairs++;
                        alreadyCounted.add(start + " - " + end);
                        alreadyCounted.add(end + " - " + start);
                    }
                }
            }


            println(result)
//            println(pairs);
//            println(resultSecond)

            println(startingTime);
            println(Date());

        }

        private fun getIndexToCountWith(coordinate: String, idx: Int): Long {
            var coord = coordinate.split(";")[idx].toLong();
            return coord;
//            return if (coord == 0L) 1L else coord;
        }

        private fun addExpansion(start: Pos, end: Pos): Long {
            var expValue = 0L;
            if (start.line == end.line) {
                // Add nothing
            }
            if (start.line < end.line) {
                val size = linesExpanded.filter { l -> start.line < l && l < end.line }.size
                if (size > 0) {
                    expValue += (size * expansion) - (size * 1);
                }
            }

            if (start.line > end.line) {
                val size = linesExpanded.filter { l -> start.line > l && l > end.line }.size
                if (size > 0) {
                    expValue += (size * expansion) - (size * 1);
                }
            }

            if (start.column == end.column) {
                // Add nothing
            }

            if (start.column < end.column) {
                val size = colsExpanded.filter { l -> start.column < l && l < end.column }.size
                if (size > 0) {
                    expValue += (size * expansion) - (size * 1);
                }
            }

            if (start.column > end.column) {
                val size = colsExpanded.filter { l -> start.column > l && l > end.column }.size
                if (size > 0) {
                    expValue += (size * expansion) - (size * 1);
                }
            }

            return expValue;
        }

        private fun addEdge(vertice1: String, vertice2: String) {
            vertices.get(vertice1)?.add(vertice2);
            vertices.get(vertice2)?.add(vertice1);
        }

        private fun getAdjacents(node: GraphNode): MutableList<GraphNode> {
            var adj = arrayListOf<GraphNode>();

            var pos = Pos(0, 0);
            pos = Pos(node.pos.line - 1, node.pos.column);
            if (node.pos.line - 1 >= 0) {
                addToAdjList(pos, adj);
            }

            pos = Pos(node.pos.line + 1, node.pos.column);
            if (node.pos.line + 1 < universe.size) {
                addToAdjList(pos, adj);
            }

            pos = Pos(node.pos.line, node.pos.column - 1);
            if (node.pos.column - 1 >= 0) {
                addToAdjList(pos, adj);
            }

            pos = Pos(node.pos.line, node.pos.column + 1);
            if (node.pos.column + 1 < universe[0].size) {
                addToAdjList(pos, adj);
            }

            return adj;
        }

        private fun addToAdjList(pos: Pos, adj: MutableList<GraphNode>) {
            adj.add(GraphNode(pos))
        }

        private fun getExpandedUniverse(): MutableList<MutableList<String>> {

            var tempUniverse: MutableList<MutableList<String>> = arrayListOf();
            var lineNum = 0;
            input.forEach { line ->
                var colNum = 0;
                tempUniverse.add(arrayListOf());
                line.forEach { c ->
                    tempUniverse[lineNum].add(c.toString());
                    colNum++;
                }
                lineNum++;
            }
            var linesAdded = 0;
            for (j in 0..input.size - 1) {
                if (!input[j].contains("#")) {
                    linesExpanded.add(j);
                    for (i in 0..(expansion - 1 - 1)) {
                        // TODO delete
//                        tempUniverse.add(j + linesAdded + 1, input[j].map { c -> c.toString() }.toMutableList());
                        linesAdded++;
                    }
                }
            }

            var cols = arrayListOf<Int>()
            for (i in 0..tempUniverse[0].size - 1) {
                var column = "";
                for (j in 0..tempUniverse.size - 1) {
                    column += tempUniverse[j][i];
                }

                if (!column.contains("#")) {
                    colsExpanded.add(i);
                    cols.add(i);
                }
            }

            var colsAdded = 0;
            var tempo = tempUniverse;
            cols.forEach { col ->
                var toAdd = col + /*colsAdded +*/ getExpa(colsAdded);
                for (j in 0..tempUniverse.size - 1) {
                    var a = 0;
                    for (i in 0..(expansion - 1 - 1)) {
                        // TODO delete
//                        tempo[j].add(toAdd, ".")
                        a++
                    }
                }
                colsAdded++;
            }


            colsExpanded = cols;

            return tempo;
        }

        private fun getExpa(colsAdded: Int): Int {
            if (colsAdded == 0) return 0;
            return colsAdded * (expansion) - 1
        }

        private fun saveLinesToFile(map: MutableList<MutableList<String>>) {
            val file = File("out/production/advent-of-code/advent/of/code/" + "inputs/" + "test_11" + ".txt");
            var output = arrayListOf<String>()
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


        data class GraphNode(var pos: Pos);
    }
}