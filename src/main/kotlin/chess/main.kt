package chess

// Controller
fun main() {
    run()
}

fun run() {
    var firstPlayerTurn = true

    outputIntro()

    val firstPlanerName = getFirstPlanerName()
    val secondPlayerName = getSecondPlanerName()

    outputGameFiled(formatGameField(generateDefaultFiled()))

    while (true) {
        val coords = getCoords(if (firstPlayerTurn) firstPlanerName else secondPlayerName)
        if (isCoordsValid(coords)) {
            firstPlayerTurn = !firstPlayerTurn
        } else {
            outputInvalidInput()
        }
    }
}