class PawnsChess(private val player1: String, private val player2: String) {
    private val chessboard = Array(8) { Array(8) { ' ' } }
    private val regexMoves = Regex("[a-h][1-8]")
    private var isPlayer1Turn = true

    init {
        for (j in chessboard.indices) {
            chessboard[1][j] = 'w'
            chessboard[6][j] = 'b'
        }
    }

    fun printChessboard() {
        val line = "  +---+---+---+---+---+---+---+---+"
        val end = "    a   b   c   d   e   f   g   h\n"

        println(line)
        for (i in chessboard.indices.reversed()) {
            print("${i + 1} |")
            for (j in chessboard.indices) print(" ${chessboard[i][j].uppercaseChar()} |")
            println("\n" + line)
        }
        println(end)
    }

    fun movePawn(move: String): Boolean {
        var invalid = "Invalid Input"
        val firstHalf = move.substring(0, 2)
        val secondHalf = move.substring(2)

        if (isValidRange(firstHalf) && isValidRange(secondHalf)) {
            val pawn = getChar(firstHalf)
            val isCorrectPawn = pawn.isLetter() && pawn.uppercaseChar() == if (isPlayer1Turn) 'W' else 'B'

            if (isCorrectPawn && possibleMoves(firstHalf, pawn).contains(secondHalf)) {
                chessboard[numToInt(move[1])][letterToInt(move[0])] = ' '
                chessboard[numToInt(move[3])][letterToInt(move[2])] = pawn.uppercaseChar()
                isPlayer1Turn = !isPlayer1Turn
                return true
            } else if (!isCorrectPawn) {
                invalid = "No " + (if (isPlayer1Turn) "white" else "black") + " pawn at $firstHalf"
            }
        }
        println(invalid)
        return false
    }

    fun currentPlayer() = if (isPlayer1Turn) player1 else player2

    private fun possibleMoves(position: String, pawn: Char): List<String> {
        val isTwoMoves = pawn.isLowerCase()
        val firstMove = nextMove(position)
        val secondMove = if (isTwoMoves) nextMove(firstMove) else ""
        val list = mutableListOf<String>()
        val isValid = { move: String -> isValidRange(move) && getChar(move) == ' ' }

        if (isValid(firstMove)) list.add(firstMove) else return list
        if (isTwoMoves && isValid(secondMove)) list.add(secondMove)
        return list
    }

    private fun nextMove(position: String): String {
        return position[0] + "" + (position[1] + (if (isPlayer1Turn) 1 else -1))
    }

    private fun isValidRange(position: String) = position.matches(regexMoves)

    private fun getChar(position: String) = chessboard[numToInt(position[1])][letterToInt(position[0])]

    private fun numToInt(num: Char) = num.digitToInt() - 1

    private fun letterToInt(letter: Char) = letter.code - (if (letter.isLowerCase()) 97 else 65)
}