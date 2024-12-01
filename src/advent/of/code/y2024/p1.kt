package advent.of.code.y2024

import advent.of.code.Reader
import java.util.*
import kotlin.math.abs

object p1 {

    const val YEAR = "2024";
    const val DAY = "1";

    private var input: MutableList<String> = arrayListOf();

    private var firstResult = 0L;

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
//        val map = Util.initMap(input);
        var leftList: MutableList<Int> = ArrayList();
        var rightList: MutableList<Int> = ArrayList();
        input.forEach{ i ->
            leftList.add(i.split("   ")[0].toInt());
            rightList.add(i.split("   ")[1].toInt())
        }

        leftList.sort();
        rightList.sort();
        var dist = 0;
        for (i in 0 until leftList.size) {
            dist += abs(leftList.get(i)- rightList.get(i));
        }
        firstResult = dist.toLong();
    }


    private fun second() {

        var leftList: MutableList<Int> = ArrayList();
        var rightList: MutableList<Int> = ArrayList();
        input.forEach{ i ->
            leftList.add(i.split("   ")[0].toInt());
            rightList.add(i.split("   ")[1].toInt())
        }

        var sim = 0;
        for (i in 0 until leftList.size) {
            sim += leftList.get(i) * rightList.count { e -> e.equals(leftList.get(i)) }
        }

//        val map = Util.initMap(input);

        secondResult = sim.toLong();
    }
}