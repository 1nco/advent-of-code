package advent.of.code

import java.util.*

object `19thPuzzle` {

    private const val DAY = "19";

    private var input: MutableList<String> = arrayListOf();

    private var result = 0L;

    private var resultSecond = 0L;


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

    fun first() {
        val workflows: Map<String, List<Rule>> = input.filter { it.isNotBlank() && it.first() != '{' }.map { line ->
            parseWorkflow(line)
        }.toMap()
        val parts: List<Part> = input.filter { it.isNotBlank() && it.first() == '{' }.map { line -> parsePart(line) }
        result = parts.map { p ->
            val res = process(p, workflows)
            if (res == "A") {
                p.categories.map { it.value }.sum()
            } else {
                0
            }
        }.sum()
    }


    fun second() {
        val workflows: Map<String, List<Rule>> = input.filter { it.isNotBlank() && it.first() != '{' }.map { line ->
            parseWorkflow(line)
        }.toMap()
        var pair = PartRange(mutableMapOf(Pair("x", 1L..4000L),
            Pair("m", 1L..4000L),
            Pair("a", 1L..4000L),
            Pair("s", 1L..4000L)))
        resultSecond = processRanges(pair, workflows, "in");

        println(resultSecond);
    }

    private fun processRanges(part: PartRange, workflows: Map<String, List<Rule>>, r: String): Long {
        if (r == "A") println("$part r: $r");

        var name = r;
        var cum = 0L
        if (name == "A") {
            var counts = 1L
            part.categories.map { it.value.count().toLong() }.forEach {
                counts *= it;
            }
            return counts;
        };
        if (name == "R") return 0L

        var part = part;

        for (i in workflows[name]!!.indices) {
            val rule = workflows[name]!![i];
            if (rule.expr != null) {
                val ruleNumber = rule.expr!!.drop(1).toLong();
                if (rule.expr.contains("<")) {
                    cum += processRanges(part.createCopyWithDifferentRange(rule.category!!, 1 until ruleNumber),
                        workflows,
                        rule.result)
                    part = part.createCopyWithDifferentRange(rule.category!!, ruleNumber..4000);
                } else {
                    cum += processRanges(part.createCopyWithDifferentRange(rule.category!!, ruleNumber + 1..4000),
                        workflows,
                        rule.result)
                    part = part.createCopyWithDifferentRange(rule.category!!, 1..ruleNumber);
                }

            } else {
                cum += processRanges(part, workflows, rule.result)
            }
        }
        return cum;
    }

    private fun process(part: Part, workflows: Map<String, List<Rule>>): String? {
        var done = false;
        var name = "in";
        while (!done) {
            if (name == "A" || name == "R") {
                return name;
            }
            for (i in workflows[name]!!.indices) {
                val rule = workflows[name]!![i];
                if (rule.expr != null) {
                    if (eval(part.categories[rule.category]!!, rule.expr)) {
                        name = rule.result;
                        break;
                    }
                } else {
                    if (rule.result == "A" || rule.result == "R") {
                        return rule.result;
                    } else {
                        name = rule.result;
                        break;
                    }
                }
            }
        }
        return null;
    }

    private fun eval(partCategory: Long, expression: String): Boolean {
        if (expression.first() == '<') {
            return partCategory < expression.drop(1).toLong();
        } else {
            return partCategory > expression.drop(1).toLong();
        }
    }

    private fun parseWorkflow(line: String): Pair<String, List<Rule>> {
        val name = line.split("{").first()
        val expressions = line.split("{").last().replace("}", "").split(",").map { r ->
            if (r.contains(":")) Rule(r[0].toString(),
                r.split(":").first().drop(1),
                r.split(":").last()) else Rule(null, null, r);
        }

        return Pair(name, expressions);
    }

    private fun parsePart(line: String): Part {
        val part = Part(mutableMapOf())
        val categories = line.replace("{", "").replace("}", "").split(",")
        part.categories.putAll(categories.map { c -> Pair(c.split("=").first(), c.split("=").last().toLong()) })
        return part;
    }

    data class Rule(val category: String?, val expr: String?, val result: String)

    data class Part(val categories: MutableMap<String, Long>)

    data class PartRange(val categories: MutableMap<String, LongRange>) {

        fun createCopy(): PartRange {
            val copy = PartRange(mutableMapOf());
            arrayOf("x", "m", "a", "s").forEach { cat ->
                copy.categories.put(cat, categories[cat]!!);
            }
            return copy;
        }

        fun createCopyWithDifferentRange(c: String, range: LongRange): PartRange {
            val p = createCopy();
            p.categories[c] = LongRange(p.categories[c]!!.intersect(range).first(),
                p.categories[c]!!.intersect(range).last());
            return p;
        }
    }
}