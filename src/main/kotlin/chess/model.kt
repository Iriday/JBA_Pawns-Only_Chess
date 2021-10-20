package chess

val emptyCell = " "
val blackCell = "B"
val whiteCell = "W"

fun generateDefaultFiled(): List<List<String>> {
    return List(8) { i -> List(8) { if (i == 1) blackCell else if (i == 6) whiteCell else emptyCell } }
}