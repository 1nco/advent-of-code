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
    var mods: MutableList<Long> = arrayListOf();


    fun solve() {
        val startingTime = LocalDateTime.now();
        inputAsString = Reader.readInputAsString(YEAR, DAY);
        input.addAll(Reader.readInput(YEAR, DAY));


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

//        output = (registerA / 2ˆ((registerA mod 8) xor 3)) mod 8
//        nextRegisterA = registerA / 2ˆ3
//
//        3,0 - 49
//        5,3,0 - 49 * 8 + x= 393
//        5,5,3,0 - 393 * 8 + x= 3145
//        1,5,5,3,0 - 3145 * 8 + x= 25162
//        4,1,5,5,3,0 - 25162 * 8 = x = 201296
//        5,4,1,5,5,3,0 - 201296 * 8 + x= 1610370
//        1,5,4,1,5,5,3,0 - 1610370 * 8 + x = 12883444
//        3,1,5,4,1,5,5,3,0 - 12883444 * 8 + x = 103067559
//        0,3,1,5,4,1,5,5,3,0 - 103067559 * 8 + x = 824540476
//        5,0,3,1,5,4,1,5,5,3,0 - 824540476 * 8 + x = 6596323810
//        7,5,0,3,1,5,4,1,5,5,3,0 - 6596323810 * 8 + x = 52770590485
//        3,7,5,0,3,1,5,4,1,5,5,3,0 - 52770590485 * 8 + x = 422164723880
//        1,3,7,5,0,3,1,5,4,1,5,5,3,0 - 422164723880 * 8 + x = 3377317791096
//        4,1,3,7,5,0,3,1,5,4,1,5,5,3,0 - 3377317791096 * 8 + x = 27018542328773
//        2,4,1,3,7,5,0,3,1,5,4,1,5,5,3,0 - 27018542328773 * 8 + x = 216148338630253

        var programAsString = "2,4,1,3,7,5,0,3,1,5,4,1,5,5,3,0"

        for (i in 216148338630184 ..<Long.MAX_VALUE) {
                registerA = Util.getNumbers(input[0]).get(0)
                registerB = Util.getNumbers(input[1]).get(0)
                registerC = Util.getNumbers(input[2]).get(0)
                pointer = 0;
                output = arrayListOf();

                registerA = i.toLong()
                runProgramSecond(registerA.toLong());
                if (output.joinToString(separator = ",") == programAsString) {
                    secondResult = i.toLong()
                    break;
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

//            for (i in 0..<output.size) {
//                if (output[i] != program[i]) {
//                    done = true
//                    break;
//                }
//            }

//            if (output.size > 6 && output[0] == program[0] && output[1] == program[1] && output[2] == program[2] && output[3] == program[3] && output[4] == program[4] && output[5] == program[5] /*&& output[6] == program[6] && output[7] == program[7] && output[8] == program[8] && output[9] == program[9]*/) {
//                mods.add(original.mod(8).toLong())
//                original.println()
//            }

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