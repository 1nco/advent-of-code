package advent.of.code.y2024

import advent.of.code.Reader
import utils.Util
import java.util.*
import java.util.logging.Logger

class p7 {

    companion object {
        val YEAR = "2024";
        val DAY = "7";
    }

    private var input: MutableList<String> = arrayListOf();

    private var firstResult = 0L;

    private var secondResult = 0L;


    fun solve() {
        val startingTime = Date();
//        input.add(Reader.readInputAsString(YEAR, DAY));
        input.addAll(Reader.readInput(YEAR, DAY));

        first();

        second();

        println("first: $firstResult");
        println("second: $secondResult");


        println(startingTime);
        println(Date());

    }

    private fun first() {
        input.forEach { line ->
            var total = line.split(":")[0].toLong();
            var numbers = Util.getNumbers(line.split(":")[1]);
            var isValidList: MutableList<Boolean> = mutableListOf();
            var isValid = eval(numbers, total, isValidList);
            if (isValidList.contains(true)) {
                println(line)
                firstResult += total;
            }
        }
    }

    private fun second() {
        input.forEach { line ->
            var total = line.split(":")[0].toLong();
            var numbers = Util.getNumbers(line.split(":")[1]);
            var isValidList: MutableList<Boolean> = mutableListOf();
            eval2(numbers, total, isValidList);
            if (isValidList.contains(true)) {
                println(line)
                secondResult += total;
            }
        }
    }


    private fun eval(numbers: List<Long>, total: Long, isValid: MutableList<Boolean>): Boolean {
        val operators = arrayOf("+", "*");
        var p = 0L;
        var sz = 0L;
        if (1 < numbers.size) {
            operators.forEach { operator ->
                if (operator == "+") {
                    p = numbers[0].toLong() + numbers[1].toLong();
                    var newL1: MutableList<Long> = mutableListOf();
                    newL1.add(p);
                    newL1.addAll(numbers.subList(2, numbers.size))
                    if (numbers.size > 2) {
                        eval(newL1, total, isValid);
                    }
                }

                if (operator == "*") {
                    sz = numbers[0] * numbers[1];
                    var newL2: MutableList<Long> = mutableListOf();
                    newL2.add(sz);
                    newL2.addAll(numbers.subList(2, numbers.size))
                    if (numbers.size > 2) {
                        eval(newL2, total, isValid);
                    }
                }
            }
        }
        isValid.add(total == p || total == sz);
        return total == p || total == sz;

    }

    private fun eval2(numbers: List<Long>, total: Long, isValid: MutableList<Boolean>): Boolean {
        val operators = arrayOf("+", "*", "||");
        var p = 0L;
        var sz = 0L;
        var c = 0L
        if (1 < numbers.size) {
            operators.forEach { operator ->
                if (operator == "+") {
                    p = numbers[0].toLong() + numbers[1].toLong();
                    var newL1: MutableList<Long> = mutableListOf();
                    newL1.add(p);
                    newL1.addAll(numbers.subList(2, numbers.size))
                    if (numbers.size > 2) {
                        eval2(newL1, total, isValid);
                    }
                }

                if (operator == "*") {
                    sz = numbers[0] * numbers[1];
                    var newL2: MutableList<Long> = mutableListOf();
                    newL2.add(sz);
                    newL2.addAll(numbers.subList(2, numbers.size))
                    if (numbers.size > 2) {
                        eval2(newL2, total, isValid);
                    }
                }

                if (operator == "||") {
                    var num0 = numbers[0];
                    var num1 = numbers[1];
                    c = "$num0$num1".toLong();
                    var newL3: MutableList<Long> = mutableListOf();
                    newL3.add(c);
                    newL3.addAll(numbers.subList(2, numbers.size))
                    if (numbers.size > 2) {
                        eval2(newL3, total, isValid);
                    }
                }
            }
        }
        if (numbers.size == 2) {
            isValid.add(total == p || total == sz || total == c);
        }
        return total == p || total == sz || total == c;

    }
}