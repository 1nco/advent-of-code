package advent.of.code.types

data class Pos(var line: Int, var column: Int) {

    fun toKey(): String {
        return "$line;$column"
    }
};