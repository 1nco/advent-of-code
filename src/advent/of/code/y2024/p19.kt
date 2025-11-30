package advent.of.code.y2024

import utils.println
import java.time.LocalDateTime
import java.util.stream.Collectors

class p19 {

    companion object {
        val YEAR = "2024";
        val DAY = "19";
    }


    private var inputAsString: String = "";

    private var firstResult = 0L;

    private var secondResult = 0L;


    fun solve() {
        val startingTime = LocalDateTime.now();
//        inputAsString = "1605026 9660775 3732843 9617262 9563983 9850037 5378202 3956795";
        inputAsString = "5115217 663158 3911826 362025 1287450 6042270 6954803 3449492";
//        inputAsString = "4843858 3666185 3426800 4674823 5123678 7747952 8766274 9565981";


        first();
        second();
//        third();

        println("first: $firstResult");
        println("second: $secondResult");


        val now = LocalDateTime.now();
        println("${startingTime}");
        println("${now}");

    }

    private fun first() {

        var tendrils = inputAsString.split(" ");
        for (i in 0..<25) {
            tendrils = tendrils.stream().map { grow(it) }.flatMap { it.stream() }.collect(Collectors.toList())
        }

        firstResult = tendrils.size.toLong();
    }

    private fun grow(tendril: String): List<String> {
        if (tendril.toLong() == 0L) {
            return listOf("3")
            // 3-nal hoszabb szam!!!
        } else if (tendril.length % 2 == 1 && tendril.length > 2) {
            // 0-val kezdodo szam nem lehet
            val newTendril1 = tendril.substring(0, tendril.length/2).toLong();
            // 0-val kezdodo szam nem lehet
            val newTendril2 = tendril.substring(tendril.length/2).toLong();
            return listOf("${newTendril1}", "${newTendril2}")
        } else {
            return listOf("${tendril.toLong() * 3141}");
        }
    }



    private fun second() {

        // Long kell, Int nem eleg, tulcsordul
        var tendrilsMap: MutableMap<String, Long> = inputAsString.split(" ").map { Pair(it, 1L) }.toMap().toMutableMap();


        for (i in 0..<75) {
            var newTendrilsMap: MutableMap<String, Long> = mutableMapOf();
            tendrilsMap.entries.stream().forEach { tendril ->
                grow(tendril.key).forEach { newTendrilsMap.put(it, newTendrilsMap.getOrPut(it, { 0L }) + tendril.value) };
            }
            tendrilsMap = newTendrilsMap;
        }

        // value-kat adjuk ossze ne az elemeket
        secondResult = tendrilsMap.values.sum().toLong();
    }

//    private fun third() {
//
//        // Long kell, Int nem eleg, tulcsordul
//        var tendrilsMap: MutableMap<String, Long> = inputAsString.split(" ").map { Pair(it, 1L) }.toMap().toMutableMap();
//
//
//        for (i in 0..<75) {
//            tendrilsMap = tendrilsMap.entries.stream().map { tendril -> grow(tendril.key).map { Pair(it, tendril.value) } }.flatMap { it.stream() }.collect(Collectors.toList()).toMap().toMutableMap()
//        }
//
//        // value-kat adjuk ossze ne az elemeket
//        println("third: ${tendrilsMap.values.sum()}");
//    }


}