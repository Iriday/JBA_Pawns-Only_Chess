package chess

import kotlin.system.exitProcess

private val verticalSep = Array(33) { i -> if (i % 4 == 0) "+" else "-" }.joinToString("")
private val files = ('a'..'h').joinToString("   ", "    ", "  ")


fun getFirstPlanerName(): String {
    println("First player's name:")
    return readLine()!!
}

fun getSecondPlanerName(): String {
    println("Second player's name:")
    return readLine()!!
}

fun getCoords(playerName: String): String {
    println("$playerName's turn:")
    val coords = readLine()!!.lowercase()

    if (coords == "exit") {
        outputBye()
        exitProcess(0)
    }
    return coords
}

fun outputIntro() = println("Pawns-Only Chess")

fun outputGameFiled(field: List<String>) = field.forEach(::println)

fun outputBye() = println("Bye!")

fun outputInvalidInput() = println("Invalid input")

fun outputNoPawnAt(pawn: String, at: String) = println("No $pawn at $at")


fun formatGameField(field: Array<Array<String>>): List<String> {

    fun stretchRows(field: Array<Array<String>>): List<List<String>> =
        field.map { row -> row.mapIndexed { i, cell -> if (i == 0) "| $cell |" else " $cell |" } }

    fun flattenRows(field: List<List<String>>): List<String> =
        field.map { r -> r.joinToString("") }

    fun addVerticalSeps(field: List<String>): MutableList<String> {
        val mutField = field.toMutableList()
        (0..field.size * 2).forEach { i -> if (i % 2 == 0) mutField.add(i, verticalSep) }
        return mutField
    }

    fun addRanks(field: MutableList<String>): MutableList<String> {
        var rank = 8
        return field.mapIndexed { i, row -> if (i % 2 == 0) "  $row" else "${rank--} $row" }.toMutableList()
    }

    fun addFiles(field: MutableList<String>): MutableList<String> =
        field.also { it.add(files) }

    return addFiles(addRanks(addVerticalSeps(flattenRows(stretchRows(field)))))
}

