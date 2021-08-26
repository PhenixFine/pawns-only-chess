class Chessboard {
    private val chessboard = Array(8) { Array(8) { ' ' } }
    private val regexMoves = Regex("[a-h][1-8][a-h][1-8]")

    init {
        for (j in 0..chessboard.lastIndex) {
            chessboard[1][j] = 'W'
            chessboard[6][j] = 'B'
        }
    }

    fun printChessboard() {
        val line = "  +---+---+---+---+---+---+---+---+"
        val end = "    a   b   c   d   e   f   g   h"

        println(line)
        for (i in 7 downTo 0) {
            print("${i + 1} |")
            for (j in 0..7) print(" ${chessboard[i][j]} |")
            println("\n" + line)
        }
        println(end)
    }

    fun validMove(move: String) = move.matches(regexMoves)
}