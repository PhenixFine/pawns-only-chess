fun main() {
    val chessboard = Chessboard()
    val gameName = "Pawns-Only Chess"
    val getName = "Player's name:"
    val player1 = getString("$gameName\nFirst $getName")
    val player2 = getString("Second $getName")
    var currentPlayer = player1
    var command = ""
    val getCommand = { command = getString("$currentPlayer's turn:"); command }

    chessboard.printChessboard()

    while (getCommand() != "exit") {
        if (chessboard.validMove(command)) {
            currentPlayer = if (currentPlayer == player1) player2 else player1
        } else {
            println("Invalid Input")
        }
    }
    println("Bye!")
}

private fun getString(message: String): String {
    println(message)
    return readLine()!!
}