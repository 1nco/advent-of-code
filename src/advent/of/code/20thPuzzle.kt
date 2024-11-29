package advent.of.code

import utils.Util
import java.util.*

object `20thPuzzle` {
    private const val DAY = "20";

    private var input: MutableList<String> = arrayListOf();

    private var result = 0L;

    private var resultSecond = 0L;

    private var lowPulses = 0L;
    private var highPulses = 0L;

    fun solve() {
        val startingTime = Date();
        input.addAll(Reader.readInput(DAY));

//        first();

        second();

        println("first: $result");
        println("second: $resultSecond");


        println(startingTime);
        println(Date());

    }

    private fun first() {
        val modules: List<Module> = input.map { l ->
            val moduleTypeAndName = l.split(" -> ")[0]
            val nextModules = l.split(" -> ")[1].split(",").toList().map { it.trim() }
            if (moduleTypeAndName[0] == '%') Module("flip-flop",
                moduleTypeAndName.drop(1),
                mutableMapOf(),
                mutableMapOf(),
                nextModules,
                false,
                "low")
            else if (moduleTypeAndName[0] == '&') Module("conjuction",
                moduleTypeAndName.drop(1),
                mutableMapOf(),
                mutableMapOf(),
                nextModules,
                false,
                "low")
            else Module("broadcast", moduleTypeAndName, mutableMapOf(), mutableMapOf(), nextModules, false, "low")
        }.toList()

        modules.forEach { module ->
            module.nextModules.forEach {
                if (modules.find { m -> m.name == it } != null && modules.find { m -> m.name == it }!!.type == "conjuction") {
                    modules.find { m -> m.name == it }!!.connectTedModules.put(module.name, "low");
                    modules.find { m -> m.name == it }!!.connectTedModulesMin.put(module.name, Int.MAX_VALUE);
                }
            }
        }
        for (i in 0..<1000) {
            var pulses = modules.filter { m -> m.type == "broadcast" }.map { Pulse("button", "broadcaster", "low") }
//            println("------------------------------------------------------------------------------------------------------------------")
            while (pulses.isNotEmpty()) {
                var temp: MutableList<Pulse> = arrayListOf();
                pulses.forEach {
                    if (it.pulse == "high") {
                        highPulses++;
                    } else {
                        lowPulses++
                    }
//                    println("${it.from} -${it.pulse}-> ${it.to}")
                    if (modules.find { m -> m.name == it.to } != null) {
                        temp.addAll(handlePulse(it.from, modules.find { m -> m.name == it.to }!!, it.pulse, i))
                    }
                }
                pulses = temp.toMutableList();
                temp = arrayListOf();
            }
        }
        result = lowPulses * highPulses;
    }

    private fun handlePulse(from: String, module: Module, pulse: String, i: Int): List<Pulse> {
        val pulses: MutableList<Pulse> = arrayListOf();
        if (module.type == "flip-flop") {
            if (pulse == "low") {
                if (module.on == false) {
                    module.on = true;
                    module.nextModules.forEach { m ->
                        pulses.add(Pulse(module.name, m, "high"))
                    }
                } else {
                    module.on = false;
                    module.nextModules.forEach { m ->
                        pulses.add(Pulse(module.name, m, "low"))
                    }
                }
            }

        } else if (module.type == "conjuction") {
            module.connectTedModules[from] = pulse;
            if (pulse == "high") {
                if (module.connectTedModulesMin[from] == Int.MAX_VALUE) {
                    module.connectTedModulesMin[from] = i;
                }
            }
            if (module.connectTedModules.all { m -> m.value == "high" }) {
                module.nextModules.forEach { m ->
                    pulses.add(Pulse(module.name, m, "low"))
                }
            } else {
                module.nextModules.forEach { m ->
                    pulses.add(Pulse(module.name, m, "high"))
                }
            }
        } else {
            module.nextModules.forEach { m ->
                pulses.add(Pulse(module.name, m, pulse))
            }
        }
        return pulses;
    }

    private fun second() {
        val modules: List<Module> = input.map { l ->
            val moduleTypeAndName = l.split(" -> ")[0]
            val nextModules = l.split(" -> ")[1].split(",").toList().map { it.trim() }
            if (moduleTypeAndName[0] == '%') Module("flip-flop",
                moduleTypeAndName.drop(1),
                mutableMapOf(),
                mutableMapOf(),
                nextModules,
                false,
                "low")
            else if (moduleTypeAndName[0] == '&') Module("conjuction",
                moduleTypeAndName.drop(1),
                mutableMapOf(),
                mutableMapOf(),
                nextModules,
                false,
                "low")
            else Module("broadcast", moduleTypeAndName, mutableMapOf(), mutableMapOf(), nextModules, false, "low")
        }.toList()

        modules.forEach { module ->
            module.nextModules.forEach {
                if (modules.find { m -> m.name == it } != null && modules.find { m -> m.name == it }!!.type == "conjuction") {
                    modules.find { m -> m.name == it }!!.connectTedModules.put(module.name, "low");
                    modules.find { m -> m.name == it }!!.connectTedModulesMin.put(module.name, Int.MAX_VALUE);
                }
            }
        }
        var i = 1;
        while (modules.find { m -> m.name == "dg" }!!.connectTedModulesMin.any { e -> e.value == Int.MAX_VALUE }) {
            var pulses = modules.filter { m -> m.type == "broadcast" }.map { Pulse("button", "broadcaster", "low") }
            while (pulses.isNotEmpty()) {
                var temp: MutableList<Pulse> = arrayListOf();
                pulses.forEach {
                    if (modules.find { m -> m.name == it.to } != null) {
                        temp.addAll(handlePulse(it.from, modules.find { m -> m.name == it.to }!!, it.pulse, i))
                    }
                }
                pulses = temp.toMutableList();
                temp = arrayListOf();
            }
            i++;
        }
        resultSecond = Util.findLCMOfListOfNumbers(modules.find { m -> m.name == "dg" }!!.connectTedModulesMin.map { e -> e.value.toLong() });
    }

    data class Module(val type: String,
                      val name: String,
                      val connectTedModules: MutableMap<String, String>,
                      val connectTedModulesMin: MutableMap<String, Int>,
                      val nextModules: List<String>,
                      var on: Boolean,
                      var lastPulse: String);

    data class Pulse(val from: String, val to: String, val pulse: String)
}