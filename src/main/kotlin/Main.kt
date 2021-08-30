fun main() {
    val gameName = "Pawns-Only Chess"
    val getName = "Player's name:"
    val pawnsChess = PawnsChess(getString("$gameName\nFirst $getName"), getString("Second $getName"))
    var command = ""
    val getCommand = { command = getString("${pawnsChess.currentPlayer()}'s turn:"); command }

    pawnsChess.printChessboard()

    while (getCommand() != "exit") {
        if (pawnsChess.movePawn(command)) {
            pawnsChess.printChessboard()
        }
    }
    println("Bye!")
}

private fun getString(message: String): String {
    println(message)
    return readLine()!!
}