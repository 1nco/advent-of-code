package advent.of.code.y2024

import advent.of.code.Reader
import utils.Util
import java.util.*

class p9 {

    companion object {
        val YEAR = "2024";
        val DAY = "9";
    }

    private var input: MutableList<String> = arrayListOf();

    private var inputAsString: String = "";

    private var firstResult = 0L;

    private var secondResult = 0L;

    private var swapped = false;


    fun solve() {
        val startingTime = Date();
        inputAsString = Reader.readInputAsString(YEAR, DAY);
//        input.addAll(Reader.readInput(YEAR, DAY));

//        first();

        second();

        println("first: $firstResult");
        println("second: $secondResult");


        println(startingTime);
        println(Date());

    }

    private fun first() {
        var asd: Long = 6398608069280L;
        var line = inputAsString;
        var disk: MutableList<String> = mutableListOf();
        var id = 0;
        for (i in 0..<line.length) {
            var num = line[i].toString().toLong();
            if (i % 2 == 0) {
                for (j in 0..<num) {
//                    disk += id.toString().repeat(num.toInt());
                    disk.add(id.toString());
                }
                id++
            } else {
                for (j in 0..<num) {
                    disk.add(".")
//                    disk += ".";
                }
            }
        }


        for (i in disk.size - 1 downTo 0) {
            var idx = disk.indexOfFirst { c -> c == "." };
            disk = swapChars(disk, i, idx);
            if (!swapped) {
                break;
            }
        }

        var idx = 0;
        for (i in 0..<disk.size) {
            if (disk[i] != ".") {
                firstResult += idx * disk[i].toInt();
                idx++;
            } else {
                break;
            }
        }
        println(disk);
    }

    private fun swapChars(str: MutableList<String>, posA: Int, posB: Int): MutableList<String> {
        val chars = str
        if (chars[posA] != "." && posA > posB) {
            val temp = chars[posA]
            chars[posA] = chars[posB]
            chars[posB] = temp
            swapped = true;
            return chars;
        } else {
            return chars;
        }
    }

    private fun second() {
        var line = inputAsString;
        var disk: MutableList<String> = mutableListOf();
        var id = 0;
        for (i in 0..<line.length) {
            var num = line[i].toString().toLong();
            if (i % 2 == 0) {
                for (j in 0..<num) {
                    disk.add(id.toString());
                }
                id++
            } else {
                for (j in 0..<num) {
                    disk += ".";
                }
            }
        }

        var i = disk.size - 1;
        while (i >= 0) {
//        for (i in disk.size - 1 downTo 0) {
            if (disk[i] != ".") {
                var count = 1;
                for (j in i - 1 downTo 0) {
                    if (disk[j] == disk[i]) {
                        count++;
                    }
                }

                var dotCount = 0;
                var idx = disk.indexOfFirst { c -> c == "." } - 1;
                while (dotCount < count && idx < disk.size) {
                    idx++
                    dotCount = 0
                    for (j in idx..<disk.size) {

                        if (disk[j] == ".") {
                            dotCount++;
                        } else {

                            break;
                        }
                    }
                }

                if (dotCount >= count) {
                    for (j in 0..<count) {
                        if (i - j >= 0 && idx + j < disk.size) {
                            disk = swapChars(disk, i - j, idx + j);
                        }
                    }
                }

                i = i - count;
            } else {
                i--;
            }

        }

        for (i in 0..<disk.size) {
            if (disk[i] != ".") {
                secondResult += i * disk[i].toInt();
            }
        }
        println(disk);
    }
}