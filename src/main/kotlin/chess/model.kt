package chess

import kotlin.math.absoluteValue


class Model {
    private val whitePawn = "white"
    private val blackPawn = "black"
    private val emptyCell = " "
    private val blackCell = "B"
    private val whiteCell = "W"
    private val coordsPatters = "([a-h][1-8]){2}".toRegex()
    private val field = generateDefaultFiled()

    private var isWhiteTurn = true

    fun makeMove(coords: String) {
        move(coordsToIndexes(coords.substring(0, 2)), coordsToIndexes(coords.substring(2, 4)))
        isWhiteTurn = !isWhiteTurn
    }

    fun isMovePossible(coords: String): MoveOutcome {
        if (!isCoordsValid(coords)) return MoveOutcome.INVALID_INPUT

        val from = coordsToIndexes(coords.substring(0, 2))

        val to = coordsToIndexes(coords.substring(2, 4))

        if (isCellPresentAt(getCurrentPawnCell(), from)) {
            if (isCellPresentAt(emptyCell, to) && isDirectionValid(from, to) && isStepValid(from, to)) {
                return MoveOutcome.MOVE_IS_POSSIBLE
            } else {
                return MoveOutcome.INVALID_INPUT
            }
        } else {
            return MoveOutcome.NO_CORRECT_PAWN
        }
    }

    fun getGameFiled() = field

    fun getCurrentPawn() = if (isWhiteTurn) whitePawn else blackPawn

    private fun isCoordsValid(coords: String): Boolean = coords.matches(coordsPatters)

    private fun isCellPresentAt(cell: String, coords: Pair<Int, Int>) = field[coords.first][coords.second] == cell

    private fun getCurrentPawnCell() = if (isWhiteTurn) whiteCell else blackCell

    private fun isFirstMove(from: Pair<Int, Int>) = from.first == 1 || from.first == 6

    private fun isStepValid(from: Pair<Int, Int>, to: Pair<Int, Int>): Boolean {
        return (from.first - to.first).absoluteValue == 1
                || (isFirstMove(from) && (from.first - to.first).absoluteValue == 2)
                && from.second == to.second
    }

    private fun isDirectionValid(from: Pair<Int, Int>, to: Pair<Int, Int>): Boolean {
        return (isWhiteTurn && from.first > to.first || !isWhiteTurn && from.first < to.first)
                && from.second == to.second
    }

    private fun generateDefaultFiled(): Array<Array<String>> {
        return Array(8) { i -> Array(8) { if (i == 1) blackCell else if (i == 6) whiteCell else emptyCell } }
    }

    private fun coordsToIndexes(singleCoords: String): Pair<Int, Int> {
        return Pair((singleCoords[1].digitToInt() - 8).absoluteValue, singleCoords[0] - 'a')
    }

    private fun move(from: Pair<Int, Int>, to: Pair<Int, Int>) {
        field[to.first][to.second] = field[from.first][from.second]
        field[from.first][from.second] = emptyCell
    }
}