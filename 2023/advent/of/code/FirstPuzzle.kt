package advent.of.code

import advent.of.code.Reader.Companion.readInput

class FirstPuzzle {
    companion object {
        private val day = "1";

        private var input: MutableList<String> = arrayListOf();

        fun solve() {
            System.out.println("first");
            input.addAll(readInput(day));
            System.out.println(input);
        }
    }
}