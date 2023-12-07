package advent.of.code

class Util {
    companion object {
        fun getNumbers(line: String, separator: String = "\\b"): List<Long> {
            return Regex(separator + "\\d+"+separator).findAll(line).toList().map { it.value.toLong() };
        }
    }
}