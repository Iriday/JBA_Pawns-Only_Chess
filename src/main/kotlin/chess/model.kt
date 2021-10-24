package chess

import chess.Move.*
import chess.Pawn.*
import kotlin.math.absoluteValue

enum class Pawn { WHITE, BLACK }

enum class Move { WHITE_WINS, BLACK_WINS, MOVE_IS_SUCCESSFUL, NO_CORRECT_PAWN, INVALID_INPUT }

class Model {
    private val emptyCell = " "
    private val blackCell = "B"
    private val whiteCell = "W"
    private val coordsPatters = "([a-h][1-8]){2}".toRegex()
    private val field = generateDefaultFiled()

    private var isWhiteTurn = true
    private var isPreviousMoveFirstVerticalDoubleMove = false
    private var prevMoveCoords = Pair(-1, -1)

    fun makeMove(coords: String): Move {
        if (!isCoordsValid(coords)) return INVALID_INPUT

        val from = coordsToIndexes(coords.substring(0, 2))
        val to = coordsToIndexes(coords.substring(2, 4))

        return if (!isCellPresentAt(getCurrentPawnCell(), from)) {
            NO_CORRECT_PAWN
        } else if (isSingleOrDoubleRegularMove(from, to) || isCapture(from, to)) {
            move(from, to, field)
            return if (isWin(prevMoveCoords, getCurrentPawnCell())) {
                if (getCurrentPawn() == WHITE) return BLACK_WINS else WHITE_WINS
            } else {
                MOVE_IS_SUCCESSFUL
            }
        } else if (isEnPassant(from, to)) {
            move(from, to, field, enPassant = true)
            if (isWin(prevMoveCoords, getCurrentPawnCell())) {
                if (getCurrentPawn() == WHITE) return BLACK_WINS else WHITE_WINS
            } else {
                MOVE_IS_SUCCESSFUL
            }
        } else {
            INVALID_INPUT
        }
    }

    fun getGameFiled() = field

    fun getCurrentPawn() = if (isWhiteTurn) WHITE else BLACK

    fun getOppositePawn() = if(isWhiteTurn) BLACK else WHITE

    private fun isCoordsValid(coords: String): Boolean = coords.matches(coordsPatters)

    private fun isCellPresentAt(cell: String, coords: Pair<Int, Int>) = field[coords.first][coords.second] == cell

    private fun getCurrentPawnCell() = if (isWhiteTurn) whiteCell else blackCell

    private fun getOppositePawnCell() = if (!isWhiteTurn) whiteCell else blackCell

    private fun isFieldContainsCell(field: Array<Array<String>>, cell: String): Boolean {
        for (row in field) {
            if (row.contains(cell)) return true
        }
        return false
    }

    private fun isFirstMove(from: Pair<Int, Int>) = from.first == 1 || from.first == 6

    private fun isForwardMove(from: Pair<Int, Int>, to: Pair<Int, Int>, isWhiteTurn: Boolean): Boolean {
        return isWhiteTurn && from.first > to.first || !isWhiteTurn && from.first < to.first

    }

    private fun isSingleVerticalMove(from: Pair<Int, Int>, to: Pair<Int, Int>): Boolean {
        return (from.first - to.first).absoluteValue == 1 && from.second == to.second
    }

    private fun isDoubleVerticalMove(from: Pair<Int, Int>, to: Pair<Int, Int>): Boolean {
        return (from.first - to.first).absoluteValue == 2 && from.second == to.second
    }

    private fun isSingleDiagonalMove(from: Pair<Int, Int>, to: Pair<Int, Int>): Boolean {
        return (from.first - to.first).absoluteValue == 1 && (from.second - to.second).absoluteValue == 1
    }

    private fun isPreviousPawnLocatedBellowDestination(to: Pair<Int, Int>): Boolean {
        return isWhiteTurn && to.first + 1 == prevMoveCoords.first && to.second == prevMoveCoords.second
                || !isWhiteTurn && to.first - 1 == prevMoveCoords.first && to.second == prevMoveCoords.second
    }

    private fun isCapture(from: Pair<Int, Int>, to: Pair<Int, Int>): Boolean {
        return isCellPresentAt(getCurrentPawnCell(), from)
                && isCellPresentAt(getOppositePawnCell(), to)
                && isForwardMove(from, to, isWhiteTurn)
                && isSingleDiagonalMove(from, to)
    }

    private fun isSingleOrDoubleRegularMove(from: Pair<Int, Int>, to: Pair<Int, Int>): Boolean {
        return isCellPresentAt(getCurrentPawnCell(), from)
                && isCellPresentAt(emptyCell, to)
                && isForwardMove(from, to, isWhiteTurn)
                && isSingleVerticalMove(from, to) || (isFirstMove(from) && isDoubleVerticalMove(from, to))
    }

    private fun isEnPassant(from: Pair<Int, Int>, to: Pair<Int, Int>): Boolean {
        return isPreviousMoveFirstVerticalDoubleMove
                && isCellPresentAt(getCurrentPawnCell(), from)
                && isCellPresentAt(emptyCell, to)
                && isForwardMove(from, to, isWhiteTurn)
                && isSingleDiagonalMove(from, to)
                && isPreviousPawnLocatedBellowDestination(to)
    }

    private fun generateDefaultFiled(): Array<Array<String>> {
        return Array(8) { i -> Array(8) { if (i == 1) blackCell else if (i == 6) whiteCell else emptyCell } }
    }

    private fun coordsToIndexes(singleCoords: String): Pair<Int, Int> {
        return Pair((singleCoords[1].digitToInt() - 8).absoluteValue, singleCoords[0] - 'a')
    }

    private fun setCellAt(cell: String, to: Pair<Int, Int>, field: Array<Array<String>>) {
        field[to.first][to.second] = cell
    }

    private fun move(from: Pair<Int, Int>, to: Pair<Int, Int>, field: Array<Array<String>>, enPassant: Boolean = false){
        isPreviousMoveFirstVerticalDoubleMove = isFirstMove(from) && isDoubleVerticalMove(from, to)

        setCellAt(field[from.first][from.second], to, field)
        setCellAt(emptyCell, from, field)

        if (enPassant) setCellAt(emptyCell, prevMoveCoords, field)

        prevMoveCoords = to
        isWhiteTurn = !isWhiteTurn
    }

    private fun isWin(prevMoveCoords: Pair<Int, Int>, currPawnCell: String): Boolean {
        return prevMoveCoords.first == 0 || prevMoveCoords.first == 7 || !isFieldContainsCell(field, currPawnCell)
    }
}
