package advent.of.code.y2024

import advent.of.code.Reader
import utils.Util
import utils.println
import java.time.LocalDateTime
import java.util.*

class p17 {

    companion object {
        val YEAR = "2024";
        val DAY = "17";
    }

    private var input: MutableList<String> = arrayListOf();

    private var inputAsString: String = "";

    private var firstResult = 0L;

    private var secondResult = 0L;

    private var output: MutableList<Long> = arrayListOf()


    var registerA = 0L
    var registerB = 0L
    var registerC = 0L
    var pointer = 0;
    var program: List<Long> = arrayListOf();


    fun solve() {
        val startingTime = LocalDateTime.now();
        inputAsString = Reader.readInputAsString(YEAR, DAY);
        input.addAll(Reader.readInput(YEAR, DAY));

        first();
        second();

        println("first: $firstResult");
        println("second: $secondResult");


        println("${startingTime}");
        println("${LocalDateTime.now()}");

    }

    private fun first() {
        registerA = Util.getNumbers(input[0]).get(0)
        registerB = Util.getNumbers(input[1]).get(0)
        registerC = Util.getNumbers(input[2]).get(0)
        pointer = 0;
        output = arrayListOf();

        program = Util.getNumbers(input[4])
        runProgram();
        output.joinToString(separator = ",").println()

    }

    private fun second() {
        program = Util.getNumbers(input[4])

        var programAsString = program.joinToString(separator = ",");
        var done = false;
        var i = 0L
        var tempProgram = ""
        while (!done) {


            registerA = Util.getNumbers(input[0]).get(0)
            registerB = Util.getNumbers(input[1]).get(0)
            registerC = Util.getNumbers(input[2]).get(0)
            pointer = 0;
            output = arrayListOf();


            for (j in 0..<16) {
                tempProgram = program.subList(program.size - 1 - j, program.size).joinToString(separator = ",")


                for (k in 0..<100000) {
                    if (k != 0) {
                        i++;
                    }
                    pointer = 0;
                    registerA = i.toLong()
                    output = arrayListOf();
                    runProgram();
                    if (output.joinToString(separator = ",") == tempProgram) {
                        i.println();
                        if (output.joinToString(separator = ",") != programAsString) {
                            i = i * 8;
                        }
                        break;
                    }

                }


                if (output.joinToString(separator = ",") == programAsString) {
                    secondResult = i.toLong()
                    done = true;
                }
            }
        }

        output.joinToString(separator = ",").println()
    }

    private fun runProgram() {
        while (pointer < program.size) {

            var pointerValue = program[pointer]
            var operand = program[pointer + 1]

            if (pointerValue == 0L) {
                adv(operand);
            }
            if (pointerValue == 1L) {
                bxl(operand);
            }
            if (pointerValue == 2L) {
                bst(operand);
            }
            if (pointerValue == 3L) {
                jnz(operand);
            }
            if (pointerValue == 4L) {
                bxc(operand);
            }
            if (pointerValue == 5L) {
                out(operand);
            }
            if (pointerValue == 6L) {
                bdv(operand);
            }
            if (pointerValue == 7L) {
                cdv(operand);
            }

            if (pointerValue != 3L) {
                pointer += 2
            }
        }
    }

    private fun runProgramSecond(original: Long) {
        var done = false
        while (pointer < program.size && !done) {

            var pointerValue = program[pointer]
            var operand = program[pointer + 1]

            if (pointerValue == 0L) {
                adv(operand);
            }
            if (pointerValue == 1L) {
                bxl(operand);
            }
            if (pointerValue == 2L) {
                bst(operand);
            }
            if (pointerValue == 3L) {
                jnz(operand);
            }
            if (pointerValue == 4L) {
                bxc(operand);
            }
            if (pointerValue == 5L) {
                out(operand);
            }
            if (pointerValue == 6L) {
                bdv(operand);
            }
            if (pointerValue == 7L) {
                cdv(operand);
            }

            if (pointerValue != 3L) {
                pointer += 2
            }
        }
    }

    private fun adv(operand: Long) {
        registerA = (registerA / Math.pow(2.0, getComboOperandValue(operand).toDouble())).toLong()
    }

    private fun bxl(operand: Long) {
        registerB = registerB.xor(operand)
    }

    private fun bst(operand: Long) {
        registerB = getComboOperandValue(operand).mod(8).toLong()
    }

    private fun jnz(operand: Long) {
        if (registerA != 0L) {
            pointer = operand.toInt();
        } else {
            pointer += 2
        }
    }

    private fun bxc(operand: Long) {
        registerB = registerB.xor(registerC)
    }

    private fun out(operand: Long) {
        output.add(getComboOperandValue(operand).mod(8).toLong())
    }

    private fun bdv(operand: Long) {
        registerB = (registerA / Math.pow(2.0, getComboOperandValue(operand).toDouble())).toLong()
    }

    private fun cdv(operand: Long) {
        registerC = (registerA / Math.pow(2.0, getComboOperandValue(operand).toDouble())).toLong()
    }

    private fun getComboOperandValue(operand: Long): Long {
        if (operand <= 3L) return operand;
        if (operand == 4L) return registerA;
        if (operand == 5L) return registerB;
        if (operand == 6L) return registerC;

        return operand
    }
}