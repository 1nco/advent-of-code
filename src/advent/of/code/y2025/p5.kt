package advent.of.code.y2025

import advent.of.code.Reader
import utils.Util
import utils.println
import java.time.LocalDateTime

class p5 {

    companion object {
        val YEAR = "2025";
        val DAY = "5";
    }

    private var input: MutableList<String> = arrayListOf();

    private var inputAsString: String = "";

    private var firstResult = 0L;

    private var secondResult = 0L;


    fun solve() {
        val startingTime = LocalDateTime.now();
        inputAsString = Reader.readInputAsString(YEAR, DAY);
        input.addAll(Reader.readInput(YEAR, DAY));


        first();
        second();

        println("first: $firstResult");
        println("second: $secondResult");


        val now = LocalDateTime.now();
        println("${startingTime}");
        println("${now}");

    }

    private fun first() {
        var ranges: MutableList<String> = ArrayList();
        var freshIds: MutableList<Long> = ArrayList();
        var availableIds: MutableList<Long> = ArrayList();

        var isRange = true;
        input.forEach { line ->
            if (line.isEmpty()) {
                isRange = false;
            }

            if (line.isNotEmpty()) {
                if (isRange) {
                    ranges.add(line)
                } else {
                    availableIds.add(line.toLong())
                }
            }
        }



        availableIds.forEach { id ->
            var idAlreadyCounted = false;
            ranges.forEach { range ->
                var start = range.split("-")[0].toLong();
                var end = range.split("-")[1].toLong();

                if (id in start..end && !idAlreadyCounted) {
                    firstResult++
                    idAlreadyCounted = true;
                }

            }
        }

    }

    private fun second() {
        var ranges: MutableList<String> = ArrayList();

        var freshIdCount = 0L;

        var processedRanges: MutableList<Util.CoordinateLong> = ArrayList();

        var isRange = true;
        input.forEach { line ->
            if (line.isEmpty()) {
                isRange = false;
            }

            if (line.isNotEmpty()) {
                if (isRange) {
                    ranges.add(line)
                }
            }
        }

        var rangeIdx = 0;
        ranges.forEach { range ->
            "starting ${rangeIdx}".println()
            var start = range.split("-")[0].toLong();
            var end = range.split("-")[1].toLong();

            if (processedRanges.filter { (x, y) -> x > start && x < end && y < end && y > start }.isNotEmpty()) {
                var innerRange = processedRanges.first { (x, y) -> x > start && x < end && y < end && y > start };

                freshIdCount = process(processedRanges, freshIdCount, start, innerRange.x)
                freshIdCount = process(processedRanges, freshIdCount, innerRange.y, end)


            } else {
                freshIdCount = process(processedRanges, freshIdCount, start, end)
            }


            rangeIdx++;
        }

        var idx = 0;
        processedRanges.forEach { processedRange ->
            var maradek = processedRanges.filter { el -> el.x != processedRange.x && el.y != processedRange.y }

            var van = maradek.stream().anyMatch { (x, y) -> processedRange.x in x..y || processedRange.y in x..y }

            if (van) {

                var range = maradek.first { (x, y) -> processedRange.x in x..y || processedRange.y in x..y }
                "range: ${processedRange} + ${idx}".println()
                "range: ${range} + ${processedRanges.indexOf(range)}".println()
            }
            idx++
        }

        secondResult = freshIdCount;
    }

    private fun process(processedRanges: MutableList<Util.CoordinateLong>, f: Long,  s: Long, e: Long): Long {
        var start = s;
        var end = e;
        var freshIdCount = f;
        while (processedRanges.stream().anyMatch { (x, y) -> start in x..y || end in x..y }) {
            processedRanges.forEach { processedRange ->
                if (start in processedRange.x..processedRange.y) {
                    start = processedRange.y + 1;
                }

                if (end in processedRange.x..processedRange.y) {
                    end = processedRange.x - 1;
                }
            }
        }

        if (end >= start) {
            freshIdCount += end - start + 1;
            processedRanges.add(Util.CoordinateLong(start, end));
        }

        return freshIdCount;
    }

}