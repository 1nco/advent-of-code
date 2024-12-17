package advent.of.code.y2024

import advent.of.code.Reader
import utils.Util
import utils.Util.Companion.findLCM
import utils.Util.Companion.initMap
import utils.Util.CoordinateWithType
import utils.println
import java.util.*

class p13 {

    companion object {
        val YEAR = "2024";
        val DAY = "13";
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
//        var map = initMap(input);
        var machines: MutableList<Machine> = mutableListOf();
        var i = 0;
        while (i < input.size) {
            if (i + 2 < input.size) {
                var buttonA = Util.CoordinateLong(Util.getNumbers(input[i])[0], Util.getNumbers(input[i])[1])
                var buttonB = Util.CoordinateLong(Util.getNumbers(input[i + 1])[0], Util.getNumbers(input[i + 1])[1])
                var prize = Util.CoordinateLong(Util.getNumbers(input[i + 2])[0], Util.getNumbers(input[i + 2])[1])
                var machine = Machine(buttonA, buttonB, prize);
                machines.add(machine);
            }
            i += 4
        }

        var wins: MutableMap<Machine, Int> = mutableMapOf();

        machines.forEach { machine ->
            wins.set(machine, Integer.MAX_VALUE);
            for (i in 1..100) {
                for (j in 1..100) {
                    if ((i * machine.buttonA.x + j * machine.buttonB.x) == machine.prize.x && (i * machine.buttonA.y + j * machine.buttonB.y) == machine.prize.y) {
                        var tokens = i * 3 + j * 1;
                        var prev = wins.getOrDefault(machine, Integer.MAX_VALUE);
                        if (tokens < prev) {
                            wins.set(machine, tokens);
                        }
                    }
                }
            }
        }

        firstResult = wins.entries.filter { w -> w.value < Integer.MAX_VALUE }.map { w -> w.value }.reduce { acc, win -> acc + win }.toLong()
    }

    private fun second() {
        var machines: MutableList<Machine> = mutableListOf();
        var i = 0;
        while (i < input.size) {
            if (i + 2 < input.size) {
                var buttonA = Util.CoordinateLong(Util.getNumbers(input[i])[0], Util.getNumbers(input[i])[1])
                var buttonB = Util.CoordinateLong(Util.getNumbers(input[i + 1])[0], Util.getNumbers(input[i + 1])[1])
                var prize = Util.CoordinateLong(Util.getNumbers(input[i + 2])[0], Util.getNumbers(input[i + 2])[1])
                var machine = Machine(buttonA, buttonB, prize);
                machines.add(machine);
            }
            i += 4
        }

        var wins: MutableMap<Machine, Long> = mutableMapOf();

        machines.forEach { machine ->
            machine.prize.x += 10000000000000;
            machine.prize.y += 10000000000000;


            wins.set(machine, 0);

            var b = (machine.prize.y.toDouble() * machine.buttonA.x.toDouble() - machine.prize.x.toDouble() * machine.buttonA.y.toDouble()) / (machine.buttonB.y.toDouble() * machine.buttonA.x.toDouble() - machine.buttonB.x.toDouble() * machine.buttonA.y.toDouble())
            var a = (machine.prize.x.toDouble() - b * machine.buttonB.x.toDouble()) / machine.buttonA.x.toDouble()
            if (Math.floor(a) == a && Math.floor(b) == b) {
                var tokens = a * 3L + b * 1L;
                wins.set(machine, tokens.toLong())
            }
        }

        wins.println()
        secondResult = wins.entries.filter { w -> w.value > 0 }.map { w -> w.value }.reduce { acc, win -> acc + win }.toLong()

    }

    data class Machine(var buttonA: Util.CoordinateLong, var buttonB: Util.CoordinateLong, var prize: Util.CoordinateLong) {}
}