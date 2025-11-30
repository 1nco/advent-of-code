package advent.of.code.y2024

import advent.of.code.Reader
import java.util.*
import kotlin.math.abs

class p2 {

    companion object {
        val YEAR = "2024";
        val DAY = "2";
    }

    private var input: MutableList<String> = arrayListOf();

    private var firstResult = 0;

    private var secondResult = 0L;

    fun solve() {
        val startingTime = Date();
        input.addAll(Reader.readInput(YEAR, DAY));

        first();

        second();

        println("first: $firstResult");
        println("second: $secondResult");


        println(startingTime);
        println(Date());

    }

    private fun first() {
        var unsafe = 0;

        input.forEach { line ->


            var li = line.split(" ");

            for (i in 0..<li.size) {

                if (li[0].toInt() < li[1].toInt()) {
                    if (i < li.size - 1) {
                        if (li[i].toInt() == (li[i + 1].toInt() - 1) || li[i].toInt() == (li[i + 1].toInt() - 2) || li[i].toInt() == (li[i + 1].toInt() - 3)) {

                        } else {
                            unsafe++;
                            break;
                        }
                    }

                } else {
                    if (i < li.size - 1) {
                        if (li[i].toInt() == (li[i + 1].toInt() + 1) || li[i].toInt() == (li[i + 1].toInt() + 2) || li[i].toInt() == (li[i + 1].toInt() + 3)) {

                        } else {
                            unsafe++;
                            break;
                        }
                    }
                }
            }
        }


        firstResult = input.size - unsafe;
    }


    private fun second() {

        var safe = 0;

        input.forEach { line ->


            for (i in 0..<line.split(" ").size) {
                var li = line.split(" ").filterIndexed { index, s -> index != i }
                var strli = li.toString();
                var usfl: MutableList<String> = arrayListOf();


                for (i in 0..<li.size) {

                    if (li[0].toInt() < li[1].toInt()) {
                        if (i < li.size - 1) {
                            if (li[i].toInt() == (li[i + 1].toInt() - 1) || li[i].toInt() == (li[i + 1].toInt() - 2) || li[i].toInt() == (li[i + 1].toInt() - 3)) {

                            } else {
                                usfl.add(strli)
                                break;
                            }
                        }

                    } else {
                        if (i < li.size - 1) {
                            if (li[i].toInt() == (li[i + 1].toInt() + 1) || li[i].toInt() == (li[i + 1].toInt() + 2) || li[i].toInt() == (li[i + 1].toInt() + 3)) {

                            } else {
                                usfl.add(strli)
                                break;
                            }
                        }

                    }

                }
                if (!usfl.contains(strli)) {
                    safe++
                    break;
                }

            }
        }

        secondResult = safe.toLong();
    }
}