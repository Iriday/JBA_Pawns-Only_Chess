package chess

import chess.Move.*

// Controller
fun main() {
    run(Model())
}

fun run(model: Model) {
    outputIntro()

    val firstPlanerName = getFirstPlanerName()
    val secondPlayerName = getSecondPlanerName()

    outputGameFiled(model.getGameFiled())

    while (true) {
        val coords = getCoords(if (model.getCurrentPawn() == Pawn.WHITE) firstPlanerName else secondPlayerName)

        when (model.makeMove(coords)) {
            NO_CORRECT_PAWN -> outputNoPawnAt(model.getCurrentPawn().name.lowercase(), coords.substring(0, 2))
            INVALID_INPUT -> outputInvalidInput()
            MOVE_IS_SUCCESSFUL -> outputGameFiled(model.getGameFiled())
        }
    }
}