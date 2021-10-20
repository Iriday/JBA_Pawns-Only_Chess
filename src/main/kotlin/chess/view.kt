package chess


fun formatGameField(field: List<List<String>>): List<String> {

    fun stretchRows(field: List<List<String>>): List<List<String>> =
        field.map { row -> row.mapIndexed { i, cell -> if (i == 0) "| $cell |" else " $cell |" } }

    fun flattenRows(field: List<List<String>>): List<String> =
        field.map { r -> r.joinToString("") }

    fun addVerticalSeps(field: List<String>): MutableList<String> {
        val sep = Array(33) { i -> if (i % 4 == 0) "+" else "-" }.joinToString("")
        val mutField = field.toMutableList()
        (0..field.size * 2).forEach { i -> if (i % 2 == 0) mutField.add(i, sep) }
        return mutField
    }

    fun addRanks(field: MutableList<String>): MutableList<String> {
        var rank = 8
        return field.mapIndexed { i, row -> if (i % 2 == 0) "  $row" else "${rank--} $row" }.toMutableList()
    }

    fun addFiles(field: MutableList<String>): MutableList<String> {
        val files = ('a'..'h').joinToString("   ", "    ", "  ")
        return field.also { it.add(files) }
    }

    return addFiles(addRanks(addVerticalSeps(flattenRows(stretchRows(field)))))
}

fun outputIntro() = println("Pawns-Only Chess")

fun outputGameFiled(field: List<String>) = field.forEach(::println)
