package chess

import chess.Move.*
import kotlin.system.exitProcess

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
            BLACK_WINS, WHITE_WINS -> {
                outputGameFiled(model.getGameFiled())
                outputPawnWins(model.getOppositePawn().name.lowercase().capitalize())
                outputBye()
                exitProcess(0)
            }
            STALEMATE -> {
                outputGameFiled(model.getGameFiled())
                outputStalemate()
                outputBye()
                exitProcess(0)
            }
        }
    }
}