class PawnsChess(private val player1: String, private val player2: String) {
    private val chessboard =
        Array(8) { index -> Array(8) { if (index == 1) 'w' else if (index == 6) 'b' else ' ' } }
    private val regexMoves = Regex("[a-h][1-8]")
    private var isPlayer1Turn = true
    private val enPassant = EnPassant()

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
            val isCorrectPawn = pawn.isLetter() && pawn.uppercaseChar() == getPawn(isPlayer1Turn)

            if (isCorrectPawn && canMove(firstHalf, secondHalf, pawn.isLowerCase())) {
                chessboard[numToInt(move[1])][letterToInt(move[0])] = ' '
                chessboard[numToInt(move[3])][letterToInt(move[2])] = pawn.uppercaseChar()
                if (enPassant.isCaptured) {
                    val capture = enPassant.getCapture()
                    chessboard[numToInt(capture[1])][letterToInt(capture[0])] = ' '
                }
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

    private fun canMove(position: String, move: String, isTwoMoves: Boolean) =
        verticalMoves(position, move) || forwardMoves(position, move, isTwoMoves)

    private fun verticalMoves(position: String, move: String): Boolean {
        val nextNum = nextMove(position)[1]
        val firstMove = (position[0] - 1) + "" + nextNum
        val secondMove = (position[0] + 1) + "" + nextNum
        val canMove: Boolean
        val isValid = { place: String -> place == move && getPawn(!isPlayer1Turn) == getChar(place).uppercaseChar() }

        enPassant.setIsCaptured(firstMove, secondMove, move)
        canMove = enPassant.isCaptured || isValid(firstMove) || isValid(secondMove)
        if (canMove) enPassant.resetEnPassant()
        return canMove
    }

    private fun forwardMoves(position: String, move: String, isTwoMoves: Boolean): Boolean {
        val firstMove = nextMove(position)
        val secondMove = if (isTwoMoves) nextMove(firstMove) else ""
        val isValid = { place: String -> isValidRange(place) && getChar(place) == ' ' }

        if (isValid(firstMove)) {
            if (firstMove == move) {
                enPassant.resetEnPassant()
                return true
            } else if (isValid(secondMove) && secondMove == move) {
                enPassant.updateEnPassant(Pair(firstMove, secondMove))
                return true
            }
        }
        return false
    }

    private fun nextMove(position: String) = position[0] + "" + (position[1] + (if (isPlayer1Turn) 1 else -1))

    private fun isValidRange(position: String) = position.matches(regexMoves)

    private fun getPawn(isPlayer1: Boolean) = if (isPlayer1) 'W' else 'B'

    private fun getChar(position: String) = chessboard[numToInt(position[1])][letterToInt(position[0])]

    private fun numToInt(num: Char) = num.digitToInt() - 1

    private fun letterToInt(letter: Char) = letter.code - (if (letter.isLowerCase()) 97 else 65)

    private inner class EnPassant {
        private var enPassant1 = Pair("", "")
        private var enPassant2 = Pair("", "")
        private val getFirst = { isPassant1: Boolean -> (if (isPassant1) enPassant1 else enPassant2).first }
        var isCaptured = false
            private set

        fun resetEnPassant() = if (getFirst(isPlayer1Turn) != "") updateEnPassant(Pair("", "")) else Unit

        fun updateEnPassant(passant: Pair<String, String>) =
            if (isPlayer1Turn) enPassant1 = passant else enPassant2 = passant

        fun getCapture() = (if (isPlayer1Turn) enPassant2 else enPassant1).second

        fun setIsCaptured(firstMove: String, secondMove: String, move: String) {
            isCaptured = (firstMove == move || secondMove == move) && getFirst(!isPlayer1Turn) == move
        }
    }
}