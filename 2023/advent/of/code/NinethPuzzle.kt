package advent.of.code

import java.util.*

class NinethPuzzle {
    companion object {

        private val day = "9";

        private var input: MutableList<String> = arrayListOf();

        private var result = 0L;

        private var resultSecond = 0L;

        private var histories = arrayListOf<History>();

        fun solve() {
            var startingTime = Date();
            input.addAll(Reader.readInput(day));

            var lineNum = 0;
            input.forEach { line ->
                histories.add(getHistory(line));
                lineNum++;
            }

            histories.forEach{history ->
                extrapolate(history);
                extrapolateBackwards(history);
                result += history.sequences[0][history.sequences[0].size - 1];
                resultSecond += history.reversed[0][history.reversed[0].size - 1];
            }

            System.out.println(result)
            System.out.println(resultSecond)

            System.out.println(startingTime);
            System.out.println(Date());

        }

        private fun getHistory(line: String): History {
            var history = History(line, arrayListOf());
            history.sequences.add(line.split(" ").map { v -> v.trim().toLong() }.toMutableList());

            var depth = 0;
            var i = 0;
            var seqence = arrayListOf<Long>();
            var allZero = false;
            while (!allZero) {
                seqence.add(history.sequences[depth][i + 1] - history.sequences[depth][i]);
                i++;
                if (i == history.sequences[depth].size - 1) {
                    i = 0;
                    depth++;
                    history.sequences.add(seqence);
                    if (seqence.all { v -> v == 0L }) {
                        allZero = true;
                    }
                    seqence = arrayListOf();
                }
            }
            return history;
        }

        private fun extrapolate(history: History) {
            history.sequences[history.sequences.size - 1].add(0);
            var depth = history.sequences.size - 1 - 1;
            while (depth != -1) {
                var depthSeq = history.sequences[depth];
                var depthSeqPlusOne = history.sequences[depth + 1]
                depthSeq.add(depthSeq[depthSeq.size - 1] + depthSeqPlusOne[depthSeqPlusOne.size - 1])
                depth--;
            }
        }

        private fun extrapolateBackwards(history: History) {
            history.sequences.forEach{
                history.reversed.add(it.reversed().toMutableList())
            }
            history.reversed[history.reversed.size - 1];
            var depth = history.reversed.size - 1 - 1;
            while (depth != -1) {
                var depthSeq = history.reversed[depth];
                var depthSeqPlusOne = history.reversed[depth + 1]
                depthSeq.add(depthSeq[depthSeq.size - 1] - depthSeqPlusOne[depthSeqPlusOne.size - 1])
                depth--;
            }
        }
    }


}

class History(line: String, sequences: MutableList<MutableList<Long>>) {
    var line: String;
    var sequences: MutableList<MutableList<Long>>;
    var reversed: MutableList<MutableList<Long>>;

    init {
        this.line = line;
        this.sequences = sequences;
        reversed = arrayListOf()
    }
}