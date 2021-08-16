class Chessboard {
    private val chessboard = Array(8) { Array(8) { ' ' } }

    init {
        for (j in 0..chessboard.lastIndex) {
            chessboard[1][j] = 'W'
            chessboard[6][j] = 'B'
        }
    }

    fun printChessboard() {
        val line = "  +---+---+---+---+---+---+---+---+"
        val end = "    a   b   c   d   e   f   g   h"

        println(" Pawns-Only Chess\n$line")
        for (i in 7 downTo 0) {
            for (j in 0..7) {
                if (j == 0) print("${i + 1} |")
                print(" ${chessboard[i][j]} |")
            }
            println("\n" + line)
        }
        println(end)
    }
}