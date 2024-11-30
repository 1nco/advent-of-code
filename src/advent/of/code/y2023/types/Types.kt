package advent.of.code.y2023.types

data class Pos(var line: Int, var column: Int) {

    fun toKey(): String {
        return "$line;$column"
    }
};