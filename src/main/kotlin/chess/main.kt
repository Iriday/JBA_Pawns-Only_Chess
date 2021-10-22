package chess

// Controller
fun main() {
    run(Model())
}

fun run(model: Model) {
    outputIntro()

    val firstPlanerName = getFirstPlanerName()
    val secondPlayerName = getSecondPlanerName()

    outputGameFiled(formatGameField(model.getGameFiled()))

    while (true) {
        val coords = getCoords(if (model.getCurrentPawn() == "white") firstPlanerName else secondPlayerName)

        when (model.isMovePossible(coords)) {
            MoveOutcome.NO_CORRECT_PAWN -> outputNoPawnAt(model.getCurrentPawn(), coords.substring(0, 2))
            MoveOutcome.INVALID_INPUT -> outputInvalidInput()
            MoveOutcome.MOVE_IS_POSSIBLE -> {
                model.makeMove(coords)
                outputGameFiled(formatGameField(model.getGameFiled()))
            }
        }
    }
}