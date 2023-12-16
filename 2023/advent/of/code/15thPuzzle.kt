package advent.of.code

import java.util.*

object `15thPuzzle` {

    private const val DAY = "15";

    private var input: MutableList<String> = arrayListOf();

    private var result = 0L;

    private var resultSecond = 0L;

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


    private fun first() {
        input[0].split(",").forEach{text ->
            result += hashText(text);
        }
    }

    private fun second() {
        val boxes: MutableList<MutableList<String>> = arrayListOf();
        for (i in 0..<256) {
            boxes.add(arrayListOf());
        }

        input[0].split(",").forEach{text ->
            val boxIdx = hashText(Util.getAlphaNumericalCharacters(text));
            val label = Util.getAlphaNumericalCharacters(text);
            val focalLength = Util.getNumbers(text).getOrNull(0);
            if (text.contains("-")) {
                boxes[boxIdx] = boxes[boxIdx].filter { lens -> lens.split(" ")[0] != label }.toMutableList();
            } else {
                if (boxes[boxIdx].any { lens -> lens.split(" ")[0] == label }) {
                    val idx = boxes[boxIdx].indexOfFirst { lens -> lens.split(" ")[0] == label }
                    boxes[boxIdx][idx] = "$label $focalLength";
                } else {
                    boxes[boxIdx].add("$label $focalLength");
                }
            }
        }

        for (i in 0..<256) {
            for (j in 0..<boxes[i].size) {
                resultSecond += (i + 1) * (j + 1) * boxes[i][j].split(" ")[1].toLong();
            }
        }
    }

    private fun hashText(text: String): Int {
        var result = 0;
        text.forEach {c ->
            result += c.code;
            result *= 17;
            result %= 256;
        }
        return result;
    }

}